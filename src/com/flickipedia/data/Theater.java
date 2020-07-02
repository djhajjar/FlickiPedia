package com.flickipedia.data;

public class Theater {
    private String name, zip, street, state, country, city;

    public Theater(String name, String zip) {
        this.setName(name);
        this.setZip(zip);
        this.setStreet("");
        this.setState("");
        this.setCountry("");
        this.setCity("");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
    
    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof Theater))
            return false;
        
        Theater theater = (Theater) obj;
        
        return theater.getName().equals(this.getName()) && theater.getZip().equals(this.getZip());
    }
}
