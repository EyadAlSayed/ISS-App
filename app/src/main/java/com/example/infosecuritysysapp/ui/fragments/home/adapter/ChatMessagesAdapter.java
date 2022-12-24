package com.example.infosecuritysysapp.ui.fragments.home.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.infosecuritysysapp.R;
import com.example.infosecuritysysapp.model.ChatMessageModel;

import java.util.ArrayList;

public class ChatMessagesAdapter extends RecyclerView.Adapter<ChatMessagesAdapter.ViewHolder> {

    ArrayList<ChatMessageModel> items;
    Context context;

    public ChatMessagesAdapter(ArrayList<ChatMessageModel> items, Context context) {
        this.items = items;
        this.context = context;
    }

    public void refresh(ArrayList<ChatMessageModel> items) {
        this.items = items;
        notifyDataSetChanged();
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

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public int getItemViewType(int position) {
        return items.get(position).getViewType();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
