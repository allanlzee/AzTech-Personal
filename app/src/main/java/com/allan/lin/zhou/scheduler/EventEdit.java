package com.allan.lin.zhou.scheduler;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.allan.lin.zhou.scheduler.databinding.EventEditActivityBinding;
import com.google.android.material.snackbar.Snackbar;

import java.time.LocalTime;

public class EventEdit extends AppCompatActivity {

    private EventEditActivityBinding binding;
    private TextView eventDate, eventTime;
    private EditText nameInput;

    private LocalTime time;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_edit_activity);

        binding = EventEditActivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initWidgets();

        time = LocalTime.now();
        String d = CalendarUtilities.dateFormat(CalendarUtilities.selected);
        eventDate.setText("Date: " + d);
        String t = CalendarUtilities.timeConversion(time);
        eventTime.setText("Time: " + t);

        binding.homeButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Back to Home", Snackbar.LENGTH_INDEFINITE)
                        .setAction("Go", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(EventEdit.this, MainActivity.class);
                                startActivity(intent);
                            }
                        }).setActionTextColor(getResources().getColor(R.color.home_action))
                        .setTextColor(getResources().getColor(R.color.home_snack))
                        .show();
            }
        });
    }

    private void initWidgets() {
        nameInput = findViewById(R.id.nameInput);
        eventDate = findViewById(R.id.dayText);
        eventTime = findViewById(R.id.timeText);
    }

    public void saveEvent(View view) {
        String name = nameInput.getText().toString();
        Event newEvent = new Event(name, CalendarUtilities.selected, time);
        Event.events.add(newEvent);
        finish();
    }
}