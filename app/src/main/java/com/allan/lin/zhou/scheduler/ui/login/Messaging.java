package com.allan.lin.zhou.scheduler.ui.login;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.allan.lin.zhou.scheduler.R;
import com.allan.lin.zhou.scheduler.databinding.MessagingActivityBinding;
import com.allan.lin.zhou.scheduler.ui.login.adapters.RecentTextMessagesAdapter;
import com.allan.lin.zhou.scheduler.ui.login.availability.BaseAvailability;
import com.allan.lin.zhou.scheduler.ui.login.firebase.Constants;
import com.allan.lin.zhou.scheduler.ui.login.firebase.FirebaseUser;
import com.allan.lin.zhou.scheduler.ui.login.text.message.ChatMessageObject;
import com.allan.lin.zhou.scheduler.ui.login.text.message.TextMessaging;
import com.allan.lin.zhou.scheduler.ui.login.text.message.listeners.RecentMessageListener;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;

import static com.allan.lin.zhou.scheduler.Navigation.backToHome;

public class Messaging extends BaseAvailability implements RecentMessageListener {

    private MessagingActivityBinding binding;
    private Toolbar toolbar;

    private ArrayList<ChatMessageObject> textMessages;
    private RecentTextMessagesAdapter textMessagesAdapter;
    private FirebaseFirestore database;
    private Preferences preferenceManager;

    private String senderEmail = null;
    private String recipientEmail = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.messaging_activity);

        binding = MessagingActivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        preferenceManager = new Preferences(getApplicationContext());

        // Buttons, Backend Database, Recycler View Adapters and Layout Managers
        initialize();

        listenRecentConversations();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    // Initialize all Views and Objects
    private void initialize() {
        binding.homeButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                backToHome(view, Messaging.this);
            }
        });

        binding.newChatButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), UsersActivity.class));
            }
        });

        textMessages = new ArrayList<>();

        // Set Linear Layout Manager
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        binding.userRecentRecyclerView.setLayoutManager(layoutManager);

        // Set Recycler View Adapter
        textMessagesAdapter = new RecentTextMessagesAdapter(textMessages, this);
        binding.userRecentRecyclerView.setAdapter(textMessagesAdapter);

        // Initialize Firebase Database
        database = FirebaseFirestore.getInstance();
    }

    private final EventListener<QuerySnapshot> eventListener = (value, error) -> {
        if (error != null) return;

        if (value != null) {
            // Iterate through all messages in Firebase
            for (DocumentChange documentChange : value.getDocumentChanges()) {
                // Find recently added messages
                if (documentChange.getType() == DocumentChange.Type.ADDED) {
                    String senderID = documentChange.getDocument().getString(Constants.KEY_SENDER_ID);
                    String receiverID = documentChange.getDocument().getString(Constants.KEY_RECEIVER_ID);

                    ChatMessageObject chatMessageObject = new ChatMessageObject();
                    chatMessageObject.senderID = senderID;
                    chatMessageObject.receiverID = receiverID;
                    chatMessageObject.messageContent = documentChange.getDocument().getString(Constants.KEY_LAST_MESSAGE);
                    chatMessageObject.dateTimeObject = documentChange.getDocument().getDate(Constants.KEY_DATETIME);

                    if (preferenceManager.getString(Constants.KEY_USER_ID).equals(senderID)) {
                        chatMessageObject.conversionProfileImage = documentChange.getDocument().getString(Constants.KEY_RECEIVER_PROFILE_IMAGE);
                        chatMessageObject.conversionUsername = documentChange.getDocument().getString(Constants.KEY_RECEIVER_NAME);
                        chatMessageObject.conversionId = documentChange.getDocument().getString(Constants.KEY_RECEIVER_ID);
                    } else {
                        chatMessageObject.conversionProfileImage = documentChange.getDocument().getString(Constants.KEY_SENDER_PROFILE_IMAGE);
                        chatMessageObject.conversionUsername = documentChange.getDocument().getString(Constants.KEY_SENDER_NAME);
                        chatMessageObject.conversionId = documentChange.getDocument().getString(Constants.KEY_SENDER_ID);
                    }

                    textMessages.add(chatMessageObject);

                } else if (documentChange.getType() == DocumentChange.Type.MODIFIED) {
                    for (ChatMessageObject chatMessageObject : textMessages) {
                        String senderID = documentChange.getDocument().getString(Constants.KEY_SENDER_ID);
                        String receiverID = documentChange.getDocument().getString(Constants.KEY_RECEIVER_ID);

                        if (chatMessageObject.senderID.equals(senderID) && chatMessageObject.receiverID.equals(receiverID)) {
                            chatMessageObject.messageContent = documentChange.getDocument().getString(Constants.KEY_LAST_MESSAGE);
                            chatMessageObject.dateTimeObject = documentChange.getDocument().getDate(Constants.KEY_DATETIME);
                            break;
                        }
                    }
                }
            }

            // Sort the messages based on time
            Collections.sort(textMessages, (msg1, msg2) -> msg2.dateTimeObject.compareTo(msg1.dateTimeObject));
            textMessagesAdapter.notifyDataSetChanged();
            binding.userRecentRecyclerView.smoothScrollToPosition(0);
            binding.userRecentRecyclerView.setVisibility(View.VISIBLE);
            binding.progressBar.setVisibility(View.GONE);
        }
    };

    private void listenRecentConversations() {
        database.collection(Constants.KEY_COLLECTION_CONVERSATIONS)
                .whereEqualTo(Constants.KEY_SENDER_ID, preferenceManager.getString(Constants.KEY_USER_ID))
                .addSnapshotListener(eventListener);

        database.collection(Constants.KEY_COLLECTION_CONVERSATIONS)
                .whereEqualTo(Constants.KEY_RECEIVER_ID, preferenceManager.getString(Constants.KEY_USER_ID))
                .addSnapshotListener(eventListener);
    }

    // Inherited RecentMessageListener function
    // Brings user directly to the message activity
    @Override
    public void onRecentMessageClicked(FirebaseUser user) {
        Intent intent = new Intent(getApplicationContext(), TextMessaging.class);
        intent.putExtra(Constants.KEY_USER, user);
        startActivity(intent);
    }
}
