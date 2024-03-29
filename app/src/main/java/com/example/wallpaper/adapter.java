package com.example.wallpaper;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class adapter extends FirebaseRecyclerAdapter<Model, adapter.MyViewholder> {

    public adapter(@NonNull FirebaseRecyclerOptions<Model> options) { super(options); }

    @NonNull
    @Override
    public MyViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.wallpaperlayout, parent, false);
        return new MyViewholder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull MyViewholder holder, int i, @NonNull Model model) {
        Toast.makeText(holder.itemView.getContext(),"my Adapter start...",Toast.LENGTH_LONG).show();
        if (model.getName()!=null && model.getUrl()!=null) {
            holder.name.setText(model.getName());
            Glide.with(holder.itemView.getContext())
                    .load(model.getUrl())
                    .into(holder.url);
            Log.e("URL in adapter:",model.getUrl());
        }else {
            Toast.makeText(holder.itemView.getContext(),"Something Wrong",Toast.LENGTH_LONG).show();
        }
    }


    public static class MyViewholder extends RecyclerView.ViewHolder {
ImageView url;
TextView name;
        public MyViewholder(@NonNull View itemView) {
            super(itemView);
            url= itemView.findViewById(R.id.wallpaperimg);
            name= itemView.findViewById(R.id.wallpapername);

        }
    }
}
