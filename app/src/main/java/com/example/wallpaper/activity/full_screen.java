package com.example.wallpaper.activity;

import android.app.DownloadManager;
import android.app.WallpaperManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.wallpaper.R;
import com.example.wallpaper.utility.utility;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.io.IOException;

public class full_screen extends AppCompatActivity {

    ImageView imageView,set;
    TextView heading;
    String url,name="not_avalabele";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen);

        set=findViewById(R.id.setscreen);
        imageView=findViewById(R.id.fullpic);
        heading=findViewById(R.id.heading);
        url=getIntent().getStringExtra("url");
        name=getIntent().getStringExtra("name");
        heading.setText(name);
        Glide.with(this).load(url).into(imageView);

        set.setOnClickListener(v->{

            BottomSheetDialog bottomSheetDialog=new BottomSheetDialog(full_screen.this);
            View view=getLayoutInflater().inflate(R.layout.bottom_layout_menu,null);
            bottomSheetDialog.setContentView(view);

            TextView homescreen=view.findViewById(R.id.homescreen);
            TextView lockscreen=view.findViewById(R.id.lockscreen);
            TextView bothscreen=view.findViewById(R.id.bothscreen);
            TextView download=view.findViewById(R.id.download);

            buttonsort(homescreen,"home");
            buttonsort(lockscreen,"lock");
            buttonsort(bothscreen,"both");

            download.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    downloadimg();
                    bottomSheetDialog.dismiss();
                }
            });

            bottomSheetDialog.show();
            });
    }

    public void buttonsort(TextView button,String arrgument) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setwallpaper(arrgument);
            }
        });
    }
    public void setwallpaper(String screen) {
        WallpaperManager wallpaperManager=WallpaperManager.getInstance(this);
        Bitmap bitmap=((BitmapDrawable)imageView.getDrawable()).getBitmap();

        try {
            switch (screen){
                case "home":
                    wallpaperManager.setBitmap(bitmap, null, true, WallpaperManager.FLAG_SYSTEM);
                    utility.toast(this,"Set on Home Screen");
                    return;
                case "lock":
                    wallpaperManager.setBitmap(bitmap, null, true, WallpaperManager.FLAG_LOCK);
                    utility.toast(this,"Set on Lock Screen");
                    return;
                case "both":
                    wallpaperManager.setBitmap(bitmap, null, true, WallpaperManager.FLAG_SYSTEM);
                    wallpaperManager.setBitmap(bitmap, null, true, WallpaperManager.FLAG_LOCK);
                    utility.toast(this, "Set on both");
                    return;
                case "download":
                    downloadimg();
                    return;
                default:
                    return;
            }

            } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void downloadimg() {
        DownloadManager downloadManager=(DownloadManager)getSystemService(Context.DOWNLOAD_SERVICE);
        Uri uri=Uri.parse(url);
        DownloadManager.Request request=new DownloadManager.Request(uri);
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,"/Crazy Wallpaper/IMG"+System.currentTimeMillis()+".jpg");
            downloadManager.enqueue(request);
            utility.toast(this,"Image Downloaded");
    }
    }
