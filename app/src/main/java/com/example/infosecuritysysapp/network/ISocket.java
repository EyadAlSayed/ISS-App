package com.example.infosecuritysysapp.network;

public interface ISocket {
    void receivedMessage(String message);
    void successfulSend(String message);
    void errorMessage(String errorMessage);
}
