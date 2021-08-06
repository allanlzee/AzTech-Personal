package com.allan.lin.zhou.scheduler.ui.login.text.message;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.MenuItem;
import android.view.View;

import com.allan.lin.zhou.scheduler.R;
import com.allan.lin.zhou.scheduler.databinding.TextMessagingActivityBinding;
import com.allan.lin.zhou.scheduler.ui.login.firebase.Constants;
import com.allan.lin.zhou.scheduler.ui.login.firebase.FirebaseUser;

public class TextMessaging extends AppCompatActivity {

    private TextMessagingActivityBinding binding;
    private FirebaseUser userRecipient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.text_messaging_activity);

        binding = TextMessagingActivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.navigateBack.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                finish();
            }
        });

        // Load User Data
        loadRecipientDetails();
    }

    // Loads User Data into the Text Message Layout
    private void loadRecipientDetails() {
        userRecipient = (FirebaseUser) getIntent().getSerializableExtra(Constants.KEY_USER);
        binding.recipientName.setText(userRecipient.username);
        byte[] bytes = Base64.decode(userRecipient.image, Base64.DEFAULT);
        Bitmap decodedBitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        binding.profilePicture.setImageBitmap(decodedBitmap);
    }
}