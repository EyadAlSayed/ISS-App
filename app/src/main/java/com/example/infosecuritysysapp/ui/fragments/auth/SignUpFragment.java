package com.example.infosecuritysysapp.ui.fragments.auth;

import static com.example.infosecuritysysapp.config.AppSharedPreferences.GET_SYMMETRIC_KEY;
import static com.example.infosecuritysysapp.helper.FN.MAIN_FC;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.infosecuritysysapp.R;
import com.example.infosecuritysysapp.databinding.FragmentSignUpBinding;
import com.example.infosecuritysysapp.helper.FN;
import com.example.infosecuritysysapp.helper.SymmetricEncryptionTools;
import com.example.infosecuritysysapp.network.api.ApiClient;
import com.example.infosecuritysysapp.ui.fragments.auth.presentation.ISignUp;
import com.google.gson.JsonObject;

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
        JsonObject jsonObject= new JsonObject();
        jsonObject.addProperty("name",binding.userName.getText().toString());
        jsonObject.addProperty("phoneNumber",binding.phoneNumber.getText().toString());
        jsonObject.addProperty("password",binding.password.getText().toString());
        iSignUp.signUp(jsonObject);
    }

    private String getMacKey(){
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
                onSignUpClicked();
                break;
            }
            default:
                break;
        }


    }

    @Override
    public void signUp(JsonObject jsonObject) {
        new ApiClient().getAPI(requireContext()).signup(getMacKey(),jsonObject).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    FN.addFixedNameFadeFragment(MAIN_FC,requireActivity(),new LoginFragment());
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {

            }
        });

    }
}