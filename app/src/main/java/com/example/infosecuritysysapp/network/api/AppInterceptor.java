package com.example.infosecuritysysapp.network.api;


import androidx.annotation.NonNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;


public class AppInterceptor implements Interceptor {

    @NonNull
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request newRequest=chain.request().newBuilder()
                .addHeader("Authorization","Bearer ")
                .addHeader("Accept","*/*")
                .addHeader("Content-Type","application/json")
                .build();

        return chain.proceed(newRequest);
    }
}
