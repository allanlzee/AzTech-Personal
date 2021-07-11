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

import com.allan.lin.zhou.scheduler.databinding.MeditationActivityBinding;

import android.os.Handler;

import static com.allan.lin.zhou.scheduler.Navigation.backToHome;

public class Meditation extends AppCompatActivity {

    private MeditationActivityBinding binding;
    private Toolbar toolbar;
    private long duration = 1500;

    // Logger
    private static final String LOGGER = "MAIN";

    // Periodic Handlers
    Handler handlerInhale;
    Handler handlerExhale;
    Handler meditation;
    Runnable runnable;
    boolean isInhale = true;

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

        binding.startButton.setOnClickListener(new View.OnClickListener() {

            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                /* meditationInhale();
                meditationExhale(); */
                meditation();
            }
        });

        binding.stopButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                cancelHandler();
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

    private void meditation() {
        meditation = new Handler();

        runnable = new Runnable() {

            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void run() {
                if (isInhale) {
                    inhale();
                    isInhale = false;
                    Log.d("Handler", "Inhale");
                } else {
                    exhale();
                    isInhale = true;
                    Log.d("Handler", "Exhale");
                }

                meditation.postDelayed(this, 3000);
            }
        };

        meditation.post(runnable);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void meditationInhale() {
        handlerInhale = new Handler();

        runnable = new Runnable() {

            @Override
            public void run() {
                inhale();
                Log.d("Handler", "Periodic Inhale");

                handlerInhale.postDelayed(this, 4000);
            }
        };

        handlerInhale.post(runnable);

        /* for (int i = 0; i < 10; i++) {

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
        } */
    }

    private void meditationExhale() {
        handlerExhale = new Handler();

        Runnable runnable = new Runnable() {

            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void run() {
                handlerExhale.postDelayed(this, 4000);

                exhale();
                Log.d("Handler", "Periodic Inhale");
            }
        };

        handlerExhale.post(runnable);
    }

    private void cancelHandler() {
        meditation.removeCallbacks(runnable);
    }
}