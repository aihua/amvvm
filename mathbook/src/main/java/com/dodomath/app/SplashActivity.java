package com.dodomath.app;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.dodomath.app.model.UserData;
import com.dodomath.app.model.UserType;
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
        //TODO: try to move this to ViewModel.
        switch (UserData.instance.getUserType()) {
            case GUEST:
                getNavigator().navigateToGuestLoginWebPage();
                break;
            case UNKNOWN:
                getNavigator().navigateToLoginPage();
                break;
            case WECHAT:
                break;
            default:
                break;
        }
    }

    private Runnable delayRunnable = new Runnable() {
        @Override
        public void run() {
            gotoNextPage();
        }
    };

}


