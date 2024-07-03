package com.production.qtickets.eventDetailQT5;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubeThumbnailLoader;
import com.google.android.youtube.player.YouTubeThumbnailView;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.production.qtickets.R;
import com.production.qtickets.moviedetailes.YoutubeActivity;
import com.production.qtickets.utils.QTUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

public class EventDetailsVideoGalleryAdapter extends RecyclerView.Adapter<EventDetailsVideoGalleryAdapter.MyViewHolder> {

    List<String> videoLinks;
    Context context;
    String YOUTUBE_API_KEY = "";


    public EventDetailsVideoGalleryAdapter(List<String> videoLinks, Context context) {
        this.videoLinks = videoLinks;
        this.context = context;
    }

    @Override
    public EventDetailsVideoGalleryAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.event_detail_video_gallery, parent, false);
        return new EventDetailsVideoGalleryAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(EventDetailsVideoGalleryAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
       /* final FirebaseRemoteConfig remoteConfig = FirebaseRemoteConfig.getInstance();
        YOUTUBE_API_KEY = remoteConfig.getString("API_KEY");
*/
        FirebaseRemoteConfig remoteConfig = FirebaseRemoteConfig.getInstance();
        remoteConfig.fetch()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Activate the fetched values
                        remoteConfig.activateFetched();

                        // Now you can access the values, including YOUTUBE_API_KEY
                        YOUTUBE_API_KEY = remoteConfig.getString("API_KEY");
                        Log.d("YOUTUBE_API_KEY", "onSuccess :" + YOUTUBE_API_KEY);

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Handle failure to fetch remote config values

                        Log.d("YOUTUBE_API_KEY", "onFailure :" + e.getLocalizedMessage());

                    }
                });


        String videoID = "";
        try {
            String video_url = URLDecoder.decode(videoLinks.get(position), "UTF-8");
            videoID = QTUtils.getvideoID222(video_url);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String finalVideoID = videoID;

        String url = "https://img.youtube.com/vi/" + finalVideoID + "/0.jpg";
        Glide.with(context).load(url).into(holder.ivthum);

//        holder.thumbnailView.initialize(YOUTUBE_API_KEY, new YouTubeThumbnailView.OnInitializedListener() {
//            @Override
//            public void onInitializationSuccess(YouTubeThumbnailView youTubeThumbnailView, YouTubeThumbnailLoader youTubeThumbnailLoader) {
//                youTubeThumbnailLoader.setVideo(finalVideoID);
//
//                // Hide the loading bar here (once the thumbnail is initialized)
//                holder.loadingBar.setVisibility(View.GONE);
//
//                // Release the loader when you're done with it to avoid resource leaks
//                youTubeThumbnailLoader.setOnThumbnailLoadedListener(new YouTubeThumbnailLoader.OnThumbnailLoadedListener() {
//                    @Override
//                    public void onThumbnailLoaded(YouTubeThumbnailView youTubeThumbnailView, String s) {
//                        youTubeThumbnailLoader.release();
//                    }
//
//                    @Override
//                    public void onThumbnailError(YouTubeThumbnailView youTubeThumbnailView, YouTubeThumbnailLoader.ErrorReason errorReason) {
//                        // Handle any errors here
//
//                        Log.d("YOUTUBE_API_KEY",errorReason.toString());
//
//                    }
//                });
//            }
//
//            @Override
//            public void onInitializationFailure(YouTubeThumbnailView youTubeThumbnailView, YouTubeInitializationResult youTubeInitializationResult) {
//                // Handle initialization failure here, e.g., by showing an error message
//                Log.d("YOUTUBE_API_KEYF",youTubeInitializationResult.toString());
//
//            }
//        });

        holder.thumbnailView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle b = new Bundle();
                String video_url = "";
                try {
                    video_url = URLDecoder.decode(videoLinks.get(position), "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                b.putString("url", video_url);
                b.putString("tittle", videoLinks.get(position));
                Intent i = new Intent(context, YoutubeActivity.class);
                i.putExtras(b);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return videoLinks.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        YouTubeThumbnailView thumbnailView;
        ProgressBar loadingBar;
        ImageView ivthum;


        public MyViewHolder(View itemView) {
            super(itemView);
            thumbnailView = itemView.findViewById(R.id.video_thumnailview);
            loadingBar = itemView.findViewById(R.id.loading_bar);
            ivthum = itemView.findViewById(R.id.ivthum);
        }
    }

}

