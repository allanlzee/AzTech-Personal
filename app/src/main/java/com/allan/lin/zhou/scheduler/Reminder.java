package com.allan.lin.zhou.scheduler;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;

public class Reminder {
    private String name;
    private String time;
    private LocalDate date;

    public static ArrayList<Reminder> allReminders = new ArrayList<>();

    public static ArrayList<Reminder> remindersToday(Calendar calendar) {
        ArrayList<Reminder> reminders = new ArrayList<>();

        for (Reminder r : allReminders) {
            // if (r.getReminderDate().equals(date))
        }
        return reminders;
    }

    public Reminder(String name, String time, LocalDate date) {
        this.name = name;
        this.time = time;
        this.date = date;
    }

    public String getReminderName() {
        return name;
    }

    public void setReminderName(String name) {
        this.name = name;
    }

    public String getReminderTime() {
        return time;
    }

    public void setReminderTime(String time) {
        this.time = time;
    }

    public LocalDate getReminderDate() {
        return date;
    }

    public void setReminderDate(LocalDate date) {
        this.date = date;
    }
}
