package com.allan.lin.zhou.scheduler;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;

import android.app.TimePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

import com.allan.lin.zhou.scheduler.databinding.EventEditActivityBinding;

import java.time.LocalTime;

import static com.allan.lin.zhou.scheduler.CalendarUtilities.cancelAlarm;
import static com.allan.lin.zhou.scheduler.CalendarUtilities.startAlarm;
import static com.allan.lin.zhou.scheduler.CalendarUtilities.updateTimeTextView;
import static com.allan.lin.zhou.scheduler.Navigation.backToHome;

public class EventEdit extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {

    private EventEditActivityBinding binding;
    private TextView eventDate, eventTime;
    private EditText nameInput;

    private LocalTime time;

    private Toolbar toolbar;

    private DialogFragment timePicker;

    private Calendar calendar;
    private String alarmTime;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_edit_activity);

        binding = EventEditActivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initWidgets();

        time = LocalTime.now();
        String d = CalendarUtilities.dateFormat(CalendarUtilities.selected);
        eventDate.setText("Date: " + d);
        String t = CalendarUtilities.timeConversion(time);
        eventTime.setText("Time: " + t);

        binding.homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backToHome( view, EventEdit.this);
            }
        });

        binding.editTime.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                timePicker = new TimePicker();
                timePicker.show(getSupportFragmentManager(), "Time Picker");
            }
        });

        binding.saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveEvent(view);
            }
        });

        binding.cancelAlarm.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                cancelAlarm(EventEdit.this, eventTime);
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
        if (!name.equals("")) {
            Event.events.add(newEvent);
            finish();
        } else {
            Toast.makeText(this, "Enter Event Name", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onTimeSet(android.widget.TimePicker view, int hourOfDay, int minute) {

        calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);

        updateTimeTextView(calendar, binding.timeText, alarmTime);
        startAlarm(calendar, EventEdit.this);

        CalendarUtilities.eventTimeTextView = hourOfDay + ":" + minute + " a.m.";

        if (hourOfDay > 12) {
            hourOfDay -= 12;
            CalendarUtilities.eventTimeTextView = hourOfDay + ":" + minute + " p.m.";
        }

        if (minute == 0 && hourOfDay <= 12) {
            CalendarUtilities.eventTimeTextView = hourOfDay + ":00" + " p.m.";
        } else if (minute == 0) {
            hourOfDay -= 12;
            CalendarUtilities.eventTimeTextView = hourOfDay + ":00" + " a.m.";
        }
    }
}