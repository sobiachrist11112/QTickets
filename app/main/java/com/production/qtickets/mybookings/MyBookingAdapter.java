package com.production.qtickets.mybookings;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.production.qtickets.R;
import com.production.qtickets.model.GetHistoryData;
import com.production.qtickets.model.MovieModel;

import java.util.ArrayList;
import java.util.List;



public class MyBookingAdapter extends RecyclerView.Adapter<MyBookingAdapter.MyViewHolder> {

    //here we are listing up the booked movie details before going to make payment
    ArrayList<GetHistoryData> nList;
    ArrayList<MovieModel> mMovieList;
    Context context;
    int layout_diff=0;


    public MyBookingAdapter(Context context, ArrayList<GetHistoryData> nList,ArrayList<MovieModel> mMovieList, int layout_diff){
        this.context = context;
        this.nList = nList;
        this.layout_diff = layout_diff;
        this.mMovieList=mMovieList;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(layout_diff==100){
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_myevent_booking, parent, false);

            return new MyViewHolder(itemView);

        }
        else {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_mymovie_booking, parent, false);

            return new MyViewHolder(itemView);
        }
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        GetHistoryData getHistoryData=nList.get(position);
        // layout:100 mean Event
        // layout:200 mean Movies
        ImageView iv_eventbanner;
        TextView tv_eventorderid,tv_eventname,tv_eventvenue,tv_eventdate,tv_tickettype,tv_totalamount;

        if(layout_diff==100){
             Glide.with(context).load(getHistoryData.banner)
                    .thumbnail(0.5f)
                    .into(holder.iv_eventbanner);
            holder.tv_eventorderid.setText(getHistoryData.orderId);
            holder.tv_eventname.setText(getHistoryData.eventName);
            holder.tv_eventvenue.setText(getHistoryData.venueName);
            holder.tv_eventdate.setText(getHistoryData.eventDate);
            holder.tv_tickettype.setText(getHistoryData.ticketDetails);
            holder.tv_totalamount.setText(getHistoryData.totalAmt + " QAR");
//            holder.tv_movie_name.setText(getHistoryData.eventName);
//            holder.tv_code.setText(getHistoryData.pkId.toString());
//            holder.tv_theater_name.setText(getHistoryData.venueName);
//            holder.tv_cost.setText(getHistoryData.totalAmt+"QR");
//            holder.tv_movie_date.setText(getHistoryData.eventDate);
//          //  holder.tv_movie_seats.setText(getHistoryData.seatsCount+"   "+"Seats:"+"  "+myBookingsModel.seats);
//            Glide.with(context).load(getHistoryData.banner)
//                    .thumbnail(0.5f)
//                    .into(holder.iv_movie_image);

        }
        else {
//            holder.tv_movie_name.setText(mMovieList.get(position).movie);
//            holder.tv_code.setText(mMovieList.get(position).id);
//            holder.tv_theater_name.setText(mMovieList.get(position).theater);
//            //holder.tv_cost.setText(mMovieList.get(position).p+ "QR");
//            holder.tv_movie_date.setText(getHistoryData.eventDate);
//           // holder.tv_movie_seats.setText(myBookingsModel.seatsCount);
//            Glide.with(context).load(getHistoryData.banner)
//                    .thumbnail(0.5f)
//                    .into(holder.iv_movie_image);

        }
    }

    @Override
    public int getItemCount() {
        int listcout=0;
        if(layout_diff==100){
            listcout=nList.size();
        }else  if(layout_diff==200){
            listcout=mMovieList.size();
        }
        return listcout;

    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        // for events
         ImageView iv_eventbanner;
         TextView tv_eventorderid,tv_eventname,tv_eventvenue,tv_eventdate,tv_tickettype,tv_totalamount;


        // for events




        public MyViewHolder(View itemView) {
            super(itemView);
            iv_eventbanner=itemView.findViewById(R.id.iv_eventbanner);
            tv_eventorderid=itemView.findViewById(R.id.tv_eventorderid);
            tv_eventname=itemView.findViewById(R.id.tv_eventname);
            tv_eventvenue=itemView.findViewById(R.id.tv_eventvenue);
            tv_eventdate=itemView.findViewById(R.id.tv_eventdate);
            tv_tickettype=itemView.findViewById(R.id.tv_tickettype);
            tv_totalamount=itemView.findViewById(R.id.tv_totalamount);


//            iv_movie_image = itemView.findViewById(R.id.iv_movie_image);
//            tv_movie_name = itemView.findViewById(R.id.tv_movie_name);
//            tv_code = itemView.findViewById(R.id.tv_code);
//            tv_theater_name = itemView.findViewById(R.id.tv_theater_name);
//            tv_cost = itemView.findViewById(R.id.tv_cost);
//            tv_movie_date = itemView.findViewById(R.id.tv_movie_date);
//            tv_movie_seats = itemView.findViewById(R.id.tv_movie_seats);
        }
    }
}