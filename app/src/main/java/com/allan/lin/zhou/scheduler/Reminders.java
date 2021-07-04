package com.allan.lin.zhou.scheduler;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.ui.AppBarConfiguration;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.allan.lin.zhou.scheduler.databinding.RemindersActivityBinding;

import java.time.LocalDate;
import java.util.ArrayList;

import static com.allan.lin.zhou.scheduler.Navigation.backToHome;

public class Reminders extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private RemindersActivityBinding binding;
    private Toolbar toolbar;

    private ListView reminderList;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = RemindersActivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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

        initWidgets();
        setReminderAdapter();
    }

    private void initWidgets() {
        reminderList = findViewById(R.id.todoList);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setReminderAdapter() {
        ArrayList<Reminder> daily = Reminder.remindersToday(LocalDate.now());
        ReminderAdapter reminderAdapter = new ReminderAdapter(getApplicationContext(), daily);
        reminderList.setAdapter(reminderAdapter);

        // TODO: ListView does not appear on the layout
    }
}