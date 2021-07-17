package com.allan.lin.zhou.scheduler;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.MenuItem;
import android.view.View;
import android.widget.TimePicker;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import java.text.DateFormat;
import java.util.Calendar;

import com.allan.lin.zhou.scheduler.databinding.ScheduleActivityBinding;

import static com.allan.lin.zhou.scheduler.Navigation.backToHome;

public class Schedule extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {

    private AppBarConfiguration appBarConfiguration;
    private ScheduleActivityBinding binding;

    private DialogFragment timePicker;
    private Calendar calendar;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ScheduleActivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_schedule);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        binding.home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backToHome(view, Schedule.this);
            }
        });

        binding.setAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timePicker = new com.allan.lin.zhou.scheduler.TimePicker();
                timePicker.show(getSupportFragmentManager(), "Time Picker");
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
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_schedule);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);

        /* Morning: 6 a.m. <-> 12 p.m.
           Afternoon: 12 p.m. <-> 6 p.m.
           Evening: 6 p.m. <-> 12 a.m.
         */

        if (hourOfDay >= 6 && hourOfDay < 12) {
            CalendarUtilities.scheduleAlarm = "Morning";
        } else if (hourOfDay < 18) {
            CalendarUtilities.scheduleAlarm = "Afternoon";
        } else {
            CalendarUtilities.scheduleAlarm = "Evening";
        }

        String time = DateFormat.getTimeInstance(DateFormat.SHORT).format(calendar.getTime());
        binding.alarmTime.setText(time);
        binding.alarmTime.setTextColor(getResources().getColor(R.color.white));

        startAlarm(calendar);
    }

    private void startAlarm(Calendar calendar) {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 69, intent, 0);

        if (calendar.before(Calendar.getInstance())) {
            calendar.add(Calendar.DATE, 1);
        }

        // Turn on device at specific calendar time and fire pending intent containing AlertReceiver
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
    }
}