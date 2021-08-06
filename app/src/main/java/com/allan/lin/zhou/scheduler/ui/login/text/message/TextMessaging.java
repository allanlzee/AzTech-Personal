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
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

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

        listenMessages();
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

    private String getDateTime(Date date) {
        return new SimpleDateFormat("MMMM dd, yyyy - hh:mm a", Locale.getDefault()).format(date);
    }

    private final EventListener<QuerySnapshot> eventListener = (value, error) -> {
        if (error != null) {
            return;
        }

        if (value != null) {
            int size = textMessages.size();
            for (DocumentChange documentChange : value.getDocumentChanges()) {

                if (documentChange.getType() == DocumentChange.Type.ADDED) {
                    // Set variables of the ChatMessageObject to send messages to the recycler view
                    ChatMessageObject chatMessageObject = new ChatMessageObject();
                    chatMessageObject.senderID = documentChange.getDocument().getString(Constants.KEY_SENDER_ID);
                    chatMessageObject.receiverID = documentChange.getDocument().getString(Constants.KEY_RECEIVER_ID);
                    chatMessageObject.messageContent = documentChange.getDocument().getString(Constants.KEY_MESSAGE);
                    chatMessageObject.messageDateTime = getDateTime(documentChange.getDocument().getDate(Constants.KEY_DATETIME));
                    chatMessageObject.dateTimeObject = documentChange.getDocument().getDate(Constants.KEY_DATETIME);

                    // Add to ArrayList for Recycler View
                    textMessages.add(chatMessageObject);
                }
            }

            // Sort Messages by Time of Sending
            Collections.sort(textMessages, (dateTime1, dateTime2) ->
                    dateTime1.dateTimeObject.compareTo(dateTime2.dateTimeObject));

            if (size == 0) {
                textMessagesAdapter.notifyDataSetChanged();
            } else {
                textMessagesAdapter.notifyItemRangeInserted(size, size);
                binding.textMessagesRecyclerView.smoothScrollToPosition(textMessages.size() - 1);
            }
            binding.textMessagesRecyclerView.setVisibility(View.VISIBLE);
        }
        binding.progressBar.setVisibility(View.GONE);
    };

    private void listenMessages() {
        database.collection(Constants.KEY_COLLECTION_CHAT)
                .whereEqualTo(Constants.KEY_SENDER_ID, preferenceManager.getString(Constants.KEY_USER_ID))
                .whereEqualTo(Constants.KEY_RECEIVER_ID, userRecipient.id)
                .addSnapshotListener(eventListener);

        database.collection(Constants.KEY_COLLECTION_CHAT)
                .whereEqualTo(Constants.KEY_SENDER_ID, userRecipient.id)
                .whereEqualTo(Constants.KEY_RECEIVER_ID, preferenceManager.getString(Constants.KEY_USER_ID))
                .addSnapshotListener(eventListener);


    }
}