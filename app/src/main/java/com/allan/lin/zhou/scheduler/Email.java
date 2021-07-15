package com.allan.lin.zhou.scheduler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.allan.lin.zhou.scheduler.databinding.EmailActivityBinding;

import static com.allan.lin.zhou.scheduler.Navigation.backToHome;

public class Email extends AppCompatActivity {

    private int messageCount = 0;
    private static Uri sound;
    private final long[] vibration = {500, 500, 500};
    private NotificationManager notificationManager;

    private EmailActivityBinding binding;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.email_activity);

        binding = EmailActivityBinding.inflate(getLayoutInflater());

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        binding.homeButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                backToHome(view, Email.this);
            }
        });

        sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        binding.notification.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                showNotification();
            }
        });
    }

    private void showNotification() {
        Log.i("Start", "Notification");

        NotificationCompat.Builder builder = new NotificationCompat.Builder(Email.this);

        builder.setContentTitle("AzTech Emails");
        builder.setContentText("Email Notification with Vibration");
        builder.setTicker("Alert");
        builder.setSmallIcon(R.drawable.notification);

        builder.setNumber(++messageCount);
        builder.setSound(sound);
        builder.setVibrate(vibration);

        Intent intent = new Intent(Email.this, EmailNotification.class);
        intent.putExtra("NotificationID", 111);
        intent.putExtra("Message", "AzTech Scheduler");

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(EmailNotification.class);

        stackBuilder.addNextIntent(intent);

        PendingIntent pendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);

        notificationManager.notify(111, builder.build());
    }
}