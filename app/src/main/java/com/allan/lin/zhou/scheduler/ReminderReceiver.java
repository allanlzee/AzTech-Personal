package com.allan.lin.zhou.scheduler;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;

import androidx.core.app.NotificationCompat;

public class ReminderReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // Shows Notification when Fired
        NotificationHelper notificationHelper = new NotificationHelper(context);

        int index = CalendarUtilities.notificationID - 1;
        String notificationName = CalendarUtilities.notificationNames.get(index);

        NotificationCompat.Builder notificationBuilder = notificationHelper.getReminderNotification(notificationName);

        // Vibration on Notification
        notificationBuilder.setVibrate(new long[] {1000, 1000, 1000, 1000, 1000});

        // Lights
        notificationBuilder.setLights(Color.BLUE, 3000, 3000);

        // Sound
        // notificationBuilder.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));
        notificationBuilder.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE));

        // TODO: figure out how to send multiple notifications simultaneously

        notificationHelper.getManager().notify(1, notificationBuilder.build());
    }
}
