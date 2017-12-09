package com.dodomath.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebView;

import com.manaschaudhari.android_mvvm.ViewModel;

public class HomeWebPageActivity extends BaseActivity {

    public static final String KEY_URL = "URL";

    @NonNull
    @Override
    protected ViewModel createViewModel() {
        return new HomeWebPageViewModel(getNavigator());
    }

    @Override
    protected int getLayoutId() {
        //return R.layout.activity_splash;
        return R.layout.page_home_web_page;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    protected void onResume() {
        super.onResume();
        loadWebPage();

    }

    private void loadWebPage() {
        Intent intent = getIntent();

        WebView wv = (WebView)this.findViewById(R.id.main_webview);
        wv.loadUrl(intent.getStringExtra(KEY_URL));

    }

    @Override
    protected void onPause() {
        super.onPause();
    }

}


