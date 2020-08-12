package com.example.assignment_androidnetworking.activity;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.assignment_androidnetworking.R;
import com.example.assignment_androidnetworking.adapter.GalleryAdapter;
import com.example.assignment_androidnetworking.model.ExampleGallery;
import com.example.assignment_androidnetworking.model.Gallery;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class GalleriesActivity extends AppCompatActivity {
    RecyclerView rvGallery;
    SwipeRefreshLayout srlGallery;
    List<Gallery> galleryList;
    GalleryAdapter galleryAdapter;
    GridLayoutManager gridLayoutManager;
    ExampleGallery exampleGallery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_galleries);
        rvGallery = findViewById(R.id.rvGallery);
        srlGallery = findViewById(R.id.srlGallery);
        galleryList = new ArrayList<>();
        FastGallery();
        srlGallery.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                FastGallery();
                srlGallery.setRefreshing(false);
            }
        });
    }

    private void FastGallery() {
        AndroidNetworking.get("https://www.flickr.com/services/rest/?method=flickr.galleries.getList&api_key=1628b2d0ed5fb84aa200450cd084886a&user_id=186985306@N02&per_page=10&page=1&format=json&nojsoncallback=1")
                .setTag("test")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject responseq) {
                        exampleGallery = new Gson().fromJson(responseq.toString(), ExampleGallery.class);
                        galleryList = exampleGallery.getGalleries().getGallery();
                        galleryAdapter = new GalleryAdapter(galleryList, GalleriesActivity.this);
                        rvGallery.setAdapter(galleryAdapter);
                        rvGallery.setHasFixedSize(true);
                        gridLayoutManager = new GridLayoutManager(GalleriesActivity.this,1);
                        rvGallery.setLayoutManager(gridLayoutManager);
                        Log.e("GL", galleryList.size() + "");
                    }

                    @Override
                    public void onError(ANError error) {
                    }
                });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.xml.slide_in_from_left, R.xml.slide_out_to_right);

    }
}