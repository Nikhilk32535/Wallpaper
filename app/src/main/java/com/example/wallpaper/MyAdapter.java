package com.example.wallpaper;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class MyAdapter extends FirebaseRecyclerAdapter<Model,MyAdapter.MyViewholder> {


    public MyAdapter(@NonNull FirebaseRecyclerOptions<Model> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull MyViewholder holder, int i, @NonNull Model model) {
        Toast.makeText(holder.itemView.getContext(), "adapter", Toast.LENGTH_SHORT).show();
        holder.name.setText(model.getName());
        Glide.with(holder.itemView.getContext())
                .load(model.getUrl())
                .into(holder.url);
        String text1= model.name;
        holder.download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(holder.itemView.getContext(), "download "+text1, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @NonNull
    @Override
    public MyViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.wallpaperlayout,parent,false);
        return new MyViewholder(view);
    }

    public static class MyViewholder extends RecyclerView.ViewHolder {
        ImageView url;
        TextView name;
        Button download;
        public MyViewholder(@NonNull View itemView) {
            super(itemView);
            url= itemView.findViewById(R.id.wallpaperimg);
            name= itemView.findViewById(R.id.wallpapername);
            download= itemView.findViewById(R.id.downloadbutton);

        }
    }
}
