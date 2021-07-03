package com.allan.lin.zhou.scheduler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.ui.AppBarConfiguration;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.allan.lin.zhou.scheduler.databinding.RemindersActivityBinding;
import com.google.android.material.snackbar.Snackbar;

import static com.allan.lin.zhou.scheduler.Navigation.backToHome;

public class Reminders extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private RemindersActivityBinding binding;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reminders_activity);

        binding = RemindersActivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backToHome(view, Reminders.this);
            }
        });
    }
}