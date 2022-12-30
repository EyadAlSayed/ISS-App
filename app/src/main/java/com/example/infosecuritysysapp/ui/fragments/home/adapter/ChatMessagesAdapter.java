package com.example.infosecuritysysapp.ui.fragments.home.adapter;

import static com.example.infosecuritysysapp.helper.encryption.EncryptionConverters.convertByteToHexadecimal;
import static com.example.infosecuritysysapp.helper.encryption.EncryptionConverters.hexStringToByteArray;
import static com.example.infosecuritysysapp.helper.encryption.EncryptionTools.do_AESDecryption;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.infosecuritysysapp.R;
import com.example.infosecuritysysapp.config.AppConstants;
import com.example.infosecuritysysapp.helper.encryption.EncryptionConverters;
import com.example.infosecuritysysapp.helper.encryption.EncryptionTools;
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
        notifyItemChanged(items.size() - 1);
    }

    @NonNull
    @Override
    public ChatMessagesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = null;
        if (viewType == R.layout.item_message_in)
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message_in, parent, false);
        else if (viewType == R.layout.item_message_out)
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message_out, parent, false);

        assert view != null;
        return new ChatMessagesAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatMessagesAdapter.ViewHolder holder, int position) {
        try {
            holder.chatMessage.setText(decryptMessage(items.get(position).content));
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
        return items.get(position).type;
    }

    private String decryptMessage(String message) throws Exception {
        return do_AESDecryption(hexStringToByteArray(message), AppConstants.sessionKey);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView chatMessage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            chatMessage = itemView.findViewById(R.id.chat_message);
        }
    }
}
