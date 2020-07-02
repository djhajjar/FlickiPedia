package com.flickipedia.data;

public class ShotLocation {
    private String city, state, country;
    private Title title;

    public ShotLocation(Title title, String city, String state, String country) {
        this.title = title;
        this.setCity(city);
        this.setState(state);
        this.setCountry(country);
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Title getTitle() {
        return title;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof ShotLocation))
            return false;

        ShotLocation loc = (ShotLocation) obj;
        return this.getState().equals(loc.getState()) && this.getCountry().equals(loc.getCountry())
                && this.getCity().equals(loc.getCity()) && this.getTitle().equals(loc.getTitle());
    }
}
