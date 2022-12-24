package com.example.infosecuritysysapp.ui.fragments.home.chats;

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
import com.example.infosecuritysysapp.ui.fragments.home.adapter.ChatsAdapter;

import java.util.ArrayList;


public class ChatsFragment extends Fragment {

    FragmentChatsBinding binding;

    int chatId;

    public ChatsFragment(int chatId) {
        this.chatId = chatId;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentChatsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initChatsRc();

    }

    private void initChatsRc() {
        binding.chatsRc.setHasFixedSize(true);
        binding.chatsRc.setLayoutManager(new LinearLayoutManager(requireContext()));
        ChatsAdapter adapter = new ChatsAdapter(new ArrayList<>(), requireContext(), onChatsClicked);
        binding.chatsRc.setAdapter(adapter);
    }

    ChatsAdapter.OnChatsClicked onChatsClicked = () -> FN.addFixedNameFadeFragment(MAIN_FC, requireActivity(), new ChatMessagesFragment());
}