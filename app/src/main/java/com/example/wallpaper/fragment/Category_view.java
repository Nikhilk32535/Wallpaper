package com.example.wallpaper.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.fragment.app.Fragment;

import com.example.wallpaper.R;

public class Category_view extends Fragment {
    RelativeLayout nature,animal,weather,cars;

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
            if(getActivity()!=null){
                getActivity().getIntent().putExtra("category",massage);
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragmentview,new category_res())
                        .addToBackStack(null).commit();
            }
        });
    }
}