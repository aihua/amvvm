package com.dodomath.app.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.dodomath.app.MathBookApplication;

import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static android.content.Context.MODE_PRIVATE;

public class PrefUtil {
    private static final String PREF_DEFAULT_FILE_NAME = "dodomath";


    //默认存储字符串
    public static void persistString(String key, String value) {
        persistString(MathBookApplication.globalAppContext, PREF_DEFAULT_FILE_NAME, key, value);
    }

    public static void persistString(Context context, String namespace, String key, String value) {
        try {
            SharedPreferences sp = context.getSharedPreferences(namespace, MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            editor.putString(key, value);
            editor.apply();
        } catch (Exception e) {
            //TODO:
        }
    }


    public static String getString(String key) {
        return getString(key,"");
    }

    public static String getString(String key, String defVal) {
        return getString(MathBookApplication.globalAppContext, PREF_DEFAULT_FILE_NAME, key, defVal);
    }

    public static String getString(Context context, String namespace, String key, String defVal) {
        try {
            SharedPreferences sp = context.getSharedPreferences(namespace, MODE_PRIVATE);
            return sp.getString(key, defVal);
        } catch (Exception e) {
            //TODO:
        }
        return defVal;
    }

    //得到sp对象
    public static SharedPreferences getSharedPreferences(Context context, String namespace) {
        return context.getSharedPreferences(namespace, 0);
    }

    //判断某个key是否存在
    public static boolean isExsit(Context context, String namespace, String key) {
        try {
            SharedPreferences sp = context.getSharedPreferences(namespace, MODE_PRIVATE);
            return sp.contains(key);
        } catch (Exception e) {
            return false;
        }
    }

    //删除
    public static void removeKey(Context context, String namespace, String key){
        try {
            SharedPreferences sp = context.getSharedPreferences(namespace, MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            editor.remove(key);
            editor.apply();
        } catch (Exception e) {
            //TODO:
        }

    }

    public static void removeAll(Context context, String namespace) {
        try {
            SharedPreferences sp = context.getSharedPreferences(namespace, MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            editor.clear();
            editor.apply();
        } catch (Exception e) {
            //TODO:
        }
    }
}
