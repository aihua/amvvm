/*
 * Copyright 2016 Manas Chaudhari
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.dodomath.app;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.support.annotation.NonNull;
import android.view.View;

import com.dodomath.app.model.UserData;
import com.dodomath.app.utils.HttpRequester;
import com.manaschaudhari.android_mvvm.ViewModel;

import io.reactivex.functions.Action;

public class LoginViewModel extends BaseObservable implements ViewModel {
    @NonNull
    private final Navigator navigator;

    public LoginViewModel(@NonNull Navigator navigator) {
        this.navigator = navigator;
    }

    public final Action onWechatLoginClick = new Action() {
        @Override
        public void run() throws Exception {
            navigator.navigateToWechatLogin();
        }
    };

    public final Action onGuestLoginClick = new Action() {
        @Override
        public void run() throws Exception {
            UserData.instance.loginAsGuest();
            loadData();
        }
    };

    public final Action onStartStudyClick = new Action() {
        @Override
        public void run() throws Exception {
            navigator.navigateToStartStudyWebPage();
        }
    };

    public final Action onPayClick = new Action() {
        @Override
        public void run() throws Exception {
            navigator.navigateToPayWebPage();
        }
    };

    public void navigateToLoginPage() {
        navigator.navigateToLoginPage();
    }

    ;

    private boolean isLoading = false;
    public void loadData() {
        setLoading(true);

        HttpRequester.executeAsync(UserData.getTestApiUrl(), new HttpRequester.Listener() {

            @Override
            public void onGetDataSucceed(byte[] data) {
                setLoading(false);
                UserData.instance.parseDataFromServer(new String(data));
                UserData.gotoAfterLoginPage(navigator);
            }

            @Override
            public void onGetDataFailed(String error) {
                setLoading(false);
            }
        });
    }

    @Bindable
    public boolean isLoading() {
        return isLoading;
    }

    public void setLoading(boolean loading) {
        isLoading = loading;
    }

}
