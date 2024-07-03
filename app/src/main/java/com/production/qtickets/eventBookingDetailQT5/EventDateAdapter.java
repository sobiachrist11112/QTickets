package com.production.qtickets.eventBookingDetailQT5;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.production.qtickets.R;
import com.production.qtickets.model.EventBookingQT5Date;
import com.production.qtickets.utils.DateTimeUtils;
import com.production.qtickets.utils.OnDateSelectListener;

import java.util.List;

public class EventDateAdapter extends RecyclerView.Adapter<EventDateAdapter.MyViewHolder> {

    List<EventBookingQT5Date> eventBookingQT5Dates;
    Context context;
    int selectedDateID = 0;
    String selectedDate = "";
    int selectedDatePosition = 0;
    OnDateSelectListener onDateSelectListener;
    Boolean isDayPass = false;

    public EventDateAdapter(List<EventBookingQT5Date> eventBookingQT5Dates, Context context, OnDateSelectListener onDateSelectListener, int selected_postion, Boolean isDayPass) {
        this.eventBookingQT5Dates = eventBookingQT5Dates;
        this.context = context;
        this.onDateSelectListener = onDateSelectListener;
        this.selectedDatePosition = selected_postion;
        this.isDayPass = isDayPass;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.event_date_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        EventBookingQT5Date data = eventBookingQT5Dates.get(position);
        String[] strDate = data.date.split("T");
        holder.text_date.setText("" + DateTimeUtils.getEventDate(strDate[0]));
        holder.text_day.setText("" + DateTimeUtils.getDayName(strDate[0]));

        if (isDayPass) {
            if (position == 0) {
                holder.lay_date.setBackgroundResource(R.drawable.drawable_unselected_lightgreycurve);
                selectedDateID = data.id;
                selectedDate = data.date;
              //  onDateSelectListener.onClickDate(holder.itemView,data.date,false);
            }
        }else {
            if (selectedDatePosition == position){
                selectedDateID = data.id;
                selectedDate = data.date;
                onDateSelectListener.onClickDate(holder.itemView,data.date,false);
                holder.lay_date.setBackgroundResource(R.drawable.selected_tab_with_corners);
            }else {
                holder.lay_date.setBackgroundResource(R.drawable.unselected_tab_with_corners);
            }
        }

        holder.itemView.setTag(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isDayPass=false;
                int itemPosition = (int) view.getTag();
                selectedDateID = data.id;
                selectedDate = data.date;
                selectedDatePosition = itemPosition;
                notifyDataSetChanged();
            }
        });

    }

    public int getSelectedDateID() {
        return selectedDateID;
    }

    public String getSelectedDate() {
        return selectedDate;
    }

    @Override
    public int getItemCount() {
        return eventBookingQT5Dates.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView text_date;
        TextView text_day;
        RelativeLayout lay_date;

        public MyViewHolder(View itemView) {
            super(itemView);
            text_date = (TextView) itemView.findViewById(R.id.text_date);
            text_day = (TextView) itemView.findViewById(R.id.text_day);
            lay_date = (RelativeLayout) itemView.findViewById(R.id.lay_date);

        }
    }

}
