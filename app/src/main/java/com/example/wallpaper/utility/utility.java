package com.example.wallpaper.utility;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;


public class utility {

    public static void toast(Context activity, String massage){
        Toast.makeText(activity, massage, Toast.LENGTH_SHORT).show();
    }
    public static final String tag="findmistake";

    public static void log(String massage){
        Log.e(utility.tag,massage);
    }


}
