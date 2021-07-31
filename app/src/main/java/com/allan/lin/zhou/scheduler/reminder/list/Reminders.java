package com.allan.lin.zhou.scheduler.reminder.list;

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

import com.allan.lin.zhou.scheduler.R;
import com.allan.lin.zhou.scheduler.databinding.RemindersActivityBinding;
import com.google.android.material.snackbar.Snackbar;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import static com.allan.lin.zhou.scheduler.Navigation.backToHome;

public class Reminders extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private RemindersActivityBinding binding;
    private Toolbar toolbar;

    private ListView reminderList;

    private ArrayList<Reminder> daily;

    private ReminderAdapter reminderAdapter;

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
            public void onClick(View view) {
                removeReminders(view);
            }
        });

        /* binding.sortReminders.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                sortReminders();
            }
        }); */
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
        reminderAdapter = new ReminderAdapter(getApplicationContext(), daily);
        reminderList.setAdapter(reminderAdapter);
        reminderList.setClickable(true);

        reminderList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Snackbar.make(view, "Delete Reminder?", Snackbar.LENGTH_INDEFINITE)
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
        sortReminders();
    }

    // Remove all notifications
    private void removeReminders(View view) {
        if (daily.isEmpty() || Reminder.allReminders.isEmpty()) {
            Toast.makeText(Reminders.this, "No Reminders to Clear!", Toast.LENGTH_SHORT).show();
        } else {
            Snackbar.make(view, "Delete All Reminders?", Snackbar.LENGTH_INDEFINITE)
                    .setAction("OK", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            daily.clear();
                            Reminder.allReminders.clear();
                            reminderAdapter.notifyDataSetChanged();
                            Toast.makeText(Reminders.this, "Reminders Cleared", Toast.LENGTH_LONG).show();
                        }
                    }).setActionTextColor(Reminders.this.getResources().getColor(R.color.home_action))
                    .setTextColor(Reminders.this.getResources().getColor(R.color.home_snack))
                    .show();
        }
    }

    public void sortReminders() {
        Collections.sort(daily, new Comparator<Reminder>() {

            @Override
            public int compare(Reminder r1, Reminder r2) {
                if (r1.getReminderCalendar().before(r2.getReminderCalendar())) {
                    return -1;
                } else {
                    return 1;
                }
            }
        });
        reminderAdapter.notifyDataSetChanged();
    }
}