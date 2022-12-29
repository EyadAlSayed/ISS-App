package com.example.infosecuritysysapp.model;

import java.io.Serializable;

public class PersonContact implements Serializable {

    public int id;
    public String name;
    public String phoneNumber;

    public PersonContact(int id, String name, String phoneNumber) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
    }
}
