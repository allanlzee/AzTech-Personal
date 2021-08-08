package com.allan.lin.zhou.scheduler.ui.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.allan.lin.zhou.scheduler.R;
import com.allan.lin.zhou.scheduler.databinding.UsersActivityBinding;
import com.allan.lin.zhou.scheduler.ui.login.adapters.UserListAdapter;
import com.allan.lin.zhou.scheduler.ui.login.availability.BaseAvailability;
import com.allan.lin.zhou.scheduler.ui.login.firebase.Constants;
import com.allan.lin.zhou.scheduler.ui.login.firebase.FirebaseUser;
import com.allan.lin.zhou.scheduler.ui.login.text.message.TextMessaging;
import com.allan.lin.zhou.scheduler.ui.login.text.message.listeners.UserListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class UsersActivity extends BaseAvailability implements UserListener {

    private UsersActivityBinding binding;
    private Preferences preferenceManager;
    private Toolbar toolbar;

    private List<FirebaseUser> users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.users_activity);

        binding = UsersActivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        preferenceManager = new Preferences(getApplicationContext());

        getUserList();
    }

    private void loadingProgressBar(Boolean isLoading) {
        if (isLoading) {
            binding.progressBar.setVisibility(View.VISIBLE);
            binding.errorMessage.setVisibility(View.INVISIBLE);
        } else {
            binding.progressBar.setVisibility(View.INVISIBLE);
        }
    }

    // Error Message
    private void showErrorMessage() {
        String error = "No User Available";
        binding.errorMessage.setText(error);
        binding.errorMessage.setVisibility(View.VISIBLE);
        binding.progressBar.setVisibility(View.INVISIBLE);
    }

    // List of Users using RecyclerView
    private void getUserList() {
        loadingProgressBar(true);
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        database.collection(Constants.KEY_COLLECTION_USERS)
                .get()
                .addOnCompleteListener(task -> {
                    loadingProgressBar(false);
                    String currentUserID = preferenceManager.getString(Constants.KEY_USER_ID);
                    if (task.isSuccessful() && task.getResult() != null) {
                        users = new ArrayList<>();

                        // Use database to compile a user list (excluding the current user)
                        for (QueryDocumentSnapshot queryDocumentSnapshot : task.getResult()) {
                            if (currentUserID.equals(queryDocumentSnapshot.getId())) {
                                continue;
                            }

                            // Instantiate a new Firebase User for the arraylist used in the recycler view
                            FirebaseUser user = new FirebaseUser();
                            user.username = queryDocumentSnapshot.getString(Constants.KEY_NAME);
                            user.email = queryDocumentSnapshot.getString(Constants.KEY_EMAIL);
                            user.image = queryDocumentSnapshot.getString(Constants.KEY_IMAGE);
                            user.fcmToken = queryDocumentSnapshot.getString(Constants.KEY_FCM_TOKEN);
                            user.id = queryDocumentSnapshot.getId();
                            users.add(user);
                        }

                        // Set up RecyclerView Adapter
                        if (users.size() > 0) {
                            LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                            binding.userRecyclerView.setLayoutManager(layoutManager);
                            UserListAdapter userListAdapter = new UserListAdapter(users, this);
                            binding.userRecyclerView.setAdapter(userListAdapter);
                            binding.userRecyclerView.setVisibility(View.VISIBLE);
                        } else {
                            showErrorMessage();
                        }
                    } else {
                        showErrorMessage();
                    }
                });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    // Recycler View Item Clicking

    @Override
    public void onUserClicked(FirebaseUser user) {
        Intent intent = new Intent(getApplicationContext(), TextMessaging.class);
        intent.putExtra(Constants.KEY_USER, user);
        startActivity(intent);
        finish();
    }

    // **************************************************** //
}