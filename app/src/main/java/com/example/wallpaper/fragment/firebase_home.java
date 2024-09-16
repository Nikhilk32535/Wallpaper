package com.example.wallpaper.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wallpaper.model.Model;
import com.example.wallpaper.adapter.MyAdapter;
import com.example.wallpaper.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class firebase_home extends Fragment {

    private static final int PAGE_SIZE = 20; // Number of images to load per page
    private boolean isLoading = false; // To check if data is currently loading
    private String lastLoadedKey = null; // Store the key of the last loaded item

    RecyclerView recyclerView;
    MyAdapter adapter;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("Wallpaper_App");
    ArrayList<Model> Datalist;
    TextView homebtn, cetegorybtn;
    LinearLayout homebar, categorybar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_firebase_home, container, false);

        recyclerView = view.findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));

        Datalist = new ArrayList<>();
        adapter = new MyAdapter(Datalist, getActivity());
        recyclerView.setAdapter(adapter);

        // Load the initial set of data
        loadMoreData();

        // Implement endless scrolling
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                if (!recyclerView.canScrollVertically(1) && !isLoading) { // Check if reached bottom and not currently loading
                    loadMoreData(); // Load more data
                }
            }
        });

        return view;
    }

    private void loadMoreData() {
        isLoading = true;

        Query query;
        if (lastLoadedKey == null) {
            query = myRef.orderByKey().limitToFirst(PAGE_SIZE);
        } else {
            query = myRef.orderByKey().startAfter(lastLoadedKey).limitToFirst(PAGE_SIZE);
        }

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    ArrayList<Model> newItems = new ArrayList<>();
                    String lastKey = null;
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        Model model = dataSnapshot.getValue(Model.class);
                        newItems.add(model);
                        lastKey = dataSnapshot.getKey();
                    }

                    if (!newItems.isEmpty()) {
                        lastLoadedKey = lastKey;
                        Datalist.addAll(newItems);
                        adapter.notifyDataSetChanged();
                    } else if (Datalist.isEmpty()) {
                        lastLoadedKey = null;
                    }
                }
                isLoading = false; // Set loading to false after data is loaded
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("DatabaseError", "Error: " + error.getMessage());
                isLoading = false;
            }
        });
    }
}
