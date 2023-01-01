package com.example.infosecuritysysapp.ui;

import static com.example.infosecuritysysapp.config.AppConstants.serverPublicKey;
import static com.example.infosecuritysysapp.config.AppSharedPreferences.CACHE_USER_PRIVATE_KEY;
import static com.example.infosecuritysysapp.config.AppSharedPreferences.CACHE_USER_PUBLIC_KEY;
import static com.example.infosecuritysysapp.config.AppSharedPreferences.GET_USER_PHONE;
import static com.example.infosecuritysysapp.config.AppSharedPreferences.GET_USER_PRIVATE_KEY;
import static com.example.infosecuritysysapp.config.AppSharedPreferences.GET_USER_PUBLIC_KEY;
import static com.example.infosecuritysysapp.config.AppSharedPreferences.InitSharedPreferences;
import static com.example.infosecuritysysapp.helper.FN.MAIN_FC;
import static com.example.infosecuritysysapp.helper.encryption.EncryptionConverters.convertByteToHexadecimal;
import static com.example.infosecuritysysapp.helper.encryption.EncryptionConverters.retrievePublicKey;
import static com.example.infosecuritysysapp.helper.encryption.EncryptionKeysUtils.generateRSAKeyPair;
import static com.example.infosecuritysysapp.helper.encryption.EncryptionTools.do_RSAEncryption;

import androidx.appcompat.app.AppCompatActivity;
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

import java.security.KeyPair;

import javax.crypto.SecretKey;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity implements ISocket {

    ActivityMainBinding binding;
    AppNotification appNotification;

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
            // if private key already generated, no need to regenerate keys and resend public key to server.
            if(GET_USER_PRIVATE_KEY() == null)
                createKeyPairsAndSendPublicKey();
        } catch (Exception e) {
            e.printStackTrace();
        }
        FN.addFixedNameFadeFragment(MAIN_FC,this,new LoginFragment());
    }

    private void createKeyPairsAndSendPublicKey() throws Exception {
        createKeyPairs();
        SocketIO.getInstance().send(new BaseSocketModel<>
                ("storing"
                , new PersonMessageModel(MyIP.getDeviceIp(), GET_USER_PHONE(), null, GET_USER_PUBLIC_KEY())).create());
    }

    private void createKeyPairs() throws Exception {
        KeyPair keyPair = generateRSAKeyPair();
        CACHE_USER_PRIVATE_KEY(convertByteToHexadecimal(keyPair.getPrivate().getEncoded()));
        CACHE_USER_PUBLIC_KEY(convertByteToHexadecimal(keyPair.getPublic().getEncoded()));
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

    @Override
    public void receivedMessage(String message) {
        appNotification.build(message);
    }
}