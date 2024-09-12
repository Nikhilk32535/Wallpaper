package com.example.wallpaper;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final int PAGE_SIZE = 20; // Number of images to load per page
    private boolean isLoading = false; // To check if data is currently loading
    private String lastLoadedKey = null; // Store the key of the last loaded item

    RecyclerView recyclerView;
    MyAdapter adapter;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("Wallpaper_App");
    ArrayList<Model> Datalist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        Datalist = new ArrayList<>();
        adapter = new MyAdapter(Datalist, this);
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
                        lastKey=dataSnapshot.getKey();
                    }

                    if (!newItems.isEmpty()) {
                        lastLoadedKey = lastKey;
                        Datalist.addAll(newItems);
                        adapter.notifyDataSetChanged();
                    } else if (Datalist.isEmpty()) {
                        lastLoadedKey=null;
                        Toast.makeText(MainActivity.this, "No data available", Toast.LENGTH_SHORT).show();
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
