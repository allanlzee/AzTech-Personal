package com.allan.lin.zhou.scheduler.schedule;

import android.graphics.Color;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.allan.lin.zhou.scheduler.CalendarUtilities;
import com.allan.lin.zhou.scheduler.R;

import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<ViewHolder> {

    private final ArrayList<LocalDate> daysOfWeek;
    private final OnItemListener onItemListener;

    public Adapter(ArrayList<LocalDate> days, OnItemListener onItemListener) {
        this.daysOfWeek = days;
        this.onItemListener = onItemListener;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.calendar_cell, parent, false);

        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if (daysOfWeek.size() > 15) {
            // Monthly View
            layoutParams.height = (int) (parent.getHeight() * 0.166666666);
        } else {
            layoutParams.height = (int) parent.getHeight();
        }

        return new ViewHolder(view, onItemListener, daysOfWeek);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        final LocalDate date = daysOfWeek.get(position);

        if (date == null) {
            holder.dayOfMonth.setText("");
        } else {
            holder.dayOfMonth.setText(String.valueOf(date.getDayOfMonth()));

            if (date.equals(CalendarUtilities.selected)) {
                holder.parentView.setBackgroundColor(Color.GREEN);
            }
        }
    }

    @Override
    public int getItemCount() {
        return daysOfWeek.size();
    }

    public interface OnItemListener {
        void onItemClick(int position, String dayText, LocalDate date);
    }
}
