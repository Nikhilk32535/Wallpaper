package com.example.wallpaper.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.wallpaper.R;
import com.example.wallpaper.activity.Fragmentview;
import com.google.android.material.tabs.TabLayout;

public class category_view extends Fragment{

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    RelativeLayout nature,animal,weather,cars;
    TabLayout tabLayout;
    ViewPager2 viewPager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_category_view, container, false);

        nature=view.findViewById(R.id.nature);
        animal=view.findViewById(R.id.animal);
        weather=view.findViewById(R.id.weather);
        cars=view.findViewById(R.id.cars);

        setcategory(nature,"NATURE");
        setcategory(animal,"ANIMALS");
        setcategory(weather,"WEATHER");
        setcategory(cars,"CARS");

        return view;
    }

    public void setcategory(RelativeLayout layout,String massage){
        layout.setOnClickListener(v->{
            Log.d("findmistake",massage);
            String category=massage.toLowerCase();
            Log.d("findmistake",category);

                Intent intent=new Intent(getActivity(), Fragmentview.class);
                intent.putExtra("category",category);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);

        });
    }
}