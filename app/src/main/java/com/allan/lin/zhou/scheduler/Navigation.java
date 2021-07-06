package com.allan.lin.zhou.scheduler;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import androidx.fragment.app.Fragment;

import com.google.android.material.snackbar.Snackbar;

public class Navigation {

    // Brings user back to main activity
    // Used for action on floating buttons
    public static void backToHome(View view, Activity activity) {
        Snackbar.make(view, "Back to Home", Snackbar.LENGTH_SHORT)
                .setAction("Go", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(activity, MainActivity.class);
                        activity.startActivity(intent);
                    }
                }).setActionTextColor(activity.getResources().getColor(R.color.home_action))
                .setTextColor(activity.getResources().getColor(R.color.home_snack))
                .show();
    }

}
