package com.flickipedia.data;

import java.util.ArrayList;

import com.flickipedia.util.Util;

public class Movie extends Title {
    private int releaseDate, releaseMonth, releaseYear;
    private String country, ageRating;
    private ArrayList<Theater> playingAt;
    private double duration;

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

    public String getAgeRating() {
        return ageRating;
    }

    public void setAgeRating(String ageRating) {
        this.ageRating = ageRating;
    }

    public ArrayList<Theater> getPlayingAt() {
        return playingAt;
    }

    public void addTheater(Theater theater) {
        this.playingAt.add(theater);
    }

    public double getDuration() {
        return duration;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Movie))
            return false;

        return this.getId() == ((Movie) obj).getId();
    }
}
