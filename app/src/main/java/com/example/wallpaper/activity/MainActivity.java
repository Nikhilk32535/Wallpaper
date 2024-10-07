package com.example.wallpaper.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
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
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    RelativeLayout relativeLayout;

    TabLayout tabLayout;
    ViewPager2 viewPager;
    private static boolean networkconnection = true;
    private FirebaseAuth mAuth;
    ImageView logout;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ActionBarDrawerToggle toggle;
    View headerview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        drawerLayout = findViewById(R.id.nav_drawer);
        navigationView = findViewById(R.id.nav_view);
        headerview=navigationView.getHeaderView(0);
        relativeLayout=findViewById(R.id.relativemain);

        // Set up NavigationView
        navigationView.setNavigationItemSelectedListener(this);

        // Set up ActionBarDrawerToggle
        toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open_nav, R.string.close_nav);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        if (currentUser != null) {
            retrieveUserInfo();
        } else {
            utility.toast(this, "Signin is Required");
            startActivity(new Intent(MainActivity.this, Fragmentview.class));
        }

        // Network live data observation
        Networklivedata networklivedata = new Networklivedata(getApplication());
        networklivedata.observe(this, isConnected -> {
            if (isConnected) {
                if (!networkconnection) {
                    utility.toast(getApplication(), "Internet Connected");
                }
                Glide.get(this).setMemoryCategory(MemoryCategory.NORMAL);
                Glide.get(this).clearMemory();
                networkconnection = true;
            } else {
                utility.toast(this, "No network connection");
                networkconnection = false;
            }
        });

        // Setup for TabLayout and ViewPager2
        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);

        Tabadapter tabAdapter = new Tabadapter(this);
        viewPager.setAdapter(tabAdapter);

        tabAdapter.addFragment(new firebase_home(), "Home");
        tabAdapter.addFragment(new category_view(), "Category");
        tabAdapter.addFragment(new FavoriteFragment(), "Favorite");

        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
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
            }
        }).attach();

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            viewPager.setCurrentItem(0); // Move to Home tab
        } else if (id == R.id.about_us) {
           developerinfo();
        } else if (id == R.id.logout) {
            logout(); // Trigger logout
        }else if (id == R.id.Deleteacc) {
            Delete_account(); // Delete User
        }

        drawerLayout.closeDrawer(GravityCompat.START); // Close drawer after item is selected
        return true;
    }

    private void developerinfo() {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("Developer information");
        builder.setMessage("Name: Nikhil Kumar \nEamil: nikhilk32535@gmail.com \nGithub: https://github.com/nikhilk32535");
        builder.setPositiveButton("OK",(dialog,which)->dialog.dismiss());
        builder.show();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        new Thread(() -> Glide.get(this).clearDiskCache()).start();
        utility.log("Glide cache cleared...");
    }

    private void retrieveUserInfo() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            String uid = user.getUid();
            db.collection("USERS").document(uid)
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                ImageView headerimg=headerview.findViewById(R.id.nav_header_image);
                                Glide.with(this).load(document.getString("image"))
                                        .placeholder(R.drawable.logo).into(headerimg);
                                TextView headername=headerview.findViewById(R.id.nav_header_name);
                                headername.setText( document.getString("name"));
                                TextView headeremail=headerview.findViewById(R.id.nav_header_email);
                                headeremail.setText(document.getString("email"));
                                utility.toast(this, "Welcome " + document.getString("name"));
                            } else {
                                utility.toast(this, "No such user found");
                            }
                        } else {
                            utility.log("Error getting user info: " + task.getException());
                            utility.toast(this, "Failed to retrieve user info");
                        }
                    });
        } else {
            utility.toast(this, "User is not logged in");
        }
    }

    public void Navigation_open(View view) {
        drawerLayout.openDrawer(GravityCompat.START);
    }

    // Logout user
    public void logout() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Logout")
                .setMessage("Are you sure you want to logout?")
                .setPositiveButton("Yes", (dialog, which) -> logoutactivity())
                .setNegativeButton("No", (dialog, which) -> dialog.dismiss())
                .show();
    }

    public void logoutactivity() {
        FirebaseAuth.getInstance().signOut();
        utility.toast(this, "Logout Successful");
        startActivity(new Intent(this, Fragmentview.class));
    }

    public void Delete_account() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete Account")
                .setMessage("Are you sure you want to delete your account?")
                .setPositiveButton("Yes", (dialog, which) -> deleteuser())
                .setNegativeButton("No", (dialog, which) -> dialog.dismiss())
                .show();
    }
    public void deleteuser() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        user.delete().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                utility.log("User account deleted.");
                startActivity(new Intent(MainActivity.this, Fragmentview.class));
                finish();
            } else {
                utility.toast(this, "Account deletion failed");
            }
        });
    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        };
    }
}
