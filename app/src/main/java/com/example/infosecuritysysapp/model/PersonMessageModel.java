package com.example.infosecuritysysapp.model;

import java.io.Serializable;

public class PersonMessageModel implements Serializable {
import com.google.gson.Gson;

public class PersonMessageModel {
    public int id;
    public   String content;
    public String fromUser;
    public String toUser;
    public int type;

    String deviceIp;

    public PersonMessageModel(String deviceIp, String fromUser, String toUser, String content) {
        this.deviceIp = deviceIp;
        this.fromUser = fromUser;
        this.toUser = toUser;
         this.content = content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public PersonMessageModel(String content, String fromUser, String toUser, int type) {
        this.content = content;
        this.fromUser = fromUser;
        this.toUser = toUser;
        this.type = type;
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
