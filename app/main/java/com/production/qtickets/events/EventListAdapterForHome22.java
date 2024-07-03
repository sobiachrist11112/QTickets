package com.production.qtickets.events;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.production.qtickets.R;
import com.production.qtickets.eventQT5.EvenListInHomeAdapter;
import com.production.qtickets.events.EventDetails.EventDetailsActivity;
import com.production.qtickets.events.EventDetails.EventWebviewActivity;
import com.production.qtickets.model.AllEventQT5Data;
import com.production.qtickets.model.EventData;
import com.production.qtickets.utils.SessionManager;

import java.util.ArrayList;
import java.util.List;

public class EventListAdapterForHome22 extends RecyclerView.Adapter<EventListAdapterForHome22.EventsListViewHolder> {


    ArrayList<AllEventQT5Data>  eventListArrayList = new ArrayList<>();
    Context context;
    int differenciating;
    SessionManager sessionManager;
    String startDate,endDate;
    int pos;
    String eventHeading;


    public EventListAdapterForHome22(Context context, ArrayList<AllEventQT5Data>  eventListArrayList, int differenciating) {
        this.context = context;
        this.eventListArrayList = eventListArrayList;
        this.differenciating = differenciating;
    }



    @NonNull
    @Override
    public EventListAdapterForHome22.EventsListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.events_list_item_qt5_row22,parent,false);
        return new EventListAdapterForHome22.EventsListViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull EventListAdapterForHome22.EventsListViewHolder holder, int position) {
        final AllEventQT5Data eventData = eventListArrayList.get(position);
        Glide.with(context).load(eventData.banner7).into(holder.iv_image);
        if (eventData.eventTitle != null) {
            holder.tv_event_title.setText(eventData.eventTitle);
        }
        if (eventData.venue != null) {
            holder.tv_event_location.setText(eventData.venue);
        }
        String date = "";
        if (eventData.startDate != null && eventData.startDate.contains(", ")) {
            String[] parts = eventData.startDate.split(", ");
            date = parts[0].toString()+" to ";
        }
        if (eventData.endDate != null && eventData.endDate.contains(", ")) {
            String[] parts = eventData.endDate.split(", ");
            date = date+parts[0];

        }else {
            if (date.contains(" to "))date = date.replace(" to ","");
        }

        holder.tv_event_date.setText(""+date);

    }

    @Override
    public int getItemCount() {
        return eventListArrayList.size();
    }

    public class EventsListViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_image;
        TextView tv_event_title;
        TextView tv_event_date;
        TextView tv_event_location;
        LinearLayout bookNow_layout;

        public EventsListViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_image = itemView.findViewById(R.id.iv_image);
            tv_event_title = itemView.findViewById(R.id.tv_event_title);
            tv_event_date = itemView.findViewById(R.id.tv_event_date);
            tv_event_location = itemView.findViewById(R.id.tv_event_location);
            bookNow_layout = itemView.findViewById(R.id.bookNow_layout);
        }
    }
}

