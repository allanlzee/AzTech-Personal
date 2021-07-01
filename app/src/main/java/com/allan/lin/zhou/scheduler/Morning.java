package com.allan.lin.zhou.scheduler;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.allan.lin.zhou.scheduler.databinding.MorningBinding;

public class Morning extends Fragment {

    private MorningBinding binding;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = MorningBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        /* binding.afternoonButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(Morning.this)
                        .navigate(R.id.morning_to_afternoon);
            }
        });

        binding.eveningButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(Morning.this)
                        .navigate(R.id.morning_to_evening);
            }
        }); */

        binding.afternoon.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(Morning.this)
                        .navigate(R.id.morning_to_afternoon);
            }
        });

        binding.evening.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(Morning.this)
                        .navigate(R.id.morning_to_evening);
            }
        });

        binding.calendar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Calendar.class);
                startActivity(intent);
                ((Activity) getActivity()).overridePendingTransition(0,0);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}