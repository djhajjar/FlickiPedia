package com.flickipedia.data;

public abstract class Title {
    private int id;
    private String name;

    public Title(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }
}
