package com.example.wallpaper.activity;

import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.wallpaper.R;
import com.example.wallpaper.fragment.Category_view;
import com.example.wallpaper.fragment.firebase_home;

public class MainActivity extends AppCompatActivity {
    TextView homebtn, cetegorybtn;
    LinearLayout homebar, categorybar;
    FrameLayout fragmentview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        homebtn = findViewById(R.id.homebtn);
        cetegorybtn = findViewById(R.id.cetegorybtn);
        homebar = findViewById(R.id.homebar);
        categorybar = findViewById(R.id.cetegorybar);

        Homescreenview();
        homebtn.setOnClickListener(v -> {
            homebar.setBackgroundColor(getResources().getColor(R.color.blue));
            categorybar.setBackgroundColor(getResources().getColor(R.color.white));
            Homescreenview();
        });

        cetegorybtn.setOnClickListener(v -> {
            homebar.setBackgroundColor(getResources().getColor(R.color.white));
            categorybar.setBackgroundColor(getResources().getColor(R.color.blue));
            CatogeryScreenView();
        });


    }

    private void Homescreenview() {
        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentview,new firebase_home());
        fragmentTransaction.commit();
    }

    private void CatogeryScreenView() {
        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentview,new Category_view());
        fragmentTransaction.commit();
    }

}