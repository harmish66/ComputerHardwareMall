package com.example.computerhardwaremall.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SesionManager {

    private static final String PREFER_NAME = "mall_app";
    private static final String IS_LOGGEDIN = "is_loggedin";

    private static final int PRIVATE_MODE = 0;
    private static SharedPreferences mSharedPreferences;
    private static SharedPreferences.Editor mSharedPreferencesEditor;

    private static void getPreferences(Context context) {
        mSharedPreferences = context.getSharedPreferences(PREFER_NAME, PRIVATE_MODE);
        mSharedPreferencesEditor = mSharedPreferences.edit();
    }

    public static void clear() {
        mSharedPreferencesEditor.clear();
        mSharedPreferencesEditor.commit();
    }

    public static void setIsLoggedin(Context context, String userId) {
        getPreferences(context);
        mSharedPreferencesEditor.putString(IS_LOGGEDIN, userId);
        mSharedPreferencesEditor.commit();
    }

    public static String getIsLoggedin(Context context) {
        getPreferences(context);
        return mSharedPreferences.getString(IS_LOGGEDIN, "");
    }
}
