package com.example.wallpaper.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class Favoritedbhelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "favorites.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "favorites";
    private static final String COLUMN_URL = "url";
    private static final String COLUMN_ID = "id";

    // SQL statement to create the favorites table
    private static final String CREATE_TABLE_FAVORITES =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    COLUMN_URL + " TEXT PRIMARY KEY ,"+COLUMN_ID +" number)";

    public Favoritedbhelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create the favorites table
        db.execSQL(CREATE_TABLE_FAVORITES);
        Log.e("findmistake", "Favorites table created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop the old table if it exists, and create a new one
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    // Add a new favorite
    public void addFavorite(String url,int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_URL, url);
        values.put(COLUMN_ID,id);
        db.insert(TABLE_NAME, null, values);
        Log.e("findmistake", "Add:-"+url+" id:-"+id);
        db.close();
    }

    // Remove a favorite
    public void removeFavorite(String url,int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, COLUMN_URL + "=?", new String[]{url});
        Log.e("findmistake", " remove:-"+url);
        db.close();
    }

    // Check if a URL is a favorite
    public boolean isFavorite(String url) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, new String[]{COLUMN_URL},
                COLUMN_URL + "=?", new String[]{url}, null, null, null);
        boolean exists = cursor.moveToFirst();
        cursor.close();
        db.close();
        Log.e("findmistake","exists:-"+exists);
        return exists;
    }

    // Method to get all favorite URLs
    public ArrayList<Model> getAllFavorites() {
        ArrayList<Model> favoriteList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME, new String[]{COLUMN_ID, COLUMN_URL}, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                int idColumnIndex = cursor.getColumnIndexOrThrow(COLUMN_ID);
                int urlColumnIndex = cursor.getColumnIndexOrThrow(COLUMN_URL);
                int id = cursor.getInt(idColumnIndex);
                String url = cursor.getString(urlColumnIndex);

                favoriteList.add(new Model(id, url)); // Assuming Model has a constructor that takes id and url
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return favoriteList;
    }

}

