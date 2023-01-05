package com.example.infosecuritysysapp.ui.fragments.auth;

import static com.example.infosecuritysysapp.config.AppConstants.eyadKey;
import static com.example.infosecuritysysapp.config.AppConstants.serverPublicKey;
import static com.example.infosecuritysysapp.config.AppSharedPreferences.CACHE_SESSION_KEY;
import static com.example.infosecuritysysapp.config.AppSharedPreferences.CACHE_USER_PRIVATE_KEY;
import static com.example.infosecuritysysapp.config.AppSharedPreferences.CACHE_USER_PUBLIC_KEY;
import static com.example.infosecuritysysapp.helper.FN.MAIN_FC;
import static com.example.infosecuritysysapp.helper.encryption.EncryptionConverters.convertByteToHexadecimal;
import static com.example.infosecuritysysapp.helper.encryption.EncryptionKeysUtils.generateRSAKeyPair;
import static com.example.infosecuritysysapp.helper.encryption.EncryptionTools.do_AESEncryption;
import static com.example.infosecuritysysapp.helper.encryption.EncryptionTools.do_RSADecryption;
import static com.example.infosecuritysysapp.helper.encryption.EncryptionTools.do_RSAEncryption;
import static com.example.infosecuritysysapp.helper.encryption.EncryptionTools.do_RSAEncryptionB;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.infosecuritysysapp.databinding.FragmentSignUpBinding;
import com.example.infosecuritysysapp.helper.FN;
import com.example.infosecuritysysapp.helper.encryption.EncryptionKeysUtils;
import com.example.infosecuritysysapp.network.api.ApiClient;
import com.example.infosecuritysysapp.ui.fragments.auth.presentation.ISignUp;
import com.google.gson.JsonObject;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

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

    private void onSignUpClicked() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("name", binding.userName.getText().toString());
        jsonObject.addProperty("phoneNumber", binding.phoneNumber.getText().toString());
        jsonObject.addProperty("password", binding.password.getText().toString());
        jsonObject.addProperty("sessionKey", getEncryptedSessionKey());
        jsonObject.addProperty("userPublicKey", createKeyPairsAndSendPublicKey());
        iSignUp.signUp(jsonObject);
    }

    @Override
    public void onClick(View view) {
        try {
            onSignUpClicked();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void signUp(JsonObject jsonObject) {
        new ApiClient().getAPI().signup(jsonObject).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    FN.addFixedNameFadeFragment(MAIN_FC, requireActivity(), new LoginFragment());
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {

            }
        });

    }

    private String createKeyPairsAndSendPublicKey() {
        String key = createKeyPairs();
        CACHE_USER_PUBLIC_KEY(key);
        return key;
    }

    private String createKeyPairs() {
        try {
            KeyPair keyPair = generateRSAKeyPair();
            CACHE_USER_PRIVATE_KEY(convertByteToHexadecimal(keyPair.getPrivate().getEncoded()));
            return convertByteToHexadecimal(keyPair.getPublic().getEncoded());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    private String getEncryptedSessionKey() {
        try {
            SecretKey sessionKey = EncryptionKeysUtils.createAESKey();
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                return Base64.getEncoder().encodeToString(sessionKey.getEncoded());
//            }


//            Log.e("EYAD", "getEncryptedSessionKey: "+new String(do_RSAEncryptionB(sessionKey.getEncoded(),publicKey)));
//            Log.e("EYAD", "getEncryptedSessionKey: "+do_RSADecryption(sessionKey.getEncoded(),privateKey));
            String key = convertByteToHexadecimal(do_RSAEncryptionB(sessionKey.getEncoded(), serverPublicKey));
            CACHE_SESSION_KEY(convertByteToHexadecimal(sessionKey.getEncoded()));
            return key;

        } catch (Exception e) {
            Log.e("EYAD", "getEncryptedSessionKey: " + e);
            e.printStackTrace();
        }
        return "";
    }


}