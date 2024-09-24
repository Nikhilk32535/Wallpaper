package com.example.wallpaper.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.wallpaper.R;
import com.example.wallpaper.fragment.Userauthentication;
import com.example.wallpaper.fragment.category_res;
import com.example.wallpaper.utility.utility;

public class Fragmentview extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fregmentview);

        String category = getIntent().getStringExtra("category");
        if (category == null || category.isEmpty()) {
            if (savedInstanceState == null) {
                userauthentication();  // Renamed for consistency
            }
        } else {
            categoryresult(category);  // Renamed for consistency
        }


    }
    public void categoryresult(String category){
        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction transaction=fragmentManager.beginTransaction();
        transaction.add(R.id.fragmentview,new category_res(category))
                .addToBackStack(null).commit();
        utility.log("Category:- "+category);
    }

    public void userauthentication(){
        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction transaction=fragmentManager.beginTransaction();
        transaction.replace(R.id.fragmentview,new Userauthentication())
                .addToBackStack(null).commit();
        utility.log("Auth start");
    }
    @Override
    public void onBackPressed() {

            super.onBackPressed();
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);

            }
}