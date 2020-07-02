package com.flickipedia.data;

public class Participant {
    private int id;
    private String first, middle, last;

    public Participant(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getFirst() {
        return first;
    }

    public void setFirst(String first) {
        this.first = first;
    }

    public String getMiddle() {
        return middle;
    }

    public void setMiddle(String middle) {
        this.middle = middle;
    }

    public String getLast() {
        return last;
    }

    public void setLast(String last) {
        this.last = last;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Participant))
            return false;

        return this.getId() == ((Participant) obj).getId();
    }
}
