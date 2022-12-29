package com.example.infosecuritysysapp.config;

import android.util.Log;
import android.util.Patterns;

import com.example.infosecuritysysapp.R;
import com.google.gson.Gson;

import java.util.regex.Pattern;

public class AppConstants {




    public final static Pattern EN_NAME_REGEX = Pattern.compile("[a-zA-Z]([a-zA-Z]+| )*");
    public final static Pattern AR_NAME_REGEX = Pattern.compile("[a-zA-Z]([a-zA-Z]+| )*"); // Pattern.compile("\\p{InArabic}");

    public final static Pattern PHONE_NUMBER_REGEX = Pattern.compile("09\\d{8}");
    public final static Pattern EMAIL_REGEX = Patterns.EMAIL_ADDRESS;


    // shared preferences keys

    public static final String USER_ID = "user_id";
    public static final String ACC_TOKEN ="acc_token";
    public static final String REMEMBER_ME = "remember_me";
    public static final String LAN = "lan";

    // const function

    public static String getErrorMessage(String errorAsString) {
//        try {
//            Log.d("content", "getErrorMessage: " + errorAsString);
//            Gson gson = new Gson();
//            ErrorModel errorModel = gson.fromJson(errorAsString, ErrorModel.class);
//            return errorModel.getMessage();
//        } catch (Exception e) {
//            return "Failed while reading the error message";
//        }
        return "UnComment the function body";

    }




}
