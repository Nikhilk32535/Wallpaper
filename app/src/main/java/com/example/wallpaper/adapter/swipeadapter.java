package com.example.wallpaper.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.wallpaper.R;
import com.example.wallpaper.model.Model;

import java.util.ArrayList;

public class swipeadapter extends RecyclerView.Adapter<swipeadapter.Viewholder> {
    ArrayList<Model> dataList;
    Context context;

    public swipeadapter(ArrayList<Model> dataList, Context context) {
        this.dataList = dataList;
        this.context = context;
    }

    @NonNull
    @Override
    public swipeadapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.fullscreen_layout, parent, false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull swipeadapter.Viewholder holder, int position) {
    Model model=dataList.get(position);
            // Load the image URL using Glide
            Glide.with(context)
                    .load(model.getUrl())   // Load image from URL
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(R.drawable.imagesmode)  // Optional: Add a loading placeholder
                    .into(holder.currentimg);  // Load into the ImageView
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        ImageView currentimg;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            currentimg = itemView.findViewById(R.id.fullpic);
        }
    }
}
