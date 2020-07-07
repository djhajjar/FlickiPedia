package com.flickipedia.data;

public class Trailer {
    private int id;
    private String name, desc;
    private Title title;
    private double duration;

    public Trailer(int id, Title title) {
        this.id = id;
        this.setName("");
        this.setDesc("");
        this.setDuration(0);
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public double getDuration() {
        return duration;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Title getTitle() {
        return title;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Trailer)) {
            return false;
        }

        Trailer trailer = (Trailer) obj;

        return this.getId() == trailer.getId() && this.getTitle().equals(trailer.getTitle());
    }
}
