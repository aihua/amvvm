package com.dodomath.app.model;

import android.text.TextUtils;

import com.dodomath.app.MathBookApplication;
import com.dodomath.app.utils.AndroidUtils;
import com.dodomath.app.utils.PrefUtil;

public class UserData {

    public static UserData instance = new UserData();

    public static String KEY_USER_ID = "KEY_USER_ID";
    public static String KEY_USER_TYPE = "KEY_USER_TYPE";

    public void saveData() {
        PrefUtil.persistString(KEY_USER_ID, userId);
        PrefUtil.persistString(KEY_USER_TYPE, String.valueOf(userType.ordinal()));
    }

    public void loadData() {
        userId = PrefUtil.getString(KEY_USER_ID);
        userType = UserType.values()[Integer.valueOf(PrefUtil.getString(KEY_USER_TYPE, "0"))];
    }

    private String userId = "";
    private UserType userType = UserType.UNKNOWN;

    //Read from server
    private UserStatus userStatus = UserStatus.UNKNOWN;
    private String urlForStatus = "";
    private String messageForStatus = "";

    public UserType getUserType() {
        return userType;
    }

    public String getUserId() {
        return TextUtils.isEmpty(userId) ? AndroidUtils.getAndroidId(MathBookApplication.globalAppContext) : userId;
    }

    public String getGuestEvaluateUrl() {
        return "file:///android_asset/mb_guest_evaluate_index.html?user_type=guest&user_id=" + getUserId();
    }

    public void loginAsGuest() {
        userId = AndroidUtils.getAndroidId(MathBookApplication.globalAppContext);
        userType = UserType.GUEST;
        saveData();
    }
}
