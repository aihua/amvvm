package com.cloudtech.ads.utils;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.zip.GZIPInputStream;

import static com.cloudtech.ads.utils.HttpRequester.SOCKET_TIMEOUT;

public class HttpRequester {

    private static Handler handler = null;
    public static final int SOCKET_TIMEOUT = 1000 * 45;

    public static Boolean isOpenLog = true;


    public interface Listener {
        public void onGetDataSucceed(byte[] data);

        public void onGetDataFailed(String error);
    }


    public interface DownloadListener {

        void onStart();

        void onFinish(File file);

        void onError(Exception e);
    }


    public static void executeAsync(String url, Listener listener) {
        getAsynData(ContextHolder.getGlobalAppContext(), url, false, listener, RequestMethod.GET, null);
    }


    public static void executeAsyncByPost(String url, String postBody, Listener listener) {
        getAsynData(ContextHolder.getGlobalAppContext(), url, false, listener, RequestMethod.POST, postBody);
    }


    public static void getAsynData(Context context, String url, Boolean isUsecache, Listener listener, RequestMethod method, String postBody) {
        if (handler == null) {
            handler = new Handler(Looper.getMainLooper()) {
                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);
                    handler.post(new ResultRunable(msg));
                }
            };
        }

        HttpRunable runable;
        if (method == RequestMethod.GET) {
            runable = new HttpRunable(url, handler, listener, context, isUsecache);
        } else {
            runable = new HttpRunable(url, handler, listener, context, isUsecache, method, postBody);
        }

        ThreadPoolProxy.getInstance().execute(runable);
    }


    public static byte[] getSyncData(Context context, String urlString, Boolean isUsecache) {
        byte[] data = null;
        final int NET_TIMEOUT = 1000;
        HttpCacher httpCacher = HttpCacher.get(context);
        byte[] cache = httpCacher.getAsBinary(urlString);

        if (cache != null && isUsecache) {
            data = cache;
        } else {
            try {
                URL url = new URL(urlString);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestProperty("Accept-Encoding", "gzip");
                conn.setConnectTimeout(NET_TIMEOUT);
                conn.setReadTimeout(SOCKET_TIMEOUT);
                conn.setUseCaches(false);
                conn.setRequestMethod("GET");
                conn.setDoInput(true);
                InputStream is = null;
                if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {

                    if ("gzip".equals(conn.getContentEncoding())) {
                        is = new GZIPInputStream(conn.getInputStream());
                    } else {
                        is = conn.getInputStream();
                    }
                    BufferedInputStream bis = new BufferedInputStream(is);
                    byte[] b = getBytes(is);
                    bis.close();
                    is.close();
                    conn.disconnect();

                    data = b;
                    if (isUsecache) {
                        httpCacher.put(urlString, b, HttpCacher.TIME_DAY);
                    }
                } else {
                    conn.disconnect();
                }
            } catch (Throwable e) {
                YeLog.d(e.toString());
            }
        }
        return data;
    }


    private static byte[] getBytes(InputStream is) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] b = new byte[1024];
        int len = 0;
        while ((len = is.read(b, 0, 1024)) != -1) {
            baos.write(b, 0, len);
            baos.flush();
        }
        byte[] bytes = baos.toByteArray();
        return bytes;
    }


    public static void download(String url, DownloadListener listener) {

        ThreadPoolProxy.getInstance().execute(new DownloadRunable(url, listener));
        // Thread thread = new Thread(new DownloadRunable(url, listener));
        // thread.start();
    }
}


class DownloadRunable implements Runnable {
    private static final String TAG = "DownloadRunable";

    private String urlStr;
    private HttpRequester.DownloadListener listener = null;


    public DownloadRunable(String url, HttpRequester.DownloadListener listener) {
        this.urlStr = url;
        this.listener = listener;
    }


    @Override
    public void run() {
        InputStream is = null;
        BufferedInputStream bis = null;
        HttpURLConnection conn = null;
        RandomAccessFile accessFile = null;
        try {
            if (listener != null) listener.onStart();
            URL url = new URL(urlStr);
            conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(10000);
            conn.setReadTimeout(SOCKET_TIMEOUT);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                is = conn.getInputStream();
                bis = new BufferedInputStream(is);
                byte[] buffer = new byte[512];
                long len;
                String adVideoPath = FileManager.getAdVideoPath();
                if (TextUtils.isEmpty(adVideoPath)) {
                    if (listener != null) listener.onError(new Exception("cache dir in use"));
                    return;
                }
                adVideoPath = adVideoPath + System.currentTimeMillis() + ".mp4";
                File file = new File(adVideoPath);
                accessFile = new RandomAccessFile(file, "rw");
                while ((len = bis.read(buffer)) != -1) {
                    accessFile.write(buffer, 0, (int) len);
                }
                if (listener != null) listener.onFinish(file);
            }
        } catch (Throwable e) {
            e.printStackTrace();
            if (listener != null) listener.onError(new RuntimeException(e));
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
            try {
                if (accessFile != null) {
                    accessFile.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (bis != null) {
                    bis.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}


class HttpRunable implements Runnable {
    private String urlString = null;
    private Handler handler = null;
    private HttpRequester.Listener listener = null;
    private Context context = null;
    private Boolean isUsecache = true;
    private final int NET_TIMEOUT = 1000 * 15;
    private RequestMethod method = RequestMethod.GET;
    private String requestBody;


    public HttpRunable(String url, Handler handler, HttpRequester.Listener listener, Context context, Boolean isUsecache) {
        this.urlString = url;
        this.handler = handler;
        this.listener = listener;
        this.context = context;
        this.isUsecache = isUsecache;
    }


    public HttpRunable(String url, Handler handler, HttpRequester.Listener listener, Context context, Boolean isUsecache, RequestMethod method, String body) {
        this.urlString = url;
        this.handler = handler;
        this.listener = listener;
        this.context = context;
        this.isUsecache = isUsecache;
        this.method = method;
        this.requestBody = body;
    }


    @Override
    public void run() {
        Message msg = new Message();
        HashMap<String, Object> data = new HashMap<String, Object>();
        data.put("callback", listener);

        HttpCacher httpCacher = HttpCacher.get(context);
        byte[] cache = httpCacher.getAsBinary(urlString);

        if (cache != null && isUsecache) {
            msg.what = 0;
            data.put("data", cache);
            msg.obj = data;
        } else {
            try {
                URL url = new URL(urlString);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setConnectTimeout(NET_TIMEOUT);
                conn.setReadTimeout(SOCKET_TIMEOUT);

                conn.setRequestProperty("Accept-Encoding", "gzip");
                conn.setUseCaches(false);
                conn.setRequestMethod(method.toString());
                conn.setDoInput(true);
                if (method == RequestMethod.POST) {
                    conn.setDoOutput(true);
                    conn.setRequestProperty("Content-type", "application/json");
                    OutputStream os = conn.getOutputStream();
                    ByteArrayInputStream stream = new ByteArrayInputStream(requestBody.getBytes());
                    int len;
                    byte[] buffer = new byte[4096];
                    while ((len = stream.read(buffer)) != -1) {
                        os.write(buffer, 0, len);
                    }
                    stream.close();
                    os.flush();
                    os.close();
                }
                InputStream is = null;
                if (conn.getResponseCode() == HttpURLConnection.HTTP_OK || conn.getResponseCode() == HttpURLConnection.HTTP_NO_CONTENT) {
                    if ("gzip".equals(conn.getContentEncoding())) {
                        is = new GZIPInputStream(conn.getInputStream());
                    } else {
                        is = conn.getInputStream();
                    }
                    BufferedInputStream bis = new BufferedInputStream(is);
                    byte[] b = getBytes(is);
                    bis.close();
                    is.close();
                    conn.disconnect();

                    msg.what = 0;
                    data.put("data", b);
                    msg.obj = data;
                    if (isUsecache) {
                        httpCacher.put(urlString, b, HttpCacher.TIME_DAY);
                    }
                } else {
                    msg.what = 1;
                    data.put("error", String.valueOf(conn.getResponseCode()));
                    msg.obj = data;
                    conn.disconnect();
                }
            } catch (Throwable e) {
                msg.what = 2;
                data.put("error", e.getMessage());
                msg.obj = data;
            }
        }
        handler.dispatchMessage(msg);
    }


    private byte[] getBytes(InputStream is) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] b = new byte[1024];
        int len = 0;
        while ((len = is.read(b, 0, 1024)) != -1) {
            baos.write(b, 0, len);
            baos.flush();
        }
        byte[] bytes = baos.toByteArray();
        return bytes;
    }
}


class ResultRunable implements Runnable {
    private Message msg = null;
    private static final String UNKOWN_ERROR = "unkown error";


    public ResultRunable(Message msg) {
        this.msg = msg;
    }


    @Override
    public void run() {
        try {
            @SuppressWarnings("unchecked")
            HashMap<String, Object> data = (HashMap<String, Object>) msg.obj;
            HttpRequester.Listener listener = (HttpRequester.Listener) data.get("callback");
            if (msg.what == 0) {
                listener.onGetDataSucceed((byte[]) data.get("data"));
            } else if (msg.what == 1) {
                listener.onGetDataFailed((String) data.get("error"));
            } else if (msg.what == 2) {
                listener.onGetDataFailed((String) data.get("error"));
            } else {
                listener.onGetDataFailed(UNKOWN_ERROR);
            }
        } catch (Throwable e) {
            @SuppressWarnings("unchecked")
            HashMap<String, Object> data = (HashMap<String, Object>) msg.obj;
            HttpRequester.Listener listener = (HttpRequester.Listener) data.get("callback");
            if (listener != null) {
                listener.onGetDataFailed("HttpRequester get data error:" + e.getMessage());
            }
        }
    }
}
