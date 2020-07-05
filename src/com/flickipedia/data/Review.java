package com.flickipedia.data;

public class Review {
    private int titleId, starRating;
    private String description;
    private String writtenByEmail;

    public Review(int titleId, String writtenByEmail) {
        this.titleId = titleId;
        this.writtenByEmail = writtenByEmail;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getTitleId() {
        return titleId;
    }

    public int getStarRating() {
        return starRating;
    }

    public void setStarRating(int starRating) {
        this.starRating = starRating;
    }

    public String getWrittenBy() {
        return writtenByEmail;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Review))
            return false;

        return this.getTitleId() == ((Review) obj).getTitleId()
                && this.getWrittenBy().equals(((Review) obj).getWrittenBy());
    }
}
