package com.allan.lin.zhou.scheduler;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public final TextView dayOfMonth;
    private final Adapter.OnItemListener onItemListener;

    public ViewHolder(@NonNull @NotNull View itemView, Adapter.OnItemListener onItemListener) {
        super(itemView);
        dayOfMonth = itemView.findViewById(R.id.day);
        this.onItemListener = onItemListener;
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        onItemListener.onItemClick(getAdapterPosition(), (String) dayOfMonth.getText());
    }
}
