package com.allan.lin.zhou.scheduler;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

public class NotificationHelper extends ContextWrapper {

    public static final String channelId = "Channel ID";
    public static final String channelName = "Channel Name";

    private NotificationManager notificationManager;

    public NotificationHelper(Context base) {
        super(base);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createChannel();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void createChannel() {
        NotificationChannel channel = new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH);

        getManager().createNotificationChannel(channel);
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
