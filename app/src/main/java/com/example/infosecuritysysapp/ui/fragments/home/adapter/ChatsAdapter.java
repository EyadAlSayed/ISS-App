package com.example.infosecuritysysapp.ui.fragments.home.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.infosecuritysysapp.R;
import com.example.infosecuritysysapp.databinding.ItemChatsBinding;
import com.example.infosecuritysysapp.model.PersonContact;
import com.example.infosecuritysysapp.model.PersonModel;

import java.util.ArrayList;
import java.util.List;

public class ChatsAdapter extends RecyclerView.Adapter<ChatsAdapter.ViewHolder> {

    private List<PersonContact> items;
    private Context context;
    private OnChatsClicked onChatsClicked;


    public ChatsAdapter(ArrayList<PersonContact> items, Context context, OnChatsClicked onChatsClicked) {
        this.items = items;
        this.context = context;
        this.onChatsClicked = onChatsClicked;
    }

    public void refresh(List<PersonContact> items) {
        this.items = items;
        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ChatsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater =
                LayoutInflater.from(parent.getContext());
        ItemChatsBinding binding =
                ItemChatsBinding.inflate(layoutInflater, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatsAdapter.ViewHolder holder, int position) {
        holder.binding.chatName.setText( items.get(position).name);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ItemChatsBinding binding;
        public ViewHolder(@NonNull ItemChatsBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onChatsClicked.onClick(items.get(getLayoutPosition()),getLayoutPosition());
        }
    }

    public interface OnChatsClicked{
        void onClick(PersonContact personModel,int position);
    }
}
