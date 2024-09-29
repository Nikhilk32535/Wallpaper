package com.example.wallpaper.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager2 viewPager;
    private static boolean networkconnection = true;
    private FirebaseAuth mAuth;
    private String user_name,emailid;
    ImageView logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        logout=findViewById(R.id.logout);

        if (currentUser != null) {
            retrieveUserInfo(); // Only call it once
        } else {
            utility.toast(this, "Signin is Required");
            startActivity(new Intent(MainActivity.this, Fragmentview.class));
        }

        // Set up the UI regardless of network status
        Networklivedata networklivedata = new Networklivedata(getApplication());
        networklivedata.observe(this, isConnected -> {
            if (isConnected) {
                if (!networkconnection) {
                    utility.toast(getApplication(), "Internet Connected");
                }
                Glide.get(this).setMemoryCategory(MemoryCategory.NORMAL); // Set memory category to NORMAL
                Glide.get(this).clearMemory(); // Clear memory cache on UI thread
                networkconnection = true;
            } else {
                utility.toast(this, "No network connection");
                networkconnection = false;
            }
        });

        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);

        // Set up the ViewPager with the sections adapter.
        Tabadapter tabAdapter = new Tabadapter(this);
        viewPager.setAdapter(tabAdapter);

        tabAdapter.addFragment(new firebase_home(), "Home");
        tabAdapter.addFragment(new category_view(), "Category");
        tabAdapter.addFragment(new FavoriteFragment(), "Favorite");

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}

            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });

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
                default:
                    tab.setText("Tab " + (position + 1));
                    break;
            }
        }).attach();

        logout.setOnLongClickListener(v->{
            AlertDialog.Builder builder=new AlertDialog.Builder(this);
            builder.setTitle("Delete Account");
            builder.setMessage("Are you sure you want to delete your account?");
            builder.setPositiveButton("Yes",(dialog,which)-> deleteuser());
            builder.setNegativeButton("No",(dialog,which)-> dialog.dismiss());
            builder.show();
            return true;
        });

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

        AlertDialog.Builder builder= new AlertDialog.Builder(this);
        builder.setTitle("Logout");
        builder.setMessage("Are you sure you want to logout?");
        builder.setPositiveButton("Yes",(dialog,which)-> logoutactivity());
        builder.setNegativeButton("No",(dialog,which)-> dialog.dismiss());
        builder.show();
    }

    private void retrieveUserInfo() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            String uid = user.getUid(); // Get the user's UID
            db.collection("USERS").document(uid)
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                String name = document.getString("name");
                                String email = document.getString("email");
                                user_name=name;
                                emailid=email;
                                utility.toast(this, "Welcome " + name);
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

    public void UserInfo(View view) {
       utility.toast(this,"Name is : "+user_name+"\nEmail is : "+emailid);
        }
        public void logoutactivity(){
            FirebaseAuth.getInstance().signOut();
            utility.toast(this, "Logout Successful"); // Show toast before starting new activity
            startActivity(new Intent(this, Fragmentview.class));
        }

        public void deleteuser(){
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

            user.delete()
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                               utility.log("User account deleted.");
                            }
                        }
                    });
        }
    }

