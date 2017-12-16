package com.dodomath.app;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;

import com.dodomath.app.model.UserData;
import com.dodomath.app.model.UserStatus;
import com.manaschaudhari.android_mvvm.ViewModel;

public class DodoHomeActivity extends BaseActivity {

    @NonNull
    @Override
    protected ViewModel createViewModel() {
        return new LoginViewModel(getNavigator());
    }

    @Override
    protected int getLayoutId() {
        return R.layout.page_onebutton;
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
    protected void onResume() {
        super.onResume();
        if (UserData.instance.getUserStatus() == UserStatus.EVALUATED) {
            findViewById(R.id.iv_start_study).setVisibility(View.VISIBLE);
        }

        if (UserData.instance.getUserStatus() == UserStatus.EXPIRED) {
            findViewById(R.id.iv_pay).setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

}


