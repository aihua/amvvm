package com.dodomath.app.webview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.webkit.WebView;

public class MathBookWebView extends WebView {

    public MathBookWebView(Context context) {
        super(context);
        init();
    }

    protected void init() {
        setupWebView();
    }

//    private void loadH5(RequestHolder params) {
//        loadDataWithBaseURL("", params.getH5String(), "text/html", "utf-8", "");
//    }

    @SuppressLint({"SetJavaScriptEnabled", "AddJavascriptInterface"})
    private void setupWebView() {
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);

        setBackgroundColor(Color.TRANSPARENT);
        getSettings().setJavaScriptEnabled(true);
        MathBookJSObject jsobject = new MathBookJSObject();
        addJavascriptInterface(jsobject, "mbjs");
        // api 11以上有个漏洞，要remove
        if (Build.VERSION.SDK_INT >= 11) {
            removeJavascriptInterface("searchBoxJavaBredge_");
        }
        MathBookWebViewClient ctWebViewClient = new MathBookWebViewClient();
        this.setWebViewClient(ctWebViewClient);
    }

}
