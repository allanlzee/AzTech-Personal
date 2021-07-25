package com.allan.lin.zhou.scheduler;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ChatAdapter extends RecyclerView.Adapter {

    private ArrayList<Chats> chatsList;
    private Context context;

    public ChatAdapter(ArrayList<Chats> chatsList, Context context) {
        this.chatsList = chatsList;
        this.context = context;
    }

    @NonNull
    @NotNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case 0:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_text_message, parent, false);
                return new UserViewHolder(view);

            case 1:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bot_text_message, parent, false);
                return new BotViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull RecyclerView.ViewHolder holder, int position) {
        Chats chats = chatsList.get(position);

        switch (chats.getSender()) {
            case "user":
                ((UserViewHolder) holder).userMessage.setText(chats.getMessage());
                break;

            case "bot":
                ((BotViewHolder) holder).botMessage.setText(chats.getMessage());
                break;
        }
    }

    @Override
    public int getItemViewType(int position) {
        switch (chatsList.get(position).getSender()) {
            case "user":
                return 0;

            case "bot":
                return 1;

            default:
                return -1;
        }
    }

    @Override
    public int getItemCount() {
        return chatsList.size();
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder {
        TextView userMessage;

        public UserViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            userMessage = itemView.findViewById(R.id.userTextMessage);
        }
    }

    public static class BotViewHolder extends RecyclerView.ViewHolder {
        TextView botMessage;

        public BotViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            botMessage = itemView.findViewById(R.id.botTextMessage);
        }
    }
}
