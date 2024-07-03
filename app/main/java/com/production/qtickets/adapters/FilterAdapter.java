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

import java.util.ArrayList;

/**
 * Created by Harsh on 5/10/2018.
 */
public class FilterAdapter extends RecyclerView.Adapter<FilterAdapter.FilterViewHolder> {

    // in this class we are going to filter the movies based on the langauges, it will allow the user to select one language among the
    //list, result will be based on the language selected.
    ArrayList<String> launguage;
    Context context;
    String txt_launguage,txt_sel_launguage,txt_id="";

    public FilterAdapter(ArrayList<String> launguage, Context context, String txt_sel_launguage) {
        this.context = context;
        this.launguage = launguage;
        this.txt_sel_launguage = txt_sel_launguage;
    }

    @Override
    public FilterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View eventlist = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_filter_layout, parent, false);
        FilterViewHolder filterViewHolder = new FilterViewHolder(eventlist);

        return filterViewHolder;
    }

    @Override
    public void onBindViewHolder(final FilterViewHolder holder, final int position) {
        holder.tv_laung_name.setText(launguage.get(position));
        if(!TextUtils.isEmpty(txt_sel_launguage)){
            if(txt_sel_launguage.equals(launguage.get(position))) {
                holder.im_tick.setVisibility(View.VISIBLE);
            }else {
                holder.im_tick.setVisibility(View.GONE);
            }
        }
        holder.filter_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.im_tick.setVisibility(View.VISIBLE);
                txt_launguage = launguage.get(position);
                sendMessageToActivity(txt_launguage,txt_id);
            }
        });

        }
    public void sendMessageToActivity(String txt_launguage,String txt_id) {
    Events.ActivityFragmentMessage activityFragmentMessageEvent =
                new Events.ActivityFragmentMessage(txt_launguage, txt_id);
        GlobalBus.getBus().post(activityFragmentMessageEvent);
    }

    @Override
    public int getItemCount() {
        return launguage.size();
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
