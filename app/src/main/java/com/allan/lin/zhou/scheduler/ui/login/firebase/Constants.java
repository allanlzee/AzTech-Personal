package com.allan.lin.zhou.scheduler.ui.login.firebase;

import java.util.HashMap;

public class Constants {
    public static final String KEY_COLLECTION_USERS = "users";
    public static final String KEY_NAME = "name";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_PREFERENCE_NAME = "appPreference";
    public static final String KEY_IS_SIGNED_IN = "isSignedIn";
    public static final String KEY_USER_ID = "userId";
    public static final String KEY_IMAGE = "image";
    public static final String KEY_FCM_TOKEN = "fcmToken";
    public static final String KEY_USER = "user";
    public static final String KEY_COLLECTION_CHAT = "chat";
    public static final String KEY_SENDER_ID = "senderID";
    public static final String KEY_RECEIVER_ID = "receiverID";
    public static final String KEY_MESSAGE = "message";
    public static final String KEY_DATETIME = "datetime";

    // Constants for Recent Conversations
    public static final String KEY_COLLECTION_CONVERSATIONS = "conversations";
    public static final String KEY_SENDER_NAME = "senderName";
    public static final String KEY_SENDER_PROFILE_IMAGE = "senderProfileImage";
    public static final String KEY_RECEIVER_NAME = "receiverName";
    public static final String KEY_RECEIVER_PROFILE_IMAGE = "receiverProfileImage";
    public static final String KEY_LAST_MESSAGE = "lastMessage";

    // User Availability
    public static final String KEY_AVAILABILITY = "availability";

    // Notifications using Remote Messages
    public static final String REMOTE_MSG_AUTHORIZATION = "Authorization";
    public static final String REMOTE_MSG_CONTENT_TYPE = "Content-Type";
    public static final String REMOTE_MSG_DATA = "data";
    public static final String REMOTE_MSG_REGISTRATION_IDS = "registration_ids";

    // Server ID: AAAA2s2Uhxw:APA91bFSgOgSA44p9J0dsO87iA_PA6WhCX10IAbz7BCCZ-6dWQp9HeMbP6-o_dGlKOPEEW1QjB_GA7lDvx_QMOMJ-KqGm69Q-QehLB4LSxRqTuR5Ir_PKhL8GdKYiiyQ0-wXm9iHxOTO

    public static HashMap<String, String> remoteMsgHeaders = null;
    public static HashMap<String, String> getRemoteMsgHeaders() {
        if (remoteMsgHeaders == null) {
            remoteMsgHeaders = new HashMap<>();
            remoteMsgHeaders.put(
                    REMOTE_MSG_AUTHORIZATION,
                    "key=AAAA2s2Uhxw:APA91bFSgOgSA44p9J0dsO87iA_PA6WhCX10IAbz7BCCZ-6dWQp9HeMbP6-o_dGlKOPEEW1QjB_GA7lDvx_QMOMJ-KqGm69Q-QehLB4LSxRqTuR5Ir_PKhL8GdKYiiyQ0-wXm9iHxOTO"
            );
            remoteMsgHeaders.put(
                    REMOTE_MSG_CONTENT_TYPE,
                    "application/json"
            );
        }

        return remoteMsgHeaders;
    }

    // Reminders
    public static final String KEY_REMINDER = "reminder";
    public static final String KEY_REMINDER_TIME = "reminder-time";
    public static final String KEY_REMINDER_COLLECTION = "reminders";
}
