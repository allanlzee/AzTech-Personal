package com.allan.lin.zhou.scheduler.schedule;

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

import com.allan.lin.zhou.scheduler.CalendarUtilities;
import com.allan.lin.zhou.scheduler.R;

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

        String eventTitle = event.getEventName() + " " + CalendarUtilities.eventTimeTextView;
        eventCell.setText(eventTitle);

        return convert;
    }
}
