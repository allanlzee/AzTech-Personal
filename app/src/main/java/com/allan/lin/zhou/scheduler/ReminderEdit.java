package com.allan.lin.zhou.scheduler;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.allan.lin.zhou.scheduler.databinding.ReminderEditActivityBinding;

import java.text.DateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;

import static com.allan.lin.zhou.scheduler.Navigation.backToHome;

public class ReminderEdit extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {

    private Toolbar toolbar;
    private ReminderEditActivityBinding binding;

    private DialogFragment timePicker;

    private TextView alarmTextView;
    private String alarmTime;

    private Calendar calendar;

    private Boolean onTimeClick = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ReminderEditActivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        CalendarUtilities.notificationNames = new ArrayList<>();
        CalendarUtilities.notificationID = 0;

        alarmTextView = findViewById(R.id.alarmSet);

        binding.homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backToHome(view, ReminderEdit.this);
            }
        });

        binding.editTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timePicker = new TimePicker();
                timePicker.show(getSupportFragmentManager(), "Time Picker");
            }
        });

        binding.cancelAlarmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancelAlarm();
            }
        });

        binding.saveReminder.setOnClickListener(new View.OnClickListener() {

            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                saveReminder(view);
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

    @Override
    public void onTimeSet(android.widget.TimePicker view, int hourOfDay, int minute) {
        calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);

        updateTimeText(calendar);
        startAlarm(calendar);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void saveReminder(View view) {
        String reminderName = binding.reminderName.getText().toString();

        CalendarUtilities.notificationNames.add(reminderName);
        CalendarUtilities.name = reminderName;

        Toast.makeText(this, reminderName, Toast.LENGTH_LONG).show();

        Reminder reminder = new Reminder(reminderName, alarmTime, LocalDate.now());
        if (!reminderName.equals("")) {
            Reminder.allReminders.add(reminder);
            CalendarUtilities.notificationID++;
            finish();
        } else if (!onTimeClick) {
            Toast.makeText(this, "Enter Reminder Time", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Enter Reminder Name", Toast.LENGTH_LONG).show();
        }
    }

    private void updateTimeText(Calendar calendar) {
        // Update Textview for Alarm Notification
        String timeText = DateFormat.getTimeInstance(DateFormat.SHORT).format(calendar.getTime());

        alarmTextView.setText(timeText);
        alarmTextView.setTextColor(getResources().getColor(R.color.white));
        alarmTime = timeText;
    }

    private void startAlarm(Calendar calendar) {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, ReminderReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, CalendarUtilities.notificationID + 1, intent, 0);

        if (calendar.before(Calendar.getInstance())) {
            calendar.add(Calendar.DATE, 1);
        }

        // Turn on device at specific calendar time and fire pending intent containing AlertReceiver
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
    }

    private void cancelAlarm() {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, ReminderReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent, 0);

        alarmManager.cancel(pendingIntent);
        alarmTextView.setText("Alarm Canceled");

        // Subtract 1 from Notification ID in CalendarUtilities
        CalendarUtilities.notificationID--;
    }
}