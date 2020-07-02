package com.flickipedia.data;

public class Genre {
    private String name, desc;

    public Genre(String name, String desc) {
        this.name = name;
        this.setDesc(desc);
    }

    public Genre(String name) {
        this(name, "");
    }

    public String getName() {
        return name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
