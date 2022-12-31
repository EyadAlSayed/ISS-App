package com.example.infosecuritysysapp.config;

import android.util.Log;
import android.util.Patterns;

import com.example.infosecuritysysapp.R;
import com.google.gson.Gson;

import java.util.regex.Pattern;

public class AppConstants {

    public final static String CHAT_SEND = "SEND";
    public final static String CHAT_SEND_E = "SEND_E";
    public final static String REG_IP = "REGIP";
    public final static String CHAT_RECEIVED = "CHAT_REC";
    public final static String CHAT_RECEIVED_E = "CHAT_REC_E";


    public final static Pattern EN_NAME_REGEX = Pattern.compile("[a-zA-Z]([a-zA-Z]+| )*");
    public final static Pattern AR_NAME_REGEX = Pattern.compile("[a-zA-Z]([a-zA-Z]+| )*"); // Pattern.compile("\\p{InArabic}");

    public final static Pattern PHONE_NUMBER_REGEX = Pattern.compile("09\\d{8}");
    public final static Pattern EMAIL_REGEX = Patterns.EMAIL_ADDRESS;
}
