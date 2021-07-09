package com.allan.lin.zhou.scheduler;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class CalendarUtilities {
    public static LocalDate selected;

    // Stores temporary values for notification names
    // Allows for notification to have custom name
    public static ArrayList<String> notificationNames = new ArrayList<>();
    public static String name;

    public static int notificationID = 1;

    // Converts date into String
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String dateConversion(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM yyyy");
        return date.format(formatter);
    }

    // Converts time into String
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String timeConversion(LocalTime time) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm:ss a");
        return time.format(formatter);
    }

    // Converts date into full date String
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String dateFormat(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy");
        return date.format(formatter);
    }

    // Sets up the calendar view
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static ArrayList<LocalDate> daysInMonthArray(LocalDate date) {

        ArrayList<LocalDate> daysInMonthArray = new ArrayList<>();
        YearMonth yearMonth = YearMonth.from(date);

        int monthLength = yearMonth.lengthOfMonth();

        LocalDate firstDay = CalendarUtilities.selected.withDayOfMonth(1);
        int dayOfWeek = firstDay.getDayOfWeek().getValue();

        for (int i = 1; i <= 42; i++) {
            if (i <= dayOfWeek || i > monthLength + dayOfWeek) {
                daysInMonthArray.add(null);
            }
            else {
                daysInMonthArray.add(LocalDate.of(selected.getYear(), selected.getMonth(), i - dayOfWeek));
            }
        }

        // Month starts on Sunday
        if (daysInMonthArray.get(6) == null) {
            while (daysInMonthArray.get(0) == null) {
                daysInMonthArray.remove(0);
            }
        }

        return daysInMonthArray;
    }

    // Weekly Array for Recycler View
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static ArrayList<LocalDate> daysInWeekArray(LocalDate selected) {
        ArrayList<LocalDate> days = new ArrayList<>();
        LocalDate currentDate = sundayForDate(selected);
        LocalDate endDate = currentDate.plusWeeks(1);

        while (currentDate.isBefore(endDate)) {
            days.add(currentDate);
            currentDate = currentDate.plusDays(1);
        }

        return days;
    }

    // Move between consecutive weeks
    @RequiresApi(api = Build.VERSION_CODES.O)
    private static LocalDate sundayForDate(LocalDate currentDate) {
        LocalDate oneWeekBefore = currentDate.minusWeeks(1);

        while (currentDate.isAfter(oneWeekBefore)) {
            if (currentDate.getDayOfWeek() == DayOfWeek.SUNDAY) {
                return currentDate;
            }

            currentDate = currentDate.minusDays(1);
        }

        return null;
    }
}
