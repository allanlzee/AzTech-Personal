package com.allan.lin.zhou.scheduler;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import java.util.List;

public class EventAdapter extends ArrayAdapter<Event> {
    public EventAdapter(@NonNull Context context, List<Event> events) {
        super(context, 0, events);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @NonNull
    @Override
    public View getView(int position, @Nullable View convert, @NonNull ViewGroup parent) {
        Event event = getItem(position);

        if (convert == null) {
            convert = LayoutInflater.from(getContext()).inflate(R.layout.event_cell, parent, false);
        }

        TextView eventCell = convert.findViewById(R.id.eventCellText);

        String eventTitle = event.getEventName() + " " + CalendarUtilities.timeConversion(event.getEventTime());
        eventCell.setText(eventTitle);

        return convert;
    }
}
