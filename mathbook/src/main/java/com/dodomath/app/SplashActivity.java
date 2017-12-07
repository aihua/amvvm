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

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.webkit.WebView;

import com.manaschaudhari.android_mvvm.ViewModel;

public class SplashActivity extends BaseActivity {

    @NonNull
    @Override
    protected ViewModel createViewModel() {
        return new MainViewModel(getNavigator());
    }

    @Override
    protected int getLayoutId() {
        //return R.layout.activity_splash;
        return R.layout.page_login;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        WebView wv = (WebView)this.findViewById(R.id.main_webview);
//        wv.loadUrl("file:///android_asset/test_mathbookjs.html");
    }
}


