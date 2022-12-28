package com.example.infosecuritysysapp.model;

import java.io.Serializable;

public class PersonMessageModel implements Serializable {
    public int id;
    public String content;
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
}
