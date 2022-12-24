package com.example.infosecuritysysapp.ui.fragments.auth;

import static com.example.infosecuritysysapp.helper.FN.MAIN_FC;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.infosecuritysysapp.R;
import com.example.infosecuritysysapp.databinding.FragmentLoginBinding;
import com.example.infosecuritysysapp.helper.FN;
import com.example.infosecuritysysapp.ui.fragments.home.chats.ChatsFragment;


public class LoginFragment extends Fragment implements View.OnClickListener {

    FragmentLoginBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentLoginBinding.inflate(inflater, container, false);
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
    }

    private void onSignUpClicked() {
        FN.addFixedNameFadeFragment(MAIN_FC,requireActivity(),new SignUpFragment());
    }

    private void onLoginClicked() {
        FN.addFixedNameFadeFragment(MAIN_FC,requireActivity(), new ChatsFragment(1));

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
            default:
                break;
        }
    }
}