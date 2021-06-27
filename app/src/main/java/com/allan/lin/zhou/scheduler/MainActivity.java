package com.allan.lin.zhou.scheduler;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    // View header = navigationView.getHeaderView(0);
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

            default:
                Toast.makeText(MainActivity.this, "Unimplemented", Toast.LENGTH_LONG).show();
                break;

        }

        return true;
    }
}