package com.allan.lin.zhou.scheduler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;

import com.allan.lin.zhou.scheduler.databinding.MeditationActivityBinding;
import com.allan.lin.zhou.scheduler.databinding.MindfulnessActivityBinding;

import static com.allan.lin.zhou.scheduler.Navigation.backToHome;

public class Meditation extends AppCompatActivity {

    private MeditationActivityBinding binding;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.meditation_activity);

        binding = MeditationActivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.homeButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                backToHome(view, Meditation.this);
            }
        });
    }
}