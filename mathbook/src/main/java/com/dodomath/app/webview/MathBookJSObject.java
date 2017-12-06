package com.dodomath.app.webview;

import android.webkit.JavascriptInterface;

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
        return;
    }

}