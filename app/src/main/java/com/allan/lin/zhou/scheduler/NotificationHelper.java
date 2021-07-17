package com.allan.lin.zhou.scheduler;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

public class NotificationHelper extends ContextWrapper {

    public static final String reminderID = "Reminders";
    public static final String reminderName = "RemindersChannel";
    public static final String emailID = "Email";
    public static final String emailName = "EmailChannel";
    public static final String mindfulnessID = "Mindfulness";
    public static final String mindfulnessName = "MindfulnessChannel";
    public static final String scheduleAlarmID = "ScheduleAlarm";
    public static final String scheduleAlarmName = "ScheduleAlarmChannel";

    // Group Notifications
    public static final String reminderGroup = "RemindersGroup";
    public static final String emailGroup = "EmailsGroup";
    public static final String mindfulnessGroup = "MindfulnessGroup";

    Intent reminderIntent = new Intent(this, Reminders.class);
    // intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
    PendingIntent reminderPendingIntent = PendingIntent.getActivity(this, 0, reminderIntent, 0);

    Intent emailIntent = new Intent(this, Email.class);
    PendingIntent emailPendingIntent = PendingIntent.getActivity(this, 1, emailIntent, 0);

    Intent mindfulnessIntent = new Intent(this, Mindfulness.class);
    PendingIntent mindfulnessPendingIntent = PendingIntent.getActivity(this, 2, mindfulnessIntent, 0);

    Intent scheduleIntent = new Intent(this, Schedule.class);
    PendingIntent schedulePendingIntent = PendingIntent.getActivity(this, 3, scheduleIntent, 0);

    private NotificationManager notificationManager;

    public NotificationHelper(Context base) {
        super(base);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createChannel();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void createChannel() {
        // 3 Notifications are able to be set off at the same time
        NotificationChannel reminderChannel = new NotificationChannel(reminderID, reminderName, NotificationManager.IMPORTANCE_DEFAULT);
        getManager().createNotificationChannel(reminderChannel);

        NotificationChannel emailChannel = new NotificationChannel(emailID, emailName, NotificationManager.IMPORTANCE_LOW);
        getManager().createNotificationChannel(emailChannel);

        NotificationChannel miscellaneousChannel = new NotificationChannel(mindfulnessID, mindfulnessName, NotificationManager.IMPORTANCE_LOW);
        getManager().createNotificationChannel(miscellaneousChannel);

        NotificationChannel morningAlarmChannel = new NotificationChannel(scheduleAlarmID, scheduleAlarmName, NotificationManager.IMPORTANCE_HIGH);
        getManager().createNotificationChannel(morningAlarmChannel);
    }

    public NotificationManager getManager() {
        if (notificationManager == null) {
            notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }

        return notificationManager;
    }

    public NotificationCompat.Builder getReminderNotification(String name) {
        String notification = "Reminder: " + name;
        return new NotificationCompat.Builder(getApplicationContext(), reminderID)
                .setContentTitle("AzTech Scheduler")
                .setContentText(notification)
                .setSmallIcon(R.drawable.notification)
                .setContentIntent(reminderPendingIntent)
                .setGroup(reminderGroup)
                .setAutoCancel(true);
    }

    public NotificationCompat.Builder getEmailNotification(String name) {
        String notification = "Email: " + name;
        return new NotificationCompat.Builder(getApplicationContext(), emailID)
                .setContentTitle("AzTech Scheduler")
                .setContentText(notification)
                .setSmallIcon(R.drawable.email)
                .setContentIntent(emailPendingIntent)
                .setGroup(emailGroup)
                .setAutoCancel(true);
    }

    public NotificationCompat.Builder getMindfulnessNotification(String name) {
        String notification = "AzTech Mindfulness: " + name;
        return new NotificationCompat.Builder(getApplicationContext(), mindfulnessID)
                .setContentTitle("AzTech Scheduler")
                .setContentText(notification)
                .setSmallIcon(R.drawable.exhalation)
                .setContentIntent(mindfulnessPendingIntent)
                .setGroup(mindfulnessGroup)
                .setAutoCancel(true);
    }

    public NotificationCompat.Builder getScheduleNotification(String name) {
        String notification = "Schedule: " + name;

        return new NotificationCompat.Builder(getApplicationContext(), scheduleAlarmID)
                .setContentTitle("AzTech Scheduler")
                .setContentText(notification)
                .setSmallIcon(R.drawable.edit_calendar)
                .setContentIntent(schedulePendingIntent)
                .setAutoCancel(true);
    }

}
