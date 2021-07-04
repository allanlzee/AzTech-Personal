package com.allan.lin.zhou.scheduler;

import java.time.LocalTime;
import java.util.ArrayList;

public class Reminder {
    private String name;
    private LocalTime time;

    public static ArrayList<Reminder> remindersToday = new ArrayList<>();

    public Reminder(String name, LocalTime time) {
        this.name = name;
        this.time = time;
    }

    public String getReminderName() {
        return name;
    }

    public void setReminderName() {
        this.name = name;
    }

    public LocalTime getReminderTime() {
        return time;
    }

    public void setReminderTime() {
        this.time = time;
    }
}
