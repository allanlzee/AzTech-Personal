package com.allan.lin.zhou.scheduler;

import android.app.Notification;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;

import androidx.core.app.NotificationCompat;

public class AlertReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // Shows Notification when Fired
        NotificationHelper notificationHelper = new NotificationHelper(context);

        int index = CalendarUtilities.notificationID - 1;
        String notificationName = CalendarUtilities.notificationNames.get(index);

        // TODO FIX THIS

        NotificationCompat.Builder notificationBuilder = notificationHelper.getChannelNotification(notificationName);

        // Vibration on Notification
        notificationBuilder.setVibrate(new long[] {1000, 1000, 1000, 1000, 1000});

        // Lights
        notificationBuilder.setLights(Color.BLUE, 3000, 3000);

        // Sound
        notificationBuilder.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));

        // TODO: figure out how to send multiple notifications simultaneously

        notificationHelper.getManager().notify(1, notificationBuilder.build());
    }
}
