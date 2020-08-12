package com.example.assignment_androidnetworking.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.assignment_androidnetworking.R;
import com.example.assignment_androidnetworking.model.PhotoFavorite;
import com.github.chrisbanes.photoview.PhotoView;

import java.util.List;

public class ViewPagerAdapter extends RecyclerView.Adapter<ViewPagerAdapter.PhotoHolder> {
    private List<PhotoFavorite> photoFavoriteList;
    private Context context;

    public ViewPagerAdapter(List<PhotoFavorite> photoFavoriteList, Context context) {
        this.photoFavoriteList = photoFavoriteList;
        this.context = context;
    }

    @NonNull
    @Override
    public PhotoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_view_pager, parent, false);
        return new PhotoHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final PhotoHolder holder, final int position) {
       Glide.with(context).load(photoFavoriteList.get(position).getUrlL()).into(holder.img);

    }

    @Override
    public int getItemCount() {
        return photoFavoriteList.size();
    }

    public class PhotoHolder extends RecyclerView.ViewHolder {
        private PhotoView img;

        public PhotoHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.imgPhoto);

        }
    }
}
