package com.example.infosecuritysysapp.ui.fragments.auth;

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


public class SignUpFragment extends Fragment implements View.OnClickListener {

    FragmentSignUpBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentSignUpBinding.inflate(inflater, container, false);
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
        FN.addFixedNameFadeFragment(MAIN_FC,requireActivity(),new LoginFragment());

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
}