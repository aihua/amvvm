package com.dodomath.app;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.manaschaudhari.android_mvvm.ViewModel;

public class SplashActivity extends BaseActivity {

    @NonNull
    @Override
    protected ViewModel createViewModel() {
        return new LoginViewModel(getNavigator());
    }

    @Override
    protected int getLayoutId() {
        return R.layout.page_splash;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();

        //TODO: Use RxJava's pub/sub logic.
        findViewById(R.id.page_bg).postDelayed(delayRunnable, 3000);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    private void gotoNextPage() {
        getNavigator().navigateToLoginPage();
    }

    private Runnable delayRunnable = new Runnable() {
        @Override
        public void run() {
            gotoNextPage();
        }
    };

}


