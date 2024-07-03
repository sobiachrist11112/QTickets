package com.production.qtickets.dashboard;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.production.qtickets.R;
import com.production.qtickets.activity.MainActivity;
import com.production.qtickets.eventDetailQT5.EventDetailQT5Activity;
import com.production.qtickets.eventQT5.CarouselBannerAdapter;
import com.production.qtickets.eventQT5.EventHomeActivity;
import com.production.qtickets.model.CarouselBannerData;
import com.production.qtickets.model.CustomBannerDataEvents;
import com.production.qtickets.moviedetailes.MovieDetailsActivity;
import com.production.qtickets.utils.AppConstants;
import com.production.qtickets.utils.SessionManager;

import java.util.ArrayList;
import java.util.List;

public class DashBoardCarouselBannerAdapter extends RecyclerView.Adapter<DashBoardCarouselBannerAdapter.MyViewHolder> {

    private int screenWidth = 0;
    ArrayList<CustomBannerDataEvents>  carouselBannerData;
    Context context;
    //sessionmanager
    SessionManager sessionManager;

    public DashBoardCarouselBannerAdapter(ArrayList<CustomBannerDataEvents> carouselBannerData, Context context) {
        this.carouselBannerData = carouselBannerData;
        this.context = context;
        sessionManager = new SessionManager(context);
    }

    @Override
    public DashBoardCarouselBannerAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((DashBoardActivity)context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        screenWidth = displayMetrics.widthPixels;
        View itemView = LayoutInflater.from(context).inflate(R.layout.carousel_banner,parent,false);
        return new DashBoardCarouselBannerAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(DashBoardCarouselBannerAdapter.MyViewHolder holder, int position) {
        Double itemViewWidth = screenWidth/1.3;
        ViewGroup.LayoutParams layoutParams = holder.itemView.getLayoutParams();
        layoutParams.height = layoutParams.height;
        layoutParams.width = (int)Math.round((itemViewWidth));
        holder.itemView.setLayoutParams(layoutParams);
        CustomBannerDataEvents Data = carouselBannerData.get(position);
        if(Data.imagepath.contains("https")){
            Glide.with(context.getApplicationContext()).load(Data.imagepath).into(holder.slider_image);
        }else {
            Glide.with(context.getApplicationContext()).load("https://"+Data.imagepath).into(holder.slider_image);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String imageType = Data.imagetype;
                if (!TextUtils.isEmpty(imageType)) {
                    if (imageType.equals("Movie")) {
                        sessionManager.setMovieId(Data.id);
                        Intent e = new Intent(context, MovieDetailsActivity.class);
                        e.putExtra("movie_title", Data.name);
                        e.putExtra("duration", Data.duration);
                        e.putExtra("movie_type", Data.imagetype);
                        if(Data.imagepath.contains("https")){
                            e.putExtra("movie_img_url", Data.imagepath);
                        }
                        else {
                            e.putExtra("movie_img_url", "https://"+Data.imagepath);
                        }
                        e.putExtra("cencor", Data.censorRating);
                        context.startActivity(e);
                    }
                    else if (imageType.equals("Event")) {
                        if(!Data.isWebView){
                            Intent intent = new Intent(context, EventDetailQT5Activity.class);
                            intent.putExtra(AppConstants.EVENT_ID, Integer.parseInt(Data.id));
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(intent);
                        }else {
                            Bundle b = new Bundle();
                            b.putString("webViewURL", Data.redirectLink);
                            b.putString("pagename", Data.name);
                            Intent i = new Intent(context, ShowWebView.class);
                            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            i.putExtras(b);
                            context.startActivity(i);
                        }

//                        Intent i = new Intent(context, MainActivity.class);
//                        i.putExtra("position", 1);
//                  //      i.putStringArrayListExtra("headerList", heading);
//                        i.putExtra("categoryId", "15");
//                        context.startActivity(i);
                    }
                    else  if(imageType.equals("AdBanner")){
                        Bundle b = new Bundle();
                        b.putString("webViewURL", Data.redirectLink);
                        b.putString("pagename", Data.name);
                        Intent i = new Intent(context, ShowWebView.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        i.putExtras(b);
                        context.startActivity(i);
                    }


                }

//                Intent intent = new Intent(context, EventDetailQT5Activity.class);
//                intent.putExtra(AppConstants.EVENT_ID, Data.id);
//                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return carouselBannerData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView slider_image;
        public MyViewHolder(View itemView) {
            super(itemView);
            slider_image = (ImageView)itemView.findViewById(R.id.slider_image);

        }
    }

}

