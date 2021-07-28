package com.allan.lin.zhou.scheduler.communication;

public class Chats {

    private String chatMessage;
    private String chatSender;

    public Chats(String message, String sender) {
        this.chatMessage = message;
        this.chatSender = sender;
    }

    public String getMessage() {
        return chatMessage;
    }

    public void setMessage(String message) {
        this.chatMessage = message;
    }

    public String getSender() {
        return chatSender;
    }

    public void setSender(String sender) {
        this.chatSender = sender;
    }

}
