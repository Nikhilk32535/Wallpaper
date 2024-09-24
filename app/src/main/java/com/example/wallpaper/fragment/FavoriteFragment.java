package com.example.wallpaper.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wallpaper.R;
import com.example.wallpaper.adapter.MyAdapter;
import com.example.wallpaper.model.Favoritedbhelper;
import com.example.wallpaper.model.Model;

import java.util.ArrayList;

public class FavoriteFragment extends Fragment{

    private static final String TAG = "findmistake"; // Log tag for logging
    private Favoritedbhelper favoritesDbHelper;
    private MyAdapter adapter;
    private TextView imgmessage;
    private ArrayList<Model> favoriteWallpapersList;
    private RecyclerView recyclerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e(TAG, "Fragment onCreate called");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.e(TAG, "Fragment onCreateView called");

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);

        favoritesDbHelper = new Favoritedbhelper(getActivity()); // Initialize here

        recyclerView = view.findViewById(R.id.favoriteitem);
        imgmessage = view.findViewById(R.id.imgmessage);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));

        // Initialize the list of favorite wallpapers
        favoriteWallpapersList = new ArrayList<>();
        adapter = new MyAdapter(favoriteWallpapersList, getActivity());
        recyclerView.setAdapter(adapter);

        loadFavoriteWallpapers();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e(TAG, "Fragment onResume called");
        loadFavoriteWallpapers();
    }

    // Load favorite wallpapers from SQLite database and update UI
    public void loadFavoriteWallpapers() {
            ArrayList<Model> favoriteWallpapers = favoritesDbHelper.getAllFavorites();
        Log.e(TAG, "Loaded URL: " + favoriteWallpapers);
        if (favoriteWallpapers.isEmpty()) {
                imgmessage.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
            } else {
                imgmessage.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
                favoriteWallpapersList.clear(); // Clear the list before adding

                favoriteWallpapersList.addAll(favoriteWallpapers); // Add all models
                adapter.notifyDataSetChanged();
            }
        }
    }

