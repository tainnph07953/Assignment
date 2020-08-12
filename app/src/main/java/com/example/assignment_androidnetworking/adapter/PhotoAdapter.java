package com.example.assignment_androidnetworking.adapter;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.assignment_androidnetworking.R;
import com.example.assignment_androidnetworking.activity.PictureActivity;
import com.example.assignment_androidnetworking.model.PhotoGP;

import java.util.List;


public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.Holder> {
    List<PhotoGP> photoGPList;
    Context context;

    public PhotoAdapter(List<PhotoGP> photoGPList, Context context) {
        this.photoGPList = photoGPList;
        this.context = context;
    }


    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row,parent,false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final Holder holder, final int position) {
        Glide.with(context).load(photoGPList.get(position).getUrlL()).into(holder.imgPicture);
        holder.tvView.setText(photoGPList.get(position).getViews()+" views");
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, PictureActivity.class);

                Bundle bundle = new Bundle();
                bundle.putInt("position", position);
                bundle.putString("Title", photoGPList.get(position).getTitle());
                bundle.putString("Pathalias", photoGPList.get(position).getPathalias());
                Log.e("POSITION",position+"");
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return photoGPList.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        ImageView imgPicture;
        TextView tvView;
        public Holder(@NonNull View itemView) {
            super(itemView);
            imgPicture=itemView.findViewById(R.id.imgPicture1);
            tvView=itemView.findViewById(R.id.tvView1);
        }
    }
}
