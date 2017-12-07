package com.dodomath.app.utils;

import android.widget.Toast;

import com.dodomath.app.MathBookApplication;

public class MyLogger {

    public static void d(String tag, String msg) {
        Toast.makeText(MathBookApplication.globalAppContext, msg, Toast.LENGTH_LONG).show();
    }
}
