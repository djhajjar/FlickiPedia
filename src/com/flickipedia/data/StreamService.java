package com.flickipedia.data;

public class StreamService {
    private String name, url;

    public StreamService(String name, String url) {
        this.name = name;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }
}
