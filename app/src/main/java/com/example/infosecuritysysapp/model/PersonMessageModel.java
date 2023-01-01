package com.example.infosecuritysysapp.model;

public class PersonMessageModel {
    public int id;
    public   String content;
    public String fromUser;
    public String toUser;
    public String digitalSignature;
    public int type;

    public PersonMessageModel(String deviceIp, String fromUser, String toUser, String content){
        this.content = content;
        this.fromUser = fromUser;
        this.toUser = toUser;
    }
}
