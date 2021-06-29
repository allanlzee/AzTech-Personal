package com.allan.lin.zhou.scheduler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.ui.AppBarConfiguration;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.allan.lin.zhou.scheduler.databinding.MindfulnessActivityBinding;
import com.google.android.material.snackbar.Snackbar;

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
                Snackbar.make(view, "Back to Home", Snackbar.LENGTH_INDEFINITE)
                        .setAction("Go", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(Mindfulness.this, MainActivity.class);
                                startActivity(intent);
                            }
                        }).setActionTextColor(getResources().getColor(R.color.home_action))
                        .setTextColor(getResources().getColor(R.color.home_snack))
                        .show();
            }
        });
    }
}