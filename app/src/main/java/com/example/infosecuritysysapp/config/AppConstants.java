package com.example.infosecuritysysapp.config;

import android.util.Log;
import android.util.Patterns;

import com.example.infosecuritysysapp.R;
import com.google.gson.Gson;

import java.security.PublicKey;
import java.util.regex.Pattern;

import javax.crypto.SecretKey;

public class AppConstants {

    public final static String CHAT_SEND = "SEND";
    public final static String CHAT_SEND_E = "SEND_E";
    public final static String CHAT_SEND_D = "SEND_D";

    public final static String REG_IP = "REGIP";
    public final static String CHAT_RECEIVED = "CHAT_REC";
    public final static String CHAT_RECEIVED_E = "CHAT_REC_E";
    public final static String STORING = "STORING";


    public final static Pattern EN_NAME_REGEX = Pattern.compile("[a-zA-Z]([a-zA-Z]+| )*");
    public final static Pattern AR_NAME_REGEX = Pattern.compile("[a-zA-Z]([a-zA-Z]+| )*"); // Pattern.compile("\\p{InArabic}");

    public final static Pattern PHONE_NUMBER_REGEX = Pattern.compile("09\\d{8}");
    public final static Pattern EMAIL_REGEX = Patterns.EMAIL_ADDRESS;

    public static PublicKey serverPublicKey;
    public static SecretKey sessionKey;
    public static SecretKey eyadKey;


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

    //  place keys
    public static final String PLACE_DB = "place_database";
    public static final String PLACE_TABLE = "place_table";
    public static final String PLACE_DETAILS_TABLE = "place_details_table";
    public static final String PLACE_DATA = "place_data";
    public static final String PLACE_PHOTO = "place_photo";


    // profile keys
    public static final String PROFILE_DB = "profile_database";
    public static final String PROFILE_TABLE = "profile_table";
    public static final String PROFILE_DATA = "profile_data";
    public static final String PROFILE_USER = "profile_user";


    // notification keys
    public static final String NOTIFICATION_DB = "notification_database";
    public static final String NOTIFICATION_TABLE = "notification_table";
    public static final String NOTIFICATION_DATA = "notification_data";


    // passengers keys
    public static final String PASSENGERS_DB = "passengers_database";
    public static final String PASSENGERS_TABLE = "passengers_table";
    public static final String PASSENGERS_DATA = "passengers_data";
    public static final String PASSENGERS_BOOKED_FOR = "passengers_booked_for";


    // poll keys
    public static final String POLL_DB = "poll_database";
    public static final String POLL_PAGE_TABLE = "poll_page_table";
    public static final String POLL_PAGE_DATA = "poll_page_data";
    public static final String POLL_DATA = "poll_data";
    public static final String POLL_CHOICE = "poll_choice";


    // road map keys
    public static final String ROAD_MAP_DB = "road_map_database";
    public static final String ROAD_MAP_TABLE = "road_map_table";
    public static final String ROAD_MAP_DATA = "road_map_data";

    // trip keys
    public static final String TRIP_DB = "trip_database";
    public static final String TRIP_PHOTO_DATA = "trip_photo_data";
    public static final String TRIP_PHOTO_TABLE = "trip_photo_table";
    public static final String CUSTOMER_TRIP_DATA = "customer_trip_data";
    public static final String PLACE_TRIP_DATA = "place_trip_data";
    public static final String TRIP_DATA = "trip_data";
    public static final String TRIP_DETAILS_TABLE = "trip_details_table";
    public static final String TRIP_TABLE = "trip_table";
    public static final String TRIP_TYPE_DATA = "trip_type_data";
    public static final String TRIP_TYPE_TABLE = "trip_type_table";
    public static final String TRIP_PAGE_DATA = "trip_pagination_data";


}
