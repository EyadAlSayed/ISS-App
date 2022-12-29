package com.example.infosecuritysysapp.ui.fragments.home.chats;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.infosecuritysysapp.R;
import com.example.infosecuritysysapp.databinding.FragmentChatMessagesBinding;
import com.example.infosecuritysysapp.helper.MyIP;
import com.example.infosecuritysysapp.model.PersonMessageModel;
import com.example.infosecuritysysapp.model.socket.BaseSocketModel;
import com.example.infosecuritysysapp.network.SocketIO;
import com.example.infosecuritysysapp.network.api.ApiClient;
import com.example.infosecuritysysapp.ui.MainActivity;
import com.example.infosecuritysysapp.ui.fragments.home.adapter.ChatMessagesAdapter;
import com.example.infosecuritysysapp.ui.fragments.home.chats.presentation.IChatMessages;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ChatMessagesFragment extends Fragment implements IChatMessages, View.OnClickListener {

    FragmentChatMessagesBinding binding;
    ChatMessagesAdapter adapter;
    IChatMessages iChatMessages;
    String receiverPhoneNumber;

    public ChatMessagesFragment(String phoneNumber) {
        this.receiverPhoneNumber = phoneNumber;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentChatMessagesBinding.inflate(inflater, container, false);
        iChatMessages.getChatMessages(receiverPhoneNumber);
        initIChatMessages();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initChatsRc();
        initClickListener();
    }

    public void initIChatMessages() {
        iChatMessages = this;
        ((MainActivity) requireActivity()).initIChatMessages(iChatMessages);
    }

    private void initChatsRc() {
        binding.messageRc.setHasFixedSize(true);
        binding.messageRc.setLayoutManager(new LinearLayoutManager(requireContext()));
        adapter = new ChatMessagesAdapter(new ArrayList(), requireContext());
        binding.messageRc.setAdapter(adapter);
    }

    private void initClickListener() {
        binding.sendBtn.setOnClickListener(this);
    }

    @Override
    public void getChatMessages(String phoneNumber) {
        new ApiClient().getAPI().getChatMessages(phoneNumber).enqueue(new Callback<List<PersonMessageModel>>() {
            @Override
            public void onResponse(@NonNull Call<List<PersonMessageModel>> call, @NonNull Response<List<PersonMessageModel>> response) {
                if (response.isSuccessful()) {
                    adapter.refresh(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<PersonMessageModel>> call, @NonNull Throwable t) {

            }
        });
    }

    @Override
    public void receivedMessage(String message) {
        adapter.addMessage(PersonMessageModel.fromJson(message));
    }

    private void sendMessage() {
        String messageContent = binding.messageContent.getText().toString();
        binding.messageContent.getText().clear();
        PersonMessageModel personMessageModel = new PersonMessageModel(MyIP.getDeviceIp(), receiverPhoneNumber, messageContent, 1);
        BaseSocketModel baseSocketModel = new BaseSocketModel<>("send", personMessageModel);
        SocketIO.getInstance().send(baseSocketModel.toJson());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.send_btn: {
                if (!binding.messageContent.getText().toString().isEmpty()) sendMessage();
                break;
            }
            default:
                break;
        }
    }
}