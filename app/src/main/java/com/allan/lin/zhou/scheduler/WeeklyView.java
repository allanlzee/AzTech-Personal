package com.allan.lin.zhou.scheduler;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.allan.lin.zhou.scheduler.databinding.WeeklyViewActivityBinding;

import java.time.LocalDate;
import java.util.ArrayList;

import static com.allan.lin.zhou.scheduler.CalendarUtilities.dateConversion;
import static com.allan.lin.zhou.scheduler.CalendarUtilities.daysInMonthArray;
import static com.allan.lin.zhou.scheduler.CalendarUtilities.daysInWeekArray;

public class WeeklyView extends AppCompatActivity implements Adapter.OnItemListener {

    private TextView monthYear;
    private RecyclerView weeklyView;
    private ListView eventListView;
    private WeeklyViewActivityBinding binding;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weekly_view_activity);

        binding = WeeklyViewActivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initWidgets();
        CalendarUtilities.selected = LocalDate.now();
        setWeekView();
    }

    private void initWidgets() {
        weeklyView = findViewById(R.id.weeklyRecyclerView);
        monthYear = findViewById(R.id.calendarDate);
        eventListView = findViewById(R.id.eventListView);
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

    public void newEventAction(View view) {

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onItemClick(int position, String dayText, LocalDate date) {
        CalendarUtilities.selected = date;
        setWeekView();
    }

    private void setEventAdapter() {
        // TODO: add functionality
    }
}