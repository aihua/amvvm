package com.dodomath.app;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.VideoView;

import com.dodomath.app.model.UserData;
import com.dodomath.app.model.UserStatus;
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

        VideoView videoView = (VideoView)findViewById(R.id.video_view);
        String path = "android.resource://" + getPackageName() + "/" + R.raw.splash;
        videoView.setVideoURI(Uri.parse(path));

        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                UserData.gotoAfterLoginPage(getNavigator());
            }
        });
        videoView.start();
        //TODO: Use RxJava's pub/sub logic.
        //findViewById(R.id.page_bg).postDelayed(delayRunnable, 3000);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

}


