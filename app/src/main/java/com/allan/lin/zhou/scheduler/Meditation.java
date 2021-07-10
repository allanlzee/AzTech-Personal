package com.allan.lin.zhou.scheduler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.allan.lin.zhou.scheduler.databinding.MeditationActivityBinding;
import com.allan.lin.zhou.scheduler.databinding.MindfulnessActivityBinding;

import static com.allan.lin.zhou.scheduler.Navigation.backToHome;

public class Meditation extends AppCompatActivity {

    private MeditationActivityBinding binding;
    private Toolbar toolbar;
    private long duration = 1500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.meditation_activity);

        binding = MeditationActivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        binding.homeButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                backToHome(view, Meditation.this);
            }
        });

        binding.inhaleButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                /* Animation animation = AnimationUtils.loadAnimation(Meditation.this, R.anim.meditation_animation);
                binding.breathIcon.startAnimation(animation); */

                // Translation
                /* ObjectAnimator animatorX = ObjectAnimator.ofFloat(binding.breathIcon, "x", 420f);
                ObjectAnimator animatorY = ObjectAnimator.ofFloat(binding.breathIcon, "y", 200f);
                animatorX.setDuration(duration);
                animatorY.setDuration(duration); */

                inhale();
            }
        });

        binding.exhaleButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                exhale();
            }
        });

    }

    public void inhale() {
        ObjectAnimator alphaAnimation = ObjectAnimator.ofFloat(binding.breathIcon, View.ALPHA, 1.0f, 0.0f);
        alphaAnimation.setDuration(duration);

        ObjectAnimator rotation = ObjectAnimator.ofFloat(binding.breathIcon, "rotation", 0f, 180f);
        rotation.setDuration(duration / 2);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(alphaAnimation, rotation);
        animatorSet.start();
    }

    public void exhale() {
        ObjectAnimator alphaAnimation = ObjectAnimator.ofFloat(binding.breathIcon, View.ALPHA, 1.0f, 0.0f);
        alphaAnimation.setDuration(duration);

        ObjectAnimator rotation = ObjectAnimator.ofFloat(binding.breathIcon, "rotation", 180f, 0f);
        rotation.setDuration(duration / 2);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(alphaAnimation, rotation);
        animatorSet.start();
    }
}