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

import android.content.Intent;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.dodomath.app.model.UserData;
import com.dodomath.app.wxapi.ThirdPartyLoginHelper;
import com.dodomath.app.utils.AndroidUtils;
import com.manaschaudhari.android_mvvm.MvvmActivity;
import com.dodomath.app.adapters.ItemListActivity;
import com.dodomath.app.adapters.MessageHelper;
import com.dodomath.app.calculator_example.CalculatorActivity;
import com.dodomath.app.functional.DataLoadingActivity;
import com.dodomath.app.two_way_binding.SearchActivity;

public abstract class BaseActivity extends MvvmActivity {

    @NonNull
    protected Navigator getNavigator() {
        return new Navigator() {
            @Override
            public void openDetailsPage(Item item) {
                navigate(ItemDetailsActivity.class);
            }

            @Override
            public void navigateToFunctionalDemo() {
                navigate(DataLoadingActivity.class);
            }

            @Override
            public void navigateToAdapterDemo() {
                navigate(ItemListActivity.class);
            }

            @Override
            public void navigateToTwoWayBindingDemo() {
                navigate(SearchActivity.class);
            }

            @Override
            public void navigateToCalculatorDemo() {
                navigate(CalculatorActivity.class);
            }

            @Override
            public void navigateToWechatLogin() {
                new ThirdPartyLoginHelper().loginWithWechat(new Runnable(){
                    @Override
                    public void run() {
                        navigateToWechatLoginWebPage();
                    }
                });
            }

            @Override
            public void navigateToGuestLoginWebPage() {
                navigateToWeb(UserData.instance.getGuestEvaluateUrl());
            }

            @Override
            public void navigateToWechatLoginWebPage() {
                navigateToWeb("file:///android_asset/mb_index.html?login_type=wechat&wechat_id=" + ThirdPartyLoginHelper.tryToGetWechatId());
            }

            @Override
            public void exitApp() {
                //TODO:
                AndroidUtils.exitApp();
            }

            @Override
            public void navigateToLoginPage() {
                navigate(LoginActivity.class);
            }

            private void navigateToWeb(String url) {
                Intent intent = new Intent(BaseActivity.this, HomeWebPageActivity.class);
                intent.putExtra(HomeWebPageActivity.KEY_URL, url);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }

            private void navigate(Class<?> destination) {
                Intent intent = new Intent(BaseActivity.this, destination);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }

        };
    }

    @NonNull
    protected MessageHelper getMessageHelper() {
        return new MessageHelper() {
            @Override
            public void show(String message) {
                Toast.makeText(BaseActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        };
    }

}
