package com.example.infosecuritysysapp.model.socket;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.Serializable;

public class BaseSocketModel<T> implements Serializable {

    String methodName;
    String methodBody;
    String mac;

    public BaseSocketModel(String methodName, T Body, String mac) {
        this.methodName = methodName;
        this.methodBody = getJsonModel(Body);
        this.mac = mac;
    }

    private String getJsonModel(T methodBody){
        return new Gson().toJson(methodBody);
    }

    public String create(){
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("methodName",methodName);
        jsonObject.addProperty("methodBody",methodBody);
        jsonObject.addProperty("mac", mac);
        return jsonObject.toString();
    }

}
