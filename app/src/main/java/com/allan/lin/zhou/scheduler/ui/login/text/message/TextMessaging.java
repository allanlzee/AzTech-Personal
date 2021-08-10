package com.allan.lin.zhou.scheduler.ui.login.text.message;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Toast;

import com.allan.lin.zhou.scheduler.R;
import com.allan.lin.zhou.scheduler.Utilities;
import com.allan.lin.zhou.scheduler.databinding.TextMessagingActivityBinding;
import com.allan.lin.zhou.scheduler.ui.login.Preferences;
import com.allan.lin.zhou.scheduler.ui.login.adapters.TextMessagesAdapter;
import com.allan.lin.zhou.scheduler.ui.login.availability.APIClient;
import com.allan.lin.zhou.scheduler.ui.login.availability.APIService;
import com.allan.lin.zhou.scheduler.ui.login.availability.BaseAvailability;
import com.allan.lin.zhou.scheduler.ui.login.firebase.Constants;
import com.allan.lin.zhou.scheduler.ui.login.firebase.FirebaseUser;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.allan.lin.zhou.scheduler.Utilities.showLongToast;

public class TextMessaging extends BaseAvailability {

    private TextMessagingActivityBinding binding;
    private FirebaseUser userRecipient;
    private ArrayList<ChatMessageObject> textMessages;
    private TextMessagesAdapter textMessagesAdapter;
    private Preferences preferenceManager;
    private FirebaseFirestore database;
    private Bitmap profilePicture;
    private String conversionID = null;

    // Retrofit API Boolean
    private Boolean isReceiverAvailable = false;

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

        binding.profilePicture.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                startActivity(new Intent(TextMessaging.this, UserInformation.class));
            }
        });

        listenMessages();
    }

    // *************************** Initialization *************************** //
    // Loads User Data into the Text Message Layout
    private void loadRecipientDetails() {
        userRecipient = (FirebaseUser) getIntent().getSerializableExtra(Constants.KEY_USER);

        Utilities.textMessageRecipient = userRecipient;

        binding.recipientName.setText(userRecipient.username);

        profilePicture = getProfilePicture(userRecipient.image);
        binding.profilePicture.setImageBitmap(profilePicture);
    }

    private Bitmap getProfilePicture(String encodedImage) {
        if (encodedImage != null) {
            byte[] bytes = Base64.decode(encodedImage, Base64.DEFAULT);
            return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        } else {
            return null;
        }
    }

    private void initializeViews() {
        chooseBackgroundTheme(Utilities.backgroundTheme);
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

        if (!isReceiverAvailable) {
            try {
                JSONArray tokens = new JSONArray();
                tokens.put(userRecipient.fcmToken);

                JSONObject data = new JSONObject();
                data.put(Constants.KEY_USER_ID, preferenceManager.getString(Constants.KEY_USER_ID));
                data.put(Constants.KEY_NAME, preferenceManager.getString(Constants.KEY_NAME));
                data.put(Constants.KEY_FCM_TOKEN, preferenceManager.getString(Constants.KEY_FCM_TOKEN));
                data.put(Constants.KEY_MESSAGE, binding.textMessageInput.getText().toString());

                JSONObject messageBody = new JSONObject();
                messageBody.put(Constants.REMOTE_MSG_DATA, data);
                messageBody.put(Constants.REMOTE_MSG_REGISTRATION_IDS, tokens);

                sendNotification(messageBody.toString());

            } catch (Exception exception) {
                showLongToast(exception.getMessage(), TextMessaging.this);
            }
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
                    Utilities.recipientEmail = documentChange.getDocument().getString(Constants.KEY_EMAIL);
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

    // *************************** Listeners for API and Messages *************************** //

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

    private void listenAvailableReceiver() {
        database.collection(Constants.KEY_COLLECTION_USERS).document(
                userRecipient.id
        ).addSnapshotListener(TextMessaging.this, (value, error) -> {
            if (error != null) return;

            if (value != null) {
                if (value.getLong(Constants.KEY_AVAILABILITY) != null) {
                    int availability = Objects.requireNonNull(
                            value.getLong(Constants.KEY_AVAILABILITY)
                    ).intValue();

                    /* if (availability == 1) {
                        isReceiverAvailable = true;
                    } else {
                        isReceiverAvailable = false;
                    } */

                    // if availability is 1, isReceiverAvailable = true
                    isReceiverAvailable = availability == 1;
                }
                // Assign the Recipient a unique ID
                userRecipient.fcmToken = value.getString(Constants.KEY_FCM_TOKEN);

                if (userRecipient.image == null) {
                    userRecipient.image = value.getString(Constants.KEY_IMAGE);
                    textMessagesAdapter.setRecipientProfilePicture(getProfilePicture(userRecipient.image));
                    textMessagesAdapter.notifyItemRangeChanged(0, textMessages.size());
                }
            }

            if (isReceiverAvailable) {
                binding.onlineStatus.setVisibility(View.VISIBLE);
            } else {
                binding.onlineStatus.setVisibility(View.INVISIBLE);
            }
        });
    }

    // Notifications
    private void sendNotification(String messageBody) {
        APIClient.getClient().create(APIService.class).sendMessage(
                Constants.getRemoteMsgHeaders(),
                messageBody
        ).enqueue(new Callback<String>() {

            @Override
            public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                if (response.isSuccessful()) {
                    try {
                        if (response.body() != null) {
                            JSONObject responseJson = new JSONObject(response.body());
                            JSONArray results = responseJson.getJSONArray("results");

                            // If response fails, display the error message
                            if (responseJson.getInt("failure") == 1) {
                                JSONObject error = (JSONObject) results.get(0);
                                Toast.makeText(getApplicationContext(), error.getString("error"), Toast.LENGTH_LONG).show();
                                return;
                            }
                        }
                    } catch (JSONException exception) {
                        exception.printStackTrace();
                    }

                    Toast.makeText(getApplicationContext(), "Notification Sent!", Toast.LENGTH_LONG).show();
                } else {
                    String errorMsg = "Error: " + response.code();
                    Toast.makeText(getApplicationContext(), errorMsg, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
                // Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                showLongToast(t.getMessage(), TextMessaging.this);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        listenAvailableReceiver();
    }

    // *************************** Listeners for API and Messages *************************** //

    private void chooseBackgroundTheme(String color) {
        switch (color) {
            case "Red":
                binding.backgroundView.setBackgroundColor(getResources().getColor(R.color.card1));
                break;
            case "Orange":
                binding.backgroundView.setBackgroundColor(getResources().getColor(R.color.button));
                break;
            case "Green":
                binding.backgroundView.setBackgroundColor(getResources().getColor(R.color.home_action));
                break;
            case "Blue":
                binding.backgroundView.setBackgroundColor(getResources().getColor(R.color.light_blue_A400));
                break;
            case "Purple":
                binding.backgroundView.setBackgroundColor(getResources().getColor(R.color.card6));
                break;
            default:
                binding.backgroundView.setBackgroundColor(getResources().getColor(R.color.white));
                break;
        }
    }
}