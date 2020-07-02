package com.flickipedia.data;

import java.util.ArrayList;

public abstract class Title {
    private int id;
    private String name;
    private ArrayList<ShotLocation> shotAt;
    private ArrayList<Genre> genre;
    private ArrayList<StreamService> streams;
    private ArrayList<Trailer> trailers;
    private ArrayList<Participant> actors, writers, directors;

    public Title(int id, String name) {
        this.id = id;
        this.name = name;
        this.shotAt = new ArrayList<ShotLocation>();
        this.genre = new ArrayList<Genre>();
        this.streams = new ArrayList<StreamService>();
        this.trailers = new ArrayList<Trailer>();
        this.actors = new ArrayList<Participant>();
        this.writers = new ArrayList<Participant>();
        this.directors = new ArrayList<Participant>();
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
        this.shotAt.add(new ShotLocation(this, city, state, country));
    }

    public ArrayList<Genre> getGenre() {
        return genre;
    }

    public void addGenre(Genre genre) {
        this.genre.add(genre);
    }

    public void genGenre(String name, String desc) {
        this.genre.add(new Genre(name, desc));
    }

    public ArrayList<StreamService> getStreams() {
        return streams;
    }

    public void addStream(StreamService stream) {
        this.streams.add(stream);
    }

    public void genStream(String name, String url) {
        this.streams.add(new StreamService(name, url));
    }

    public ArrayList<Trailer> getTrailers() {
        return trailers;
    }

    public void addTrailer(Trailer trailer) {
        this.trailers.add(trailer);
    }

    public ArrayList<Participant> getActors() {
        return actors;
    }

    public void addActor(Participant actor) {
        this.actors.add(actor);
    }

    public ArrayList<Participant> getWriters() {
        return writers;
    }

    public void addWriter(Participant writer) {
        this.writers.add(writer);
    }

    public ArrayList<Participant> getDirectors() {
        return directors;
    }

    public void addDirector(Participant director) {
        this.directors.add(director);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Title))
            return false;

        return this.getId() == ((Title) obj).getId();
    }
}
