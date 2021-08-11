package com.allan.lin.zhou.scheduler.school;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.ui.AppBarConfiguration;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.allan.lin.zhou.scheduler.R;
import com.allan.lin.zhou.scheduler.databinding.InspireActivityBinding;

import static com.allan.lin.zhou.scheduler.Navigation.backToHome;

public class Inspire extends AppCompatActivity {

    private Toolbar toolbar;
    private InspireActivityBinding binding;
    private AppBarConfiguration appBarConfiguration;

    int prevQuote;
    Button inspire;
    TextView quote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inspire_activity);

        binding = InspireActivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        binding.fab.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                backToHome(view, Inspire.this);
            }
        });

        // Set Up Quote Generator
        inspire = findViewById(R.id.quoteGenerator);
        quote = findViewById(R.id.quote);

        // Generate Random Quote
        randomQuote();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

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