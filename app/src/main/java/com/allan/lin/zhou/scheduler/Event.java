package com.allan.lin.zhou.scheduler;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class Event {

    private String name;
    private LocalDate date;
    private LocalTime time;

    public static ArrayList<Event> events = new ArrayList<>();

    public static ArrayList<Event> eventsToday(LocalDate date) {
        ArrayList<Event> events = new ArrayList<>();

        for (Event event : events) {
            if (event.getEventDate().equals(date)) {
                events.add(event);
            }
        }

        return events;
    }

    public Event(String name, LocalDate date, LocalTime time) {
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public String getEventName() {
        return name;
    }

    public void setEventName() {
        this.name = name;
    }

    public LocalDate getEventDate() {
        return date;
    }

    public void setEventDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getEventTime() {
        return time;
    }

    public void setEventTime(LocalTime time) {
        this.time = time;
    }
}
