package com.allan.lin.zhou.scheduler;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.allan.lin.zhou.scheduler.databinding.MeditationActivityBinding;
import com.allan.lin.zhou.scheduler.databinding.MindfulnessActivityBinding;

import java.util.concurrent.TimeUnit;

import static com.allan.lin.zhou.scheduler.Navigation.backToHome;
import static com.allan.lin.zhou.scheduler.Utilities.scheduler;

public class Meditation extends AppCompatActivity {

    private MeditationActivityBinding binding;
    private Toolbar toolbar;
    private long duration = 1500;

    // Logger
    private static final String LOGGER = "MAIN";

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

            @RequiresApi(api = Build.VERSION_CODES.O)
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

            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                exhale();
            }
        });

        binding.resetButton.setOnClickListener(new View.OnClickListener() {

            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                meditation(); // TODO: meditation function is very buggy
                // startThread(view);
            }
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void inhale() {
        ObjectAnimator alphaAnimation = ObjectAnimator.ofFloat(binding.breathIcon, View.ALPHA, 1.0f, 0.0f);
        alphaAnimation.setDuration(duration);

        ObjectAnimator rotation = ObjectAnimator.ofFloat(binding.breathIcon, "rotation", 0f, 180f);
        rotation.setDuration(duration / 2);

        ObjectAnimator animatorAir = ObjectAnimator.ofFloat(binding.breathIcon, "translationY", -300f);
        animatorAir.setDuration(duration);

        ObjectAnimator animatorBody = ObjectAnimator.ofFloat(binding.animationIcon, "translationY", -300f);
        animatorBody.setDuration(duration);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(alphaAnimation, rotation, animatorAir, animatorBody);
        animatorSet.start();

        vibrate(duration / 2);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void exhale() {
        ObjectAnimator alphaAnimation = ObjectAnimator.ofFloat(binding.breathIcon, View.ALPHA, 1.0f, 0.0f);
        alphaAnimation.setDuration(duration);

        ObjectAnimator rotation = ObjectAnimator.ofFloat(binding.breathIcon, "rotation", 180f, 0f);
        rotation.setDuration(duration / 2);

        ObjectAnimator animatorAir = ObjectAnimator.ofFloat(binding.breathIcon, "translationY", 300f);
        animatorAir.setDuration(duration);

        ObjectAnimator animatorBody = ObjectAnimator.ofFloat(binding.animationIcon, "translationY", 300f);
        animatorBody.setDuration(duration);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(alphaAnimation, rotation, animatorAir, animatorBody);
        animatorSet.start();

        vibrate(duration / 2);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void vibrate(long duration) {
        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);

        vibrator.vibrate(VibrationEffect.createOneShot(duration, VibrationEffect.DEFAULT_AMPLITUDE));
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void meditation() {
        for (int i = 0; i < 10; i++) {

            try {
                inhale();
                Thread.sleep(3000);
            } catch(InterruptedException ex) {
                Thread.currentThread().interrupt();
            }

            try {
                exhale();
                Thread.sleep(3000);
            } catch(InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
        }

        /* for (int n = 0; n < 10; n++) {
            /* inhale();
            exhale();

            // scheduler(Meditation.this);
        } */
    }

    /* public void startThread(View view) {
        for (int i = 0; i < 10; i++) {
            Log.d(LOGGER, "Start Thread: " + 1);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
               e.printStackTrace();
            }
        }
    }

    public void stopThread(View view) {
        /*MeditationThread thread = new MeditationThread(10);
        thread.start();

        MeditationRunnable runnable = new MeditationRunnable(10);
        new Thread(runnable).start();
    }

    class MeditationThread extends Thread {

        int length;

        MeditationThread(int length) {
            this.length = length;
        }

        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
                Log.d(LOGGER, "Start Thread: " + 1);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    class MeditationRunnable implements Runnable {

        int length;

        MeditationRunnable(int length) {
            this.length = length;
        }

        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
                Log.d(LOGGER, "Start Thread: " + 1);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    } */
}