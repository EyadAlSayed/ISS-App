package com.example.infosecuritysysapp.network.api;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.AssetManager;


import com.example.infosecuritysysapp.App;
import com.example.infosecuritysysapp.BuildConfig;
import com.example.infosecuritysysapp.R;
import com.example.infosecuritysysapp.config.AppSharedPreferences;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.readystatesoftware.chuck.ChuckInterceptor;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    public static String BASE_IP = "192.168.1.104:8081";
    public static String BASE_URL = "https://" + BASE_IP + "/";

    public static Retrofit retrofit;

    public static AppInterceptor interceptor = new AppInterceptor();

    public static OkHttpClient client = new OkHttpClient.Builder()
            .addInterceptor(new ChuckInterceptor(App.getContext()))
            .addInterceptor(interceptor).build();

    public static Retrofit getRetrofitInstance(Context context) {
        if (retrofit == null) {
            Gson gson = new GsonBuilder().setLenient().create();
            retrofit = new Retrofit.Builder()
//                    .client(client)
//                    .client(getUnsafeOkHttpClient())
                    .client(getTrustedOkHttpClient(context))
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
            return retrofit;
        }
        return retrofit;
    }

    public API getAPI(Context context) {
        return getRetrofitInstance(context).create(API.class);
    }

    public static OkHttpClient getUnsafeOkHttpClient() {
        try {
            TrustManager[] arrayOfTrustManager = new TrustManager[1];
            X509TrustManager x509TrustManager = new X509TrustManager() {
                @SuppressLint("TrustAllX509TrustManager")
                public void checkClientTrusted(X509Certificate[] param1ArrayOfX509Certificate, String param1String) throws CertificateException {
                }

                @SuppressLint("TrustAllX509TrustManager")
                public void checkServerTrusted(X509Certificate[] param1ArrayOfX509Certificate, String param1String) throws CertificateException {
                }

                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[0];
                }
            };

            arrayOfTrustManager[0] = x509TrustManager;
            SSLContext sSLContext = SSLContext.getInstance("SSL");
            SecureRandom secureRandom = new SecureRandom();

            sSLContext.init(null, arrayOfTrustManager, secureRandom);


            SSLSocketFactory sSLSocketFactory = sSLContext.getSocketFactory();
            OkHttpClient.Builder builder = new OkHttpClient.Builder();

            builder.sslSocketFactory(sSLSocketFactory, (X509TrustManager) arrayOfTrustManager[0]);
            HostnameVerifier hostnameVerifier = (param1String, param1SSLSession) -> true;
            builder.hostnameVerifier(hostnameVerifier);
            builder.addInterceptor(interceptor);
            builder.addInterceptor(new ChuckInterceptor(App.getContext()));

            builder.connectTimeout(90, TimeUnit.SECONDS);
            builder.readTimeout(90, TimeUnit.SECONDS);
            builder.writeTimeout(90, TimeUnit.SECONDS);

            return builder.addInterceptor(interceptor).build();
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    public static OkHttpClient getTrustedOkHttpClient(Context context) {
        try {
            TrustManager[] arrayOfTrustManager = new TrustManager[1];
            X509TrustManager x509TrustManager = new X509TrustManager() {
                @SuppressLint("TrustAllX509TrustManager")
                public void checkClientTrusted(X509Certificate[] param1ArrayOfX509Certificate, String param1String) throws CertificateException {
                }

                @SuppressLint("TrustAllX509TrustManager")
                public void checkServerTrusted(X509Certificate[] param1ArrayOfX509Certificate, String param1String) throws CertificateException {
                }

                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[0];
                }
            };

            arrayOfTrustManager[0] = x509TrustManager;

            InputStream caFileInputStream = context.getResources().openRawResource(R.raw.iss_app_2);

            KeyStore keyStore = KeyStore.getInstance("pkcs12");

            keyStore.load(caFileInputStream, "123456".toCharArray());

            KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance("X509");
            keyManagerFactory.init(keyStore, "123456".toCharArray());

            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, arrayOfTrustManager, new SecureRandom());

            SSLSocketFactory sSLSocketFactory = sslContext.getSocketFactory();
            OkHttpClient.Builder builder = new OkHttpClient.Builder();

            builder.sslSocketFactory(sSLSocketFactory, (X509TrustManager) arrayOfTrustManager[0]);
            HostnameVerifier hostnameVerifier = (param1String, param1SSLSession) -> true;
            builder.hostnameVerifier(hostnameVerifier);
            builder.addInterceptor(interceptor);
            builder.addInterceptor(new ChuckInterceptor(App.getContext()));

            builder.connectTimeout(90, TimeUnit.SECONDS);
            builder.readTimeout(90, TimeUnit.SECONDS);
            builder.writeTimeout(90, TimeUnit.SECONDS);

            return builder.addInterceptor(interceptor).build();
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }


//
//    public static OkHttpClient getSslOkHttpClient(Context context) {
//        OkHttpClient.Builder builder = new OkHttpClient.Builder();
//        builder.readTimeout(90, TimeUnit.SECONDS);
//        builder.connectTimeout(90, TimeUnit.SECONDS);
//        builder.addInterceptor(interceptor);
//        try {
//            InputStream caFileInputStream = context.getResources().openRawResource(R.raw.iss_app);
//
//            KeyStore keyStore = KeyStore.getInstance("JKS");
//
//            keyStore.load(caFileInputStream, "123456".toCharArray());
//
//            KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance("X509");
//            keyManagerFactory.init(keyStore, "123456".toCharArray());
//
//            SSLContext sslContext = SSLContext.getInstance("TLS");
//            sslContext.init(keyManagerFactory.getKeyManagers(), null, new SecureRandom());
//
//            builder.sslSocketFactory(sslContext.getSocketFactory(), (X509TrustManager) keyManagerFactory).build();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return builder.build();
//    }
}
