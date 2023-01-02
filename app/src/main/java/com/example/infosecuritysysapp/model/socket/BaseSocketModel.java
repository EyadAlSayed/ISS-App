package com.example.infosecuritysysapp.model.socket;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.Serializable;

public class BaseSocketModel<T> implements Serializable {

   private String methodName;
   private String methodBody;
   private String digitalSignature;

    public BaseSocketModel(String methodName, T Body) {
        this.methodName = methodName;
        this.methodBody = getJsonModel(Body);
    }
    public BaseSocketModel(String methodName, T Body, String digitalSignature) {
        this.methodName = methodName;
        this.methodBody = getJsonModel(Body);
        this.digitalSignature = digitalSignature;
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
        jsonObject.addProperty("digitalSignature", digitalSignature);
        return jsonObject.toString();
    }

    public static BaseSocketModel fromJson(String json){
        return new Gson().fromJson(json,BaseSocketModel.class);
    }

}
