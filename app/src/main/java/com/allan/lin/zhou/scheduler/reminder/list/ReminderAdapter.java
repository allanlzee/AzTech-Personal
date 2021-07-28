package com.allan.lin.zhou.scheduler.reminder.list;

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

import com.allan.lin.zhou.scheduler.R;

import java.util.List;

public class ReminderAdapter extends ArrayAdapter<Reminder> {
    public ReminderAdapter(@NonNull Context context, List<Reminder> reminders) {
        super(context, 0, reminders);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @NonNull
    @Override
    public View getView(int position, @Nullable View convert, @NonNull ViewGroup parent) {
        Reminder reminder = getItem(position);

        if (convert == null) {
            convert = LayoutInflater.from(getContext()).inflate(R.layout.reminder_cell, parent, false);
        }

        TextView reminderCell = convert.findViewById(R.id.reminderCellText);

        String reminderTitle = reminder.getReminderName() + " " + reminder.getReminderTime();
        reminderCell.setText(reminderTitle);

        return convert;
    }
}