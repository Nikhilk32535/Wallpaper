package com.example.wallpaper.model;

public class Model {

    String name,url,orignal,midium;
    int id;
    Model() {

    }

    public Model( int id,String orignal, String midium) {
        this.orignal = orignal;
        this.midium = midium;
        this.id = id;
    }

    public Model(String name, String url) {
        this.name = name;
        this.url = url;
    }

    public String getName() { return name;    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getOrignal() {
        return orignal;
    }

    public void setOrignal(String orignal) {
        this.orignal = orignal;
    }

    public String getMidium() {
        return midium;
    }

    public void setMidium(String midium) {
        this.midium = midium;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
