package com.allan.lin.zhou.scheduler;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

public class NotificationHelper extends ContextWrapper {

    public static final String channelId = "Channel ID 1";
    public static final String channelName = "Channel Name 1";
    public static final String channelId2 = "Channel ID 2";
    public static final String channelName2 = "Channel Name 2";
    public static final String channelId3 = "Channel ID 3";
    public static final String channelName3 = "Channel Name 3";

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
        NotificationChannel channel = new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH);
        getManager().createNotificationChannel(channel);

        NotificationChannel channel2 = new NotificationChannel(channelId2, channelName2, NotificationManager.IMPORTANCE_HIGH);
        getManager().createNotificationChannel(channel2);

        NotificationChannel channel3 = new NotificationChannel(channelId3, channelName3, NotificationManager.IMPORTANCE_HIGH);
        getManager().createNotificationChannel(channel3);
    }

    public NotificationManager getManager() {
        if (notificationManager == null) {
            notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }

        return notificationManager;
    }

    public NotificationCompat.Builder getChannelNotification(String name) {
        String notification = "Reminder: " + name;
        return new NotificationCompat.Builder(getApplicationContext(), channelId)
                .setContentTitle("AzTech Scheduler")
                .setContentText(notification)
                .setSmallIcon(R.drawable.notification);
    }
}
