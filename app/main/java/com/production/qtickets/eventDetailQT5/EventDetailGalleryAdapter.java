package com.production.qtickets.eventDetailQT5;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.production.qtickets.R;
import com.production.qtickets.model.GalleryImage;
import com.production.qtickets.utils.QTUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

public class EventDetailGalleryAdapter extends RecyclerView.Adapter<EventDetailGalleryAdapter.MyViewHolder> {

    List<String> galleryImages;
    Context context;

    public EventDetailGalleryAdapter(List<String> galleryImages, Context context) {
        this.galleryImages = galleryImages;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       View itemView = LayoutInflater.from(context).inflate(R.layout.event_detail_gallery,parent,false);
       return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        try {
            String imageurl= URLDecoder.decode(galleryImages.get(position), "UTF-8");
            Glide.with(context.getApplicationContext()).load(imageurl).into(holder.slider_image);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,FullImageActivity.class);
                try {
                    intent.putExtra("IMAGE",URLDecoder.decode(galleryImages.get(position), "UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return galleryImages.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView slider_image;
        public MyViewHolder(View itemView) {
            super(itemView);
            slider_image = (ImageView)itemView.findViewById(R.id.slider_image);
        }
    }

}
