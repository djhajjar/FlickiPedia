package com.flickipedia.data;

public class TVShow extends Title {

    public TVShow(int id, String name) {
        super(id, name);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof TVShow))
            return false;

        return this.getId() == ((TVShow) obj).getId();
    }
}
