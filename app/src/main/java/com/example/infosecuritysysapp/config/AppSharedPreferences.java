package com.example.infosecuritysysapp.config;


import static com.example.infosecuritysysapp.network.api.ApiClient.BASE_URL;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

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

    public static void CACHE_USER_ID(int userId) {
        spEdit.putInt("USER_ID", userId).apply();
    }

    public static void CACHE_USER_PHONE_NUMBER(String phoneNumber) {
        spEdit.putString("USER_PHONE_NUMBER", phoneNumber).apply();
    }

    public static void CACHE_IS_LOGIN() {
        spEdit.putBoolean("ISLOGIN", true);
    }

    public static boolean GET_IS_LOGIN() {
        return sp.getBoolean("ISLOGIN", false);
    }

    public static String GET_USER_PHONE_NUMBER() {
        return sp.getString("USER_PHONE_NUMBER", null);
    }

    public static String GET_BASE_URL() {
        return sp.getString("BASE_URL", BASE_URL);
    }

    public static int GET_USER_ID() {
        return sp.getInt("USER_ID", 0);
    }

    public static void CACHE_USER_PRIVATE_KEY(String privateKey){
        spEdit.putString("userPrivateKey", privateKey).apply();
    }

    public static String GET_USER_PRIVATE_KEY(){
        return sp.getString("userPrivateKey", null);
    }

    public static void CACHE_USER_PUBLIC_KEY(String publicKey){
        spEdit.putString("userPublicKey", publicKey).apply();
    }

    public static String GET_USER_PUBLIC_KEY(){
        return sp.getString("userPublicKey", null);
    }


    public static void CACHE_USER_SESSION_KEY(String sessionKey){
        spEdit.putString("sessionKey", sessionKey).apply();
    }

    public static String GET_USER_SESSION_KEY(){
        return sp.getString("sessionKey", null);
    }


    public static void CACHE_USER_SERVER_KEY(String sessionKey){
        spEdit.putString("serverKey", sessionKey).apply();
    }

    public static String GET_USER_SERVER_KEY(){
        return sp.getString("serverKey", null);
    }

    public static void CACHE_SESSION_KEY(String sessionKey){
        Log.d("SharedPreferences", "cache session key: " + sessionKey);
        spEdit.putString("sessionKey", sessionKey).apply();
    }

    public static String GET_SESSION_KEY(){
        Log.d("SharedPreferences", "get session key: " + sp.getString("sessionKey", null));
        return sp.getString("sessionKey", null);
    }
}
