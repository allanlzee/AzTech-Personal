package com.allan.lin.zhou.scheduler.ui.login.text.message;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.allan.lin.zhou.scheduler.R;
import com.allan.lin.zhou.scheduler.Utilities;
import com.allan.lin.zhou.scheduler.databinding.TextingSettingsActivityBinding;
import com.makeramen.roundedimageview.RoundedImageView;

import static com.allan.lin.zhou.scheduler.Utilities.showShortToast;

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

        initializeOnClick();
    }

    private void showShortToast(String msg) {
        Toast.makeText(TextingSettings.this, msg, Toast.LENGTH_SHORT).show();
    }

    private void initializeOnClick() {
        binding.colorThemeWhite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utilities.backgroundTheme = "White";
                showShortToast("White Theme");
            }
        });

        binding.colorThemeRed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utilities.backgroundTheme = "Red";
                showShortToast("Red Theme");
            }
        });

        binding.colorThemeOrange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utilities.backgroundTheme = "Orange";
                showShortToast("Orange Theme");
            }
        });

        binding.colorThemeGreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utilities.backgroundTheme = "Green";
                showShortToast("Green Theme");
            }
        });

        binding.colorThemeBlue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utilities.backgroundTheme = "Blue";
                showShortToast("Blue Theme");
            }
        });

        binding.colorThemePurple.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utilities.backgroundTheme = "Purple";
                showShortToast("Purple Theme");
            }
        });

        binding.colorMessageThemeWhite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utilities.messageTheme = "White";
                showShortToast("White Theme");
            }
        });

        binding.colorMessageThemeRed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utilities.messageTheme = "Red";
                showShortToast("Red Theme");
            }
        });

        binding.colorMessageThemeOrange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utilities.messageTheme = "Orange";
                showShortToast("Orange Theme");
            }
        });

        binding.colorMessageThemeGreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utilities.messageTheme = "Green";
                showShortToast("Green Theme");
            }
        });

        binding.colorMessageThemeBlue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utilities.messageTheme = "Blue";
                showShortToast("Blue Theme");
            }
        });

        binding.colorMessageThemePurple.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utilities.messageTheme = "Purple";
                showShortToast("Purple Theme");
            }
        });

        binding.colorReceivedThemeWhite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utilities.receivedTheme = "White";
                showShortToast("White Theme");
            }
        });

        binding.colorReceivedThemeRed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utilities.receivedTheme = "Red";
                showShortToast("Red Theme");
            }
        });

        binding.colorReceivedThemeOrange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utilities.receivedTheme = "Orange";
                showShortToast("Orange Theme");
            }
        });

        binding.colorReceivedThemeGreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utilities.receivedTheme = "Green";
                showShortToast("Green Theme");
            }
        });

        binding.colorReceivedThemeBlue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utilities.receivedTheme = "Blue";
                showShortToast("Blue Theme");
            }
        });

        binding.colorReceivedThemePurple.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utilities.receivedTheme = "Purple";
                showShortToast("Purple Theme");
            }
        });
    }
}