package com.allan.lin.zhou.scheduler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;

import android.app.TimePickerDialog;
import android.os.Bundle;

import android.view.View;
import android.widget.TextView;

import com.allan.lin.zhou.scheduler.databinding.ReminderEditActivityBinding;

import static com.allan.lin.zhou.scheduler.Navigation.backToHome;

public class ReminderEdit extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {

    private Toolbar toolbar;
    private ReminderEditActivityBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reminder_edit_activity);

        binding = ReminderEditActivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        binding.homeButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                backToHome(view, ReminderEdit.this);
            }
        });

        binding.editTime.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                DialogFragment timePicker = new TimePicker();
                timePicker.show(getSupportFragmentManager(), "Time Picker");
            }
        });
    }

    @Override
    public void onTimeSet(android.widget.TimePicker view, int hourOfDay, int minute) {
        TextView text = findViewById(R.id.currentTime);
        text.setText("Hour: " + hourOfDay + " Minute: " + minute);
    }
}