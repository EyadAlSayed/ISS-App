package com.example.infosecuritysysapp.ui.fragments.home.chats;

import static com.example.infosecuritysysapp.config.AppSharedPreferences.GET_USER_ID;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.infosecuritysysapp.R;
import com.example.infosecuritysysapp.databinding.FragmentAddContactBinding;
import com.example.infosecuritysysapp.databinding.FragmentChatMessagesBinding;
import com.example.infosecuritysysapp.helper.FN;
import com.example.infosecuritysysapp.model.PersonContact;
import com.example.infosecuritysysapp.network.api.ApiClient;
import com.example.infosecuritysysapp.ui.fragments.home.chats.presentation.IAddContact;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AddContactFragment extends Fragment implements View.OnClickListener , IAddContact {
    FragmentAddContactBinding binding;
    IAddContact iAddContact;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentAddContactBinding.inflate(inflater, container, false);
        iAddContact = this;
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initCliclListener();
    }

    private void initCliclListener(){
        binding.addContactBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.add_contact_btn:{
                addPersonContact();
                break;
            }
            default:break;
        }
    }

    private void addPersonContact(){
        iAddContact.addContact(new PersonContact(0,binding.userName.getText().toString(),binding.phoneNumber.getText().toString()));
    }

    @Override
    public void addContact(PersonContact personContact) {
        new ApiClient().getAPI().createContact(GET_USER_ID(),personContact).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()) FN.popTopStack(requireActivity());
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }
}