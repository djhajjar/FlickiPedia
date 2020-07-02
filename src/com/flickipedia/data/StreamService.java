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

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof StreamService))
            return false;

        return ((StreamService) obj).getName().equals(this.getName());
    }
}
