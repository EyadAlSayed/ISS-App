package com.example.infosecuritysysapp.ui.fragments.home.chats.presentation;

public interface IChatMessages {
    void getChatMessages(String phoneNumber);
    void receivedMessage(String message);
}
