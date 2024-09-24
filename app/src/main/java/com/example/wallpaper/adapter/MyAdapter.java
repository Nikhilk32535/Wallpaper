package com.example.wallpaper.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.wallpaper.R;
import com.example.wallpaper.activity.full_screen;
import com.example.wallpaper.model.Model;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private ArrayList<Model> dataList;
    private Context context;

    public MyAdapter(ArrayList<Model> dataList, Context context) {
        this.dataList = dataList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.wallpaperlayout, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Model model = dataList.get(position);
        Glide.with(context).load(model.getUrl()).diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(R.drawable.imagesmode).into(holder.imageView);
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, full_screen.class);
            intent.putParcelableArrayListExtra("datalist", dataList); // Ensure Model implements Parcelable
            intent.putExtra("position", position);
            context.startActivity(intent);

            ((Activity) context).overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.wallpaperimg);
        }
    }

}
