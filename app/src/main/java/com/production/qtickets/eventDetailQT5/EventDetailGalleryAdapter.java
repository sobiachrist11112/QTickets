package com.production.qtickets.eventDetailQT5;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
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
        View itemView = LayoutInflater.from(context).inflate(R.layout.event_detail_gallery, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, @SuppressLint("RecyclerView") int position) {

        try {
            String imageurl = URLDecoder.decode(galleryImages.get(position), "UTF-8");
//          Glide.with(context.getApplicationContext()).load(imageurl).into(holder.slider_image);

            Glide.with(context.getApplicationContext())
                    .load(imageurl)
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            // Handle the case where image loading fails
                            holder.loading_bar.setVisibility(View.GONE); // Hide the loading bar
                            return false; // Return false to allow Glide to continue handling the error
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            // Image has successfully loaded
                            holder.loading_bar.setVisibility(View.GONE); // Hide the loading bar
                            return false; // Return false to allow Glide to continue handling the resource ready event
                        }
                    })
                    .into(holder.slider_image);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, FullImageActivity.class);
                try {
                    intent.putExtra("IMAGE", URLDecoder.decode(galleryImages.get(position), "UTF-8"));
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
        ProgressBar loading_bar;

        public MyViewHolder(View itemView) {
            super(itemView);
            slider_image = (ImageView) itemView.findViewById(R.id.slider_image);
            loading_bar =  itemView.findViewById(R.id.loading_bar);
        }
    }

}
