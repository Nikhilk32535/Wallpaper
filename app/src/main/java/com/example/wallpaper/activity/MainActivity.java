package com.example.wallpaper.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.bumptech.glide.MemoryCategory;
import com.example.wallpaper.R;
import com.example.wallpaper.adapter.Tabadapter;
import com.example.wallpaper.fragment.FavoriteFragment;
import com.example.wallpaper.fragment.category_view;
import com.example.wallpaper.fragment.firebase_home;
import com.example.wallpaper.utility.Networklivedata;
import com.example.wallpaper.utility.utility;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager2 viewPager;
    private static boolean networkconnection=true;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAuth=FirebaseAuth.getInstance();
        FirebaseUser currentUser=mAuth.getCurrentUser();

        if (currentUser!=null){
            utility.toast(this,"Welcome");
        }else{
            utility.toast(this,"Signin is Required");
            startActivity(new Intent(MainActivity.this,Fragmentview.class));
        }


        // Set up the UI regardless of network status
        setContentView(R.layout.activity_main);
        Networklivedata networklivedata=new Networklivedata(getApplication());
        networklivedata.observe(this,isConnected->{
            if(isConnected){
                if (!networkconnection){
                    utility.toast(getApplication(),"Internet Connected");
                }
        Glide.get(this).setMemoryCategory(MemoryCategory.NORMAL); // Set memory category to NORMAL
        Glide.get(this).clearMemory(); // Clear memory cache on UI thread
                networkconnection=true;
            }else{
                utility.toast(this,"No network connection");
                networkconnection=false;
            }
        });

        setContentView(R.layout.activity_main);
        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);

        // Set up the ViewPager with the sections adapter.
        Tabadapter tabAdapter = new Tabadapter(this);
        viewPager.setAdapter(tabAdapter);

        tabAdapter.addFragment(new firebase_home(),"Home");
        tabAdapter.addFragment(new category_view(),"Category");
        tabAdapter.addFragment(new FavoriteFragment(),"Favorite");

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            // Set the tab text based on position
            switch (position) {
                case 0:
                    tab.setText("Home");
                    break;
                case 1:
                    tab.setText("Category");
                    break;
                case 2:
                    tab.setText("Favorite");
                    break;
                default:
                    tab.setText("Tab " + (position + 1));
                    break;
            }
        }).attach();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // Clear Glide disk cache in a background thread when the app is closing
        new Thread(() -> {
            Glide.get(this).clearDiskCache(); // Clear disk cache in a background thread
        }).start();
       utility.log("Glide cache cleared...");

    }

    public void logout(View view) {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(this, Fragmentview.class));
    }
}

