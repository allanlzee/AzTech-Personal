package com.allan.lin.zhou.scheduler.ui.login.adapters;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.allan.lin.zhou.scheduler.databinding.UserItemBinding;
import com.allan.lin.zhou.scheduler.ui.login.firebase.FirebaseUser;
import com.allan.lin.zhou.scheduler.ui.login.text.message.listeners.UserListener;

import java.util.List;

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.UserListViewHolder> {

    // User List
    private final List<FirebaseUser> users;

    // Custom User Listener
    private final UserListener userListener;

    public UserListAdapter(List<FirebaseUser> users, UserListener userListener) {
        this.users = users;
        this.userListener = userListener;
    }

    // Object Class for View Holders (Recycler View)
    class UserListViewHolder extends RecyclerView.ViewHolder {

        UserItemBinding binding;

        public UserListViewHolder(UserItemBinding userItemBinding) {
            super(userItemBinding.getRoot());
            binding = userItemBinding;
        }

        protected void setUserData(FirebaseUser user) {
            binding.username.setText(user.username);
            binding.userEmail.setText(user.email);
            binding.profilePicture.setImageBitmap(getProfilePicture(user.image));

            // Lambda to make the recycler view items clickable
            binding.getRoot().setOnClickListener(view -> userListener.onUserClicked(user));
        }
    }

    private Bitmap getProfilePicture(String encodedImage) {
        byte[] bytes = Base64.decode(encodedImage, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }

    // Inherited Methods for Recycler View
    @NonNull
    @Override
    public UserListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        UserItemBinding userItemBinding = UserItemBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false
        );
        return new UserListViewHolder(userItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull UserListAdapter.UserListViewHolder holder, int position) {
        holder.setUserData(users.get(position));
    }

    @Override
    public int getItemCount() {
        return users.size();
    }
    // **************************************************************************** //


}
