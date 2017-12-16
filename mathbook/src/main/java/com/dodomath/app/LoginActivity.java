package com.dodomath.app;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebView;

import com.manaschaudhari.android_mvvm.ViewModel;

public class LoginActivity extends BaseActivity {

    @NonNull
    @Override
    protected ViewModel createViewModel() {
        return new LoginViewModel(getNavigator());
    }

    @Override
    protected int getLayoutId() {
        return R.layout.page_login;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //TODO:
        //startAnimation();
//        WebView wv = (WebView)this.findViewById(R.id.main_webview);
//        wv.loadUrl("file:///android_asset/test_mathbookjs.html");
    }


    @Override
    protected void onPause() {
        super.onPause();
    }

}


