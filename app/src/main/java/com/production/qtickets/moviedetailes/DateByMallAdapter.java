package com.production.qtickets.moviedetailes;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.production.qtickets.R;
import com.production.qtickets.model.DateModel;
import com.production.qtickets.model.ItemModel;
import com.production.qtickets.model.ShowDetailModel;
import com.production.qtickets.utils.Events;
import com.production.qtickets.utils.GlobalBus;
import com.production.qtickets.utils.QTUtils;
import com.production.qtickets.utils.RecyclerItemClickListener;
import com.production.qtickets.activity.ShowWebView;
import com.production.qtickets.movies.seatselection.SeatSelectionActivity;
import com.production.qtickets.novocinema.NovoTicketSelectionActivity;

import org.greenrobot.eventbus.Subscribe;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class DateByMallAdapter extends RecyclerView.Adapter<DateByMallAdapter.MyViewHolder> {

    private Context context;
    private List<ItemModel> nList;
    public static RecyclerView[] arrayRecycleView;
    public static TimeAdapter[] articleAdapter;
    private String totalItemCounts[], movie_title, ticket_count, duration, movie_type, movie_img_url, cencor, url;
    TimeAdapter adapter;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public RecyclerView topicRecycler;
        public TextView mall_name, location, txt_no_show;


        public MyViewHolder(View view) {
            super(view);
            topicRecycler = view.findViewById(R.id.recycler_malls);
            mall_name = view.findViewById(R.id.mall_name);
            location = view.findViewById(R.id.location);
            txt_no_show = view.findViewById(R.id.txt_no_show);
        }

    }


    public DateByMallAdapter(Context mContext, List<ItemModel> nList, String movie_title, String ticket_count, String duration, String movie_type, String movie_img_url, String url, String cencor) {
        GlobalBus.getBus().register(this);
        this.context = mContext;
        this.nList = nList;
        this.movie_title = movie_title;
        this.ticket_count = ticket_count;
        this.duration = duration;
        this.movie_type = movie_type;
        this.movie_img_url = movie_img_url;
        this.url = url;
        this.cencor = cencor;
    }

    public void addValuesAdapter(List<ItemModel> nList) {
        this.nList = nList;
        notifyDataSetChanged();
    }

    @Subscribe
    public void getMessage(Events.FragmentActivityMessage activityFragmentMessage) {
        ticket_count = activityFragmentMessage.getMessage();
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_movie_malls, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        ItemModel mList = nList.get(position);
        int articlecount = mList.showDetails.size();
        holder.mall_name.setText(mList.cinemaName);
        holder.location.setText(mList.address);
        totalItemCounts = new String[nList.size()];
        arrayRecycleView = new RecyclerView[nList.size()];
        articleAdapter = new TimeAdapter[nList.size()];
        if (mList.showDetails.size() == 0) {
            holder.location.setVisibility(View.GONE);
            holder.txt_no_show.setVisibility(View.VISIBLE);
        } else {
            holder.location.setVisibility(View.VISIBLE);
            holder.txt_no_show.setVisibility(View.GONE);
        }
        for (int i = 0; i < totalItemCounts.length; i++) {
            totalItemCounts[i] = "1";
        }
        for (int i = 0; i < articlecount; i++) {
            List<ShowDetailModel> showList = mList.showDetails;
            setRecyclerView(nList, showList, context, holder.topicRecycler, holder.getAdapterPosition(), movie_title);
        }
    }


    @Override
    public int getItemCount() {
        return nList.size();
    }

    Handler handler;


    public void setRecyclerView(List<ItemModel> mList, List<ShowDetailModel> sList, Context con, RecyclerView recyclerView, int pos, String movieTitle) {
        handler = new Handler();
        arrayRecycleView[pos] = recyclerView;
        arrayRecycleView[pos].setHasFixedSize(true);
        int numberOfColumns = 4;
        arrayRecycleView[pos].setLayoutManager(new GridLayoutManager(con, numberOfColumns));
        adapter = new TimeAdapter(context, sList, mList, movieTitle, ticket_count, duration, movie_type, movie_img_url, cencor);
        arrayRecycleView[pos].setAdapter(adapter);

    }


}