package com.example.infosecuritysysapp.model.socket;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.Serializable;

public class BaseSocketModel<T> implements Serializable {

   private String methodName;
   private String methodBody;

    public BaseSocketModel(String methodName, T Body) {
        this.methodName = methodName;
        this.methodBody = getJsonModel(Body);
    }

    public String getMethodName() {
        return methodName;
    }

    public String getMethodBody() {
        return methodBody;
    }

    private String getJsonModel(T methodBody){
        return new Gson().toJson(methodBody);
    }

    public String toJson(){
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("methodName",methodName);
        jsonObject.addProperty("methodBody",methodBody);
        return jsonObject.toString();
    }

    public static BaseSocketModel fromJson(String json){
        return new Gson().fromJson(json,BaseSocketModel.class);
    }

    public String create(){
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("methodName",methodName);
        jsonObject.addProperty("methodBody",methodBody);
        return jsonObject.toString();
    }

}
