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
import com.example.infosecuritysysapp.databinding.FragmentChatsBinding;
import com.example.infosecuritysysapp.model.ChatMessageModel;
import com.example.infosecuritysysapp.ui.fragments.home.adapter.ChatMessagesAdapter;
import com.example.infosecuritysysapp.ui.fragments.home.adapter.ChatsAdapter;

import java.util.ArrayList;


public class ChatMessagesFragment extends Fragment {

    FragmentChatMessagesBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentChatMessagesBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initChatsRc();
    }

    private void initChatsRc() {
        binding.messageRc.setHasFixedSize(true);
        ArrayList<ChatMessageModel> messageModels = new ArrayList<>();
        messageModels.add(new ChatMessageModel(R.layout.item_message_in));
        messageModels.add(new ChatMessageModel(R.layout.item_message_out));
        messageModels.add(new ChatMessageModel(R.layout.item_message_in));
        messageModels.add(new ChatMessageModel(R.layout.item_message_out));
        messageModels.add(new ChatMessageModel(R.layout.item_message_in));
        messageModels.add(new ChatMessageModel(R.layout.item_message_out));
        messageModels.add(new ChatMessageModel(R.layout.item_message_in));
        messageModels.add(new ChatMessageModel(R.layout.item_message_out));
        messageModels.add(new ChatMessageModel(R.layout.item_message_in));
        messageModels.add(new ChatMessageModel(R.layout.item_message_out));

        binding.messageRc.setLayoutManager(new LinearLayoutManager(requireContext()));
        ChatMessagesAdapter adapter = new ChatMessagesAdapter(messageModels, requireContext());
        binding.messageRc.setAdapter(adapter);
    }
}