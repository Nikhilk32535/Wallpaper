package com.example.wallpaper.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.wallpaper.fragment.FavoriteFragment;
import com.example.wallpaper.fragment.category_view;
import com.example.wallpaper.fragment.firebase_home;

import java.util.ArrayList;
import java.util.List;

public class Tabadapter extends FragmentStateAdapter {

    private final List<Fragment> fragmentList=new ArrayList<>();
    private final List<String> fragmentTitleList=new ArrayList<>();

    public Tabadapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    public void addFragment(Fragment fragment,String title){
        fragmentList.add(fragment);
        fragmentTitleList.add(title);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 0) {
            return new firebase_home();
        } else if (position == 1) {
            return new category_view();
        }else if (position == 2) {
            return new FavoriteFragment();
        }else {
            return new firebase_home();
        }
    }

    @Override
    public int getItemCount() {
        return fragmentList.size();
    }

    // Method to get the fragment at a specific position
    public Fragment getFragmentAtPosition(int position) {
        return fragmentList.get(position);
    }

}
