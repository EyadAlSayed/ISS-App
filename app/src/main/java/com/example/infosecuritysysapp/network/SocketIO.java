package com.example.infosecuritysysapp.network;

import static android.os.Looper.getMainLooper;
import static com.example.infosecuritysysapp.config.AppConstants.CHAT_RECEIVED;
import static com.example.infosecuritysysapp.config.AppConstants.CHAT_RECEIVED_E;
import static com.example.infosecuritysysapp.config.AppConstants.CHAT_SEND;
import static com.example.infosecuritysysapp.config.AppConstants.CHAT_SEND_E;
import static com.example.infosecuritysysapp.config.AppSharedPreferences.GET_IS_LOGIN;
import static com.example.infosecuritysysapp.config.AppSharedPreferences.GET_USER_PHONE_NUMBER;
import static com.example.infosecuritysysapp.network.api.ApiClient.BASE_IP;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;

import com.example.infosecuritysysapp.helper.MyIP;
import com.example.infosecuritysysapp.model.socket.BaseSocketModel;

import java.net.URI;

import tech.gusavila92.websocketclient.WebSocketClient;

public class SocketIO extends WebSocketClient {

    private static SocketIO instance;
    private static final String TAG = "SocketIO";
    public static final String BASE_URI = "ws://" + BASE_IP + "/iss";

    private ISocket iSocket;

    public static SocketIO getInstance() {
        if (instance == null) {
            instance = new SocketIO(URI.create(BASE_URI));
        }
        return instance;
    }

    public SocketIO(URI uri) {
        super(uri);
    }

    public void initWebSocketAndConnect(ISocket iSocket) {
        this.iSocket = iSocket;
        instance.setConnectTimeout(10000);
//        instance.setReadTimeout(60000);
        instance.enableAutomaticReconnection(5000);
        instance.connect();
    }

    private void reInitWebSocketAndConnect() {
        instance = new SocketIO(URI.create(BASE_URI));
        instance.setConnectTimeout(10000);
        instance.setReadTimeout(60000);
        instance.enableAutomaticReconnection(5000);
        instance.connect();
    }

    @Override
    public void onOpen() {
        Log.e(TAG, "onOpen: ");
        if (GET_IS_LOGIN())
            send(new BaseSocketModel<>("REGIP", GET_USER_PHONE_NUMBER()).toJson());
    }

    @Override
    public void onTextReceived(String message) {
        Log.e(TAG, "onTextReceived: " + message);

        BaseSocketModel baseSocketModel = BaseSocketModel.fromJson(message);
        switch (baseSocketModel.getMethodName().toUpperCase()) {
            case CHAT_SEND:
            case CHAT_RECEIVED: {
                Log.e(TAG, "onTextReceived: S");
                iSocket.receivedMessage(baseSocketModel.getMethodBody());
                break;
            }

            case CHAT_SEND_E:
            case CHAT_RECEIVED_E: {
                Log.e(TAG, "onTextReceived: E");
                iSocket.receivedMessage(baseSocketModel.getMethodBody());
                break;
            }

            default:
                break;
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
        Log.e(TAG, "onException: " + e);

        iSocket.errorMessage(e.toString());
    }

    @Override
    public void onCloseReceived() {
        Log.e(TAG, "onCloseReceived: ");
        instance.connect();
    }


}
