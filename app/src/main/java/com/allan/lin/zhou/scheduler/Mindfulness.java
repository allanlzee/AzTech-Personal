package com.allan.lin.zhou.scheduler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.ui.AppBarConfiguration;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.allan.lin.zhou.scheduler.databinding.MindfulnessActivityBinding;
import com.google.android.material.snackbar.Snackbar;

import static com.allan.lin.zhou.scheduler.Navigation.backToHome;

public class Mindfulness extends AppCompatActivity {

    private Toolbar toolbar;
    private MindfulnessActivityBinding binding;
    private AppBarConfiguration appBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mindfulness_activity);

        binding = MindfulnessActivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        binding.fab.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                backToHome(view, Mindfulness.this);
            }
        });
    }
}