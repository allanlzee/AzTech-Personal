package com.allan.lin.zhou.scheduler.ui.login.text.message;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;

import com.allan.lin.zhou.scheduler.R;
import com.allan.lin.zhou.scheduler.databinding.TextingSettingsActivityBinding;

public class TextingSettings extends AppCompatActivity {

    private Toolbar toolbar;
    private TextingSettingsActivityBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.texting_settings_activity);

        binding = TextingSettingsActivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }
}