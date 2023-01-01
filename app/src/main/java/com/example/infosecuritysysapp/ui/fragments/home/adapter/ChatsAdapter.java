package com.example.infosecuritysysapp.ui.fragments.home.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.infosecuritysysapp.R;
import com.example.infosecuritysysapp.model.PersonModel;

import java.util.ArrayList;
import java.util.List;

public class ChatsAdapter extends RecyclerView.Adapter<ChatsAdapter.ViewHolder> {

    private List<PersonModel> items;
    private Context context;
    private OnChatsClicked onChatsClicked;


    public ChatsAdapter(ArrayList<PersonModel> items, Context context, OnChatsClicked onChatsClicked) {
        this.items = items;
        this.context = context;
        this.onChatsClicked = onChatsClicked;
    }

    public void refresh(List<PersonModel> items) {
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
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onChatsClicked.onClick(items.get(getLayoutPosition()),getLayoutPosition());
        }
    }

    public interface OnChatsClicked{
        void onClick(PersonModel personModel,int position);
    }
}
