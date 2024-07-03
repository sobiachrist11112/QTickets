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
 * Created by Harsh on 7/30/2018.
 */
public class TicketInfoAdapter extends RecyclerView.Adapter<TicketInfoAdapter.ViewHolder> {

    List<String> mSelectedSeatsList;
    Context context;


    public TicketInfoAdapter(List<String> mSelectedSeatsList, Context context) {
        this.context = context;
        this.mSelectedSeatsList = mSelectedSeatsList;

    }

    @Override
    public TicketInfoAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View eventlist = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ticket_info_adapter, parent, false);
        TicketInfoAdapter.ViewHolder ViewHolder = new TicketInfoAdapter.ViewHolder(eventlist);

        return ViewHolder;
    }

    @Override
    public void onBindViewHolder(final TicketInfoAdapter.ViewHolder holder, final int position) {
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