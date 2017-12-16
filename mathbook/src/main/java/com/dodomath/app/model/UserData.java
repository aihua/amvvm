package com.dodomath.app.model;

public class UserData {

    public static UserData instance = new UserData();

    public void saveData() {

    }

    public void loadData() {

    }

    private String userId = "";
    private UserType userType = UserType.UNKNOWN;

    //Read from server
    private UserStatus userStatus = UserStatus.UNKNOWN;
    private String urlForStatus = "";
    private String messageForStatus = "";

}
