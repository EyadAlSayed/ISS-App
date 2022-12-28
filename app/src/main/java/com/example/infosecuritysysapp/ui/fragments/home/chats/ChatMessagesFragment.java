package com.example.infosecuritysysapp.ui.fragments.home.chats;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.infosecuritysysapp.databinding.FragmentChatMessagesBinding;
import com.example.infosecuritysysapp.model.PersonMessageModel;
import com.example.infosecuritysysapp.network.api.ApiClient;
import com.example.infosecuritysysapp.ui.fragments.home.adapter.ChatMessagesAdapter;
import com.example.infosecuritysysapp.ui.fragments.home.chats.presentation.IChatMessages;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ChatMessagesFragment extends Fragment implements IChatMessages {

    FragmentChatMessagesBinding binding;
    ChatMessagesAdapter adapter;
    IChatMessages iChatMessages;
    String phoneNumber;
    public ChatMessagesFragment(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentChatMessagesBinding.inflate(inflater, container, false);
        iChatMessages = this;
        iChatMessages.getChatMessages(phoneNumber);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initChatsRc();
    }

    private void initChatsRc() {
        binding.messageRc.setHasFixedSize(true);
        binding.messageRc.setLayoutManager(new LinearLayoutManager(requireContext()));
        adapter = new ChatMessagesAdapter(new ArrayList(), requireContext());
        binding.messageRc.setAdapter(adapter);
    }

    @Override
    public void getChatMessages(String phoneNumber) {
        new ApiClient().getAPI().getChatMessages(phoneNumber).enqueue(new Callback<List<PersonMessageModel>>() {
            @Override
            public void onResponse(@NonNull Call<List<PersonMessageModel>> call, @NonNull Response<List<PersonMessageModel>> response) {
                if(response.isSuccessful()){
                    adapter.refresh(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<PersonMessageModel>> call, @NonNull Throwable t) {

            }
        });
    }
}