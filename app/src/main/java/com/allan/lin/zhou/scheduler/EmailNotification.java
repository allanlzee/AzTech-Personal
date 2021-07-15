package com.allan.lin.zhou.scheduler;

import androidx.appcompat.app.AppCompatActivity;

import android.app.NotificationManager;
import android.content.Context;
import android.os.Bundle;
import android.text.util.Linkify;
import android.widget.TextView;

public class EmailNotification extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.email_notification_activity);

        TextView received = (TextView) findViewById(R.id.notification);
        received.setText(receiveData());
        Linkify.addLinks(received, Linkify.ALL);
    }

    private String receiveData() {
        String message = "";
        int id = 0;

        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            message = "Error";
        } else {
            id = extras.getInt("NotificationID");
            message = extras.getString("Message");
        }

        message = "Notification ID: " + id + " Message: " + message;
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.cancel(id);
        return message;
    }

}