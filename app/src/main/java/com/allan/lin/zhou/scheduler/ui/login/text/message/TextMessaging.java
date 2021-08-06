package com.allan.lin.zhou.scheduler.ui.login.text.message;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;

import com.allan.lin.zhou.scheduler.R;
import com.allan.lin.zhou.scheduler.databinding.TextMessagingActivityBinding;
import com.allan.lin.zhou.scheduler.ui.login.Preferences;
import com.allan.lin.zhou.scheduler.ui.login.adapters.TextMessagesAdapter;
import com.allan.lin.zhou.scheduler.ui.login.firebase.Constants;
import com.allan.lin.zhou.scheduler.ui.login.firebase.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class TextMessaging extends AppCompatActivity {

    private TextMessagingActivityBinding binding;
    private FirebaseUser userRecipient;
    private ArrayList<ChatMessageObject> textMessages;
    private TextMessagesAdapter textMessagesAdapter;
    private Preferences preferenceManager;
    private FirebaseFirestore database;
    private Bitmap profilePicture;

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
        initializeViews();

        binding.sendButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                sendMessage();
            }
        });
    }

    // Loads User Data into the Text Message Layout
    private void loadRecipientDetails() {
        userRecipient = (FirebaseUser) getIntent().getSerializableExtra(Constants.KEY_USER);
        binding.recipientName.setText(userRecipient.username);
        byte[] bytes = Base64.decode(userRecipient.image, Base64.DEFAULT);
        Bitmap decodedBitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        profilePicture = decodedBitmap;
        binding.profilePicture.setImageBitmap(decodedBitmap);
    }

    private void initializeViews() {
        preferenceManager = new Preferences(getApplicationContext());
        textMessages = new ArrayList<>();
        textMessagesAdapter = new TextMessagesAdapter(textMessages, profilePicture, preferenceManager.getString(Constants.KEY_USER_ID));
        binding.textMessagesRecyclerView.setAdapter(textMessagesAdapter);
        database = FirebaseFirestore.getInstance();
    }

    private void sendMessage() {
        HashMap<String, Object> message = new HashMap<>();
        message.put(Constants.KEY_SENDER_ID, preferenceManager.getString(Constants.KEY_USER_ID));
        message.put(Constants.KEY_RECEIVER_ID, userRecipient.id);
        message.put(Constants.KEY_MESSAGE, binding.textMessageInput.getText().toString());
        message.put(Constants.KEY_DATETIME, new Date());
        database.collection(Constants.KEY_COLLECTION_CHAT).add(message);
        binding.textMessageInput.setText(null);
    }
}