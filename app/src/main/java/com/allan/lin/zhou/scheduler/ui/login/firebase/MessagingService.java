package com.allan.lin.zhou.scheduler.ui.login.firebase;

import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.allan.lin.zhou.scheduler.R;
import com.allan.lin.zhou.scheduler.ui.login.Login;
import com.allan.lin.zhou.scheduler.ui.login.text.message.TextMessaging;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class MessagingService extends FirebaseMessagingService {

    @Override
    public void onNewToken(@NonNull @NotNull String token) {
        super.onNewToken(token);
        Log.d("FCM", "Token: " + token);
    }

    @Override
    public void onMessageReceived(@NonNull @NotNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        Log.d("FCM", "Message: " + remoteMessage.getNotification().getBody());

        FirebaseUser user = new FirebaseUser();
        user.id = remoteMessage.getData().get(Constants.KEY_USER_ID);
        user.username = remoteMessage.getData().get(Constants.KEY_NAME);
        user.fcmToken = remoteMessage.getData().get(Constants.KEY_FCM_TOKEN);

        int notificationID = new Random().nextInt();
        String channelID = "chat_message";

        Intent intent = new Intent(this, TextMessaging.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.putExtra(Constants.KEY_USER, user);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, channelID);
        builder.setSmallIcon(R.drawable.message_notification);
        builder.setContentTitle(user.username);
        builder.setContentText(remoteMessage.getData().get(Constants.KEY_MESSAGE));
        builder.setStyle(new NotificationCompat.BigTextStyle().bigText(
                remoteMessage.getData().get(Constants.KEY_MESSAGE)
        ));
        builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
        builder.setContentIntent(pendingIntent);
        builder.setAutoCancel(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence channelName = "Chat Message";
            String channelDescription = "Chat Message Notification Channel";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;

            NotificationChannel channel = new NotificationChannel(channelID, channelName, importance);
            channel.setDescription(channelDescription);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }

        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(this);
        managerCompat.notify(notificationID, builder.build());
    }

    public static void getCurrentToken(Activity activity) {
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.w("FCM", "Fetching FCM registration token failed", task.getException());
                            return;
                        }

                        // Get new FCM registration token
                        String token = task.getResult();

                        // Log and toast
                        String msg = token;
                        Log.d("FCM", msg);
                        Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();
                    }
                });
    }
}

// TOKEN: cKfVIvSmStWckIAEwAtZqW:APA91bFU59zb-LBXSaIppnkIUW-PmDQJGNmnIC2W9B75ZuxZaxICT-6eu_mYJmvcbuZKKaS5ny2S7TSFxv3EaHIiJSoExs5coPb_RzqeiFP0_yUakVHtt7qRKVqKjDDon8_Lk3f_CFXh
