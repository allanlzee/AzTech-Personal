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
        // NotificationCompat.Builder notificationBuilder = notificationHelper.
        //        getChannelNotification(CalendarUtilities.notificationNames.get(CalendarUtilities.notificationID - 1));

        NotificationCompat.Builder notificationBuilder = notificationHelper.
                getChannelNotification(CalendarUtilities.name);
        notificationHelper.getManager().notify(CalendarUtilities.notificationID, notificationBuilder.build());
    }
}
