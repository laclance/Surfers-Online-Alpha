package com.coesolutions.surfersonline.model;

import java.util.ArrayList;
import java.util.List;

public class Contact {
    private String mName;
    private boolean mOnline;

    public Contact(String name, boolean online) {
        mName = name;
        mOnline = online;
    }

    public String getName() {
        return mName;
    }

    public boolean isOnline() {
        return mOnline;
    }

    public void setName(String mName) {
        this.mName = mName;
    }

    public void setOnline(boolean mOnline) {
        this.mOnline = mOnline;
    }

    private static int lastContactId = 0;

    public static List<Contact> createContactsList(int numContacts) {
        List<Contact> contacts = new ArrayList<>();

        for (int i = 1; i <= numContacts; i++) {
            contacts.add(new Contact("Person " + ++lastContactId, i <= numContacts / 2));
        }

        return contacts;
    }
}
