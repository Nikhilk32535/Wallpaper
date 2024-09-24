package com.example.wallpaper.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import androidx.annotation.NonNull;


public class Model implements Parcelable {

    private int id;
    private String url;
    private boolean isFavorite;

    // Public no-arg constructor for Realm
    public Model() {
    }

    // Constructor with only 'url'
    public Model(int id, String url) {
        this.id = id;
        this.url = url;
    }

    // Constructor with all fields
    public Model(int id, String url, boolean isFavorite) {
        this.url = url;
        this.id = id;
        this.isFavorite = isFavorite;
    }

    // Parcelable constructor
    protected Model(Parcel in) {
        url = in.readString();
        id = in.readInt();
        isFavorite = in.readByte() != 0;
    }

    // Parcelable Creator
    public static final Creator<Model> CREATOR = new Creator<Model>() {
        @Override
        public Model createFromParcel(Parcel in) {
            return new Model(in);
        }

        @Override
        public Model[] newArray(int size) {
            return new Model[size];
        }
    };

    // Getter and Setter for 'isFavorite'
    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        Log.e("findmistake", "favorite: " + favorite);
        this.isFavorite = favorite;
    }

    // Getter and Setter for 'url'
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    // Getter and Setter for 'id'
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    // Parcelable methods
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(url);
        parcel.writeInt(id);
        parcel.writeByte((byte) (isFavorite ? 1 : 0));
    }
}
