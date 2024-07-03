package com.production.qtickets.ticketbookingdetaile;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.production.qtickets.R;

import java.util.List;

/**
 * Created by Harsh on 6/8/2018.
 */
public class SeatAdapter extends RecyclerView.Adapter<SeatAdapter.ViewHolder> {

    List<String> mSelectedSeatsList;
    Context context;


    public SeatAdapter(List<String> mSelectedSeatsList, Context context) {
        this.context = context;
        this.mSelectedSeatsList = mSelectedSeatsList;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View eventlist = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_booking_seat, parent, false);
        ViewHolder ViewHolder = new ViewHolder(eventlist);

        return ViewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.tv_seat.setText(mSelectedSeatsList.get(position));
        }


    @Override
    public int getItemCount() {
        return mSelectedSeatsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        TextView tv_seat;


        public ViewHolder(View itemView) {
            super(itemView);
            tv_seat = itemView.findViewById(R.id.txt_seat);

        }
    }
}