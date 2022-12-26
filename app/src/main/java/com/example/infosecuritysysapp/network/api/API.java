package com.example.infosecuritysysapp.network.api;


import com.example.infosecuritysysapp.model.PersonMessageModel;
import com.example.infosecuritysysapp.model.PersonModel;
import com.google.gson.JsonObject;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface API {


    @POST("/signup")
    Call<ResponseBody> signup(@Body JsonObject userObj);

    @POST("/login")
    Call<JsonObject> login(@Body JsonObject loginObj);

    @GET("/getAllUserChats")
    Call<List<PersonModel>> getChats(@Query("userId")int userId);

    @GET("/getChatMessages")
    Call<List<PersonMessageModel>> getChatMessages(@Query("phoneNumber")String phoneNumber);
}
