package com.flickipedia.data;

import java.util.ArrayList;

public class User {
    private int id;
    private String email;
    private ArrayList<Title> watched, toWatch;

    public User(int id) {
        this.id = id;
        this.toWatch = new ArrayList<Title>();
        this.watched = new ArrayList<Title>();
    }

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ArrayList<Title> getWatched() {
        return watched;
    }

    public ArrayList<Title> getToWatch() {
        return toWatch;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof User))
            return false;

        return this.getId() == ((User) obj).getId();
    }
}
