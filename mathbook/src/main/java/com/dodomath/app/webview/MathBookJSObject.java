package com.dodomath.app.webview;

import android.webkit.JavascriptInterface;

import com.dodomath.app.utils.AndroidUtils;
import com.dodomath.app.utils.MyLogger;

import io.reactivex.annotations.Nullable;

public class MathBookJSObject {

    public MathBookJSObject() {

    }

    /**
     * 查看设备的OS类型
     *
     * @return
     * Android平台返回 -- 0
     * iOS平台返回 -- 1
     */
    @JavascriptInterface
    public int get_platform_type() {
        MyLogger.d(this.getClass().getSimpleName(), "get_platform_type has been called");
        return 0;
    }

    /**
     * 调用原生的微信登录
     * @param loginSuccessfulCb: 登录成功后，跳转到URL或者执行JS，为空则为刷新当前页面。
     * @param loingFailedCb: 登录失败后，跳转到URL或者执行JS，为空则为刷新当前页面。
     *
     *
     */
    @JavascriptInterface
    public void open_wechat_login(@Nullable String loginSuccessfulCb, @Nullable String loingFailedCb) {
        MyLogger.d(this.getClass().getSimpleName(), "coming soon......");
        return;
    }

    @JavascriptInterface
    public void open_wechat_login() {
        MyLogger.d(this.getClass().getSimpleName(), "coming soon......");
        return;
    }

    /**
     * 是否已经微信登录
     */
    @JavascriptInterface
    public boolean is_wechat_logined() {
        return false;
    }

    /**
     * 查询当前登录用户的微信 User ID
     *
     * @return
     * 已经登录，则返回用户的微信的 User ID
     * 没有登录，则返回空字符串
     */
    @JavascriptInterface
    public String get_wechat_id() {
        return "";
    }

    /**
     * 退出微信登录
     *
     * @return
     */
    @JavascriptInterface
    public void exit_wechat_login() {
        return;
    }

    /**
     * @param payInfo: 使用JSON结构，付款相关信息，包括：产品数量、产品ID、产品名字、产品单价、付款金额、接受账号等。
     * TODO：数据结构待定
     *
     * @return
     */
    @JavascriptInterface
    public void open_wechat_pay(String payInfo) {
        return;
    }

    /**
     * 关闭屏幕
     */
    @JavascriptInterface
    public void close_screen() {
        return;
    }

    /**
     * 退出APP
     */
    @JavascriptInterface
    public void exit_app() {
        AndroidUtils.exitApp();
        return;
    }

    /**
     * H5页面通知APP资源全部下载完成
     */
    @JavascriptInterface
    public void h5_predownload_finished() {
        AndroidUtils.h5PredownloadFinished();
        return;
    }


    /**
     * H5页面通知APP当前资源的下载进度
     *
     * @param percent 资源的下载进度  100 表示全部下载完成。
     * @param resourceType 资源的类型  0--全部资源  1--图片 2--音频 3--视频
     *
     */
    @JavascriptInterface
    public void h5_predownload_progress(int percent, int resourceType) {
        AndroidUtils.h5PredownloadProgress(percent, resourceType);
        return;
    }

    /**
     * H5页面通知APP当前资源的下载进度
     */
    @JavascriptInterface
    public void h5_predownload_error(String err_msg) {
        AndroidUtils.h5PredownloadError(err_msg);
        return;
    }

}