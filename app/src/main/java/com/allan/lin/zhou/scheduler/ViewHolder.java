package com.allan.lin.zhou.scheduler;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.util.ArrayList;

public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private final ArrayList<LocalDate> daysInWeek;
    public final View parentView;
    public final TextView dayOfMonth;
    private final Adapter.OnItemListener onItemListener;

    public ViewHolder(@NonNull @NotNull View itemView,
                      Adapter.OnItemListener onItemListener, ArrayList<LocalDate> daysInWeek) {
        super(itemView);
        dayOfMonth = itemView.findViewById(R.id.day);
        parentView = itemView.findViewById(R.id.parentView);
        this.onItemListener = onItemListener;
        this.daysInWeek = daysInWeek;
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        onItemListener.onItemClick(getAdapterPosition(),
                (String) dayOfMonth.getText(), daysInWeek.get(getAdapterPosition()));
    }
}
