package com.flickipedia.data;

import java.util.ArrayList;

public class TVShow extends Title {
    private String country;
    private ArrayList<Season> seasons;

    public TVShow(int id, String name) {
        super(id, name);
        seasons = new ArrayList<Season>();
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public ArrayList<Season> getSeasons() {
        return seasons;
    }

    public void addSeason(Season season) {
        this.seasons.add(season);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof TVShow))
            return false;

        return this.getId() == ((TVShow) obj).getId();
    }

    @Override
    public String toString() {
        return this.getName();
    }
}
