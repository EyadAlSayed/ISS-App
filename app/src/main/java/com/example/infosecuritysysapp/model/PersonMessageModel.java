package com.example.infosecuritysysapp.model;

import java.io.Serializable;

import com.google.gson.Gson;

public class PersonMessageModel implements Serializable {

    public int id;
    public String content;
    public String fromUser;
    public String toUser;
    public int type;

    String deviceIp;

    public PersonMessageModel(String deviceIp, String fromUser, String toUser, String content, int type) {
        this.deviceIp = deviceIp;
        this.fromUser = fromUser;
        this.toUser = toUser;
        this.content = content;
        this.type = type;
    }

    public void setContent(String content) {
        this.content = content;
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
