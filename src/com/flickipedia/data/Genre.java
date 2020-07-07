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

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Genre))
            return false;

        return this.getName().equals(((Genre) obj).getName());
    }
}
