package com.allan.lin.zhou.scheduler;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.allan.lin.zhou.scheduler.ui.login.Login;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;

    CardView schedule;
    CardView reminders;
    CardView mindfulness;
    CardView homework;
    CardView extracurriculars;
    CardView inspire;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);
        toolbar =  findViewById(R.id.toolbar);

        setSupportActionBar(toolbar); // Theme.AppCompat.NoActionBar

        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.menu_open, R.string.menu_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        // Make the Menu Items Clickable
        navigationView.setNavigationItemSelectedListener(this);

        // CardViews
        schedule = findViewById(R.id.schedule_card);
        reminders = findViewById(R.id.reminders_card);
        mindfulness = findViewById(R.id.mindfulness_card);
        homework = findViewById(R.id.homework_card);
        extracurriculars = findViewById(R.id.extracurriculars_card);
        inspire = findViewById(R.id.inspire_card);

        // Bring to Activity
        schedule.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Schedule.class));
            }
        });

        reminders.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Reminders.class));
            }
        });

        mindfulness.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Mindfulness.class));
            }
        });

        homework.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Homework.class));
            }
        });

        extracurriculars.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Extracurriculars.class));
            }
        });

        inspire.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Inspire.class));
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
                Toast.makeText(MainActivity.this, "Logged Out!", Toast.LENGTH_LONG).show();
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

            default:
                Toast.makeText(MainActivity.this, "Unimplemented", Toast.LENGTH_LONG).show();
                break;
        }

        return true;
    }
    
    protected void logout(View view) {
        Snackbar.make(view, "Logout?", Snackbar.LENGTH_INDEFINITE)
                .setAction("OK", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(MainActivity.this, "Logged Out!", Toast.LENGTH_LONG).show();
                    }
                }).setActionTextColor(getResources().getColor(R.color.home_action))
                .setTextColor(getResources().getColor(R.color.home_snack))
                .show();
    }
}