package com.dodomath.app.model;

import android.text.TextUtils;

import com.dodomath.app.MathBookApplication;
import com.dodomath.app.Navigator;
import com.dodomath.app.utils.AndroidUtils;
import com.dodomath.app.utils.PrefUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Random;

public class UserData {

    public static UserData instance = new UserData();

    public static String KEY_USER_ID = "KEY_USER_ID";
    public static String KEY_USER_TYPE = "KEY_USER_TYPE";
    public static String KEY_USER_SERVER_RESPONSE = "KEY_USER_SERVER_RESPONSE";

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


    public UserType getUserType() {
        return userType;
    }

    public String getUserId() {
        return TextUtils.isEmpty(userId) ? AndroidUtils.getAndroidId(MathBookApplication.globalAppContext) : userId;
    }

    public String getUserTypeAsString() {
        return String.valueOf(userType.ordinal());
    }

    public String getGuestEvaluateUrl() {
        return HOST_NAME + urlForStatus;//"file:///android_asset/mb_guest_evaluate_index.html?user_type=" + getUserTypeAsString() + "&user_id=" + getUserId();
    }

    public String getStartStudyUrl() {
        return HOST_NAME + urlForStatus;//"file:///android_asset/mb_start_study_index.html?user_type=" + getUserTypeAsString() + "&user_id=" + getUserId();
    }

    public String getPayUrl() {
        return HOST_NAME + urlForStatus;//"file:///android_asset/mb_pay_index.html?user_type=" + getUserTypeAsString() + "&user_id=" + getUserId();
    }

    public void loginAsGuest() {
        login(UserType.GUEST, AndroidUtils.getAndroidId(MathBookApplication.globalAppContext));
    }

    public void loginAsWechat(String wechatOpenid) {
        login(UserType.WECHAT, wechatOpenid);
    }

    private void login(UserType userType, String userId) {
        this.userType = userType;
        this.userId = userId;
        saveData();
    }


    //Read from server
    //private UserStatus userStatus = UserStatus.UNKNOWN;
    private UserStatus userStatus = UserStatus.values()[new Random().nextInt(3) + 1];

    private String urlForStatus = "";
    private String messageForStatus = "";


    public void parseDataFromServer(String s) {
        //{"url": "/curriculum/evaluation/create/", "user_status": 3}
        try {
            JSONObject jsonObject = new JSONObject(s);
            urlForStatus = jsonObject.getString("url");
            messageForStatus = jsonObject.optString("msg");
            userStatus = UserStatus.values()[Integer.valueOf(jsonObject.getString("user_status"))];
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public UserStatus getUserStatus() {
        return userStatus;
    }

    public static final String HOST_NAME = "http://180.76.177.111:8004";

    //API3 的范例：
    public static String getTestApiUrl() {
        return HOST_NAME + "/api/3/385/";
    }

    public static void gotoAfterLoginPage(Navigator navigator) {
        //TODO: try to move this to ViewModel.
        UserType userType = UserData.instance.getUserType();
        switch (userType) {
            case UNKNOWN:
                navigator.navigateToLoginPage();
                break;
            case GUEST:
                if (UserData.instance.getUserStatus() == UserStatus.UNEVALUATE) {
                    navigator.navigateToGuestLoginWebPage();
                } else {
                    navigator.navigateToDodoNativeHome();
                }
                break;
            case WECHAT:
                if (UserData.instance.getUserStatus() == UserStatus.UNEVALUATE) {
                    navigator.navigateToGuestLoginWebPage();
                } else {
                    navigator.navigateToDodoNativeHome();
                }
                break;
        }
    }

}
