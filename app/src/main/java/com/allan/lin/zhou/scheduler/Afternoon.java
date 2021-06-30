package com.allan.lin.zhou.scheduler;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.allan.lin.zhou.scheduler.databinding.AfternoonBinding;

public class Afternoon extends Fragment {

    private AfternoonBinding binding;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = AfternoonBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        /* binding.buttonMorning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(Afternoon.this)
                        .navigate(R.id.afternoon_to_morning);
            }
        });

        binding.buttonEvening.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(Afternoon.this)
                        .navigate(R.id.afternoon_to_evening);
            }
        }); */

        binding.morning.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(Afternoon.this)
                        .navigate(R.id.afternoon_to_morning);
            }
        });

        binding.evening.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(Afternoon.this)
                        .navigate(R.id.afternoon_to_evening);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}