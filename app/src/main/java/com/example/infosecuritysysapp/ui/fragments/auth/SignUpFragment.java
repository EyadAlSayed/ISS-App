package com.example.infosecuritysysapp.ui.fragments.auth;

import static com.example.infosecuritysysapp.config.AppConstants.serverPublicKey;
import static com.example.infosecuritysysapp.config.AppSharedPreferences.CACHE_USER_PRIVATE_KEY;
import static com.example.infosecuritysysapp.config.AppSharedPreferences.CACHE_USER_PUBLIC_KEY;
import static com.example.infosecuritysysapp.config.AppSharedPreferences.GET_USER_PHONE_NUMBER;
import static com.example.infosecuritysysapp.config.AppSharedPreferences.GET_USER_PRIVATE_KEY;
import static com.example.infosecuritysysapp.config.AppSharedPreferences.GET_USER_PUBLIC_KEY;
import static com.example.infosecuritysysapp.helper.FN.MAIN_FC;
import static com.example.infosecuritysysapp.helper.encryption.EncryptionConverters.convertByteToHexadecimal;
import static com.example.infosecuritysysapp.helper.encryption.EncryptionKeysUtils.generateRSAKeyPair;
import static com.example.infosecuritysysapp.helper.encryption.EncryptionTools.do_RSAEncryption;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.infosecuritysysapp.R;
import com.example.infosecuritysysapp.config.AppConstants;
import com.example.infosecuritysysapp.databinding.FragmentSignUpBinding;
import com.example.infosecuritysysapp.helper.FN;
import com.example.infosecuritysysapp.helper.MyIP;
import com.example.infosecuritysysapp.helper.SymmetricEncryptionTools;
import com.example.infosecuritysysapp.helper.encryption.EncryptionKeysUtils;
import com.example.infosecuritysysapp.model.PersonMessageModel;
import com.example.infosecuritysysapp.model.socket.BaseSocketModel;
import com.example.infosecuritysysapp.network.SocketIO;
import com.example.infosecuritysysapp.network.api.ApiClient;
import com.example.infosecuritysysapp.ui.fragments.auth.presentation.ISignUp;
import com.google.gson.JsonObject;

import java.security.KeyPair;

import javax.crypto.SecretKey;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SignUpFragment extends Fragment implements View.OnClickListener, ISignUp {

    FragmentSignUpBinding binding;

    ISignUp iSignUp;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentSignUpBinding.inflate(inflater, container, false);
        iSignUp = this;
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initClickEvent();
    }

    private void initClickEvent() {
        binding.signupBtn.setOnClickListener(this);
    }

    private void onSignUpClicked() throws Exception {
        JsonObject jsonObject= new JsonObject();
        jsonObject.addProperty("name",binding.userName.getText().toString());
        jsonObject.addProperty("phoneNumber",binding.phoneNumber.getText().toString());
        jsonObject.addProperty("password",binding.password.getText().toString());
        jsonObject.addProperty("sessionKey", createAndSendEncryptedSessionKey());
        jsonObject.addProperty("userPublicKey", createKeyPairsAndSendPublicKey());
        iSignUp.signUp(jsonObject);
    }

    private String getSymmetricKey(){
        try {
            return SymmetricEncryptionTools.convertByteToHexadecimal(SymmetricEncryptionTools.createAESKey().getEncoded());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.signup_btn: {
                try {
                    onSignUpClicked();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            }
            default:
                break;
        }


    }

    @Override
    public void signUp(JsonObject jsonObject) {
        new ApiClient().getAPI().signup(jsonObject).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                if(response.isSuccessful()){
                  //  try {
                        //createAndSendEncryptedSessionKey();
                        // if private key already generated, no need to regenerate keys and resend public key to server.
//                        if(GET_USER_PRIVATE_KEY() == null)
//                            createKeyPairsAndSendPublicKey();
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
                    FN.addFixedNameFadeFragment(MAIN_FC,requireActivity(),new LoginFragment());
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {

            }
        });

    }

    private String createKeyPairsAndSendPublicKey() throws Exception {
        createKeyPairs();
        return GET_USER_PUBLIC_KEY();
    }

//    private void createKeyPairsAndSendPublicKey() throws Exception {
//        createKeyPairs();
//        SocketIO.getInstance().send(new BaseSocketModel<>
//                ("storing"
//                        , new PersonMessageModel(MyIP.getDeviceIp(), GET_USER_PHONE_NUMBER(), null, GET_USER_PUBLIC_KEY(),GET_USER_PHONE_NUMBER())).toJson());
//    }

    private void createKeyPairs() throws Exception {
        KeyPair keyPair = generateRSAKeyPair();
        CACHE_USER_PRIVATE_KEY(convertByteToHexadecimal(keyPair.getPrivate().getEncoded()));
        CACHE_USER_PUBLIC_KEY(convertByteToHexadecimal(keyPair.getPublic().getEncoded()));
    }

    private String createAndSendEncryptedSessionKey() throws Exception {
        SecretKey sessionKey = EncryptionKeysUtils.createAESKey();
        AppConstants.sessionKey = sessionKey;
        return convertByteToHexadecimal(do_RSAEncryption(convertByteToHexadecimal(sessionKey.getEncoded()), serverPublicKey));

    }

//    private void createAndSendEncryptedSessionKey() throws Exception {
//        SecretKey sessionKey = EncryptionKeysUtils.createAESKey();
//        AppConstants.sessionKey = sessionKey;
//        String encryptedSessionKey = convertByteToHexadecimal(do_RSAEncryption(convertByteToHexadecimal(sessionKey.getEncoded()), serverPublicKey));
//        SocketIO.getInstance().send(new BaseSocketModel<>("handshaking", new PersonMessageModel(MyIP.getDeviceIp(), GET_USER_PHONE_NUMBER(), null, encryptedSessionKey,GET_USER_PHONE_NUMBER())).toJson());
//        // IF WE CAN'T GET USER'S ID FROM DEVICE IP IN SERVER.. THEN THIS FUNCTION SHOULD BE WRITTEN
//        // IN "LoginFragment" -> onLoginButtonClicked, SO WE CAN GET USER'S ID FROM USER'S PHONE NUMBER.
//    }
}