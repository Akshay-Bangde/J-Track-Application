package com.example.trackinghub_basic.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class PreferenceUtils {
    public PreferenceUtils() {

    }

    public static boolean saveUserName(String user_name, Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor preEditor = sharedPreferences.edit();
        preEditor.putString(Constants.KEY_USER_NAME, user_name);
        preEditor.apply();
        return true;
    }

    public static String getUserName(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(Constants.KEY_USER_NAME, null);
    }

    public static boolean saveName(String name, Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor preEditor = sharedPreferences.edit();
        preEditor.putString(Constants.KEY_NAME, name);
        preEditor.apply();
        return true;
    }

    public static String getName(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(Constants.KEY_NAME, null);
    }


    public static boolean savePassword(String password, Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor preEditor = sharedPreferences.edit();
        preEditor.putString(Constants.KEY_PASSWORD, password);
        preEditor.apply();
        return true;
    }

    public static String getPassword(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(Constants.KEY_PASSWORD, null);
    }
    public static boolean saveMessage(String message, Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor preEditor = sharedPreferences.edit();
        preEditor.putString(Constants.KEY_MESSAGE, message);
        preEditor.apply();
        return true;
    }

    public static String getMessage(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(Constants.KEY_MESSAGE, null);
    }

}
