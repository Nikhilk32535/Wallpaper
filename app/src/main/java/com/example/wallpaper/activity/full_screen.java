package com.example.wallpaper.activity;

import android.app.DownloadManager;
import android.app.WallpaperManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.wallpaper.R;
import com.example.wallpaper.adapter.swipeadapter;
import com.example.wallpaper.fragment.FavoriteFragment;
import com.example.wallpaper.model.Favoritedbhelper;
import com.example.wallpaper.model.Model;
import com.example.wallpaper.utility.utility;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.io.IOException;
import java.util.ArrayList;

public class full_screen extends AppCompatActivity {

    private ImageView option;
    private ViewPager2 imageView;
    private ImageView favorite, notfavorite;
    private ArrayList<Model> imagelist;
    private int position;
    private swipeadapter adapter;
    private Favoritedbhelper favoritedbhelper;
    String tag="findmistake";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen);

        initializeViews();
        favoritedbhelper=new Favoritedbhelper(this);
        setupViewPager();
        setupOptionsButton();

    }

    private void initializeViews() {
        Log.e(tag, "initialize");
        option = findViewById(R.id.setscreen);
        imageView = findViewById(R.id.fullpic);
        favorite = findViewById(R.id.setfavorite);
        notfavorite = findViewById(R.id.notfavorite);

        imagelist = getIntent().getParcelableArrayListExtra("datalist");
        position = getIntent().getIntExtra("position", 0);

    }

    private void setupViewPager() {
        adapter = new swipeadapter(imagelist, this);
        imageView.setAdapter(adapter);
        imageView.setCurrentItem(position);
        setfav();  // Set favorite status for the current item

        imageView.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                setfav();  // Update favorite status when a new image is selected
            }
        });
    }

    private void setupOptionsButton() {
        notfavorite.setOnClickListener(v -> {
            setstatus(true);
            setfav();
            Log.e(tag, "fav");
        });

        favorite.setOnClickListener(v -> {
            setstatus(false);
            setfav();
            Log.e(tag, "notfav");
        });

        option.setOnClickListener(v -> {
            Log.e(tag, "option");
            BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(full_screen.this);
            View view = getLayoutInflater().inflate(R.layout.bottom_layout_menu, null);
            bottomSheetDialog.setContentView(view);
            setupBottomSheetButtons(view);
            bottomSheetDialog.show();
        });


    }

    private void setstatus(boolean b) {
        String wallpaperId = String.valueOf(imagelist.get(imageView.getCurrentItem()).getUrl());
        int id=imagelist.get(imageView.getCurrentItem()).getId();
        if(b) {
            favoritedbhelper.addFavorite(wallpaperId, id);
            updatefavlist();
            utility.toast(this,"Set Favorite");
        }else{
            favoritedbhelper.removeFavorite(wallpaperId,id);
            updatefavlist();
            utility.toast(this,"Remove from Favorite");
        }
        setfav();
    }

    private void updatefavlist() {
        try {
            FavoriteFragment favoriteFragment = (FavoriteFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.favoritefragment);
            favoriteFragment.loadFavoriteWallpapers();
            utility.log("refreshed");
        } catch (Exception e) {
           utility.log("error "+e.getMessage());
        }
    }

    private void setfav() {
        String currentimg=imagelist.get(imageView.getCurrentItem()).getUrl();
        boolean fav=favoritedbhelper.isFavorite(currentimg);
        Log.e(tag, "setfav : "+fav);
        if (fav) {
            favorite.setVisibility(View.VISIBLE);
            notfavorite.setVisibility(View.GONE);
        } else {
            favorite.setVisibility(View.GONE);
            notfavorite.setVisibility(View.VISIBLE);
        }
    }



    private void setupBottomSheetButtons(View view) {
        TextView homescreen = view.findViewById(R.id.homescreen);
        TextView lockscreen = view.findViewById(R.id.lockscreen);
        TextView bothscreen = view.findViewById(R.id.bothscreen);
        TextView download = view.findViewById(R.id.download);

        homescreen.setOnClickListener(v -> setWallpaper("home"));
        lockscreen.setOnClickListener(v -> setWallpaper("lock"));
        bothscreen.setOnClickListener(v -> setWallpaper("both"));

        download.setOnClickListener(v -> downloadImage());
    }

    private void setWallpaper(String screen) {
        WallpaperManager wallpaperManager = WallpaperManager.getInstance(this);
        String imageUrl = imagelist.get(imageView.getCurrentItem()).getUrl();

        Glide.with(this)
                .asBitmap()
                .load(imageUrl)
                .into(new CustomTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        try {
                            switch (screen) {
                                case "home":
                                    wallpaperManager.setBitmap(resource, null, true, WallpaperManager.FLAG_SYSTEM);
                                    utility.toast(full_screen.this, "Set on Home Screen");
                                    break;
                                case "lock":
                                    wallpaperManager.setBitmap(resource, null, true, WallpaperManager.FLAG_LOCK);
                                    utility.toast(full_screen.this, "Set on Lock Screen");
                                    break;
                                case "both":
                                    wallpaperManager.setBitmap(resource, null, true, WallpaperManager.FLAG_SYSTEM);
                                    wallpaperManager.setBitmap(resource, null, true, WallpaperManager.FLAG_LOCK);
                                    utility.toast(full_screen.this, "Set on both screens");
                                    break;
                                default:
                                    break;
                            }
                        } catch (IOException e) {
                            utility.toast(full_screen.this, "Error setting wallpaper: " + e.getMessage());
                        }
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {
                    }
                });
    }

    private void downloadImage() {
        DownloadManager downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        String imageUrl = imagelist.get(imageView.getCurrentItem()).getUrl();
        Uri uri = Uri.parse(imageUrl);
        DownloadManager.Request request = new DownloadManager.Request(uri)
                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "/Crazy Wallpaper/IMG" + System.currentTimeMillis() + ".jpg");
        downloadManager.enqueue(request);
        utility.toast(this, "Image Downloaded");
    }

    @Override
    public void onBackPressed() {

            super.onBackPressed();

    }
}
