package com.dodomath.app.wxapi;

import android.content.Context;
import android.util.Log;

import com.dodomath.app.utils.MyLogger;

import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.wechat.friends.Wechat;

public class ThirdPartyLoginHelper {

    public static void loginWithWechat(final Runnable runnableAfterLoginSucessful) {
        Platform weixin = ShareSDK.getPlatform(Wechat.NAME);
        weixin.isClientValid();

        if (weixin.isAuthValid()) {
            weixin.removeAccount(true);
        }

        weixin.setPlatformActionListener(new PlatformActionListener() {
            @Override
            public void onComplete(Platform platform, int i, final HashMap<String, Object> hashMap) {
                if (runnableAfterLoginSucessful != null) {
                    runnableAfterLoginSucessful.run();
                }
            }

            @Override
            public void onError(Platform platform, int i, Throwable throwable) {
                //TODO:
            }

            @Override
            public void onCancel(Platform platform, int i) {
                //TODO:
            }
        });
        weixin.SSOSetting(false);
        weixin.showUser(null);
    }


    public static String tryToGetWechatId() {
        Platform weixin = ShareSDK.getPlatform(Wechat.NAME);
        if (!weixin.isClientValid()) {
            return "";
        }
        return weixin.getDb().getUserId();
    }


    public static void initSDK(Context context) {
        ShareSDK.initSDK(context);
    }

}
