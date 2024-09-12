package com.example.wallpaper;

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
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import java.io.IOException;

public class full_screen extends AppCompatActivity {

    ImageView imageView;
    TextView heading;
    String url,name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen);

        imageView=findViewById(R.id.fullpic);
        heading=findViewById(R.id.heading);
        url=getIntent().getStringExtra("url");
        name=getIntent().getStringExtra("name");
        heading.setText(name);
        Glide.with(this).load(url).into(imageView);

    }

    public void setwallpaper(View view) {
        WallpaperManager wallpaperManager=WallpaperManager.getInstance(this);
        Bitmap bitmap=((BitmapDrawable)imageView.getDrawable()).getBitmap();

        try {
            wallpaperManager.setBitmap(bitmap);
            Toast.makeText(this,"Wallpaper Set",Toast.LENGTH_SHORT).show();
            moveTaskToBack(true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void downloadimg(View view) {
        DownloadManager downloadManager=(DownloadManager)getSystemService(Context.DOWNLOAD_SERVICE);
        Uri uri=Uri.parse(url);
        DownloadManager.Request request=new DownloadManager.Request(uri);
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,"/Crazy Wallpaper/IMG"+System.currentTimeMillis()+".jpg");
            downloadManager.enqueue(request);
            Toast.makeText(this,"Image Downloaded",Toast.LENGTH_SHORT).show();
    }
}