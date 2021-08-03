package com.allan.lin.zhou.scheduler;

import android.app.Activity;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import static androidx.core.content.ContextCompat.getSystemService;

public class Utilities {

    public static int picReminders = 0;

    // Freezes the app's thread for a specified amount of time
    public static void wait(int length) {
        try {
            Thread.sleep(length);
        } catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }

    public static void scheduler(Activity activity) {
        Runnable task = ()->Toast.makeText(activity, "Toast Message", Toast.LENGTH_SHORT).show();

        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(2);
        ScheduledFuture scheduler = executorService.scheduleAtFixedRate(task, 1000, 2500, TimeUnit.MILLISECONDS);
    }
}
