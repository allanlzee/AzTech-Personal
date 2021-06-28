package com.allan.lin.zhou.scheduler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.allan.lin.zhou.scheduler.databinding.MindfulnessActivityBinding;

public class Mindfulness extends AppCompatActivity {

    Toolbar toolbar;
    private MindfulnessActivityBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mindfulness_activity);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        binding = MindfulnessActivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.fab.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Mindfulness.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }
}