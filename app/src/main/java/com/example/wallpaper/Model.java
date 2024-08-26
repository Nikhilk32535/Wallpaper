package com.example.wallpaper;

public class Model {

    String url;
    Model() {

    }

    public Model(String url, int idnum) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
