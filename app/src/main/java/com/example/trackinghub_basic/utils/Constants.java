package com.example.trackinghub_basic.utils;

import com.example.trackinghub_basic.Model.Vehical_List_Model;

import java.util.ArrayList;

public class Constants {
    public static String KEY_USER_NAME = "user_name";
    public static String KEY_NAME = "name";
    public static String KEY_PASSWORD = "password";
    public static String KEY_MESSAGE = "address";
    public static final String BaseURL = "http://5.189.130.200/api/v1/";

    public static ArrayList<Vehical_List_Model> assets;

    public static ArrayList<Vehical_List_Model> getAssets() {
        return assets;
    }
}
