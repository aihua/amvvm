package com.dodomath.app.utils;


import android.content.Context;
import android.provider.Settings;

public class AndroidUtils {
    public static void exitApp() {
        //TODO: find a good way.
        System.exit(0);
    }

    public static void h5PredownloadFinished() {
        //TODO:
    }

    public static void h5PredownloadError(String err_msg) {
        //TODO:
    }

    public static void h5PredownloadProgress(int percent, int resourceType) {
        //TODO:
    }

    /**
     * 获取Android Id
     */
    public static String getAndroidId(Context context) {
        String androidId = "";
        try {
            androidId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
            //YeLog.d(String.format("[msg=get AndroidId][result=success][androidId=%s]", androidId));
        } catch (Exception e) {
            //TODO:
        }
        return androidId;
    }

}
