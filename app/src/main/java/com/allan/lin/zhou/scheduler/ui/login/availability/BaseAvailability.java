package com.allan.lin.zhou.scheduler.ui.login.availability;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.allan.lin.zhou.scheduler.ui.login.Preferences;
import com.allan.lin.zhou.scheduler.ui.login.firebase.Constants;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class BaseAvailability extends AppCompatActivity {

    private DocumentReference documentReference;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Preferences preferenceManager = new Preferences(getApplicationContext());
        FirebaseFirestore database = FirebaseFirestore.getInstance();

        // Retrieves user IDs from the Users section in Firebase
        documentReference = database.collection(Constants.KEY_COLLECTION_USERS)
            .document(preferenceManager.getString(Constants.KEY_USER_ID));

    }

    // If user closes activity, they will go offline
    @Override
    protected void onPause() {
        super.onPause();
        documentReference.update(Constants.KEY_AVAILABILITY, 0);
    }

    @Override
    protected void onResume() {
        super.onResume();
        documentReference.update(Constants.KEY_AVAILABILITY, 1);
    }
}
