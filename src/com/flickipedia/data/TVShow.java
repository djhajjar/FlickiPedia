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

    public String getAgeRating() {
        return seasons.get(0).getEpisodes().get(0).getAgeRating();
    }

    public int getReleaseYear() {
        Season earliestSeason = null;
        Episode earliestEpisode = null;
        int minSeasonNum;
        int minEpisodeNum;
        if (getSeasons().size() > 0) {
            minSeasonNum = getSeasons().get(0).getNumber();
            earliestSeason = getSeasons().get(0);
            for (int i = 1; i < getSeasons().size(); i++) {
                if (getSeasons().get(i).getNumber() < minSeasonNum) {
                    minSeasonNum = getSeasons().get(i).getNumber();
                    earliestSeason = getSeasons().get(i);
                }
            }
        }

        if (earliestSeason != null && earliestSeason.getEpisodes().size() > 0) {
            minEpisodeNum = earliestSeason.getEpisodes().get(0).getNumber();
            earliestEpisode = earliestSeason.getEpisodes().get(0);
            for (int i = 1; i < earliestSeason.getEpisodes().size(); i++) {
                if (earliestSeason.getEpisodes().get(i).getYear() < earliestEpisode.getYear()) {
                    minEpisodeNum = earliestSeason.getEpisodes().get(i).getNumber();
                    earliestEpisode = earliestSeason.getEpisodes().get(i);
                }
            }
        }
        int releaseYear = earliestEpisode.getYear();
        return releaseYear;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof TVShow))
            return false;

        return this.getId() == ((TVShow) obj).getId();
    }

    @Override
    public String toString() {

        String output = "";

        output = String.format("%-30s\t %d\t %-25s %-12s", getName(), getReleaseYear(), getCountry(), getAgeRating());
        if (getActors().size() > 0) {
            output = output + "\n\tActors:";
            for (int i = 0; i < getActors().size(); i++) {
                output = output + "\n\t\t" + getActors().get(i).getFirst() + " " + getActors().get(i).getLast();
            }
        }
        if (getDirectors().size() > 0) {
            output = output + "\n\tDirectors:";
            for (int i = 0; i < getDirectors().size(); i++) {
                output = output + "\n\t\t" + getDirectors().get(i).getFirst() + " " + getDirectors().get(i).getLast();
            }
        }
        if (getWriters().size() > 0) {
            output = output + "\n\tWriters:";
            for (int i = 0; i < getWriters().size(); i++) {
                output = output + "\n\t\t" + getWriters().get(i).getFirst() + " " + getWriters().get(i).getLast();
            }
        }
        if (getStreams().size() > 0) {
            output = output + "\n\tStreaming From:";
            for (int i = 0; i < getStreams().size(); i++) {
                output = output + "\n\t\t" + getStreams().get(i).getName() + " - URL: " + getStreams().get(i).getUrl();
            }
        }
        output = output + "\n";
        System.out.println(output);
        return output;
    }
}
