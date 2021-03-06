package com.allan.lin.zhou.scheduler.ui.login.text.message;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;

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
import com.allan.lin.zhou.scheduler.ui.login.adapters.UserListAdapter;
import com.allan.lin.zhou.scheduler.ui.login.firebase.Constants;
import com.allan.lin.zhou.scheduler.ui.login.firebase.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

public class UserInformation extends AppCompatActivity {

    private UserInformationActivityBinding binding;
    private Toolbar toolbar;

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
        binding.editTextName.setText(Utilities.textMessageRecipient.username);

        FirebaseFirestore database = FirebaseFirestore.getInstance();
        database.collection(Constants.KEY_COLLECTION_USERS)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && task.getResult() != null) {

                        // Use database to compile a user list (excluding the current user)
                        for (QueryDocumentSnapshot queryDocumentSnapshot : task.getResult()) {
                            if (queryDocumentSnapshot.getString(Constants.KEY_NAME).equals(Utilities.textMessageRecipient.username)) {
                                binding.editTextEmail.setText(queryDocumentSnapshot.getString(Constants.KEY_EMAIL));

                                /*
                                try {
                                    int onlineStatus = (Integer) queryDocumentSnapshot.get(Constants.KEY_AVAILABILITY);

                                    String status;
                                    if (onlineStatus == 1) {
                                        status = "Status: Online";
                                    } else {
                                        status = "Status: Offline";
                                    }
                                    binding.onlineStatus.setText(status);
                                } catch (NullPointerException e) {
                                    e.printStackTrace();
                                } */
                                break;
                            }
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "No User Available", Toast.LENGTH_LONG).show();
                    }
                });

        byte[] bytes = Base64.decode(Utilities.textMessageRecipient.image, Base64.DEFAULT);
        Bitmap decodedBitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        binding.profilePicture.setImageBitmap(decodedBitmap);
    }
}