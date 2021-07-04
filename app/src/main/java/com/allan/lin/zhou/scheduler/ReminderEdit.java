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

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.allan.lin.zhou.scheduler.databinding.ReminderEditActivityBinding;

import java.text.DateFormat;
import java.time.LocalTime;
import java.util.Calendar;

import static com.allan.lin.zhou.scheduler.Navigation.backToHome;

public class ReminderEdit extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {

    private Toolbar toolbar;
    private ReminderEditActivityBinding binding;

    private LocalTime time;
    private int hourTime, minuteTime;
    private DialogFragment timePicker;

    private TextView alarmTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ReminderEditActivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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
            public void onClick(View v) {
                cancelAlarm();
            }
        });

        /* binding.saveReminder.setOnClickListener(new View.OnClickListener() {

            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                saveReminder(view);
            }
        }); */
    }

    @Override
    public void onTimeSet(android.widget.TimePicker view, int hourOfDay, int minute) {
        TextView text = findViewById(R.id.currentTime);
        text.setText("Hour: " + hourOfDay + " Minute: " + minute);
        hourTime = hourOfDay;
        minuteTime = minute;

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);

        updateTimeText(calendar);
        startAlarm(calendar);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void saveReminder(View view) {
        String name = binding.nameInput.getText().toString();
        // TODO: finish functionality
    }

    private void updateTimeText(Calendar calendar) {
        // Update Textview for Alarm Notification
        String timeText = "Alarm set for: ";
        timeText += DateFormat.getTimeInstance(DateFormat.SHORT).format(calendar.getTime());

        alarmTextView.setText(timeText);
    }

    private void startAlarm(Calendar calendar) {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent, 0);

        if (calendar.before(Calendar.getInstance())) {
            calendar.add(Calendar.DATE, 1);
        }

        // Turn on device at specific calendar time and fire pending intent containing AlertReceiver
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
    }

    private void cancelAlarm() {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent, 0);

        alarmManager.cancel(pendingIntent);
        alarmTextView.setText("Alarm Canceled");
    }
}