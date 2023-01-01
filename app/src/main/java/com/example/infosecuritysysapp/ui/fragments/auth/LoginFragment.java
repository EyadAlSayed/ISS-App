package com.example.infosecuritysysapp.ui.fragments.auth;

import static com.example.infosecuritysysapp.config.AppSharedPreferences.CACHE_USER_ID;
import static com.example.infosecuritysysapp.helper.FN.MAIN_FC;
import static com.example.infosecuritysysapp.helper.encryption.EncryptionConverters.convertByteToHexadecimal;
import static com.example.infosecuritysysapp.helper.encryption.EncryptionTools.do_AESEncryption;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.infosecuritysysapp.R;
import com.example.infosecuritysysapp.config.AppConstants;
import com.example.infosecuritysysapp.databinding.FragmentLoginBinding;
import com.example.infosecuritysysapp.helper.FN;
import com.example.infosecuritysysapp.helper.MyIP;
import com.example.infosecuritysysapp.helper.encryption.EncryptionConverters;
import com.example.infosecuritysysapp.helper.encryption.EncryptionTools;
import com.example.infosecuritysysapp.model.PersonMessageModel;
import com.example.infosecuritysysapp.model.socket.BaseSocketModel;
import com.example.infosecuritysysapp.network.SocketIO;
import com.example.infosecuritysysapp.network.api.ApiClient;
import com.example.infosecuritysysapp.ui.fragments.auth.presentation.ILogin;
import com.example.infosecuritysysapp.ui.fragments.home.chats.ChatsFragment;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginFragment extends Fragment implements View.OnClickListener, ILogin {

    FragmentLoginBinding binding;
    ILogin iLogin;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentLoginBinding.inflate(inflater, container, false);
        iLogin = this;
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initClickEvent();
    }

    private void initClickEvent() {
        binding.signup.setOnClickListener(this);
        binding.loginBtn.setOnClickListener(this);
        binding.test.setOnClickListener(this);
    }

    private void onSignUpClicked() {
        FN.addFixedNameFadeFragment(MAIN_FC, requireActivity(), new SignUpFragment());
    }

    private void onLoginClicked() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("phoneNumber", binding.userName.getText().toString());
        jsonObject.addProperty("password",  binding.password.getText().toString());
        iLogin.login(jsonObject);
    }

    private void onTestClicked() throws Exception{
        SocketIO.getInstance().send(new BaseSocketModel<>("send", new PersonMessageModel(MyIP.getDeviceIp(), "0991423014", "09999999999", encryptMessage())).create());
    }

    private String encryptMessage() throws Exception {
        String message = "Hello Ab Ayham";
        return convertByteToHexadecimal(do_AESEncryption(message, AppConstants.sessionKey));
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.signup: {
                onSignUpClicked();
                break;
            }
            case R.id.login_btn: {
                onLoginClicked();
                break;
            }
            case R.id.test: {
                try {
                    onTestClicked();
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
    public void login(JsonObject jsonObject) {
        new ApiClient().getAPI().login(jsonObject).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                if(response.isSuccessful()){
                    CACHE_USER_ID(response.body().get("userId").getAsInt());
                    FN.addFixedNameFadeFragment(MAIN_FC, requireActivity(), new ChatsFragment());
                }
            }

            @Override
            public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
                Toast.makeText(requireContext(),t.toString(),Toast.LENGTH_LONG).show();
            }
        });
    }
}