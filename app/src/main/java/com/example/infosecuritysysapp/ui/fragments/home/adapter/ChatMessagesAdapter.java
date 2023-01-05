package com.example.infosecuritysysapp.ui.fragments.home.adapter;


import static com.example.infosecuritysysapp.config.AppSharedPreferences.GET_SESSION_KEY;
import static com.example.infosecuritysysapp.config.AppSharedPreferences.GET_USER_PHONE_NUMBER;
import static com.example.infosecuritysysapp.helper.encryption.EncryptionConverters.hexStringToByteArray;
import static com.example.infosecuritysysapp.helper.encryption.EncryptionConverters.retrieveSymmetricSecretKey;
import static com.example.infosecuritysysapp.helper.encryption.EncryptionTools.do_AESDecryption;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.infosecuritysysapp.R;
import com.example.infosecuritysysapp.config.AppConstants;

import com.example.infosecuritysysapp.model.PersonMessageModel;

import java.util.List;

public class ChatMessagesAdapter extends RecyclerView.Adapter<ChatMessagesAdapter.ViewHolder> {

    List<PersonMessageModel> items;
    Context context;

    public ChatMessagesAdapter(List<PersonMessageModel> items, Context context) {
        this.items = items;
        this.context = context;
    }

    public void refresh(List<PersonMessageModel> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    public void addMessage(PersonMessageModel item) {
        this.items.add(item);
        notifyDataSetChanged();
    }

    public void addMessageEn(PersonMessageModel item) {
        this.items.add(item);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ChatMessagesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = null;
        if (viewType == 2)
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message_in, parent, false);
        else if (viewType == 1)
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message_out, parent, false);

        assert view != null;
        return new ChatMessagesAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatMessagesAdapter.ViewHolder holder, int position) {
        try {
            holder.chatMessage.setText(getDecryptedMessage(items.get(position)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (items.get(position).sender.equals(GET_USER_PHONE_NUMBER())) return 1;
        else return 2;
    }

    private String getDecryptedMessage(PersonMessageModel model) throws Exception {
                retrieveSymmetricSecretKey(GET_SESSION_KEY());
       return do_AESDecryption(hexStringToByteArray(model.content),
                retrieveSymmetricSecretKey(GET_SESSION_KEY()));
    }



    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView chatMessage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            chatMessage = itemView.findViewById(R.id.chat_message);
        }
    }
}
