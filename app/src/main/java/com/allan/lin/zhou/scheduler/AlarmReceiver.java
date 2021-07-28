package com.allan.lin.zhou.scheduler;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;

import androidx.core.app.NotificationCompat;

import com.allan.lin.zhou.scheduler.notification.NotificationHelper;

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // Shows Notification when Fired
        NotificationHelper notificationHelper = new NotificationHelper(context);

        NotificationCompat.Builder notificationBuilder = notificationHelper.getScheduleNotification(CalendarUtilities.scheduleAlarm);

        // Vibration on Notification
        notificationBuilder.setVibrate(new long[] {1000, 1000, 1000, 1000, 1000});

        // Lights
        notificationBuilder.setLights(Color.BLUE, 3000, 3000);

        // Sound
        // notificationBuilder.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));
        notificationBuilder.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE));

        notificationHelper.getManager().notify(10, notificationBuilder.build());
    }
}
