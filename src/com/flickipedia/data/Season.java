package com.flickipedia.data;

import java.util.ArrayList;

public class Season {
    private TVShow show;
    private ArrayList<Episode> episodes;
    private int number;

    public Season(int number, TVShow show) {
        this.number = number;
        this.show = show;
        this.episodes = new ArrayList<Episode>();
    }

    public TVShow getShow() {
        return show;
    }

    public ArrayList<Episode> getEpisodes() {
        return episodes;
    }

    public void addEpisode(Episode ep) {
        this.episodes.add(ep);
    }

    public int getNumber() {
        return number;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Season))
            return false;

        return this.getNumber() == ((Season) obj).getNumber() && this.getShow().equals(this.getShow());
    }
}
