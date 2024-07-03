package com.production.qtickets.notifications;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.production.qtickets.R;
import com.production.qtickets.events.EventDetails.EventDetailsActivity;
import com.production.qtickets.events.EventDetails.EventWebviewActivity;
import com.production.qtickets.model.CustomModel;
import com.production.qtickets.model.NotificationModel;
import com.production.qtickets.moviedetailes.MovieDetailsActivity;
import com.production.qtickets.utils.SessionManager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.MyViewHolder> {

    //bind the views to the list of notifications
    List<NotificationModel> nList;
    Context context;
    String sourceType, resultDate;
    SessionManager sessionManager;
    ArrayList<CustomModel> str = new ArrayList<>();


    public NotificationAdapter(Context context, List<NotificationModel> nList, ArrayList<CustomModel> str) {
        this.context = context;
        this.nList = nList;
        this.str = str;


    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_notification, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final NotificationModel notificationModel = nList.get(position);
        if (notificationModel.pushNotificationShortMsg == "") {
            holder.tvGeneral.setText(notificationModel.SourceType);

        } else {
            holder.tvGeneral.setText(notificationModel.pushNotificationShortMsg);
        }

        try {
            dateConversion(notificationModel.pushNotificationInsertTime);
            holder.tvTime.setText(resultDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        holder.tv_msg.setText(notificationModel.pushNotificationMsg);
        sessionManager = new SessionManager(context);
        String imagePath = notificationModel.imgpath;
        if (imagePath.isEmpty()) {
            holder.banner_image.setVisibility(View.GONE);
        } else {

            holder.banner_image.setVisibility(View.VISIBLE);
            Glide.with(context).load(notificationModel.imgpath)
                    .thumbnail(0.5f)
                    .into(holder.banner_image);
        }

        holder.notif_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sourceType = notificationModel.SourceType;
                if (sourceType.equalsIgnoreCase("Movies")) {
                    sessionManager.setMovieId(notificationModel.SoruceID);
                    Intent i = new Intent(context, MovieDetailsActivity.class);
                    context.startActivity(i);
                } else if (sourceType.equalsIgnoreCase("Events")) {
                    if (nList.get(holder.getAdapterPosition()).show_browser.equals("1")) {
                        Intent webViewIntent = new Intent(context, EventWebviewActivity.class);
                        webViewIntent.putExtra("webViewURL", nList.get(holder.getAdapterPosition()).QTurl);
                        webViewIntent.putExtra("title", nList.get(holder.getAdapterPosition()).event_name);
                        context.startActivity(webViewIntent);
                    } else {
                        sessionManager.setEventId(nList.get(holder.getAdapterPosition()).event_id);
                        Intent eventIntent = new Intent(context, EventDetailsActivity.class);
                        eventIntent.putExtra("eventID", nList.get(holder.getAdapterPosition()).event_id);
                        eventIntent.putExtra("eventSource", "eventPage");
                        eventIntent.putExtra("eventBanner", nList.get(holder.getAdapterPosition()).imgpath);
                        eventIntent.putExtra("eventName", nList.get(holder.getAdapterPosition()).event_name);
                        eventIntent.putExtra("eventStartDate", nList.get(holder.getAdapterPosition()).event_startdate);
                        eventIntent.putExtra("eventEndDate", nList.get(holder.getAdapterPosition()).event_enddate);
                        eventIntent.putExtra("EventUrl", nList.get(holder.getAdapterPosition()).QTurl);
                        context.startActivity(eventIntent);
                    }
                   /* Bundle b = new Bundle();
                    b.putInt("position", 1);
                    b.putString("categoryId","15");
                    b.putStringArrayList("headerList",str);
                    Intent i = new Intent(context, MainActivity.class);
                    i.putExtras(b);
                    context.startActivity(i);*/

                }

            }
        });
    }


    @Override
    public int getItemCount() {
        return nList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.image)
        ImageView image;
        @BindView(R.id.tv_general)
        TextView tvGeneral;
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.tv_msg)
        TextView tv_msg;
        @BindView(R.id.banner_image)
        ImageView banner_image;
        @BindView(R.id.notif_layout)
        RelativeLayout notif_layout;
        @BindView(R.id.tv_booknow)
        LinearLayout tv_booknow;


        public MyViewHolder(View itemView) {
            super(itemView);
            // binding view
            ButterKnife.bind(this, itemView);
        }
    }

    public void dateConversion(String date) throws ParseException {
        SimpleDateFormat spf = new SimpleDateFormat("M/d/yyyy hh:mm:ss aaa");
        Date newDate = spf.parse(date);
        spf = new SimpleDateFormat("EEE, MMM d . hh:mm aaa");
        resultDate = spf.format(newDate);
        System.out.println(resultDate);
    }
}
