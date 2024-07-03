package com.production.qtickets.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.production.qtickets.R;
import com.production.qtickets.eventDetailQT5.EventDetailGalleryAdapter;
import com.production.qtickets.eventDetailQT5.EventDetailQT5Activity;
import com.production.qtickets.eventDetailQT5.FullImageActivity;
import com.production.qtickets.model.AllEventQT5Data;
import com.production.qtickets.model.GetSimilarEventData;
import com.production.qtickets.utils.AppConstants;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

public class SimilarEventsAdapter extends RecyclerView.Adapter<SimilarEventsAdapter.MyViewHolder> {

    ArrayList<GetSimilarEventData.Datum> mSimilarEventList;
    Activity context;

    public SimilarEventsAdapter(ArrayList<GetSimilarEventData.Datum> similarevents, Activity context) {
        this.mSimilarEventList = similarevents;
        this.context = context;
    }

    @Override
    public SimilarEventsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.event_similartype, parent, false);
        return new SimilarEventsAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(SimilarEventsAdapter.MyViewHolder holder, int position) {
        Glide.with(context).load(mSimilarEventList.get(position).banner1).into(holder.iv_similarimage);
        if (mSimilarEventList.get(position).eventTitle != null) {
            holder.tv_similarevent_title.setText(mSimilarEventList.get(position).eventTitle);
        }
        if (mSimilarEventList.get(position).venue != null) {
            holder.tv_similarevent_location.setText(mSimilarEventList.get(position).venue);
        }
        String date = "";
        if (mSimilarEventList.get(position).startDate != null && mSimilarEventList.get(position).startDate.contains(", ")) {
            String[] parts = mSimilarEventList.get(position).startDate.split(", ");
            date = parts[0].toString() + " To ";
        }
        if (mSimilarEventList.get(position).endDate != null && mSimilarEventList.get(position).endDate.contains(", ")) {
            String[] parts = mSimilarEventList.get(position).endDate.split(", ");
            date = date + parts[0];

        } else {
            if (date.contains(" To ")) date = date.replace(" To ", "");
        }

        holder.tv_similarevent_date.setText("" + date);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.finish();
                Intent intent = new Intent(context, EventDetailQT5Activity.class);
                intent.putExtra(AppConstants.EVENT_ID, mSimilarEventList.get(position).id);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return mSimilarEventList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_similarimage;
        TextView tv_similarevent_title, tv_similarevent_date, tv_similarevent_location;

        public MyViewHolder(View itemView) {
            super(itemView);
            iv_similarimage = (ImageView) itemView.findViewById(R.id.iv_similarimage);
            tv_similarevent_title = (TextView) itemView.findViewById(R.id.tv_similarevent_title);
            tv_similarevent_date = (TextView) itemView.findViewById(R.id.tv_similarevent_date);
            tv_similarevent_location = (TextView) itemView.findViewById(R.id.tv_similarevent_location);
        }
    }

}

