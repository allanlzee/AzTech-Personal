package com.allan.lin.zhou.scheduler.ui.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.allan.lin.zhou.scheduler.MainActivity;
import com.allan.lin.zhou.scheduler.R;
import com.allan.lin.zhou.scheduler.Utilities;
import com.allan.lin.zhou.scheduler.databinding.LoginActivityBinding;
import com.allan.lin.zhou.scheduler.ui.login.firebase.Constants;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class Login extends AppCompatActivity {

    private Toolbar toolbar;
    private LoginActivityBinding binding;

    // Preference Manager for Firebase
    private Preferences preferenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        binding = LoginActivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        preferenceManager = new Preferences(getApplicationContext());

        binding.newAccount.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Toast.makeText(Login.this, "Account Sign Up", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Login.this, SignUp.class));
            }
        });

        // Signs user in when button is clicked
        binding.signInButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                // addDataToFirestore();
                if (isValidLogin()) {
                    signIn();
                }
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

    // Signs user in based on Firestore Database
    private void signIn() {
        loadingProgressBar(true);
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        database.collection(Constants.KEY_COLLECTION_USERS)
                .whereEqualTo(Constants.KEY_EMAIL, binding.email.getText().toString())
                .whereEqualTo(Constants.KEY_PASSWORD, binding.password.getText().toString())
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && task.getResult() != null
                            && task.getResult().getDocuments().size() > 0) {
                        DocumentSnapshot documentSnapshot = task.getResult().getDocuments().get(0);
                        preferenceManager.putBoolean(Constants.KEY_IS_SIGNED_IN, true);
                        preferenceManager.putString(Constants.KEY_USER_ID, documentSnapshot.getId());
                        preferenceManager.putString(Constants.KEY_NAME, documentSnapshot.getString(Constants.KEY_NAME));
                        preferenceManager.putString(Constants.KEY_IMAGE, documentSnapshot.getString(Constants.KEY_IMAGE));
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        Toast.makeText(Login.this, "Login Successful!", Toast.LENGTH_SHORT).show();
                        Utilities.isLoggedIn = true;
                    } else {
                        loadingProgressBar(false);
                        showToast("Unable to Login!");
                    }
                })
                .addOnFailureListener(exception -> showToast(exception.getMessage()));
    }

    // Changes button view and progress bar based on login status
    private void loadingProgressBar(Boolean isLoading) {
        if (isLoading) {
            binding.signInButton.setVisibility(View.INVISIBLE);
            binding.progressBar.setVisibility(View.VISIBLE);
        } else {
            binding.signInButton.setVisibility(View.VISIBLE);
            binding.progressBar.setVisibility(View.INVISIBLE);
        }
    }

    // Testing Function for Firebase Firestore Database
    private void addDataToFirestore() {
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        HashMap<String, Object> data = new HashMap<>();
        data.put("First-Name", "Allan");
        data.put("Last-Name", "Zhou");
        database.collection("users")
                .add(data)
                .addOnSuccessListener(documentReference -> {
                    Toast.makeText(getApplicationContext(), "Data Inserted", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(exception -> {
                    Toast.makeText(getApplicationContext(), exception.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }

    // Checks if all necessary login information is in a valid format
    private Boolean isValidLogin() {
        if (binding.email.getText().toString().trim().isEmpty()) {
            showToast("Enter Email!");
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(binding.email.getText().toString()).matches()) {
            showToast("Enter Valid Email!");
            return false;
        } else if (binding.password.getText().toString().trim().isEmpty()) {
            showToast("Enter Password!");
            return false;
        } else {
            return true;
        }
    }

    // Utility function for Toast messages
    private void showToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }
}