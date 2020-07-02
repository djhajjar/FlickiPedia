package com.flickipedia.data;

import java.util.ArrayList;

public abstract class Title {
    private int id;
    private String name;
    private ArrayList<ShotLocation> shotAt;
    private ArrayList<Genre> genre;
    private ArrayList<StreamService> streams;
    private ArrayList<Trailer> trailers;

    public Title(int id, String name) {
        this.id = id;
        this.name = name;
        this.shotAt = new ArrayList<ShotLocation>();
        this.genre = new ArrayList<Genre>();
        this.streams = new ArrayList<StreamService>();
        this.trailers = new ArrayList<Trailer>();
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public ArrayList<ShotLocation> getShotAt() {
        return this.shotAt;
    }

    public void addShotAt(ShotLocation loc) {
        this.shotAt.add(loc);
    }

    public void genShotAt(String city, String state, String country) {
        ShotLocation loc = new ShotLocation(this, city, state, country);
        this.shotAt.add(loc);
    }

    public ArrayList<Genre> getGenre() {
        return genre;
    }

    public void addGenre(Genre genre) {
        this.genre.add(genre);
    }

    public void genGenre(String name, String desc) {
        Genre gen = new Genre(name, desc);
        this.genre.add(gen);
    }

    public ArrayList<StreamService> getStreams() {
        return streams;
    }

    public void addStream(StreamService stream) {
        this.streams.add(stream);
    }

    public void genStream(String name, String url) {
        StreamService stream = new StreamService(name, url);
        this.streams.add(stream);
    }

    public ArrayList<Trailer> getTrailers() {
        return trailers;
    }

    public void addTrailer(Trailer trailer) {
        this.trailers.add(trailer);
    }
}
