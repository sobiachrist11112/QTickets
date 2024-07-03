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

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.production.qtickets.R;
import com.production.qtickets.events.EventDetails.EventDetailsActivity;
import com.production.qtickets.events.EventDetails.EventTicketsActivity;
import com.production.qtickets.events.EventDetails.EventWebviewActivity;
import com.production.qtickets.model.AllEventQT5Data;
import com.production.qtickets.model.EventData;
import com.production.qtickets.utils.SessionManager;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EventListAdapterForHome extends RecyclerView.Adapter<EventListAdapterForHome.EventsListViewHolder> {

 //   List<EventData> eventListArrayList = new ArrayList<>();
    ArrayList<AllEventQT5Data>  eventListArrayList = new ArrayList<>();
    List<EventData> eventListArrayList1;
    Context context;
    int differenciating;
    SessionManager sessionManager;
    String startDate,endDate;
    int pos;
    String eventHeading;


    public EventListAdapterForHome(Context context, ArrayList<AllEventQT5Data>  eventListArrayList, int differenciating) {
        this.context = context;
        this.eventListArrayList = eventListArrayList;
        this.differenciating = differenciating;
    }

    public EventListAdapterForHome(Context context, ArrayList<AllEventQT5Data>  eventListArrayList, int differenciating, String eventHeading) {
        this.eventListArrayList = eventListArrayList;
        this.context = context;
        this.differenciating = differenciating;
        this.eventHeading = eventHeading;
    }

    @Override
    public EventListAdapterForHome.EventsListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        EventListAdapterForHome.EventsListViewHolder eventsListViewHolder;
        if (differenciating == 100) {
            View eventsList = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_item_row, parent, false);
            eventsListViewHolder = new EventListAdapterForHome.EventsListViewHolder(eventsList);
        } else {
            View eventsList = LayoutInflater.from(parent.getContext()).inflate(R.layout.events_list_item_row, parent, false);
            eventsListViewHolder = new EventListAdapterForHome.EventsListViewHolder(eventsList);
        }
        return eventsListViewHolder;
    }

    @Override
    public void onBindViewHolder(EventListAdapterForHome.EventsListViewHolder holder, int position) {
        final AllEventQT5Data  eventData = eventListArrayList.get(position);
        sessionManager = new SessionManager(context);
        if(eventData.eventTitle != null){
            holder.tv_event_title.setText(eventData.eventTitle);
        }
        //leisure api
//        if(eventData.tourName != null){
//            holder.tv_event_title.setText(eventData.tourName);
//        }

        if(!TextUtils.isEmpty(eventData.startDate)) {
            String[] parts = eventData.startDate.split(" ");
            startDate = parts[0];
        }
        if(!TextUtils.isEmpty(eventData.endDate)){
            String[] parts = eventData.endDate.split(" ");
            endDate = parts[0];
        }

        if(!(TextUtils.isEmpty(startDate)&&TextUtils.isEmpty(endDate))){
            holder.tv_event_date.setText(startDate + " " +eventData.startDate+" "+"TO"+" \n" +endDate+" "+eventData.endDate);
        }

//        if(eventData.duration != null){
//
//            holder.tv_event_date.setText(eventData.duration);
//        }
        if(eventData.venue != null ){
            holder.tv_event_location.setVisibility(View.VISIBLE);
            holder.locationlogo.setVisibility(View.VISIBLE);
            if(eventData.venue != null){
                String capitalized = capitalizeFirstLetter(eventData.venue);
                holder.tv_event_location.setText(capitalized);
            }

        } else {
            holder.tv_event_location.setVisibility(View.GONE);
            holder.locationlogo.setVisibility(View.GONE);
        }

//        if(eventData.venue != null || eventData.cityTourType != null){
//            holder.tv_event_location.setVisibility(View.VISIBLE);
//            holder.locationlogo.setVisibility(View.VISIBLE);
//            if(eventData.venue != null){
//                holder.tv_event_location.setText(eventData.venue);
//            }
//            if(eventData.cityTourType != null){
//                holder.tv_event_location.setText(eventData.cityTourType);
//            }
//
//        } else {
//            holder.tv_event_location.setVisibility(View.GONE);
//            holder.locationlogo.setVisibility(View.GONE);
//        }



        if (differenciating == 200) {
            if(eventData.banner != null){
                Glide.with(context).load(eventData.banner)
                        .thumbnail(0.5f)
                        .into(holder.iv_event_banner_wide);
            }
            //leisure api
            if(eventData.banner != null){
                Glide.with(context).load(eventData.banner)
                        .thumbnail(0.5f)
                        .into(holder.iv_event_banner_wide);
            }
            holder.iv_event_banner_wide.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(eventData.isWebView != null){
                        if (eventData.isWebView) {
                            String eventWebViewUrl = eventData.webViewUrl;
                            String event_title = eventData.eventTitle;
                            Intent webViewIntent = new Intent(context, EventWebviewActivity.class);
                            webViewIntent.putExtra("webViewURL", eventWebViewUrl);
                            webViewIntent.putExtra("title",event_title);
                            context.startActivity(webViewIntent);

                        } else {
                            sessionManager.setEventId(String.valueOf(eventData.id));
                            Intent eventIntent = new Intent(context, EventDetailsActivity.class);
                            eventIntent.putExtra("eventID", String.valueOf(eventData.id));
                            eventIntent.putExtra("eventSource", "eventPage");
                            eventIntent.putExtra("eventBanner", eventData.banner);
                            eventIntent.putExtra("eventName", eventData.eventTitle);
                            eventIntent.putExtra("eventStartDate", eventData.startDate);
                            eventIntent.putExtra("eventEndDate", eventData.endDate);
//                            eventIntent.putExtra("eventStartTime", eventData.startTime);
//                            eventIntent.putExtra("eventEndTime", eventData.endTime);
//                            eventIntent.putExtra("eventVenue", eventData.venue);
//                            eventIntent.putExtra("eventLatitude", eventData.latitude);
//                            eventIntent.putExtra("eventLongitude", eventData.longitude);
//                            eventIntent.putExtra("eventEmail",eventData.contactPersonEmail);
//                            eventIntent.putExtra("eventDescription", eventData.ev);
//                            eventIntent.putExtra("EventUrl",eventData.eventUrl);
                            context.startActivity(eventIntent);

                        }
                    } else {
//                        if(eventData.imageTourUrls.tourUrl != null){
//                            String eventWebViewUrl = eventData.imageTourUrls.tourUrl;
//                            String event_title = eventData.tourName;
//                            Intent webViewIntent = new Intent(context, EventWebviewActivity.class);
//                            webViewIntent.putExtra("webViewURL", eventWebViewUrl);
//                            webViewIntent.putExtra("title",event_title);
//                            context.startActivity(webViewIntent);
//                        }
                    }
                }
            });

//            holder.bookNow_layout.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    if(eventData.showBrowser != null){
//                       /* if(eventHeading!= null && eventHeading.equals("Free to go events")){
//                            eventData.showBrowser="1";
//                        }*/
//                        if (eventData.showBrowser.equals("1")) {
//                            String event_title = eventData.eventname;
//                            String eventWebViewUrl = eventData.eventUrl;
//                            Intent webViewIntent = new Intent(context, EventWebviewActivity.class);
//                            webViewIntent.putExtra("webViewURL", eventWebViewUrl);
//                            webViewIntent.putExtra("title",event_title);
//                            context.startActivity(webViewIntent);
//
//                        } else {
//
//                            Intent eventIntent = new Intent(context, EventTicketsActivity.class);
//                            eventIntent.putExtra("eventID", eventData.eventid);
//                            eventIntent.putExtra("eventSource", "eventPage");
//                            eventIntent.putExtra("eventBanner", eventData.mobileURL);
//                            eventIntent.putExtra("eventName", eventData.eventname);
//                            eventIntent.putExtra("eventStartDate", eventData.startDate);
//                            eventIntent.putExtra("eventEndDate", eventData.endDate);
//                            eventIntent.putExtra("eventStartTime", eventData.startTime);
//                            eventIntent.putExtra("eventEndTime", eventData.endTime);
//                            eventIntent.putExtra("eventEmail",eventData.contactPersonEmail);
//                            eventIntent.putExtra("eventVenue", eventData.venue);
//                            eventIntent.putExtra("eventLatitude", eventData.latitude);
//                            eventIntent.putExtra("eventLongitude", eventData.longitude);
//                            eventIntent.putExtra("eventDescription", eventData.eDescription);
//                            eventIntent.putExtra("EventUrl",eventData.eventUrl);
//                            context.startActivity(eventIntent);
//
//                        }
//                    } else {
//                        if(eventData.imageTourUrls.tourUrl != null){
//                            String eventWebViewUrl = eventData.imageTourUrls.tourUrl;
//                            String event_title = eventData.tourName;
//                            Intent webViewIntent = new Intent(context, EventWebviewActivity.class);
//                            webViewIntent.putExtra("webViewURL", eventWebViewUrl);
//                            webViewIntent.putExtra("title",event_title);
//                            context.startActivity(webViewIntent);
//                        }
//                    }
//
//
//                }
//            });

        } else {
//            if(eventData.mobileURL != null) {
//                Glide.with(context).load(eventData.mobileURL)
//                        .thumbnail(0.5f)
//                        .into(holder.iv_event_banner);
//            }
//
//            holder.tv_booknow.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//
//                    if (eventData.showBrowser.equals("1")) {
//                        String event_title = eventData.eventname;
//                        String eventWebViewUrl = eventData.eventUrl;
//                        Intent webViewIntent = new Intent(context, EventWebviewActivity.class);
//                        webViewIntent.putExtra("webViewURL", eventWebViewUrl);
//                        webViewIntent.putExtra("title",event_title);
//                        context.startActivity(webViewIntent);
//
//                    } else {
//
//                        Intent eventIntent = new Intent(context, EventTicketsActivity.class);
//                        eventIntent.putExtra("eventID", eventData.eventid);
//                        eventIntent.putExtra("eventSource", "homePage");
//                        eventIntent.putExtra("eventBanner", eventData.mobileURL);
//                        eventIntent.putExtra("eventName", eventData.eventname);
//                        eventIntent.putExtra("eventStartDate", eventData.startDate);
//                        eventIntent.putExtra("eventEndDate", eventData.endDate);
//                        eventIntent.putExtra("eventStartTime", eventData.startTime);
//                        eventIntent.putExtra("eventEndTime", eventData.endTime);
//                        eventIntent.putExtra("eventEmail",eventData.contactPersonEmail);
//                        eventIntent.putExtra("eventVenue", eventData.venue);
//                        eventIntent.putExtra("eventLatitude", eventData.latitude);
//                        eventIntent.putExtra("eventLongitude", eventData.longitude);
//                        eventIntent.putExtra("eventDescription", eventData.eDescription);
//                        eventIntent.putExtra("EventUrl",eventData.eventUrl);
//                        context.startActivity(eventIntent);
//
//                    }
//                }
//            });
//
//            holder.iv_event_banner.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    if (eventData.showBrowser.equals("1")) {
//                        String event_title = eventData.eventname;
//                        String eventWebViewUrl = eventData.eventUrl;
//                        Intent webViewIntent = new Intent(context, EventWebviewActivity.class);
//                        webViewIntent.putExtra("webViewURL", eventWebViewUrl);
//                        webViewIntent.putExtra("title",event_title);
//                        context.startActivity(webViewIntent);
//
//                    } else {
//                        sessionManager.setEventId(eventData.eventid);
//                        Intent eventIntent = new Intent(context, EventDetailsActivity.class);
//                        eventIntent.putExtra("eventID", eventData.eventid);
//                        eventIntent.putExtra("eventSource", "homePage");
//                        eventIntent.putExtra("eventBanner", eventData.mobileURL);
//                        eventIntent.putExtra("eventName", eventData.eventname);
//                        eventIntent.putExtra("eventStartDate", eventData.startDate);
//                        eventIntent.putExtra("eventEndDate", eventData.endDate);
//                        eventIntent.putExtra("eventStartTime", eventData.startTime);
//                        eventIntent.putExtra("eventEndTime", eventData.endTime);
//                        eventIntent.putExtra("eventEmail",eventData.contactPersonEmail);
//                        eventIntent.putExtra("eventVenue", eventData.venue);
//                        eventIntent.putExtra("eventLatitude", eventData.latitude);
//                        eventIntent.putExtra("eventLongitude", eventData.longitude);
//                        eventIntent.putExtra("eventDescription", eventData.eDescription);
//                        eventIntent.putExtra("EventUrl",eventData.eventUrl);
//                        context.startActivity(eventIntent);
//
//                    }
//                }
//            });
        }
    }

    @Override
    public int getItemCount() {
        return eventListArrayList.size();
    }

    public class EventsListViewHolder extends RecyclerView.ViewHolder {

        ImageView iv_event_banner;
        ImageView iv_event_banner_wide,locationlogo,clockLogo;
        TextView tv_event_title;
        TextView tv_event_date;
        TextView tv_event_location;

        LinearLayout bookNow_layout;
        LinearLayout tv_booknow;
        LinearLayout layout_one;


        public EventsListViewHolder(View itemView) {
            super(itemView);
            iv_event_banner = itemView.findViewById(R.id.iv_event_banner);
            iv_event_banner_wide = itemView.findViewById(R.id.iv_event_banner_wide);
            tv_event_title = itemView.findViewById(R.id.tv_event_title);
            tv_event_date = itemView.findViewById(R.id.tv_event_date);
            tv_event_location = itemView.findViewById(R.id.tv_event_location);
            bookNow_layout = itemView.findViewById(R.id.bookNow_layout);
            tv_booknow = itemView.findViewById(R.id.tv_booknow);
            layout_one = itemView.findViewById(R.id.layout_one);
            locationlogo = itemView.findViewById(R.id.Locationlogo);
            clockLogo = itemView.findViewById(R.id.clockLogo);
        }
    }

    private String capitalizeFirstLetter(String capString){
        StringBuffer capBuffer = new StringBuffer();
        Matcher capMatcher = Pattern.compile("([a-z])([a-z]*)", Pattern.CASE_INSENSITIVE).matcher(capString);
        while (capMatcher.find()){
            capMatcher.appendReplacement(capBuffer, capMatcher.group(1).toUpperCase() + capMatcher.group(2).toLowerCase());
        }

        return capMatcher.appendTail(capBuffer).toString();
    }
}

