package com.production.qtickets.eventDetailQT5;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.production.qtickets.R;

import java.util.List;

public class EventDetailBannerAdapter extends RecyclerView.Adapter<EventDetailBannerAdapter.MyViewHolder> {

    List<String> bannerList;
    Context context;

    public EventDetailBannerAdapter(List<String> bannerList, Context context) {
        this.bannerList = bannerList;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       View itemView = LayoutInflater.from(context).inflate(R.layout.event_detail_banner,parent,false);
       return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        String Data = bannerList.get(position);
        Glide.with(context.getApplicationContext()).load(Data).into(holder.slider_image);

    }

    @Override
    public int getItemCount() {
        return bannerList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView slider_image;
        public MyViewHolder(View itemView) {
            super(itemView);
            slider_image = (ImageView)itemView.findViewById(R.id.slider_image);

        }
    }

}
