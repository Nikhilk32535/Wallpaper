package com.example.wallpaper.adapter;

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
import com.example.wallpaper.model.Model;
import com.example.wallpaper.R;
import com.example.wallpaper.activity.full_screen;

import java.util.ArrayList;

public class Wallpaperadapter extends RecyclerView.Adapter<Wallpaperadapter.wallpaperviewholder>{
    ArrayList<Model> datalist;
    Context context;

    public Wallpaperadapter(ArrayList<Model> datalist, Context context) {
        this.datalist = datalist;
        this.context = context;
    }
    @NonNull
    @Override
    public Wallpaperadapter.wallpaperviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.wallpaperlayout,parent,false);
        return new wallpaperviewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Wallpaperadapter.wallpaperviewholder holder, int position) {
        Model model=datalist.get(position);
    Glide.with(context).load(model.getUrl()).diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(R.drawable.imagesmode).into(holder.imageView);
    holder.itemView.setOnClickListener(v->{
        Intent intent=new Intent(context, full_screen.class)
                .putParcelableArrayListExtra("datalist",datalist)
                .putExtra("position",position);
        context.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return datalist.size();
    }

    public static class wallpaperviewholder extends RecyclerView.ViewHolder {
        ImageView imageView;
        public wallpaperviewholder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.wallpaperimg);
        }
    }
}
