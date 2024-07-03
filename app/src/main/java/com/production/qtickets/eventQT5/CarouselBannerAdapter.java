package com.production.qtickets.eventQT5;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.production.qtickets.R;
import com.production.qtickets.activity.MainActivity;
import com.production.qtickets.activity.ShowWebView;
import com.production.qtickets.activity.ShowWebView2;
import com.production.qtickets.dashboard.ListDataAdapter;
import com.production.qtickets.eventDetailQT5.EventDetailQT5Activity;
import com.production.qtickets.model.CarouselBannerData;
import com.production.qtickets.model.Data;
import com.production.qtickets.model.ListData;
import com.production.qtickets.utils.AppConstants;

import java.util.ArrayList;
import java.util.List;

public class CarouselBannerAdapter extends RecyclerView.Adapter<CarouselBannerAdapter.MyViewHolder> {

    private int screenWidth = 0;
    List<CarouselBannerData> carouselBannerData;
    Context context;

    public CarouselBannerAdapter(List<CarouselBannerData> carouselBannerData, Context context) {
        this.carouselBannerData = carouselBannerData;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((EventHomeActivity)context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        screenWidth = displayMetrics.widthPixels;
       View itemView = LayoutInflater.from(context).inflate(R.layout.carousel_banner,parent,false);
       return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Double itemViewWidth = screenWidth/1.3;
        ViewGroup.LayoutParams layoutParams = holder.itemView.getLayoutParams();
        layoutParams.height = layoutParams.height;
        layoutParams.width = (int)Math.round((itemViewWidth));
        holder.itemView.setLayoutParams(layoutParams);
        CarouselBannerData Data = carouselBannerData.get(position);
        Glide.with(context.getApplicationContext()).load(Data.banner).into(holder.slider_image);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!Data.isWebView){
                    Intent intent = new Intent(context, EventDetailQT5Activity.class);
                    intent.putExtra(AppConstants.EVENT_ID, Data.eventId);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }else {
                    Bundle b = new Bundle();
                    b.putString("webViewURL", Data.webViewUrl);
                    b.putString("pagename", Data.eventTitle);
                    Intent i = new Intent(context, ShowWebView2.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    i.putExtras(b);
                    context.startActivity(i);
                }
//                Intent intent = new Intent(context, EventDetailQT5Activity.class);
//                intent.putExtra(AppConstants.EVENT_ID, Data.eventId);
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
