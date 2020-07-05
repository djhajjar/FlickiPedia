package com.flickipedia.data;

public class Episode {
    private TVShow show;
    private Season season;
    private int number, day, month, year;
    private String name, ageRating;
    private double duration;

    public Episode(int number, Season season, TVShow show) {
        this.setNumber(number);
        this.season = season;
        this.show = show;
    }

    public TVShow getShow() {
        return show;
    }

    public Season getSeason() {
        return season;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAgeRating() {
        return ageRating;
    }

    public void setAgeRating(String ageRating) {
        this.ageRating = ageRating;
    }

    public double getDuration() {
        return duration;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Episode))
            return false;

        Episode ep = (Episode) obj;
        return this.getNumber() == ep.getNumber() && this.getShow().equals(ep.getShow())
                && this.getSeason().equals(ep.getSeason());
    }
}
