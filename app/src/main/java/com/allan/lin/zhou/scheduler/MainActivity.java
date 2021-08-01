package com.allan.lin.zhou.scheduler;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.allan.lin.zhou.scheduler.camera.CameraView;
import com.allan.lin.zhou.scheduler.communication.Social;
import com.allan.lin.zhou.scheduler.mind.Mindfulness;
import com.allan.lin.zhou.scheduler.reminder.list.Reminders;
import com.allan.lin.zhou.scheduler.schedule.Schedule;
import com.allan.lin.zhou.scheduler.ui.login.Login;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;

    /*
    CardView schedule;
    CardView reminders;
    CardView mindfulness;
    CardView homework;
    CardView extracurriculars;
    CardView inspire; */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);
        toolbar =  findViewById(R.id.toolbar);

        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.menu_open, R.string.menu_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        // Make the Menu Items Clickable
        navigationView.setNavigationItemSelectedListener(this);

        /*
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
        }); */


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

            /* case R.id.nav_login:
                intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                Toast.makeText(MainActivity.this, "Login", Toast.LENGTH_LONG).show();
                break; */

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

/* <?xml version="1.0" encoding="utf-8"?>
<RelativeLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".ui.login.Login">

    <include
        android:id="@+id/toolbar"
        layout="@layout/toolbar" />

    <ScrollView
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:layout_below="@+id/toolbar"
        android:background="@drawable/login_gradient"
        android:clipToPadding="false"
        android:overScrollMode="never"
        android:scrollbars="none"
        >

        <LinearLayout
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:gravity="center_horizontal"
            android:orientation="vertical">

            <TextView
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:layout_marginTop="40dp"
                android:text="Welcome!"
                android:textColor="@color/white"
                android:textSize="24sp"
                android:textStyle="bold"
                />

            <TextView
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:layout_marginTop="15dp"
                android:text="@string/login_to_continue"
                android:textColor="@color/white"
                android:textSize="16sp"
                android:textStyle="bold"
                />

            <EditText
                android:id="@+id/email"
                android:layout_width="350dp"
                android:layout_height="45dp"
                android:layout_marginTop="40dp"
                android:background="@drawable/input_background"
                android:textColor="@color/black"
                android:textSize="18sp"
                android:hint="@string/emailText"
                android:textColorHint="@color/hintText"
                android:imeOptions="actionNext"
                android:importantForAutofill="no"
                android:inputType="textEmailAddress"
                android:paddingStart="16dp"
                android:paddingEnd="16dp"
                />

            <EditText
                android:id="@+id/password"
                android:layout_width="350dp"
                android:layout_height="45dp"
                android:layout_marginTop="40dp"
                android:background="@drawable/input_background"
                android:textColor="@color/black"
                android:textSize="18sp"
                android:hint="@string/passwordText"
                android:textColorHint="@color/hintText"
                android:imeOptions="actionDone"
                android:importantForAutofill="no"
                android:inputType="textPassword"
                android:paddingStart="16dp"
                android:paddingEnd="16dp"
                />

            <com.google.android.material.button.MaterialButton
                android:id="@+id/signInButton"
                android:layout_width="350dp"
                android:layout_height="60dp"
                android:layout_marginTop="25dp"
                android:text="@string/sign_in"
                android:textColor="@color/white"
                android:textStyle="bold"
                android:textSize="20sp"
                android:gravity="center"
                android:textAlignment="center"
                app:cornerRadius="40dp" />

            <TextView
                android:id="@+id/newAccount"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:layout_marginTop="40dp"
                android:text="@string/create_new_account"
                android:textColor="@color/white"
                android:textSize="18sp"
                android:textStyle="bold" />

        </LinearLayout>

    </ScrollView>

</RelativeLayout> */