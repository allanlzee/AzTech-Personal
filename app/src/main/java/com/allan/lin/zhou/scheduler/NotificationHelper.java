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
    public static final String miscellaneousID = "Miscellaneous";
    public static final String miscellaneousName = "MiscellaneousChannel";
    public static final String morningAlarmID = "MorningAlarm";
    public static final String morningAlarmName = "MorningAlarmChannel";

    // Group Notifications
    public static final String reminderGroup = "RemindersGroup";
    public static final String emailGroup = "EmailsGroup";
    public static final String miscellaneousGroup = "MiscellaneousGroup";

    Intent reminderIntent = new Intent(this, Reminders.class);
    // intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
    PendingIntent reminderPendingIntent = PendingIntent.getActivity(this, 0, reminderIntent, 0);

    Intent emailIntent = new Intent(this, Email.class);
    PendingIntent emailPendingIntent = PendingIntent.getActivity(this, 1, emailIntent, 0);

    Intent miscellaneousIntent = new Intent(this, MainActivity.class);
    PendingIntent miscellaneousPendingIntent = PendingIntent.getActivity(this, 2, miscellaneousIntent, 0);

    Intent morningIntent = new Intent(this, Schedule.class);
    PendingIntent morningPendingIntent = PendingIntent.getActivity(this, 3, morningIntent, 0);

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

        NotificationChannel miscellaneousChannel = new NotificationChannel(miscellaneousID, miscellaneousName, NotificationManager.IMPORTANCE_LOW);
        getManager().createNotificationChannel(miscellaneousChannel);

        NotificationChannel morningAlarmChannel = new NotificationChannel(morningAlarmID, morningAlarmName, NotificationManager.IMPORTANCE_HIGH);
        getManager().createNotificationChannel(morningAlarmChannel);
    }

    public NotificationManager getManager() {
        if (notificationManager == null) {
            notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }

        return notificationManager;
    }

    // TODO: add notification channel parameter
    // TODO: change parameters for reminder activities subsequently

    public NotificationCompat.Builder getReminderNotification(String name) {
        String notification = "Reminder: " + name;
        return new NotificationCompat.Builder(getApplicationContext(), reminderID)
                .setContentTitle("AzTech Scheduler")
                .setContentText(notification)
                .setSmallIcon(R.drawable.notification)
                .setContentIntent(reminderPendingIntent) // goes to Reminders.class on click
                .setGroup(reminderGroup)
                .setAutoCancel(true);
    }

    public NotificationCompat.Builder getEmailNotification(String name) {
        String notification = "Email: " + name;
        return new NotificationCompat.Builder(getApplicationContext(), emailID)
                .setContentTitle("AzTech Scheduler")
                .setContentText(notification)
                .setSmallIcon(R.drawable.email)
                .setContentIntent(emailPendingIntent) // goes to Reminders.class on click
                .setGroup(emailGroup)
                .setAutoCancel(true);
    }

    public NotificationCompat.Builder getMiscellaneousNotification(String name) {
        String notification = "AzTech: " + name;
        return new NotificationCompat.Builder(getApplicationContext(), miscellaneousID)
                .setContentTitle("AzTech Scheduler")
                .setContentText(notification)
                .setSmallIcon(R.drawable.notification)
                .setContentIntent(miscellaneousPendingIntent) // goes to Reminders.class on click
                .setGroup(miscellaneousGroup)
                .setAutoCancel(true);
    }

    public NotificationCompat.Builder getMorningNotification(String name) {
        String notification = "Schedule: " + name;
        return new NotificationCompat.Builder(getApplicationContext(), morningAlarmID)
                .setContentTitle("AzTech Scheduler")
                .setContentText(notification)
                .setSmallIcon(R.drawable.edit_calendar)
                .setContentIntent(morningPendingIntent) // goes to Reminders.class on click
                .setAutoCancel(true);
    }

}
