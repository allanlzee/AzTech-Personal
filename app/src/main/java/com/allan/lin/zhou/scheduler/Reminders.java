package com.allan.lin.zhou.scheduler;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.ui.AppBarConfiguration;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.allan.lin.zhou.scheduler.databinding.RemindersActivityBinding;
import com.google.android.material.snackbar.Snackbar;

import java.time.LocalDate;
import java.util.ArrayList;

import static com.allan.lin.zhou.scheduler.Navigation.backToHome;

public class Reminders extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private RemindersActivityBinding binding;
    private Toolbar toolbar;

    private ListView reminderList;

    private ArrayList<Reminder> daily;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = RemindersActivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        initWidgets();
        setReminderAdapter();

        binding.homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backToHome(view, Reminders.this);
            }
        });

        binding.addEvent.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                startActivity(new Intent(Reminders.this, ReminderEdit.class));
            }
        });

        binding.removeReminders.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                removeReminders();
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

    private void initWidgets() {
        reminderList = findViewById(R.id.todoList);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setReminderAdapter() {
        daily = Reminder.remindersToday(LocalDate.now());
        ReminderAdapter reminderAdapter = new ReminderAdapter(getApplicationContext(), daily);
        reminderList.setAdapter(reminderAdapter);
        reminderList.setClickable(true);

        reminderList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Snackbar.make(view, "Delete Reminder?", Snackbar.LENGTH_SHORT)
                        .setAction("OK", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                daily.remove(position);
                                Reminder.allReminders.remove(position);
                                Toast.makeText(Reminders.this, "Reminder Removed", Toast.LENGTH_SHORT).show();
                                reminderAdapter.notifyDataSetChanged();
                            }
                        }).setActionTextColor(Reminders.this.getResources().getColor(R.color.home_action))
                        .setTextColor(Reminders.this.getResources().getColor(R.color.home_snack))
                        .show();
            }
        });
    }



    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onResume() {
        super.onResume();
        setReminderAdapter();
    }

    // Remove all notifications
    private void removeReminders() {
        daily.clear();
        Reminder.allReminders.clear();
        binding.todoList.setAdapter(null);
        Toast.makeText(Reminders.this, "Reminders Cleared", Toast.LENGTH_LONG).show();
    }
}