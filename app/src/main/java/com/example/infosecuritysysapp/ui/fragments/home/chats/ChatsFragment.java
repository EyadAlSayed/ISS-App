package com.example.infosecuritysysapp.ui.fragments.home.chats;

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
import com.example.infosecuritysysapp.model.PersonModel;
import com.example.infosecuritysysapp.network.api.ApiClient;
import com.example.infosecuritysysapp.ui.fragments.home.adapter.ChatsAdapter;
import com.example.infosecuritysysapp.ui.fragments.home.chats.presentation.IChats;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ChatsFragment extends Fragment implements IChats {

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
        new ApiClient().getAPI().getChats(userId).enqueue(new Callback<List<PersonModel>>() {
            @Override
            public void onResponse(@NonNull Call<List<PersonModel>> call, @NonNull Response<List<PersonModel>> response) {
                if(response.isSuccessful()){
                    adapter.refresh(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<PersonModel>> call, @NonNull Throwable t) {

            }
        });
    }
}