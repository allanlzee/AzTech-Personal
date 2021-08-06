package com.allan.lin.zhou.scheduler.ui.login.text.message;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.allan.lin.zhou.scheduler.R;
import com.allan.lin.zhou.scheduler.Utilities;
import com.allan.lin.zhou.scheduler.databinding.UserInformationActivityBinding;
import com.allan.lin.zhou.scheduler.ui.login.Preferences;
import com.allan.lin.zhou.scheduler.ui.login.firebase.Constants;
import com.allan.lin.zhou.scheduler.ui.login.firebase.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class UserInformation extends AppCompatActivity {

    private UserInformationActivityBinding binding;
    private Toolbar toolbar;

    private FirebaseUser userRecipient;
    private Preferences preferenceManager;
    private FirebaseFirestore database;
    private Bitmap profilePicture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_information_activity);

        binding = UserInformationActivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        binding.backToMessaging.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                finish();
            }
        });

        loadUserDetails();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    private void loadUserDetails() {
        binding.editTextName.setText(Utilities.username);
        binding.editTextEmail.setText(Utilities.email);
        binding.profilePicture.setImageBitmap(Utilities.senderProfileImage);
    }
}