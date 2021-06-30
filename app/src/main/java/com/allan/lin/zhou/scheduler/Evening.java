package com.allan.lin.zhou.scheduler;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.allan.lin.zhou.scheduler.databinding.EveningBinding;

public class Evening extends Fragment {

    private EveningBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = EveningBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        /* binding.morningButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(Evening.this)
                        .navigate(R.id.evening_to_morning);
            }
        });

        binding.afternoonButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(Evening.this)
                        .navigate(R.id.evening_to_afternoon);
            }
        }); */

        binding.afternoon.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(Evening.this)
                        .navigate(R.id.evening_to_afternoon);
            }
        });

        binding.morning.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(Evening.this)
                        .navigate(R.id.evening_to_morning);
            }
        });
    }
}