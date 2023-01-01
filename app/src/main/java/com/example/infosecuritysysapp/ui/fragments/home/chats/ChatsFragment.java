package com.example.infosecuritysysapp.ui.fragments.home.chats;

import static com.example.infosecuritysysapp.config.AppSharedPreferences.CLEAR_DATA;
import static com.example.infosecuritysysapp.config.AppSharedPreferences.GET_USER_ID;
import static com.example.infosecuritysysapp.helper.FN.MAIN_FC;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.infosecuritysysapp.R;
import com.example.infosecuritysysapp.databinding.FragmentChatsBinding;
import com.example.infosecuritysysapp.databinding.FragmentLoginBinding;
import com.example.infosecuritysysapp.helper.FN;
import com.example.infosecuritysysapp.model.PersonContact;
import com.example.infosecuritysysapp.model.PersonModel;
import com.example.infosecuritysysapp.network.api.ApiClient;
import com.example.infosecuritysysapp.ui.MainActivity;
import com.example.infosecuritysysapp.ui.fragments.auth.LoginFragment;
import com.example.infosecuritysysapp.ui.fragments.home.adapter.ChatsAdapter;
import com.example.infosecuritysysapp.ui.fragments.home.chats.presentation.IChats;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ChatsFragment extends Fragment implements IChats, View.OnClickListener {

    FragmentChatsBinding binding;
    IChats iChats;
    ChatsAdapter adapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentChatsBinding.inflate(inflater, container, false);
        iChats = this;
        iChats.getChats(GET_USER_ID());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initChatsRc();
        initClickListener();
    }

    private void initClickListener(){
        binding.addContact.setOnClickListener(this);
        binding.logout.setOnClickListener(this);
    }
    private void initChatsRc() {
        binding.chatsRc.setHasFixedSize(true);
        binding.chatsRc.setLayoutManager(new LinearLayoutManager(requireContext()));
         adapter = new ChatsAdapter(new ArrayList<>(), requireContext(), onChatsClicked);
        binding.chatsRc.setAdapter(adapter);
    }

    ChatsAdapter.OnChatsClicked onChatsClicked = (personModel, position) -> FN.addFixedNameFadeFragment(MAIN_FC, requireActivity(), new ChatMessagesFragment(personModel.phoneNumber));

    @Override
    public void getChats(int userId) {
        new ApiClient().getAPI().getChats(userId).enqueue(new Callback<List<PersonContact>>() {
            @Override
            public void onResponse(@NonNull Call<List<PersonContact>> call, @NonNull Response<List<PersonContact>> response) {
                if(response.isSuccessful()){
                    adapter.refresh(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<PersonContact>> call, @NonNull Throwable t) {

            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.add_contact:{
                FN.addFixedNameFadeFragment(MAIN_FC, requireActivity(), new AddContactFragment());
                break;
            }
            case R.id.logout:{
                CLEAR_DATA();
                FN.addFixedNameFadeFragment(MAIN_FC, requireActivity(), new LoginFragment());
            }
            default:break;
        }
    }
}