package com.allan.lin.zhou.scheduler;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.allan.lin.zhou.scheduler.databinding.CalendarActivityBinding;
import com.google.android.material.snackbar.Snackbar;

import java.time.LocalDate;
import java.util.ArrayList;

import static com.allan.lin.zhou.scheduler.CalendarUtilities.dateConversion;
import static com.allan.lin.zhou.scheduler.CalendarUtilities.daysInMonthArray;
import static com.allan.lin.zhou.scheduler.Navigation.backToHome;

public class Calendar extends AppCompatActivity implements Adapter.OnItemListener {

    // Calendar
    private TextView monthYear;
    private RecyclerView calendarView;

    private CalendarActivityBinding binding;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar_activity);

        binding = CalendarActivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initWidgets();
        CalendarUtilities.selected = LocalDate.now();
        setMonthView();

        binding.homeButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                backToHome(view, Calendar.this);
            }
        });
    }

    private void initWidgets() {
        calendarView = findViewById(R.id.calendarRecyclerView);
        monthYear = findViewById(R.id.calendarDate);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setMonthView() {
        monthYear.setText(dateConversion(CalendarUtilities.selected));
        ArrayList<LocalDate> daysInMonth = daysInMonthArray(CalendarUtilities.selected);

        Adapter adapter = new Adapter(daysInMonth, this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 7);
        calendarView.setLayoutManager(layoutManager);
        calendarView.setAdapter(adapter);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onItemClick(int position, String dayText, LocalDate date) {
        if(date != null) {
            CalendarUtilities.selected = date;
            String msg = dayText + " " + dateConversion(CalendarUtilities.selected);
            Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
            setMonthView();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void previousMonthAction(View view) {
        CalendarUtilities.selected = CalendarUtilities.selected.minusMonths(1);
        setMonthView();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void nextMonthAction(View view) {
        CalendarUtilities.selected = CalendarUtilities.selected.plusMonths(1);
        setMonthView();
    }

    // Weekly Views
    public void weeklyAction(View view) {
        startActivity(new Intent(this, WeeklyView.class));
    }
}