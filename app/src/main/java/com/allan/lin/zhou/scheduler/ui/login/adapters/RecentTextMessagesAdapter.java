package com.allan.lin.zhou.scheduler.ui.login.adapters;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.allan.lin.zhou.scheduler.databinding.RecentUserMessageBinding;
import com.allan.lin.zhou.scheduler.ui.login.firebase.FirebaseUser;
import com.allan.lin.zhou.scheduler.ui.login.text.message.ChatMessageObject;
import com.allan.lin.zhou.scheduler.ui.login.text.message.listeners.RecentMessageListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class RecentTextMessagesAdapter extends RecyclerView.Adapter<RecentTextMessagesAdapter.ConversionViewHolder> {

    // List to Store Messages for Recycler View
    private final ArrayList<ChatMessageObject> textMessages;
    private final RecentMessageListener recentMessageListener;

    public RecentTextMessagesAdapter(ArrayList<ChatMessageObject> textMessages, RecentMessageListener recentMessageListener) {
        this.textMessages = textMessages;
        this.recentMessageListener = recentMessageListener;
    }

    private Bitmap getProfilePicture(String encodedImage) {
        byte[] bytes = Base64.decode(encodedImage, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }


    // **************************** Recycler View Inherited Methods **************************** //
    @NonNull
    @NotNull
    @Override
    public ConversionViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        return new ConversionViewHolder(
                RecentUserMessageBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ConversionViewHolder holder, int position) {
        holder.setData(textMessages.get(position));
    }

    @Override
    public int getItemCount() {
        return textMessages.size();
    }

    // **************************** Recycler View Inherited Methods **************************** //

    // Conversion View Holder for Recent Message Item
    class ConversionViewHolder extends RecyclerView.ViewHolder {
        RecentUserMessageBinding binding;

        // Constructor
        ConversionViewHolder(RecentUserMessageBinding recentUserMessageBinding) {
            super(recentUserMessageBinding.getRoot());
            binding = recentUserMessageBinding;
        }

        // Set Profile Picture and TextViews for User
        void setData(ChatMessageObject chatMessageObject) {
            binding.profilePicture.setImageBitmap(getProfilePicture(chatMessageObject.conversionProfileImage));
            binding.username.setText(chatMessageObject.conversionUsername);
            binding.recentTextMessage.setText(chatMessageObject.messageContent);

            binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    FirebaseUser firebaseUser = new FirebaseUser();
                    firebaseUser.id = chatMessageObject.conversionId;
                    firebaseUser.username = chatMessageObject.conversionUsername;
                    firebaseUser.image = chatMessageObject.conversionProfileImage;
                    recentMessageListener.onRecentMessageClicked(firebaseUser);
                }
            });
        }
    }
}
