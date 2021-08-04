package com.allan.lin.zhou.scheduler;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.allan.lin.zhou.scheduler.camera.CameraView;
import com.allan.lin.zhou.scheduler.communication.Social;
import com.allan.lin.zhou.scheduler.databinding.ActivityMainBinding;
import com.allan.lin.zhou.scheduler.mind.Mindfulness;
import com.allan.lin.zhou.scheduler.reminder.list.Reminders;
import com.allan.lin.zhou.scheduler.schedule.Schedule;
import com.allan.lin.zhou.scheduler.ui.login.Login;
import com.allan.lin.zhou.scheduler.ui.login.Preferences;
import com.allan.lin.zhou.scheduler.ui.login.firebase.Constants;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.HashMap;

import static com.allan.lin.zhou.scheduler.Utilities.profileImage;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;

    private ActivityMainBinding binding;
    private Preferences preferenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        preferenceManager = new Preferences(getApplicationContext());

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);
        toolbar =  findViewById(R.id.toolbar);

        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.menu_open, R.string.menu_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        // Make the Menu Items Clickable
        navigationView.setNavigationItemSelectedListener(this);

        /* if (Utilities.isLoggedIn && Utilities.profileImage != null) {
            ImageView profile = findViewById(R.id.profilePicture);
            profile.setImageBitmap(Utilities.profileImage);
        } */

        ImageView profilePicture = findViewById(R.id.profilePicture);
        profilePicture.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, Login.class));
            }
        });

        loadUserData();
        getToken();

        // Logout
        binding.logoutButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                logout(view);
            }
        });
    }

    @Override
    public void onBackPressed() {

        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }

        else {
            super.onBackPressed();
        }
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        Intent intent;

        switch (menuItem.getItemId()) {
            case R.id.nav_home:
                break;

            case R.id.nav_schedule:
                intent = new Intent(MainActivity.this, Schedule.class);
                startActivity(intent);
                Toast.makeText(MainActivity.this, "Schedule", Toast.LENGTH_LONG).show();
                break;

            case R.id.nav_reminder:
                intent = new Intent(MainActivity.this, Reminders.class);
                startActivity(intent);
                Toast.makeText(MainActivity.this, "Reminders", Toast.LENGTH_LONG).show();
                break;

            case R.id.nav_mind:
                intent = new Intent(MainActivity.this, Mindfulness.class);
                startActivity(intent);
                Toast.makeText(MainActivity.this, "Mindfulness", Toast.LENGTH_LONG).show();
                break;

            case R.id.nav_settings:
                intent = new Intent(MainActivity.this, Settings.class);
                startActivity(intent);
                Toast.makeText(MainActivity.this, "Settings", Toast.LENGTH_LONG).show();
                break;

            case R.id.nav_login:
                intent = new Intent(MainActivity.this, Login.class);
                startActivity(intent);
                Toast.makeText(MainActivity.this, "Login", Toast.LENGTH_LONG).show();
                break;

            case R.id.nav_logout:
                logout(findViewById(R.id.navigation_view));
                Toast.makeText(MainActivity.this, "Logged Out!", Toast.LENGTH_LONG).show();
                break;

            case R.id.nav_account:
                intent = new Intent(MainActivity.this, PersonalAccount.class);
                startActivity(intent);
                Toast.makeText(MainActivity.this, "Account Settings", Toast.LENGTH_LONG).show();
                break;

            case R.id.nav_homework:
                intent = new Intent(MainActivity.this, Homework.class);
                startActivity(intent);
                Toast.makeText(MainActivity.this, "Homework", Toast.LENGTH_LONG).show();
                break;

            case R.id.nav_extracurriculars:
                intent = new Intent(MainActivity.this, Extracurriculars.class);
                startActivity(intent);
                Toast.makeText(MainActivity.this, "Extracurriculars", Toast.LENGTH_LONG).show();
                break;

            case R.id.nav_inspire:
                intent = new Intent(MainActivity.this, Inspire.class);
                startActivity(intent);
                Toast.makeText(MainActivity.this, "Inspire", Toast.LENGTH_LONG).show();
                break;

            case R.id.nav_email:
                intent = new Intent(MainActivity.this, Email.class);
                startActivity(intent);
                Toast.makeText(MainActivity.this, "Email", Toast.LENGTH_LONG).show();
                break;

            case R.id.nav_camera:
                intent = new Intent(MainActivity.this, CameraView.class);
                startActivity(intent);
                Toast.makeText(MainActivity.this, "Camera", Toast.LENGTH_LONG).show();
                break;

            case R.id.nav_social:
                intent = new Intent(MainActivity.this, Social.class);
                startActivity(intent);
                Toast.makeText(MainActivity.this, "Chat Bot", Toast.LENGTH_LONG).show();
                break;

            default:
                Toast.makeText(MainActivity.this, "Unimplemented", Toast.LENGTH_LONG).show();
                break;
        }

        return true;
    }

    private void loadUserData() {
        String greet = preferenceManager.getString(Constants.KEY_NAME);
        binding.textName.setText(greet);
        byte[] bytes = Base64.decode(preferenceManager.getString(Constants.KEY_IMAGE), Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        binding.profilePicture.setImageBitmap(bitmap);
    }

    private void showToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    private void updateToken(String token) {
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        DocumentReference documentReference =
                database.collection(Constants.KEY_COLLECTION_USERS).document(
                        preferenceManager.getString(Constants.KEY_USER_ID)
                );

        documentReference.update(Constants.KEY_FCM_TOKEN, token)
                .addOnSuccessListener(unused -> showToast("Token Updated!"))
                .addOnFailureListener(exception -> showToast(exception.getMessage()));
    }

    private void getToken() {
        FirebaseMessaging.getInstance().getToken().addOnSuccessListener(this::updateToken);
    }

    private void logoutDatabase() {
        showToast("Logging Out...");
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        DocumentReference documentReference =
                database.collection(Constants.KEY_COLLECTION_USERS).document(
                        preferenceManager.getString(Constants.KEY_USER_ID)
                );
        HashMap<String, Object> logoutUpdate = new HashMap<>();
        logoutUpdate.put(Constants.KEY_FCM_TOKEN, FieldValue.delete());
        documentReference.update(logoutUpdate)
                .addOnSuccessListener(unused -> {
                    preferenceManager.clear();
                    showToast("Logout Successful!");
                    startActivity(new Intent(getApplicationContext(), Login.class));
                })
                .addOnFailureListener(exception -> showToast("Unable to Logout"));
    }

    private void logout(View view) {
        Snackbar.make(view, "Logout?", Snackbar.LENGTH_LONG)
                .setAction("OK", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        logoutDatabase();
                    }
                }).setActionTextColor(getResources().getColor(R.color.home_action))
                .setTextColor(getResources().getColor(R.color.home_snack))
                .show();
    }
}
