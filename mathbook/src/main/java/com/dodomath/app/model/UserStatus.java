package com.dodomath.app.model;

public enum UserStatus {
    // 1:  已评测，未过期，跳转学习页面； 2： 已评测，已过期，跳转登录页； 3： 未评测，跳转评测页
    UNKNOWN,
    EVALUATED,
    EXPIRED,
    UNEVALUATE;
}
