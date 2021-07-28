package com.allan.lin.zhou.scheduler.communication;

public class MessageModel {

    private String cnt;

    public MessageModel(String msg) {
        this.cnt = msg;
    }

    public String getCnt() {
        return cnt;
    }

    public void setMsg(String msg) {
        this.cnt = msg;
    }
}
