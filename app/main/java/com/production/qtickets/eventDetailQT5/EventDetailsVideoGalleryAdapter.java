package com.production.qtickets.eventDetailQT5;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

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

    List<String> videoLinks ;
    Context context;
    String YOUTUBE_API_KEY = "";



    public EventDetailsVideoGalleryAdapter(List<String> videoLinks , Context context) {
        this.videoLinks = videoLinks;
        this.context = context;
    }

    @Override
    public EventDetailsVideoGalleryAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.event_detail_video_gallery,parent,false);
        return new EventDetailsVideoGalleryAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(EventDetailsVideoGalleryAdapter.MyViewHolder holder, int position) {
        final FirebaseRemoteConfig remoteConfig = FirebaseRemoteConfig.getInstance();
        YOUTUBE_API_KEY = remoteConfig.getString("API_KEY");
        String videoID="";
        try {
            String video_url= URLDecoder.decode(videoLinks.get(position), "UTF-8");
             videoID= QTUtils.getvideoID222(video_url);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String finalVideoID = videoID;
        holder.video_thumnailview.initialize(YOUTUBE_API_KEY, new YouTubeThumbnailView.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubeThumbnailView youTubeThumbnailView, YouTubeThumbnailLoader youTubeThumbnailLoader) {
               youTubeThumbnailLoader.setVideo(finalVideoID);
                youTubeThumbnailLoader.setOnThumbnailLoadedListener(new YouTubeThumbnailLoader.OnThumbnailLoadedListener() {
                    @Override
                    public void onThumbnailLoaded(YouTubeThumbnailView youTubeThumbnailView, String s) {
                        youTubeThumbnailLoader.release();
                    }

                    @Override
                    public void onThumbnailError(YouTubeThumbnailView youTubeThumbnailView, YouTubeThumbnailLoader.ErrorReason errorReason) {
                                 String dsf="";
                                 String sdgdsgd="";
                    }
                });
            }

            @Override
            public void onInitializationFailure(YouTubeThumbnailView youTubeThumbnailView, YouTubeInitializationResult youTubeInitializationResult) {
                   String dsf="";
            }
        });


        holder.video_thumnailview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle b = new Bundle();
                String video_url="";
                try {
                     video_url= URLDecoder.decode(videoLinks.get(position), "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                b.putString("url",video_url);
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

        YouTubeThumbnailView video_thumnailview ;


        public MyViewHolder(View itemView) {
            super(itemView);
            video_thumnailview=itemView.findViewById(R.id.video_thumnailview);
        }
    }

}

