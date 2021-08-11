package com.allan.lin.zhou.scheduler.ui.login.adapters;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.allan.lin.zhou.scheduler.R;
import com.allan.lin.zhou.scheduler.Utilities;
import com.allan.lin.zhou.scheduler.databinding.ReceivedTextMessageBinding;
import com.allan.lin.zhou.scheduler.databinding.SentTextMessageBinding;
import com.allan.lin.zhou.scheduler.ui.login.text.message.ChatMessageObject;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class TextMessagesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final ArrayList<ChatMessageObject> textMessages;
    private Bitmap recipientProfilePicture;
    private final String senderID;

    // User ID (Recipient/Sender)
    public static final int VIEW_TYPE_SENT = 1;
    public static final int VIEW_TYPE_RECEIVED = 2;

    // Constructor
    public TextMessagesAdapter(ArrayList<ChatMessageObject> textMessages, Bitmap recipientProfilePicture, String senderID) {
        this.textMessages = textMessages;
        this.recipientProfilePicture = recipientProfilePicture;
        this.senderID = senderID;
    }

    public void setRecipientProfilePicture(Bitmap bitmap) {
        recipientProfilePicture = bitmap;
    }

    // ****************** Inherited Recycler View Methods ****************** //
    @NonNull
    @NotNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_SENT) {
            return new SentMessageViewHolder(
                    SentTextMessageBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false)
            );
        } else {
            return new ReceivedMessageViewHolder(
                    ReceivedTextMessageBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false)
            );
        }
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == VIEW_TYPE_SENT) {
            ((SentMessageViewHolder) holder).setData(textMessages.get(position), Utilities.senderProfileImage);
        } else {
            ((ReceivedMessageViewHolder) holder).setData(textMessages.get(position), recipientProfilePicture);
        }
    }

    @Override
    public int getItemCount() {
        return textMessages.size();
    }

    @Override
    public int getItemViewType(int position) {
        // If the message is being sent
        if (textMessages.get(position).senderID.equals(senderID)) {
            return VIEW_TYPE_SENT;
        } else {
            return VIEW_TYPE_RECEIVED;
        }
    }

    // ****************** Inherited Recycler View Methods ****************** //

    static class SentMessageViewHolder extends RecyclerView.ViewHolder {

        private final SentTextMessageBinding binding;

        SentMessageViewHolder(SentTextMessageBinding sentTextMessageBinding) {
            super(sentTextMessageBinding.getRoot());
            binding = sentTextMessageBinding;
        }

        void setData(ChatMessageObject chatMessage, Bitmap profilePicture) {
            binding.userTextMessage.setText(chatMessage.messageContent);
            binding.textDateTime.setText(chatMessage.messageDateTime);
            binding.profilePicture.setImageBitmap(profilePicture);
            chooseBackgroundTheme(Utilities.backgroundTheme);

            if (Utilities.messageTheme != null) {
                chooseSenderMessageTheme(Utilities.messageTheme);
            }
        }

        private void chooseBackgroundTheme(String color) {
            switch (color) {
                case "Red":
                    binding.textMessageBackground.setBackgroundColor(Color.parseColor("#ff0000"));
                    binding.textDateTime.setTextColor(Color.parseColor("#FFFFFF"));
                    binding.cardViewTextMsg.setBackgroundColor(Color.parseColor("#ff0000"));
                    break;
                case "Orange":
                    binding.textMessageBackground.setBackgroundColor(Color.parseColor("#ffa500"));
                    binding.textDateTime.setTextColor(Color.parseColor("#FFFFFF"));
                    binding.cardViewTextMsg.setBackgroundColor(Color.parseColor("#ffa500"));
                    break;
                case "Green":
                    binding.textMessageBackground.setBackgroundColor(Color.parseColor("#00ff00"));
                    binding.textDateTime.setTextColor(Color.parseColor("#FFFFFF"));
                    binding.cardViewTextMsg.setBackgroundColor(Color.parseColor("#00ff00"));
                    break;
                case "Blue":
                    binding.textMessageBackground.setBackgroundColor(Color.parseColor("#FF00B0FF"));
                    binding.textDateTime.setTextColor(Color.parseColor("#FFFFFF"));
                    binding.cardViewTextMsg.setBackgroundColor(Color.parseColor("#FF00B0FF"));
                    break;
                case "Purple":
                    binding.textMessageBackground.setBackgroundColor(Color.parseColor("#8a2be2"));
                    binding.textDateTime.setTextColor(Color.parseColor("#FFFFFF"));
                    binding.cardViewTextMsg.setBackgroundColor(Color.parseColor("#8a2be2"));
                    break;
                default:
                    binding.textMessageBackground.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    binding.textDateTime.setTextColor(Color.parseColor("#A9A9A9"));
                    binding.cardViewTextMsg.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    break;
            }
        }

        private void chooseSenderMessageTheme(String color) {
            switch (color) {
                case "Red":
                    binding.relativeViewTextMsg.setBackgroundColor(Color.parseColor("#ff0000"));
                    break;
                case "Orange":
                    binding.relativeViewTextMsg.setBackgroundColor(Color.parseColor("#ffa500"));
                    break;
                case "Green":
                    binding.relativeViewTextMsg.setBackgroundColor(Color.parseColor("#00ff00"));
                    break;
                case "Blue":
                    binding.relativeViewTextMsg.setBackgroundColor(Color.parseColor("#FF00B0FF"));
                    break;
                case "Purple":
                    binding.relativeViewTextMsg.setBackgroundColor(Color.parseColor("#8a2be2"));
                    break;
                default:
                    binding.relativeViewTextMsg.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    break;
            }
        }
    }

    static class ReceivedMessageViewHolder extends RecyclerView.ViewHolder {

        private final ReceivedTextMessageBinding binding;

        ReceivedMessageViewHolder(ReceivedTextMessageBinding receivedTextMessageBinding) {
            super(receivedTextMessageBinding.getRoot());
            binding = receivedTextMessageBinding;
        }

        void setData(ChatMessageObject chatMessage, Bitmap profilePicture) {
            binding.userTextMessage.setText(chatMessage.messageContent);
            binding.textDateTime.setText(chatMessage.messageDateTime);
            if (profilePicture != null) {
                binding.profilePicture.setImageBitmap(profilePicture);
            }
            chooseBackgroundTheme(Utilities.backgroundTheme);

            if (Utilities.receivedTheme != null) {
                chooseReceiverMessageTheme(Utilities.receivedTheme);
            }
        }

        private void chooseBackgroundTheme(String color) {
            switch (color) {
                case "Red":
                    binding.textMessageBackground.setBackgroundColor(Color.parseColor("#ff0000"));
                    binding.textDateTime.setTextColor(Color.parseColor("#FFFFFF"));
                    binding.cardViewTextMsg.setBackgroundColor(Color.parseColor("#ff0000"));
                    break;
                case "Orange":
                    binding.textMessageBackground.setBackgroundColor(Color.parseColor("#ffa500"));
                    binding.textDateTime.setTextColor(Color.parseColor("#FFFFFF"));
                    binding.cardViewTextMsg.setBackgroundColor(Color.parseColor("#ffa500"));
                    break;
                case "Green":
                    binding.textMessageBackground.setBackgroundColor(Color.parseColor("#00ff00"));
                    binding.textDateTime.setTextColor(Color.parseColor("#FFFFFF"));
                    binding.cardViewTextMsg.setBackgroundColor(Color.parseColor("#00ff00"));
                    break;
                case "Blue":
                    binding.textMessageBackground.setBackgroundColor(Color.parseColor("#FF00B0FF"));
                    binding.textDateTime.setTextColor(Color.parseColor("#FFFFFF"));
                    binding.cardViewTextMsg.setBackgroundColor(Color.parseColor("#FF00B0FF"));
                    break;
                case "Purple":
                    binding.textMessageBackground.setBackgroundColor(Color.parseColor("#8a2be2"));
                    binding.textDateTime.setTextColor(Color.parseColor("#FFFFFF"));
                    binding.cardViewTextMsg.setBackgroundColor(Color.parseColor("#8a2be2"));
                    break;
                default:
                    binding.textMessageBackground.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    binding.textDateTime.setTextColor(Color.parseColor("#A9A9A9"));
                    binding.cardViewTextMsg.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    break;
            }
        }

        private void chooseReceiverMessageTheme(String color) {
            switch (color) {
                case "Red":
                    binding.relativeViewTextMsg.setBackgroundColor(Color.parseColor("#ff0000"));
                    break;
                case "Orange":
                    binding.relativeViewTextMsg.setBackgroundColor(Color.parseColor("#ffa500"));
                    break;
                case "Green":
                    binding.relativeViewTextMsg.setBackgroundColor(Color.parseColor("#00ff00"));
                    break;
                case "Blue":
                    binding.relativeViewTextMsg.setBackgroundColor(Color.parseColor("#FF00B0FF"));
                    break;
                case "Purple":
                    binding.relativeViewTextMsg.setBackgroundColor(Color.parseColor("#8a2be2"));
                    break;
                default:
                    binding.relativeViewTextMsg.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    break;
            }
        }
    }
}
