package com.allan.lin.zhou.scheduler;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.allan.lin.zhou.scheduler.databinding.RemindersActivityBinding;

public class Reminders extends AppCompatActivity {

    private RemindersActivityBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reminders_activity);

        binding = RemindersActivityBinding.inflate(getLayoutInflater());

        /* binding.fab.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Reminders.this, MainActivity.class);
                startActivity(intent);
            }
        }); */
    }
}