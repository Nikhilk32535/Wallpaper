package com.example.wallpaper.utility;

import android.app.Activity;
import android.widget.Toast;

public class utility {

    public static void toast(Activity activity,String massage){
        Toast.makeText(activity, massage, Toast.LENGTH_SHORT).show();
    }
}
