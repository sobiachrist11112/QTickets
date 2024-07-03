package com.production.qtickets.adapters;

import android.content.Context;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.production.qtickets.R;
import com.production.qtickets.utils.Events;
import com.production.qtickets.utils.GlobalBus;
import com.production.qtickets.model.MovieModel;

import java.util.List;



/**
 * Created by Harsh on 5/24/2018.
 */
public class CinemaFilterAdapter extends RecyclerView.Adapter<CinemaFilterAdapter.FilterViewHolder> {

    // in this Class we will list the list of cinemas in which movies are available, based on the list we can select one among that
    // we can filter the movies based on the movie the user selected.
    List<MovieModel> cinema;
    Context context;
    String txt_cinema, txt_sel_cinema,txt_id;


    public CinemaFilterAdapter( List<MovieModel> cinema, Context context, String txt_sel_cinema) {
        this.context = context;
        this.cinema = cinema;
        this.txt_sel_cinema = txt_sel_cinema;
    }

    @Override
    public FilterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View eventlist = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_filter_layout, parent, false);
        FilterViewHolder filterViewHolder = new FilterViewHolder(eventlist);

        return filterViewHolder;
    }

    @Override
    public void onBindViewHolder(final FilterViewHolder holder, final int position) {
        final MovieModel mList = cinema.get(position);
        holder.tv_laung_name.setText(mList.name);
        if(cinema.get(position).id.equals("-1")){
            holder.tv_laung_name.setVisibility(View.VISIBLE);
        }else {
            holder.tv_laung_name.setVisibility(View.VISIBLE);
        }
        if (!TextUtils.isEmpty(txt_sel_cinema)) {
            if (txt_sel_cinema.equals(mList.name)) {
                holder.im_tick.setVisibility(View.VISIBLE);
            } else {
                holder.im_tick.setVisibility(View.GONE);
            }
        }
        holder.filter_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.im_tick.setVisibility(View.VISIBLE);
                txt_cinema = mList.name;
                txt_id = mList.id;
                sendMessageToActivity(txt_cinema,txt_id);
            }
        });

    }

    public void sendMessageToActivity(String txt_cinema,String txt_id) {
        Events.ActivityFragmentMessage activityFragmentMessageEvent =
                new Events.ActivityFragmentMessage(txt_cinema,txt_id);
        GlobalBus.getBus().post(activityFragmentMessageEvent);
    }

    @Override
    public int getItemCount() {
        return cinema.size();
    }

    public class FilterViewHolder extends RecyclerView.ViewHolder {

        ImageView im_tick;
        TextView tv_laung_name;
        ConstraintLayout filter_layout;

        public FilterViewHolder(View itemView) {
            super(itemView);
            tv_laung_name = itemView.findViewById(R.id.txt_laung);
            im_tick = itemView.findViewById(R.id.img_tick);
            filter_layout = itemView.findViewById(R.id.filter_layout);

        }
    }
}