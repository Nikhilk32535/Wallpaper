package com.example.wallpaper;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
RecyclerView recyclerView;
adapter myadapter;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("Nature");

    String appName = "Nikhil";
    String appUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcT_YJouNKi-9EG28vgulTd_2OFxMCV1jrkwrHZqjNQv4A&s";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView=findViewById(R.id.recyclerview);
        Toast.makeText(MainActivity.this,"start...",Toast.LENGTH_LONG).show();
     /*   // Create wallpaper Model instance
        Model wallpaperModel = new Model();
        wallpaperModel.setName(appName);
        wallpaperModel.seturl(appUrl);

        // Push data to Firebase
        myRef.push().setValue(wallpaperModel); */

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<Model> options =
                new FirebaseRecyclerOptions.Builder<Model>()
                        .setQuery(myRef, Model.class)
                        .build();
        if(options==null){
        Toast.makeText(MainActivity.this,"Step 2...",Toast.LENGTH_LONG).show();}

        myadapter = new adapter(options);
        Toast.makeText(MainActivity.this,"Step 3...",Toast.LENGTH_LONG).show();
        recyclerView.setAdapter(myadapter);

    }

    @Override
    protected void onStart() {
        super.onStart();
        myadapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        myadapter.stopListening();
    }

}