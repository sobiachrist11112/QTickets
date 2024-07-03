package com.production.qtickets.mybookings;

import android.content.Context;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.production.qtickets.R;
import com.production.qtickets.interfaces.APIClient;
import com.production.qtickets.model.GetHistoryData;
import com.production.qtickets.model.MovieModel;

import java.util.ArrayList;
import java.util.List;


public class MyBookingAdapter extends RecyclerView.Adapter<MyBookingAdapter.MyViewHolder> {

    //here we are listing up the booked movie details before going to make payment
    ArrayList<GetHistoryData> nList;
    ArrayList<MovieModel> mMovieList;
    Context context;
    int layout_diff = 0;


    public MyBookingAdapter(Context context, ArrayList<GetHistoryData> nList, ArrayList<MovieModel> mMovieList, int layout_diff) {
        this.context = context;
        this.nList = nList;
        this.layout_diff = layout_diff;
        this.mMovieList = mMovieList;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (layout_diff == 100) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_myevent_booking, parent, false);

            return new MyViewHolder(itemView);

        } else {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_mymovie_booking, parent, false);

            return new MyViewHolder(itemView);
        }
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        GetHistoryData getHistoryData = nList.get(holder.getAdapterPosition());

        if (getHistoryData != null) {

            if (layout_diff == 100) {
                Glide.with(context).load(getHistoryData.banner)
                        .thumbnail(0.5f)
                        .into(holder.iv_eventbanner);
                holder.tv_eventorderid.setText(getHistoryData.orderId);
                holder.tv_eventname.setText(getHistoryData.eventName);
                holder.tv_eventvenue.setText(getHistoryData.venueName);
                holder.tv_eventdate.setText(getHistoryData.eventDate);
                holder.tv_tickettype.setText(getHistoryData.ticketDetails);
                if (getHistoryData.currencyCode != null) {
                    holder.tv_totalamount.setText(getHistoryData.totalAmt + " " + getHistoryData.currencyCode);
                } else {
                    holder.tv_totalamount.setText(getHistoryData.totalAmt + " QAR");
                }

            } else {
                holder.tv_movie_name.setText(mMovieList.get(holder.getAdapterPosition()).movie);
                holder.tv_code.setText(mMovieList.get(holder.getAdapterPosition()).confirmationCode);
                holder.tv_theater_name.setText(mMovieList.get(holder.getAdapterPosition()).theater);
                holder.tv_cost.setText(mMovieList.get(holder.getAdapterPosition()).totalCost + "QAR");
                holder.tv_movie_date.setText(mMovieList.get(holder.getAdapterPosition()).bookedtime);
                holder.tv_movie_seats.setText(mMovieList.get(holder.getAdapterPosition()).seatsCount);
                Glide.with(context).load(mMovieList.get(holder.getAdapterPosition()).movieImageURL)
                        .thumbnail(0.5f)
                        .into(holder.iv_movie_image);
            }

            holder.cv_eventslist.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String pkID = String.valueOf(nList.get(holder.getAdapterPosition()).pkId);
                    String ticks = String.valueOf(nList.get(holder.getAdapterPosition()).ticks);
                    Log.d("19sep", "pkid " + pkID);
                    Log.d("19sep", "ticks " + ticks);
                    Intent httpIntent = new Intent(Intent.ACTION_VIEW);
                    httpIntent.setData(Uri.parse(APIClient.PAYMENT_URL + "/Order/PrintTicket?pkId=" + pkID + "&ticketId=" + ticks));
                    context.startActivity(httpIntent);
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        if (layout_diff == 100) {
            return nList.size();
        } else if (layout_diff == 200) {
            return mMovieList.size();
        }
        return 0;

    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        // for events
        ImageView iv_eventbanner;
        TextView tv_eventorderid, tv_eventname, tv_eventvenue, tv_eventdate, tv_tickettype, tv_totalamount;
        // for events
        CardView cv_eventslist;
        ImageView iv_movie_image;
        TextView tv_movie_name;
        TextView tv_code, tv_movie_seats, tv_cost;
        TextView tv_theater_name, tv_movie_date;

        public MyViewHolder(View itemView) {
            super(itemView);
            iv_eventbanner = itemView.findViewById(R.id.iv_eventbanner);
            tv_eventorderid = itemView.findViewById(R.id.tv_eventorderid);
            tv_eventname = itemView.findViewById(R.id.tv_eventname);
            tv_eventvenue = itemView.findViewById(R.id.tv_eventvenue);
            tv_eventdate = itemView.findViewById(R.id.tv_eventdate);
            tv_tickettype = itemView.findViewById(R.id.tv_tickettype);
            tv_totalamount = itemView.findViewById(R.id.tv_totalamount);
            cv_eventslist = itemView.findViewById(R.id.cv_eventslist);
            iv_movie_image = itemView.findViewById(R.id.iv_movie_image);
            tv_movie_name = itemView.findViewById(R.id.tv_movie_name);
            tv_code = itemView.findViewById(R.id.tv_code);
            tv_theater_name = itemView.findViewById(R.id.tv_theater_name);
            tv_cost = itemView.findViewById(R.id.tv_cost);
            tv_movie_date = itemView.findViewById(R.id.tv_movie_date);
            tv_movie_seats = itemView.findViewById(R.id.tv_movie_seats);

        }
    }
}