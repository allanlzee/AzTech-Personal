package com.allan.lin.zhou.scheduler;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.allan.lin.zhou.scheduler.databinding.WeeklyViewActivityBinding;
import com.google.android.material.snackbar.Snackbar;

import java.time.LocalDate;
import java.util.ArrayList;

import static com.allan.lin.zhou.scheduler.CalendarUtilities.dateConversion;
import static com.allan.lin.zhou.scheduler.CalendarUtilities.daysInWeekArray;

public class WeeklyView extends AppCompatActivity implements Adapter.OnItemListener {

    private TextView monthYear;
    private RecyclerView weeklyView;
    private ListView eventList; // TODO: ListView does not appear on screen
    private WeeklyViewActivityBinding binding;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = WeeklyViewActivityBinding.inflate(getLayoutInflater());
        setContentView(R.layout.weekly_view_activity);

        initWidgets();
        setWeekView();

        binding.homeButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Back to Home", Snackbar.LENGTH_INDEFINITE)
                        .setAction("Go", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(WeeklyView.this, MainActivity.class);
                                startActivity(intent);
                            }
                        }).setActionTextColor(getResources().getColor(R.color.home_action))
                        .setTextColor(getResources().getColor(R.color.home_snack))
                        .show();
            }
        });
    }

    private void initWidgets() {
        weeklyView = findViewById(R.id.weeklyRecyclerView);
        monthYear = findViewById(R.id.calendarDate);
        eventList = findViewById(R.id.eventListView);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setWeekView() {
        monthYear.setText(dateConversion(CalendarUtilities.selected));
        ArrayList<LocalDate> daysInWeek = daysInWeekArray(CalendarUtilities.selected);

        Adapter adapter = new Adapter(daysInWeek, this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 7);
        weeklyView.setLayoutManager(layoutManager);
        weeklyView.setAdapter(adapter);
        setEventAdapter();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void previousWeekAction(View view) {
        CalendarUtilities.selected = CalendarUtilities.selected.minusWeeks(1);
        setWeekView();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void nextWeekAction(View view) {
        CalendarUtilities.selected = CalendarUtilities.selected.plusWeeks(1);
        setWeekView();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onItemClick(int position, String dayText, LocalDate date) {
        CalendarUtilities.selected = date;
        setWeekView();
    }

    // Event Functions and Actions
    public void newEventAction(View view) {
        startActivity(new Intent(this, EventEdit.class));
    }

    private void setEventAdapter() {
        ArrayList<Event> daily = Event.eventsToday(CalendarUtilities.selected);
        EventAdapter eventAdapter = new EventAdapter(getApplicationContext(), daily);
        eventList.setAdapter(eventAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setEventAdapter();
    }
}