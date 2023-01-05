package com.example.infosecuritysysapp.ui;

import static com.example.infosecuritysysapp.config.AppConstants.eyadKey;
import static com.example.infosecuritysysapp.config.AppConstants.serverPublicKey;
import static com.example.infosecuritysysapp.config.AppConstants.sessionKey;
import static com.example.infosecuritysysapp.config.AppSharedPreferences.CACHE_SESSION_KEY;
import static com.example.infosecuritysysapp.config.AppSharedPreferences.CACHE_USER_PRIVATE_KEY;
import static com.example.infosecuritysysapp.config.AppSharedPreferences.CACHE_USER_PUBLIC_KEY;
import static com.example.infosecuritysysapp.config.AppSharedPreferences.GET_USER_ID;
import static com.example.infosecuritysysapp.config.AppSharedPreferences.GET_USER_PHONE_NUMBER;
import static com.example.infosecuritysysapp.config.AppSharedPreferences.GET_USER_PRIVATE_KEY;
import static com.example.infosecuritysysapp.config.AppSharedPreferences.GET_USER_PUBLIC_KEY;
import static com.example.infosecuritysysapp.config.AppSharedPreferences.GET_IS_LOGIN;
import static com.example.infosecuritysysapp.config.AppSharedPreferences.InitSharedPreferences;
import static com.example.infosecuritysysapp.helper.FN.MAIN_FC;
import static com.example.infosecuritysysapp.helper.encryption.EncryptionConverters.convertByteToHexadecimal;
import static com.example.infosecuritysysapp.helper.encryption.EncryptionConverters.hexStringToByteArray;
import static com.example.infosecuritysysapp.helper.encryption.EncryptionConverters.retrievePublicKey;
import static com.example.infosecuritysysapp.helper.encryption.EncryptionConverters.retrieveSymmetricSecretKey;
import static com.example.infosecuritysysapp.helper.encryption.EncryptionKeysUtils.generateRSAKeyPair;
import static com.example.infosecuritysysapp.helper.encryption.EncryptionTools.do_AESDecryption;
import static com.example.infosecuritysysapp.helper.encryption.EncryptionTools.do_RSAEncryption;
import static com.example.infosecuritysysapp.helper.encryption.EncryptionTools.do_RSAEncryptionB;


import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CompoundButton;
import android.widget.Toast;

import android.widget.Toast;

import com.example.infosecuritysysapp.R;
import com.example.infosecuritysysapp.config.AppConstants;
import com.example.infosecuritysysapp.databinding.ActivityMainBinding;
import com.example.infosecuritysysapp.helper.AppNotification;
import com.example.infosecuritysysapp.helper.FN;
import com.example.infosecuritysysapp.helper.MyIP;
import com.example.infosecuritysysapp.helper.encryption.EncryptionKeysUtils;
import com.example.infosecuritysysapp.model.PersonMessageModel;
import com.example.infosecuritysysapp.model.socket.BaseSocketModel;
import com.example.infosecuritysysapp.network.ISocket;
import com.example.infosecuritysysapp.network.SocketIO;
import com.example.infosecuritysysapp.network.api.ApiClient;
import com.example.infosecuritysysapp.ui.fragments.auth.LoginFragment;

import java.security.KeyPair;

import javax.crypto.SecretKey;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.example.infosecuritysysapp.ui.fragments.home.chats.ChatMessagesFragment;
import com.example.infosecuritysysapp.ui.fragments.home.chats.ChatsFragment;
import com.example.infosecuritysysapp.ui.fragments.home.chats.presentation.IChatMessages;


public class MainActivity extends AppCompatActivity implements ISocket {

    ActivityMainBinding binding;
    AppNotification appNotification;
    IChatMessages iChatMessages;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        appNotification = new AppNotification(this);
        SocketIO.getInstance().initWebSocketAndConnect(this);
        InitSharedPreferences(this);
        storeServerPublicKey();
        openFragment();
    }


    private void openFragment() {
        if (GET_IS_LOGIN()) {
            FN.addFixedNameFadeFragment(MAIN_FC, this, new ChatsFragment());
        } else {
            FN.addFixedNameFadeFragment(MAIN_FC, this, new LoginFragment());
        }
    }

    @Override
    public void onBackPressed() {
        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.main_fcr);
        if (currentFragment instanceof ChatsFragment) finish();
        else if (currentFragment instanceof LoginFragment) finish();
        else super.onBackPressed();
    }

    private void storeServerPublicKey() {
        new ApiClient().getAPI().serverHandshaking().enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    try {
                        serverPublicKey = retrievePublicKey(response.body());
                        SecretKey sessionKey = EncryptionKeysUtils.createAESKey();

                        String sesKey = convertByteToHexadecimal(sessionKey.getEncoded());
                        CACHE_SESSION_KEY(sesKey);

                        KeyPair keyPair = EncryptionKeysUtils.generateRSAKeyPair();
                        String pubKey = convertByteToHexadecimal(keyPair.getPublic().getEncoded());
                        CACHE_USER_PRIVATE_KEY(convertByteToHexadecimal(keyPair.getPrivate().getEncoded()));
                        CACHE_USER_PUBLIC_KEY(pubKey);

                        cache(sesKey,pubKey);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error Connection", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void cache(String sesKey,String pubKey){
        if(GET_IS_LOGIN()){
            new ApiClient().getAPI().updatekeys(GET_USER_ID(),sesKey,pubKey).enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    Log.e("ISS", "onResponse: "+response.isSuccessful());
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    Log.e("ISS", "onFailure: "+t );
                }
            });
        }
    }



    private String decryptMessage(String message) {
        try {
            return do_AESDecryption(hexStringToByteArray(message), sessionKey);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void receivedMessage(String message) {
        if (iChatMessages != null) {
            iChatMessages.receivedMessage(message);
        }
    }

    @Override
    public void successfulSend(String message) {
        runOnUiThread(() -> Toast.makeText(MainActivity.this, decryptMessage(message), Toast.LENGTH_LONG).show());
    }

    @Override
    public void errorMessage(String errorMessage) {
//        runOnUiThread(() -> new ErrorDialog(MainActivity.this).setErrorMessage(errorMessage).show());
    }

    public void initIChatMessages(IChatMessages iChatMessages) {
        this.iChatMessages = iChatMessages;
    }


}