package com.example.infosecuritysysapp.model;

import com.google.gson.Gson;

public class PersonMessageModel {
    public int id;
    public String content;
    public String fromUser;
    public String toUser;
    public int type;

    public String deviceIp;
    public String sender;

    public PersonMessageModel(String content, String fromUser, String toUser, int type) {
        this.content = content;
        this.fromUser = fromUser;
        this.toUser = toUser;
        this.type = type;
    }

    public PersonMessageModel(String deviceIp, String fromUser, String toUser, String content, String sender) {
        this.deviceIp = deviceIp;
        this.fromUser = fromUser;
        this.toUser = toUser;
        this.content = content;
        this.sender = sender;
    }

    public int getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public String getFromUser() {
        return fromUser;
    }

    public String getToUser() {
        return toUser;
    }

    public int getType() {
        return type;
    }

    public static PersonMessageModel fromJson(String json) {
        return new Gson().fromJson(json, PersonMessageModel.class);
    }
}
