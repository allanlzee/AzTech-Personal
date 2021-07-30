package com.allan.lin.zhou.scheduler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.allan.lin.zhou.scheduler.databinding.LoginMainActivityBinding;

import static com.allan.lin.zhou.scheduler.Navigation.backToHome;

public class LoginMainActivity extends AppCompatActivity {

    private LoginMainActivityBinding binding;
    private Toolbar toolbar;

    private EditText username;
    private EditText email;
    private EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_main_activity);

        binding = LoginMainActivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        username = binding.username;
        email = binding.email;
        password = binding.password;

        binding.homeButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                backToHome(view, LoginMainActivity.this);
            }
        });

        binding.login.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String usernameString = username.getText().toString();
                String emailString = email.getText().toString();
                String passwordString = password.getText().toString();

                if (passwordString.length() > 5) {
                    Toast.makeText(LoginMainActivity.this, "Passwords must be longer than 5 characters.", Toast.LENGTH_LONG).show();
                }

                if (usernameString.isEmpty()) {
                    Toast.makeText(LoginMainActivity.this, "Enter a Username!", Toast.LENGTH_SHORT).show();
                    return;
                } else if (emailString.isEmpty()){
                    Toast.makeText(LoginMainActivity.this, "Enter an Email!", Toast.LENGTH_SHORT).show();
                    return;
                } else if (passwordString.isEmpty()) {
                    Toast.makeText(LoginMainActivity.this, "Enter a Password!", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}