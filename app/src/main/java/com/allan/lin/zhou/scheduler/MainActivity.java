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
import com.allan.lin.zhou.scheduler.school.Extracurriculars;
import com.allan.lin.zhou.scheduler.school.Homework;
import com.allan.lin.zhou.scheduler.school.Inspire;
import com.allan.lin.zhou.scheduler.ui.login.Login;
import com.allan.lin.zhou.scheduler.ui.login.Messaging;
import com.allan.lin.zhou.scheduler.ui.login.Preferences;
import com.allan.lin.zhou.scheduler.ui.login.firebase.Constants;
import com.allan.lin.zhou.scheduler.ui.login.firebase.PersonalAccount;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    // Online Status
    private DocumentReference documentReference;

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

        // Checks if user is online
        FirebaseFirestore database = FirebaseFirestore.getInstance();

        // Retrieves user IDs from the Users section in Firebase
        documentReference = database.collection(Constants.KEY_COLLECTION_USERS)
                .document(preferenceManager.getString(Constants.KEY_USER_ID));

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);
        toolbar =  findViewById(R.id.toolbar);

        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.menu_open, R.string.menu_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        // Make the Menu Items Clickable
        navigationView.setNavigationItemSelectedListener(this);

        ImageView profilePicture = findViewById(R.id.profilePicture);
        profilePicture.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, PersonalAccount.class));
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
                intent = new Intent(MainActivity.this, Messaging.class);
                startActivity(intent);
                Toast.makeText(MainActivity.this, "Messaging", Toast.LENGTH_LONG).show();
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
        String name = preferenceManager.getString(Constants.KEY_NAME);
        binding.textName.setText(name);
        byte[] bytes = Base64.decode(preferenceManager.getString(Constants.KEY_IMAGE), Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        binding.profilePicture.setImageBitmap(bitmap);

        Utilities.username = preferenceManager.getString(Constants.KEY_NAME);
        Utilities.senderProfileImage = bitmap;
    }

    private void showToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    private void updateToken(String token) {
        preferenceManager.putString(Constants.KEY_FCM_TOKEN, token);
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        DocumentReference documentReference =
                database.collection(Constants.KEY_COLLECTION_USERS).document(
                        preferenceManager.getString(Constants.KEY_USER_ID)
                );

        documentReference.update(Constants.KEY_FCM_TOKEN, token)
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

    // If user closes activity, they will go offline
    @Override
    protected void onPause() {
        super.onPause();
        documentReference.update(Constants.KEY_AVAILABILITY, 1);
        binding.onlineStatus.setVisibility(View.INVISIBLE);
    }

    // If user is on the app, they will be online
    @Override
    protected void onResume() {
        super.onResume();
        documentReference.update(Constants.KEY_AVAILABILITY, 0);
        binding.onlineStatus.setVisibility(View.VISIBLE);
    }
}
