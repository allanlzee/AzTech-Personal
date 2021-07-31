package com.allan.lin.zhou.scheduler.reminder.list;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;

public class Reminder implements Comparable<Reminder> {
    private String name;
    private String time;
    private LocalDate date;
    private Calendar calendar;

    public static ArrayList<Reminder> allReminders = new ArrayList<>();

    public static ArrayList<Reminder> remindersToday(LocalDate date) {
        ArrayList<Reminder> reminders = new ArrayList<>();

        for (Reminder r : allReminders) {
            if (r.getReminderDate().equals(date)) {
                reminders.add(r);
            }
        }

        return reminders;
    }

    public Reminder(String name, String time, LocalDate date, Calendar calendar) {
        this.name = name;
        this.time = time;
        this.date = date;
        this.calendar = calendar;
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

    public Calendar getReminderCalendar() {
        return calendar;
    }

    public void setCalendarDate(Calendar calendar) {
        this.calendar = calendar;
    }

    @Override
    public int compareTo(Reminder reminder) {
        if (this.calendar.before(reminder.calendar)) {
            return -1;
        } else {
            return 1;
        }
    }
}
