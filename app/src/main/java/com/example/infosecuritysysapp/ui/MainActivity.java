package com.example.infosecuritysysapp.ui;

import static com.example.infosecuritysysapp.config.AppConstants.serverPublicKey;
import static com.example.infosecuritysysapp.config.AppConstants.serverPublicKey;
import static com.example.infosecuritysysapp.config.AppSharedPreferences.InitSharedPreferences;
import static com.example.infosecuritysysapp.helper.FN.MAIN_FC;
import static com.example.infosecuritysysapp.helper.encryption.EncryptionConverters.convertByteToHexadecimal;
import static com.example.infosecuritysysapp.helper.encryption.EncryptionConverters.retrievePublicKey;
import static com.example.infosecuritysysapp.helper.encryption.EncryptionTools.do_RSAEncryption;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
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

import javax.crypto.SecretKey;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
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
        try {
            createAndSendEncryptedSessionKey();
        } catch (Exception e) {
            e.printStackTrace();
        }
        FN.addFixedNameFadeFragment(MAIN_FC,this,new LoginFragment());
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

    private void storeServerPublicKey(){
        new ApiClient().getAPI().getServerPublicKey().enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.isSuccessful()){
                    try {
                        serverPublicKey = retrievePublicKey(response.body());
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

    private void createAndSendEncryptedSessionKey() throws Exception {
        SecretKey sessionKey = EncryptionKeysUtils.createAESKey();
        AppConstants.sessionKey = sessionKey;
        String encryptedSessionKey = convertByteToHexadecimal(do_RSAEncryption(convertByteToHexadecimal(sessionKey.getEncoded()), serverPublicKey));
        SocketIO.getInstance().send(new BaseSocketModel<>("handshaking", new PersonMessageModel(MyIP.getDeviceIp(), null, null, encryptedSessionKey)).create());
        // IF WE CAN'T GET USER'S ID FROM DEVICE IP IN SERVER.. THEN THIS FUNCTION SHOULD BE WRITTEN
        // IN "LoginFragment" -> onLoginButtonClicked, SO WE CAN GET USER'S ID FROM USER'S PHONE NUMBER.
    }

    public void initIChatMessages(IChatMessages iChatMessages){
        this.iChatMessages = iChatMessages;
    }
}