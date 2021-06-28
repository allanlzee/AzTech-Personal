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
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.allan.lin.zhou.scheduler.ui.login.Login;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;

    int prevQuote;
    Button inspire;
    TextView quote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize Toolbar
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);
        toolbar =  findViewById(R.id.toolbar);

        inspire = findViewById(R.id.quoteGenerator);
        quote = findViewById(R.id.quote);

        setSupportActionBar(toolbar); // Theme.AppCompat.NoActionBar

        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.menu_open, R.string.menu_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        // Make the Menu Items Clickable
        navigationView.setNavigationItemSelectedListener(this);

        // Quote Generator
        randomQuote();
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

            default:
                Toast.makeText(MainActivity.this, "Unimplemented", Toast.LENGTH_LONG).show();
                break;
        }

        return true;
    }

    // Quote Changing
    private void randomQuote() {
        final String[] quotes = {"Once you choose hope, anything is possible.",
                "Failure is not the opposite of success. It is part of it.",
                "Fall down 7 times, get up 8.",
                "Wherever you go, go with all your heart.",
                "It is during our darkest moments that we must focus on the light.",
                "Be stronger than your excuses.",
                "You get in life what you have the courage to ask for.",
                "Who you are is defined by what you're willing to struggle for.",
                "Don't just sit there. Do something. The answers will follow.",
                "The more something threatens your identity, the more you will avoid it."
        };

        inspire.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                int random = (int) (Math.random() * quotes.length);
                if (random == prevQuote) {
                    while (random == prevQuote) {
                        random = (int) (Math.random() * quotes.length);
                    }
                    quote.setText(quotes[random]);
                    prevQuote = random;
                }
            }
        });
    }
}