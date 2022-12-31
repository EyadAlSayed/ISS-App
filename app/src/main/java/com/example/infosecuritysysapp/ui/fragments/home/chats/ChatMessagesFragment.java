package com.example.infosecuritysysapp.ui.fragments.home.chats;

import static com.example.infosecuritysysapp.config.AppSharedPreferences.GET_SYMMETRIC_KEY;
import static com.example.infosecuritysysapp.config.AppSharedPreferences.GET_USER_PHONE_NUMBER;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.infosecuritysysapp.R;
import com.example.infosecuritysysapp.databinding.FragmentChatMessagesBinding;
import com.example.infosecuritysysapp.helper.MyIP;
import com.example.infosecuritysysapp.helper.SymmetricEncryptionTools;
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

    boolean isEncrypted;

    public ChatMessagesFragment(String phoneNumber) {
        this.receiverPhoneNumber = phoneNumber;
        this.isEncrypted = false;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentChatMessagesBinding.inflate(inflater, container, false);
        initIChatMessages();
        iChatMessages.getChatMessages(receiverPhoneNumber);
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

    private PersonMessageModel getMessage(String messageContent) {
        return new PersonMessageModel(MyIP.getDeviceIp(), GET_USER_PHONE_NUMBER(), receiverPhoneNumber, messageContent, GET_USER_PHONE_NUMBER());
    }

    private void sendMessage() {
        String messageContent = binding.messageContent.getText().toString();
        binding.messageContent.getText().clear();
        PersonMessageModel personMessageModel = getMessage(messageContent);
        BaseSocketModel baseSocketModel = new BaseSocketModel<>("send", personMessageModel);
        SocketIO.getInstance().send(baseSocketModel.toJson());
    }

    private List<PersonMessageModel> decryptedMessages(List<PersonMessageModel> items) {
        try {
            for (PersonMessageModel model : items) {
                model.setContent(SymmetricEncryptionTools.do_AESDecryption(SymmetricEncryptionTools.hexStringToByteArray(model.content),
                        SymmetricEncryptionTools.retrieveSecretKey(GET_SYMMETRIC_KEY())));
            }
            return items;
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    private PersonMessageModel decryptedMessage(PersonMessageModel personMessageModel) {
        try {
            personMessageModel.setContent(SymmetricEncryptionTools.do_AESDecryption(SymmetricEncryptionTools.hexStringToByteArray(personMessageModel.content),
                    SymmetricEncryptionTools.retrieveSecretKey(GET_SYMMETRIC_KEY())));
            return personMessageModel;
        } catch (Exception e) {
            return null;
        }

    }

    private void sendEncryptedMessage() {
        try {
            String encryptedMessage = getEncryptedMessage();
            String mac = SymmetricEncryptionTools.getMac(GET_SYMMETRIC_KEY(), encryptedMessage);
            PersonMessageModel personMessageModel = getMessage(encryptedMessage);
            SocketIO.getInstance().send(new BaseSocketModel<>("send_e", personMessageModel, mac).toJson());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private String getEncryptedMessage() throws Exception {
        String plainText = binding.messageContent.getText().toString();
        binding.messageContent.getText().clear();
        byte[] cipherText = SymmetricEncryptionTools.do_AESEncryption(plainText, SymmetricEncryptionTools.retrieveSecretKey(GET_SYMMETRIC_KEY()));
        String message = SymmetricEncryptionTools.convertByteToHexadecimal(cipherText);
        return message;
    }

    @Override
    public void getChatMessages(String phoneNumber) {
        new ApiClient().getAPI().getChatMessages(phoneNumber).enqueue(new Callback<List<PersonMessageModel>>() {
            @Override
            public void onResponse(@NonNull Call<List<PersonMessageModel>> call, @NonNull Response<List<PersonMessageModel>> response) {
                if (response.isSuccessful()) {
//                    adapter.refresh(response.body());
                    adapter.refresh(decryptedMessages(response.body()));
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<PersonMessageModel>> call, @NonNull Throwable t) {

            }
        });
    }

    @Override
    public void receivedMessage(String message) {
        if (isEncrypted)
            requireActivity().runOnUiThread(() -> adapter.addMessage(decryptedMessage(PersonMessageModel.fromJson(message))));
        else
            requireActivity().runOnUiThread(() -> adapter.addMessage(PersonMessageModel.fromJson(message)));
    }

    @Override
    public void enableEncryptedMode(boolean isEncrypted) {
        this.isEncrypted = isEncrypted;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.send_btn: {
                if (!binding.messageContent.getText().toString().isEmpty()) sendEncryptedMessage();
                break;
            }
            default:
                break;
        }
    }
}