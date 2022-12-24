package com.example.infosecuritysysapp.ui.fragments.home.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.infosecuritysysapp.R;

import java.util.ArrayList;

public class ChatsAdapter extends RecyclerView.Adapter<ChatsAdapter.ViewHolder> {

    private ArrayList<String> items;
    private Context context;
    private OnChatsClicked onChatsClicked;


    public ChatsAdapter(ArrayList<String> items, Context context, OnChatsClicked onChatsClicked) {
        this.items = items;
        this.context = context;
        this.onChatsClicked = onChatsClicked;
    }

    public void refresh(ArrayList<String> items) {
        this.items = items;
        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ChatsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chats, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatsAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onChatsClicked.onClick();
        }
    }

    public interface OnChatsClicked{
        void onClick();
    }
}
