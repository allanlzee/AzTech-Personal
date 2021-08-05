package com.allan.lin.zhou.scheduler.ui.login.firebase;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.allan.lin.zhou.scheduler.R;
import com.allan.lin.zhou.scheduler.ui.login.Login;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.jetbrains.annotations.NotNull;

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
