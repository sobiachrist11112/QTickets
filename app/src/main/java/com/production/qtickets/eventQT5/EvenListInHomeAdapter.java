package com.production.qtickets.eventQT5;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.production.qtickets.R;
import com.production.qtickets.activity.ShowWebView;
import com.production.qtickets.activity.ShowWebView2;
import com.production.qtickets.eventDetailQT5.EventDetailQT5Activity;
import com.production.qtickets.model.AllEventQT5Data;
import com.production.qtickets.utils.AppConstants;
import com.production.qtickets.utils.SessionManager;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EvenListInHomeAdapter extends RecyclerView.Adapter<EvenListInHomeAdapter.EventsListViewHolder> {

    List<AllEventQT5Data> eventListArrayList;
    Context context;
    SessionManager sessionManager;

    public EvenListInHomeAdapter(Context context, List<AllEventQT5Data> eventListArrayList) {
        this.context = context;
        this.eventListArrayList = eventListArrayList;
        sessionManager = new SessionManager(context);

    }


    @Override
    public EvenListInHomeAdapter.EventsListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.events_list_item_qt5_row22, parent, false);
        return new EvenListInHomeAdapter.EventsListViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(EvenListInHomeAdapter.EventsListViewHolder holder, int position) {
        final AllEventQT5Data eventData = eventListArrayList.get(position);
        Glide.with(context).load(eventData.banner7).into(holder.iv_image);
        if (eventData.eventTitle != null) {
            String eventTitle = eventData.eventTitle.substring(0, 1).toUpperCase() + eventData.eventTitle.substring(1).toLowerCase();
            holder.tv_event_title.setText(eventTitle);
        }
        if (eventData.venue != null) {
            String capitalized = capitalizeFirstLetter(eventData.venue);

            Log.d("8Nov:",eventData.venue);

            holder.tv_event_location.setText(capitalized);
        }


        String date = "";
        //22/09/2022, 12:00:00 AM
        String startdate=eventData.startDate;
        String endDate=eventData.endDate;

        DateFormat originalFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
        DateFormat targetFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date startdatess = null,enddatess = null;
        try {
            startdatess = originalFormat.parse(String.valueOf(startdate));
            enddatess = originalFormat.parse(String.valueOf(endDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String formattedStartDate = targetFormat.format(startdatess);
        String formattedEndtDate = targetFormat.format(enddatess);
        if (formattedStartDate.equals(formattedEndtDate)) {
            String[] parts = formattedStartDate.split(", ");
            date = parts[0].toString() ;
        }
        else {

            if (formattedStartDate != null && !formattedStartDate.equals("")) {
                //  String[] parts = formattedStartDate.split(", ");
                date =formattedStartDate + " to ";
            }
            if (formattedEndtDate != null && !formattedEndtDate.equals("")) {
                //  String[] parts = formattedEndtDate.split(", ");
                date = date + formattedEndtDate;

            } else {
                if (date.contains(" to ")) date = date.replace(" to ", "");
            }

        }
        holder.tv_event_date.setText("" + date);
        holder.bookNow_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!eventData.isWebView) {
                    Intent intent = new Intent(context, EventDetailQT5Activity.class);
                    intent.putExtra(AppConstants.EVENT_ID, eventData.id);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                } else {
                    Bundle b = new Bundle();

                    b.putString("webViewURL", eventData.webViewUrl + "?webview=true&country="+sessionManager.getCountry());//10oct
                    b.putString("pagename", eventData.eventTitle);
                    Intent i = new Intent(context, ShowWebView2.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    i.putExtras(b);
                    context.startActivity(i);
//                    Bundle b = new Bundle();
//                    b.putString("url","https://developer.android.com/guide/webapps/webview");
//                    b.putString("pagename", eventData.eventTitle);
//                    Intent i = new Intent(context, EventWebView.class);
//                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                    i.putExtras(b);
//                    context.startActivity(i);

                    Log.d("webViewURL :", eventData.webViewUrl+"?webview=true&country="+ sessionManager.getCountry());

                }
            }
        });

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

        public EventsListViewHolder(View itemView) {
            super(itemView);
            iv_image = itemView.findViewById(R.id.iv_image);
            tv_event_title = itemView.findViewById(R.id.tv_event_title);
            tv_event_date = itemView.findViewById(R.id.tv_event_date);
            tv_event_location = itemView.findViewById(R.id.tv_event_location);
            bookNow_layout = itemView.findViewById(R.id.bookNow_layout);
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

