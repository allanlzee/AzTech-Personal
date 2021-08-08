package com.allan.lin.zhou.scheduler;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.allan.lin.zhou.scheduler.ui.login.firebase.FirebaseUser;

import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import static androidx.core.content.ContextCompat.getSystemService;

public class Utilities {

    public static int picReminders = 0;

    // Profile Image
    public static Bitmap profileImage = null;
    public static Boolean isLoggedIn = false;

    public static Bitmap senderProfileImage = null;
    public static String username = null;
    public static String recipientEmail = null;

    public static FirebaseUser textMessageRecipient = null;

    public static void scheduler(Activity activity) {
        Runnable task = ()->Toast.makeText(activity, "Toast Message", Toast.LENGTH_SHORT).show();

        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(2);
        ScheduledFuture scheduler = executorService.scheduleAtFixedRate(task, 1000, 2500, TimeUnit.MILLISECONDS);
    }

    public static void showLongToast(String msg, Activity activity) {
        Toast.makeText(activity, msg, Toast.LENGTH_LONG).show();
    }

    public static void showShortToast(String msg, Activity activity) {
        Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();
    }
}
