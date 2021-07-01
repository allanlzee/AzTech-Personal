package com.allan.lin.zhou.scheduler;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Calendar extends AppCompatActivity implements Adapter.OnItemListener{

    // Calendar
    private TextView monthYear;
    private RecyclerView calendarView;
    private LocalDate selected;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar_activity);

        initWidgets();
        selected = LocalDate.now();
        setMonthView();
    }

    private void initWidgets() {
        calendarView = findViewById(R.id.calendarRecyclerView); 
        monthYear = findViewById(R.id.calendarDate);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setMonthView() {
        monthYear.setText(dateConversion(selected));
        ArrayList<String> daysInMonth = daysInMonthArray(selected);

        Adapter adapter = new Adapter(daysInMonth, this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 7);
        calendarView.setLayoutManager(layoutManager);
        calendarView.setAdapter(adapter);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private ArrayList<String> daysInMonthArray(LocalDate date) {
        ArrayList<String> daysInMonthArray = new ArrayList<>();
        YearMonth yearMonth = YearMonth.from(date);

        int monthLength = yearMonth.lengthOfMonth();

        LocalDate firstDay = selected.withDayOfMonth(1);
        int dayOfWeek = firstDay.getDayOfWeek().getValue();

        for (int i = 1; i <= 42; i++) {
            if (i <= dayOfWeek || i > monthLength + dayOfWeek) {
                daysInMonthArray.add("");
            }

            else {
                daysInMonthArray.add(String.valueOf(i - dayOfWeek));
            }
        }

        // Month starts on Sunday
        if (daysInMonthArray.get(6) == "") {
            for (int i = 0; i < 7; i++) {
                daysInMonthArray.remove(i);
            }
        }

        return daysInMonthArray;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private String dateConversion(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM yyyy");
        return date.format(formatter);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onItemClick(int position, String dayText) {
        if(!dayText.equals("")) {
            String msg = dayText + " " + dateConversion(selected);
            Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void previousMonthAction(View view) {
        selected = selected.minusMonths(1);
        setMonthView();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void nextMonthAction(View view) {
        selected = selected.plusMonths(1);
        setMonthView();
    }
}