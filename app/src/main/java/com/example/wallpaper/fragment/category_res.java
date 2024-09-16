package com.example.wallpaper.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.wallpaper.model.Model;
import com.example.wallpaper.R;
import com.example.wallpaper.adapter.Wallpaperadapter;
import com.example.wallpaper.utility.utility;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class category_res extends Fragment {

    RecyclerView recyclerView;
    Wallpaperadapter adapter;
    ArrayList<Model> WallpaperList;
    int number=1,currentitem,scrollitem,totalitem;
    String category;
    boolean isScroll=false;

    String pixcelurl;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        category=getActivity().getIntent().getStringExtra("category");
        // Inflate the layout for this fragment
     View view=inflater.inflate(R.layout.fragment_category_res, container, false);

      recyclerView=view.findViewById(R.id.recyclerview);
      WallpaperList=new ArrayList<>();
      adapter= new Wallpaperadapter(WallpaperList,getContext());
      recyclerView.setAdapter(adapter);
      GridLayoutManager gridLayoutManager=new GridLayoutManager(getContext(),2);
      recyclerView.setLayoutManager(gridLayoutManager);
      recyclerView.setHasFixedSize(true);

      recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
          @Override
          public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
              super.onScrollStateChanged(recyclerView, newState);
              isScroll=true;
          }
          @Override
          public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
              super.onScrolled(recyclerView, dx, dy);
              scrollitem=gridLayoutManager.getChildCount();
              currentitem=gridLayoutManager.findFirstVisibleItemPosition();
              totalitem=gridLayoutManager.getItemCount();

              if (isScroll=true && (currentitem+scrollitem==totalitem)){
                  isScroll=false;
                  number++;
                  loaddata();
              }
          }
      });

        utility.toast(getActivity(),category);
        refreshdata();

     return view;
    }

    private void refreshdata() {
        WallpaperList.clear();
        adapter.notifyDataSetChanged();
        number=1;
        WallpaperList.clear();
        category=category.toLowerCase();
        pixcelurl="https://api.pexels.com/v1/search/?page="+number+"&per_page=30&query="+category;
        loaddata();
    }

    private void loaddata() {
        pixcelurl="https://api.pexels.com/v1/search/?page="+number+"&per_page=30&query="+category;
        StringRequest request=new StringRequest(Request.Method.GET, pixcelurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("photos");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                        int id = jsonObject1.getInt("id");
                        JSONObject jsonObject2 = jsonObject1.getJSONObject("src");
                        String orignal = jsonObject2.getString("original");
                        String midium = jsonObject2.getString("medium");
                        Model model = new Model(id, orignal, midium);
                        WallpaperList.add(model);
                    }
                    adapter.notifyDataSetChanged();
                    utility.toast(getActivity(),"page"+number);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Authorization", "fh7yaR7vEj3gHOfrAmbuUZj2YFKlcHdwtaw0pDJFAg0oP8G0po7ELi8l");
                return params;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(getActivity().getApplicationContext());
        requestQueue.add(request);
    }
}