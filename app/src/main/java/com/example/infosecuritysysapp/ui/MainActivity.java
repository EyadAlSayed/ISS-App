package com.example.infosecuritysysapp.ui;

import static com.example.infosecuritysysapp.config.AppSharedPreferences.CACHE_USER_SYMMETRIC_KEY;
import static com.example.infosecuritysysapp.config.AppSharedPreferences.CLEAR_DATA;
import static com.example.infosecuritysysapp.config.AppSharedPreferences.GET_IS_LOGIN;
import static com.example.infosecuritysysapp.config.AppSharedPreferences.GET_SYMMETRIC_KEY;
import static com.example.infosecuritysysapp.config.AppSharedPreferences.InitSharedPreferences;
import static com.example.infosecuritysysapp.helper.FN.MAIN_FC;
import static com.example.infosecuritysysapp.helper.SymmetricEncryptionTools.do_AESDecryption;
import static com.example.infosecuritysysapp.helper.SymmetricEncryptionTools.hexStringToByteArray;
import static com.example.infosecuritysysapp.helper.SymmetricEncryptionTools.retrieveSecretKey;

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

import com.example.infosecuritysysapp.R;
import com.example.infosecuritysysapp.databinding.ActivityMainBinding;
import com.example.infosecuritysysapp.helper.AppNotification;
import com.example.infosecuritysysapp.helper.FN;
import com.example.infosecuritysysapp.helper.SymmetricEncryptionTools;
import com.example.infosecuritysysapp.network.ISocket;
import com.example.infosecuritysysapp.network.SocketIO;
import com.example.infosecuritysysapp.ui.fragments.auth.LoginFragment;
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
        SocketIO.getInstance().initWebSocketAndConnect(this);
        InitSharedPreferences(this);
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

    private String decryptMessage(String message){
        try {
            return do_AESDecryption(hexStringToByteArray(message), retrieveSecretKey(GET_SYMMETRIC_KEY()));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void errorMessage(String errorMessage) {
//        runOnUiThread(() -> new ErrorDialog(MainActivity.this).setErrorMessage(errorMessage).show());
    }

    public void initIChatMessages(IChatMessages iChatMessages) {
        this.iChatMessages = iChatMessages;
    }


}