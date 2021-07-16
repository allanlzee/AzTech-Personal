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

    // Group Notifications
    public static final String reminderGroup = "RemindersGroup";
    public static final String emailGroup = "EmailsGroup";
    public static final String miscellaneousGroup = "MiscellaneousGroup";

    Intent intent = new Intent(this, Reminders.class);
    // intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
    PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

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
        NotificationChannel reminderChannel = new NotificationChannel(reminderID, reminderName, NotificationManager.IMPORTANCE_HIGH);
        getManager().createNotificationChannel(reminderChannel);

        NotificationChannel emailChannel = new NotificationChannel(emailID, emailName, NotificationManager.IMPORTANCE_HIGH);
        getManager().createNotificationChannel(emailChannel);

        NotificationChannel miscellaneousChannel = new NotificationChannel(miscellaneousID, miscellaneousName, NotificationManager.IMPORTANCE_LOW);
        getManager().createNotificationChannel(miscellaneousChannel);
    }

    public NotificationManager getManager() {
        if (notificationManager == null) {
            notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }

        return notificationManager;
    }

    // TODO: add notification channel parameter
    // TODO: change parameters for reminder activities subsequently

    public NotificationCompat.Builder getChannelNotification(String name, String notificationID) {
        String notification = "Reminder: " + name;
        return new NotificationCompat.Builder(getApplicationContext(), notificationID)
                .setContentTitle("AzTech Scheduler")
                .setContentText(notification)
                .setSmallIcon(R.drawable.notification)
                .setContentIntent(pendingIntent) // goes to Reminders.class on click
                .setGroup(reminderGroup)
                .setAutoCancel(true);
    }
}
