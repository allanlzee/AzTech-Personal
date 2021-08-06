package com.allan.lin.zhou.scheduler.ui.login.text.message;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
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
    private String conversionID = null;

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

    // *************************** Initialization *************************** //
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
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        binding.textMessagesRecyclerView.setLayoutManager(layoutManager);
        textMessagesAdapter = new TextMessagesAdapter(textMessages, profilePicture, preferenceManager.getString(Constants.KEY_USER_ID));
        binding.textMessagesRecyclerView.setAdapter(textMessagesAdapter);
        database = FirebaseFirestore.getInstance();
    }

    // *************************** Initialization *************************** //

    // *************************** Message Sending *************************** //

    private void sendMessage() {
        HashMap<String, Object> message = new HashMap<>();
        message.put(Constants.KEY_SENDER_ID, preferenceManager.getString(Constants.KEY_USER_ID));
        message.put(Constants.KEY_RECEIVER_ID, userRecipient.id);
        message.put(Constants.KEY_MESSAGE, binding.textMessageInput.getText().toString());
        message.put(Constants.KEY_DATETIME, new Date());
        database.collection(Constants.KEY_COLLECTION_CHAT).add(message);

        if (conversionID != null) {
            updateConversion(binding.textMessageInput.getText().toString());
        } else { // New Message Conversion
            HashMap<String, Object> conversion = new HashMap<>();
            conversion.put(Constants.KEY_SENDER_ID, preferenceManager.getString(Constants.KEY_USER_ID));
            conversion.put(Constants.KEY_SENDER_NAME, preferenceManager.getString(Constants.KEY_NAME));
            conversion.put(Constants.KEY_SENDER_PROFILE_IMAGE, preferenceManager.getString(Constants.KEY_IMAGE));
            conversion.put(Constants.KEY_RECEIVER_ID, userRecipient.id);
            conversion.put(Constants.KEY_RECEIVER_NAME, userRecipient.username);
            conversion.put(Constants.KEY_RECEIVER_PROFILE_IMAGE, userRecipient.image);
            conversion.put(Constants.KEY_LAST_MESSAGE, binding.textMessageInput.getText().toString());
            conversion.put(Constants.KEY_DATETIME, new Date());
            addConversion(conversion);
        }

        binding.textMessageInput.setText(null);
    }

    private String getDateTime(Date date) {
        return new SimpleDateFormat("MMMM dd, yyyy - hh:mm a", Locale.getDefault()).format(date);
    }

    // *************************** Message Sending *************************** //

    // *************************** Message Recycler View *************************** //

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

        // Check for Conversions
        if (conversionID == null) {
            checkForConversion();
        }
    };

    // *************************** Message Recycler View *************************** //

    // *************************** Conversion *************************** //

    // Uses Firebase to get the most recent user message
    private final OnCompleteListener<QuerySnapshot> conversionOnCompleteListener = task -> {
        if (task.isSuccessful() && task.getResult() != null && task.getResult().getDocuments().size() > 0) {
            DocumentSnapshot documentSnapshot = task.getResult().getDocuments().get(0);
            conversionID = documentSnapshot.getId();
        }
    };

    // Checks for message conversion in Firebase
    private void checkForConversionRemotely(String senderID, String receiverID) {
        database.collection(Constants.KEY_COLLECTION_CONVERSATIONS)
                .whereEqualTo(Constants.KEY_SENDER_ID, senderID)
                .whereEqualTo(Constants.KEY_RECEIVER_ID, receiverID)
                .get()
                .addOnCompleteListener(conversionOnCompleteListener);
    }

    private void checkForConversion() {
        if (textMessages.size() != 0) {
            // Sender -> Recipient
            checkForConversionRemotely(preferenceManager.getString(Constants.KEY_USER_ID), userRecipient.id);
            // Recipient -> Sender
            checkForConversionRemotely(userRecipient.id, preferenceManager.getString(Constants.KEY_USER_ID));
        }
    }

    // Send the message conversion to Firebase Messaging
    private void addConversion(HashMap<String, Object> conversion) {
        database.collection(Constants.KEY_COLLECTION_CONVERSATIONS)
                .add(conversion)
                .addOnSuccessListener(documentReference -> conversionID = documentReference.getId());
    }

    private void updateConversion(String message) {
        DocumentReference documentReference = database.collection(Constants.KEY_COLLECTION_CONVERSATIONS).document(conversionID);
        documentReference.update(Constants.KEY_LAST_MESSAGE, message, Constants.KEY_DATETIME, new Date());
    }

    // *************************** Conversion *************************** //

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