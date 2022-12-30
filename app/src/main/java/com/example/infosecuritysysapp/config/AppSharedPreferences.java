package com.example.infosecuritysysapp.config;


import static com.example.infosecuritysysapp.network.api.ApiClient.BASE_URL;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class AppSharedPreferences {

    public static SharedPreferences sp;
    public static SharedPreferences.Editor spEdit;


    @SuppressLint({"applyPrefEdits", "CommitPrefEdits"})
    public static void InitSharedPreferences(Context context) {
        if (sp == null) {
            sp = PreferenceManager.getDefaultSharedPreferences(context);
            spEdit = sp.edit();
        }
    }

    public static void CLEAR_DATA() {
        spEdit.clear().apply();
    }

    public static void CACHE_BASE_URL(String baseUrl) {
        spEdit.putString("BASE_URL", baseUrl).apply();
    }

    public static void CACHE_USER_PHONE_NUMBER(String phoneNumber){
        spEdit.putString("userPhoneNumber", phoneNumber).apply();
    }

    public static String GET_USER_PHONE(){
        return sp.getString("userPhoneNumber", null);
    }

    public static void CACHE_USER_ID(int userId) {
        spEdit.putInt("USER_ID", userId).apply();
    }

    public static String GET_BASE_URL() {
        return sp.getString("BASE_URL", BASE_URL);
    }

    public static int GET_USER_ID() {
        return sp.getInt("USER_ID", 0);
    }


}
