package com.bawp.contactmanager.model;

public class Contact {

    private int id;
    private String name;
    private String phoneNumber;

    //constructor gol, pt ca vrei sa ai optiunea de a nu pasa
    //toti params cand creezi un obj nou:
    public Contact() {}

    //constru cu de toate:
    public Contact(int id, String name, String phoneNumber) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
