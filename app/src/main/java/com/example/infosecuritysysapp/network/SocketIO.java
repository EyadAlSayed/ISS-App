package com.example.infosecuritysysapp.network;

import static com.example.infosecuritysysapp.network.api.ApiClient.BASE_IP;

import android.util.Log;

import com.example.infosecuritysysapp.helper.MyIP;
import com.example.infosecuritysysapp.model.socket.BaseSocketModel;

import java.net.URI;

import tech.gusavila92.websocketclient.WebSocketClient;

public class SocketIO extends WebSocketClient {

    private static SocketIO instance;
    private static final String TAG = "SocketIO";
    public static final String BASE_URI = "ws://"+BASE_IP+"/iss";

    private ISocket iSocket;
    public static SocketIO getInstance() {
        if(instance == null){
            instance = new SocketIO(URI.create(BASE_URI));
        }
        return instance;
    }

    public SocketIO(URI uri) {
        super(uri);
    }

    public void initWebSocketAndConnect(ISocket iSocket){
        this.iSocket = iSocket;
        instance.setConnectTimeout(10000);
        instance.setReadTimeout(60000);
        instance.enableAutomaticReconnection(5000);
        instance.connect();
    }

    @Override
    public void onOpen() {
        Log.e(TAG, "onOpen: ");
        send(new BaseSocketModel<>("REGIP",MyIP.getDeviceIp()).toJson());
    }

    @Override
    public void onTextReceived(String message) {
        Log.e(TAG, "onTextReceived: "+message);

        BaseSocketModel baseSocketModel = BaseSocketModel.fromJson(message);
        switch (baseSocketModel.getMethodName()){
            case "CHAT_REC":{
                iSocket.receivedMessage(baseSocketModel.getMethodBody());
                break;
            }
            default:break;
        }
    }

    @Override
    public void onBinaryReceived(byte[] data) {
        Log.e(TAG, "onBinaryReceived: ");
    }

    @Override
    public void onPingReceived(byte[] data) {
        Log.e(TAG, "onPingReceived: ");
    }

    @Override
    public void onPongReceived(byte[] data) {
        Log.e(TAG, "onPongReceived: ");
    }

    @Override
    public void onException(Exception e) {
        Log.e(TAG, "onException: "+e);
        iSocket.errorMessage(e.toString());
    }

    @Override
    public void onCloseReceived() {
        Log.e(TAG, "onCloseReceived: ");
        instance.connect();
    }


}
