package com.flickipedia.data;

import java.util.ArrayList;

import com.flickipedia.util.Util;

public class Movie extends Title {
    private int releaseDate, releaseMonth, releaseYear;
    private String country, agerating;
    private ArrayList<Theater> playingAt;

    public Movie(int id, String name) {
        super(id, name);
        this.playingAt = new ArrayList<Theater>();
    }

    public int getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(int releaseDate) {
        this.releaseDate = releaseDate;
    }

    public int getReleaseMonth() {
        return releaseMonth;
    }

    public void setReleaseMonth(int releaseMonth) {
        this.releaseMonth = releaseMonth;
    }

    public String getReleaseMonthString() {
        return Util.monthToString(this.releaseMonth);
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getAgerating() {
        return agerating;
    }

    public void setAgerating(String agerating) {
        this.agerating = agerating;
    }

    public ArrayList<Theater> getPlayingAt() {
        return playingAt;
    }

    public void addTheater(Theater theater) {
        this.playingAt.add(theater);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Movie))
            return false;

        return this.getId() == ((Movie) obj).getId();
    }
}
