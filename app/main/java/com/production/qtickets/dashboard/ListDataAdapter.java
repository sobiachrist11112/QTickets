package com.production.qtickets.dashboard;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
import com.production.qtickets.events.EventDetails.EventDetailsActivity;
import com.production.qtickets.events.EventDetails.EventTicketsActivity;
import com.production.qtickets.events.EventDetails.EventWebviewActivity;
import com.production.qtickets.events.EventsListAdapter;
import com.production.qtickets.model.Data;
import com.production.qtickets.model.EventData;
import com.production.qtickets.model.MovieModel;
import com.production.qtickets.moviedetailes.MovieDetailsActivity;
import com.production.qtickets.moviedetailes.ShowTimingActivity;
import com.production.qtickets.moviedetailes.YoutubeActivity;
import com.production.qtickets.movies.MovieListAdapter;
import com.production.qtickets.utils.QTUtils;
import com.production.qtickets.utils.SessionManager;

import java.util.List;

public class ListDataAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_ONE = 1;
    private static final int TYPE_TWO = 2;
    List<Data> listDataArrayList;
    String type;
    Context ctx;
    int differenciate;
    SessionManager sessionManager;
    String commingsoon = "";
    String url, startDate, endDate, eventheading;


    public ListDataAdapter(List<Data> movieListArrayList, String type, Context ctx, int differenciate) {
        this.listDataArrayList = movieListArrayList;
        this.type = type;
        this.ctx = ctx;
        this.differenciate = differenciate;
    }

    public ListDataAdapter(List<Data> listDataArrayList, String type, Context ctx, int differenciate, String eventheading) {
        this.listDataArrayList = listDataArrayList;
        this.type = type;
        this.ctx = ctx;
        this.differenciate = differenciate;
        this.eventheading = eventheading;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ONE) {
            MovieViewHolder movieViewHolder;
            if (differenciate == 100) {
                View movieList = LayoutInflater.from(parent.getContext()).inflate(R.layout.movies_item_row, parent, false);
                movieViewHolder = new MovieViewHolder(movieList);
            } else {
                View movieList = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_items_row_with_vertical_scroll, parent, false);
                movieViewHolder = new MovieViewHolder(movieList);
            }
            return movieViewHolder;
        } else if (viewType == TYPE_TWO) {
            EventViewHolder eventViewHolder;
            if (differenciate == 100) {
                View eventsList = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_item_row, parent, false);
                eventViewHolder = new EventViewHolder(eventsList);
            } else {
                View eventsList = LayoutInflater.from(parent.getContext()).inflate(R.layout.events_list_item_row, parent, false);
                eventViewHolder = new EventViewHolder(eventsList);
            }
            return eventViewHolder;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case TYPE_ONE:
                movieList((MovieViewHolder) holder, position);
                break;
            case TYPE_TWO:
                eventList((EventViewHolder) holder, position);
                break;
            default:
                break;
        }
    }

    @Override
    public int getItemCount() {
        return listDataArrayList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (type.equals("movie")) {
            return TYPE_ONE;
        } else {
            return TYPE_TWO;
        }
    }

    class MovieViewHolder extends RecyclerView.ViewHolder {

        ImageView iv_movie_image, img_tic_watch;
        TextView tv_imdb_rating, txt_book;
        TextView tv_movie_name;
        TextView tv_movie_genre;
        LinearLayout tv_booknow;
        // View imdb_view;


        public MovieViewHolder(View itemView) {
            super(itemView);
            iv_movie_image = itemView.findViewById(R.id.iv_movie_image);
            //    tv_sensor_rating = itemView.findViewById(R.id.tv_sensor_rating);
            tv_imdb_rating = itemView.findViewById(R.id.tv_imdb_rating);
            tv_movie_name = itemView.findViewById(R.id.tv_movie_name);
            tv_movie_genre = itemView.findViewById(R.id.tv_movie_genre);
            tv_booknow = itemView.findViewById(R.id.tv_booknow);
            txt_book = itemView.findViewById(R.id.txt_book);
            img_tic_watch = itemView.findViewById(R.id.img_tic_watch);
            //   imdb_layout = itemView.findViewById(R.id.imdb_layout);
            //   pgLayout = itemView.findViewById(R.id.pgLayout);
            //imdb_view = itemView.findViewById(R.id.view);
        }
    }

    class EventViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_event_banner;
        ImageView iv_event_banner_wide, locationlogo, clockLogo;
        TextView tv_event_title;
        TextView tv_event_date;
        TextView tv_event_location;

        LinearLayout bookNow_layout;
        LinearLayout tv_booknow;
        LinearLayout layout_one;

        public EventViewHolder(View itemView) {
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

    public void movieList(MovieViewHolder holder, int pos) {
        Data mList = listDataArrayList.get(pos);
        sessionManager = new SessionManager(ctx);
        String eventname = mList.name.substring(0, 1).toUpperCase() + mList.name.substring(1).toLowerCase();
        holder.tv_movie_name.setText(eventname);

        if (TextUtils.isEmpty(mList.iMDBRating) || mList.iMDBRating.equalsIgnoreCase("N/A") || mList.iMDBRating.contains("0")) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMarginStart(10);
            // holder.pgLayout.setLayoutParams(params);
            //   holder.imdb_layout.setVisibility(View.GONE);
            //  holder.imdb_view.setVisibility(View.GONE);
        } else {
            //  holder.imdb_layout.setVisibility(View.VISIBLE);
            //      holder.imdb_view.setVisibility(View.VISIBLE);
            //   holder.tv_imdb_rating.setText(mList.iMDBRating + "/10");
        }

//        if (!TextUtils.isEmpty(mList.censor)){
//            holder.tv_sensor_rating.setText(mList.censor);
//        }else {
//            holder.tv_sensor_rating.setText(" ");
//        }

        if (!TextUtils.isEmpty(mList.genre)) {
            holder.tv_movie_genre.setText(mList.genre);
        } else {
            holder.tv_movie_genre.setText(mList.genre);
        }

        if (!TextUtils.isEmpty(mList.iphonethumb)) {
            Glide.with(ctx.getApplicationContext()).load(mList.iphonethumb)
                    .thumbnail(0.5f)
                    .into(holder.iv_movie_image);
        } else if (!TextUtils.isEmpty(mList.thumbnail)) {
            Glide.with(ctx.getApplicationContext()).load(mList.thumbnail)
                    .thumbnail(0.5f)
                    .into(holder.iv_movie_image);
        } else if (!TextUtils.isEmpty(mList.thumbURL)) {
            Glide.with(ctx.getApplicationContext()).load(mList.thumbURL)
                    .thumbnail(0.5f)
                    .into(holder.iv_movie_image);
        } else {
            Glide.with(ctx.getApplicationContext()).load(mList.thumbnail)
                    .thumbnail(0.5f)
                    .into(holder.iv_movie_image);
        }
        if (TextUtils.isEmpty(commingsoon)) {
            if (sessionManager.getCountryName().equals("Qatar")) {
                //    holder.txt_book.setText(ctx.getString(R.string.book));
                //     holder.img_tic_watch.setImageResource(R.drawable.ic_ticket);
                holder.iv_movie_image.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        sessionManager.setMovieId(mList.id);
                        Intent movieDetails = new Intent(ctx, MovieDetailsActivity.class);
                        ctx.startActivity(movieDetails);
                    }
                });
                holder.tv_booknow.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (!TextUtils.isEmpty(mList.ageRestrictRating)) {
                            sessionManager.setAgeRestrict_Censor(mList.ageRestrictRating, mList.censor);
                        } else {
                            sessionManager.setAgeRestrict_Censor(mList.ageRestrictRating, mList.censor);
                        }
                        if (!TextUtils.isEmpty(mList.ipadthumb)) {
                            url = mList.ipadthumb;
                        } else if (!TextUtils.isEmpty(mList.thumbURL)) {
                            url = mList.thumbURL;
                        } else if (!TextUtils.isEmpty(mList.iphonethumb)) {
                            url = mList.iphonethumb;
                        } else {
                            url = mList.thumbnail;
                        }
                        sessionManager.setMovieId(mList.id);
                        Bundle bundle = new Bundle();
                        bundle.putString("movie_title", mList.name);
                        bundle.putString("duration", mList.duration);
                        bundle.putString("movie_type", mList.movieType);
                        bundle.putString("movie_img_url", url);
                        bundle.putString("cencor", mList.censor);
                        Intent movieDetails = new Intent(ctx, ShowTimingActivity.class);
                        movieDetails.putExtras(bundle);
                        ctx.startActivity(movieDetails);
                    }
                });
            } else {
//
                if (sessionManager.getCountryName().equals("Dubai")) {
                    //   holder.img_tic_watch.setVisibility(View.GONE);
                   // holder.txt_book.setText(ctx.getString(R.string.book));
                } else {
                    //  holder.img_tic_watch.setVisibility(View.VISIBLE);
                    //   holder.img_tic_watch.setImageResource(R.drawable.ic_play_button);
                    //holder.txt_book.setText(ctx.getString(R.string.watch));
                }

                holder.tv_booknow.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (sessionManager.getCountryName().equals("Dubai")) {
                            String eventWebViewUrl = mList.clickURL + "/" + sessionManager.getCountryName().toLowerCase() + "/alone-english";
                            String event_title = mList.name;
                            Intent webViewIntent = new Intent(ctx, EventWebviewActivity.class);
                            webViewIntent.putExtra("webViewURL", eventWebViewUrl);
                            webViewIntent.putExtra("title", event_title);
                            webViewIntent.putExtra("pagename", event_title);
                            ctx.startActivity(webViewIntent);
                        } else {
                            if (!TextUtils.isEmpty(mList.trailerURL)) {
                                Bundle b = new Bundle();
                                b.putString("url", mList.trailerURL);
                                b.putString("tittle", mList.name);
                                Intent i = new Intent(ctx, YoutubeActivity.class);
                                i.putExtras(b);
                                ctx.startActivity(i);
                            } else {
                                QTUtils.showDialogbox(ctx, ctx.getString(R.string.notrailerurl));
                            }
                        }


                    }
                });
            }

        } else {
            holder.img_tic_watch.setImageResource(R.drawable.ic_play_button);
           // holder.txt_book.setText(ctx.getString(R.string.watch));
            holder.tv_booknow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!TextUtils.isEmpty(mList.trailerURL)) {
                        Bundle b = new Bundle();
                        b.putString("url", mList.trailerURL);
                        b.putString("tittle", mList.name);
                        Intent i = new Intent(ctx, YoutubeActivity.class);
                        i.putExtras(b);
                        ctx.startActivity(i);
                    } else {
                        QTUtils.showDialogbox(ctx, ctx.getString(R.string.notrailerurl));
                    }
                }
            });
        }
    }

    public void eventList(EventViewHolder holder, int pos) {
        final Data eventData = listDataArrayList.get(pos);
        sessionManager = new SessionManager(ctx);
        if (eventData.eventname != null) {
            String eventname = eventData.eventname.substring(0, 1).toUpperCase() + eventData.eventname.substring(1).toLowerCase();
            holder.tv_event_title.setText(eventname);
        }
        //leisure api
        /*if(eventData.tourName != null){
            holder.tv_event_title.setText(eventData.tourName);
        }*/

        if (!TextUtils.isEmpty(eventData.startDate)) {
            String[] parts = eventData.startDate.split(" ");
            startDate = parts[0];
        }
        if (!TextUtils.isEmpty(eventData.endDate)) {
            String[] parts = eventData.endDate.split(" ");
            endDate = parts[0];
        }

        if (!(TextUtils.isEmpty(startDate) && TextUtils.isEmpty(endDate))) {
            holder.tv_event_date.setText(startDate + " " + eventData.startTime + " " + "TO" + " \n" + endDate + " " + eventData.endTime);
        }
        if (eventData.duration != null) {

            holder.tv_event_date.setText(eventData.duration);
        }


        if (eventData.venue != null /*|| eventData.cityTourType != null*/) {
            holder.tv_event_location.setVisibility(View.VISIBLE);
            holder.locationlogo.setVisibility(View.VISIBLE);
            if (eventData.venue != null) {
                holder.tv_event_location.setText(eventData.venue);
            }
          /*  if(eventData.cityTourType != null){
                holder.tv_event_location.setText(eventData.cityTourType);
            }*/

        } else {
            holder.tv_event_location.setVisibility(View.GONE);
            holder.locationlogo.setVisibility(View.GONE);
        }


        if (differenciate == 200) {
            if (eventData.bannerURL != null) {
                Glide.with(ctx).load(eventData.bannerURL)
                        .thumbnail(0.5f)
                        .into(holder.iv_event_banner_wide);
            }
            //leisure api
          /*  if(eventData.imageTourUrls != null){
                Glide.with(ctx).load(eventData.imageTourUrls.imagePath)
                        .thumbnail(0.5f)
                        .into(holder.iv_event_banner_wide);
            }*/
            holder.iv_event_banner_wide.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (eventData.showBrowser != null) {

                        if (eventData.showBrowser.equals("1")) {

                            String eventWebViewUrl = eventData.eventUrl;
                            String event_title = eventData.eventname;
                            Intent webViewIntent = new Intent(ctx, EventWebviewActivity.class);
                            webViewIntent.putExtra("webViewURL", eventWebViewUrl);
                            webViewIntent.putExtra("title", event_title);
                            webViewIntent.putExtra("pagename", event_title);
                            ctx.startActivity(webViewIntent);

                        } else {
                            sessionManager.setEventId(eventData.eventid);
                            Intent eventIntent = new Intent(ctx, EventDetailsActivity.class);
                            eventIntent.putExtra("eventID", eventData.eventid);
                            eventIntent.putExtra("eventSource", "eventPage");
                            eventIntent.putExtra("eventBanner", eventData.mobileURL);
                            eventIntent.putExtra("eventName", eventData.eventname);
                            eventIntent.putExtra("eventStartDate", eventData.startDate);
                            eventIntent.putExtra("eventEndDate", eventData.endDate);
                            eventIntent.putExtra("eventStartTime", eventData.startTime);
                            eventIntent.putExtra("eventEndTime", eventData.endTime);
                            eventIntent.putExtra("eventVenue", eventData.venue);
                            eventIntent.putExtra("eventLatitude", eventData.latitude);
                            eventIntent.putExtra("eventLongitude", eventData.longitude);
                            eventIntent.putExtra("eventEmail", eventData.contactPersonEmail);
                            eventIntent.putExtra("eventDescription", eventData.eDescription);
                            eventIntent.putExtra("EventUrl", eventData.eventUrl);
                            ctx.startActivity(eventIntent);

                        }
                    } /*else {
                        if(eventData.imageTourUrls.tourUrl != null){
                            String eventWebViewUrl = eventData.imageTourUrls.tourUrl;
                            String event_title = eventData.tourName;
                            Intent webViewIntent = new Intent(ctx, EventWebviewActivity.class);
                            webViewIntent.putExtra("webViewURL", eventWebViewUrl);
                            webViewIntent.putExtra("title",event_title);
                            ctx.startActivity(webViewIntent);
                        }
                    }*/
                }
            });

            holder.bookNow_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (eventData.showBrowser != null) {
                       /* if(eventheading!= null && eventheading.equals("Free to go events")){
                            eventData.showBrowser="1";
                        }*/
                        if (eventData.showBrowser.equals("1")) {
                            String event_title = eventData.eventname;
                            String eventWebViewUrl = eventData.eventUrl;
                            Intent webViewIntent = new Intent(ctx, EventWebviewActivity.class);
                            webViewIntent.putExtra("webViewURL", eventWebViewUrl);
                            webViewIntent.putExtra("title", event_title);
                            webViewIntent.putExtra("pagename", event_title);
                            ctx.startActivity(webViewIntent);

                        } else {

                            Intent eventIntent = new Intent(ctx, EventTicketsActivity.class);
                            eventIntent.putExtra("eventID", eventData.eventid);
                            eventIntent.putExtra("eventSource", "eventPage");
                            eventIntent.putExtra("eventBanner", eventData.mobileURL);
                            eventIntent.putExtra("eventName", eventData.eventname);
                            eventIntent.putExtra("eventStartDate", eventData.startDate);
                            eventIntent.putExtra("eventEndDate", eventData.endDate);
                            eventIntent.putExtra("eventStartTime", eventData.startTime);
                            eventIntent.putExtra("eventEndTime", eventData.endTime);
                            eventIntent.putExtra("eventEmail", eventData.contactPersonEmail);
                            eventIntent.putExtra("eventVenue", eventData.venue);
                            eventIntent.putExtra("eventLatitude", eventData.latitude);
                            eventIntent.putExtra("eventLongitude", eventData.longitude);
                            eventIntent.putExtra("eventDescription", eventData.eDescription);
                            eventIntent.putExtra("EventUrl", eventData.eventUrl);
                            ctx.startActivity(eventIntent);

                        }
                    } /*else {
                        if(eventData.imageTourUrls.tourUrl != null){
                            String eventWebViewUrl = eventData.imageTourUrls.tourUrl;
                            String event_title = eventData.tourName;
                            Intent webViewIntent = new Intent(context, EventWebviewActivity.class);
                            webViewIntent.putExtra("webViewURL", eventWebViewUrl);
                            webViewIntent.putExtra("title",event_title);
                            context.startActivity(webViewIntent);
                        }
                    }*/


                }
            });

        } else {
            if (eventData.mobileURL != null) {
                Glide.with(ctx).load(eventData.mobileURL)
                        .thumbnail(0.5f)
                        .into(holder.iv_event_banner);
            }

            holder.tv_booknow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    /*if(eventheading!= null && eventheading.equals("Free to go events")){
                        eventData.showBrowser="1";
                    }*/

                    if (eventData.showBrowser.equals("1")) {
                        String event_title = eventData.eventname;
                        String eventWebViewUrl = eventData.eventUrl;
                        Intent webViewIntent = new Intent(ctx, EventWebviewActivity.class);
                        webViewIntent.putExtra("webViewURL", eventWebViewUrl);
                        webViewIntent.putExtra("title", event_title);
                        webViewIntent.putExtra("pagename", event_title);
                        ctx.startActivity(webViewIntent);

                    } else {

                        Intent eventIntent = new Intent(ctx, EventTicketsActivity.class);
                        eventIntent.putExtra("eventID", eventData.eventid);
                        eventIntent.putExtra("eventSource", "homePage");
                        eventIntent.putExtra("eventBanner", eventData.mobileURL);
                        eventIntent.putExtra("eventName", eventData.eventname);
                        eventIntent.putExtra("eventStartDate", eventData.startDate);
                        eventIntent.putExtra("eventEndDate", eventData.endDate);
                        eventIntent.putExtra("eventStartTime", eventData.startTime);
                        eventIntent.putExtra("eventEndTime", eventData.endTime);
                        eventIntent.putExtra("eventEmail", eventData.contactPersonEmail);
                        eventIntent.putExtra("eventVenue", eventData.venue);
                        eventIntent.putExtra("eventLatitude", eventData.latitude);
                        eventIntent.putExtra("eventLongitude", eventData.longitude);
                        eventIntent.putExtra("eventDescription", eventData.eDescription);
                        eventIntent.putExtra("EventUrl", eventData.eventUrl);
                        ctx.startActivity(eventIntent);

                    }
                }
            });

            holder.iv_event_banner.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (eventData.showBrowser.equals("1")) {
                        String event_title = eventData.eventname;
                        String eventWebViewUrl = eventData.eventUrl;
                        Intent webViewIntent = new Intent(ctx, EventWebviewActivity.class);
                        webViewIntent.putExtra("webViewURL", eventWebViewUrl);
                        webViewIntent.putExtra("title", event_title);
                        webViewIntent.putExtra("pagename", event_title);
                        ctx.startActivity(webViewIntent);

                    } else {
                        sessionManager.setEventId(eventData.eventid);
                        Intent eventIntent = new Intent(ctx, EventDetailsActivity.class);
                        eventIntent.putExtra("eventID", eventData.eventid);
                        eventIntent.putExtra("eventSource", "homePage");
                        eventIntent.putExtra("eventBanner", eventData.mobileURL);
                        eventIntent.putExtra("eventName", eventData.eventname);
                        eventIntent.putExtra("eventStartDate", eventData.startDate);
                        eventIntent.putExtra("eventEndDate", eventData.endDate);
                        eventIntent.putExtra("eventStartTime", eventData.startTime);
                        eventIntent.putExtra("eventEndTime", eventData.endTime);
                        eventIntent.putExtra("eventEmail", eventData.contactPersonEmail);
                        eventIntent.putExtra("eventVenue", eventData.venue);
                        eventIntent.putExtra("eventLatitude", eventData.latitude);
                        eventIntent.putExtra("eventLongitude", eventData.longitude);
                        eventIntent.putExtra("eventDescription", eventData.eDescription);
                        eventIntent.putExtra("EventUrl", eventData.eventUrl);
                        ctx.startActivity(eventIntent);

                    }
                }
            });
        }
    }
}
