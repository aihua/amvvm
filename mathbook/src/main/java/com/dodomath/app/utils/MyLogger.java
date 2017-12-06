package com.dodomath.app.utils;

import android.widget.Toast;

import com.dodomath.app.ExampleApplication;

public class MyLogger {

    public static void d(String tag, String msg) {
        Toast.makeText(ExampleApplication.globalAppContext, msg, Toast.LENGTH_LONG).show();
    }
}
