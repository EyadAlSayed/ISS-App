package com.example.infosecuritysysapp.ui;

import static com.example.infosecuritysysapp.config.AppSharedPreferences.InitSharedPreferences;
import static com.example.infosecuritysysapp.helper.FN.MAIN_FC;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.widget.Toast;

import com.example.infosecuritysysapp.R;
import com.example.infosecuritysysapp.databinding.ActivityMainBinding;
import com.example.infosecuritysysapp.helper.AppNotification;
import com.example.infosecuritysysapp.helper.FN;
import com.example.infosecuritysysapp.network.ISocket;
import com.example.infosecuritysysapp.network.SocketIO;
import com.example.infosecuritysysapp.ui.fragments.auth.LoginFragment;
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
        FN.addFixedNameFadeFragment(MAIN_FC, this, new LoginFragment());
    }

    @Override
    public void onBackPressed() {
        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.main_fcr);
        if(currentFragment instanceof ChatsFragment) finish();
        else
        if(currentFragment instanceof LoginFragment) finish();
        else super.onBackPressed();
    }

    @Override
    public void receivedMessage(String message) {
        appNotification.build(message);
        if(iChatMessages != null) {
            Toast.makeText(this,"Message Received",Toast.LENGTH_LONG).show();
            iChatMessages.receivedMessage(message);
        }
    }

    @Override
    public void errorMessage(String errorMessage) {
        runOnUiThread(() -> new ErrorDialog(MainActivity.this).setErrorMessage(errorMessage).show());
    }

    public void initIChatMessages(IChatMessages iChatMessages){
        this.iChatMessages = iChatMessages;
    }
}