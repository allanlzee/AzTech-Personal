package com.allan.lin.zhou.scheduler;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;

public class AlertReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // Shows Notification when Fired
        NotificationHelper notificationHelper = new NotificationHelper(context);
        int index = CalendarUtilities.notificationID - 1;
        String notificationName = CalendarUtilities.notificationNames.get(index);
        NotificationCompat.Builder notificationBuilder = notificationHelper.getChannelNotification(notificationName);

        /* NotificationCompat.Builder notificationBuilder = notificationHelper.
               getChannelNotification(CalendarUtilities.name); */

        // TODO: figure out how to send multiple notifications simultaneously

        notificationHelper.getManager().notify(CalendarUtilities.notificationID, notificationBuilder.build());
    }
}
