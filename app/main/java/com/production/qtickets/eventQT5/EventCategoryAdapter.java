package com.production.qtickets.eventQT5;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.production.qtickets.R;
import com.production.qtickets.model.AllCategoryQT5Data;

import java.util.ArrayList;

public class EventCategoryAdapter extends RecyclerView.Adapter<EventCategoryAdapter.MyViewHodler> {

    Context context;
    ArrayList<AllCategoryQT5Data> mCategoryList;
    public int selectedPosition  = -1;
    CategoryItemClickListener listener;


    public void resetCategory(Integer position){
        selectedPosition  = position;
        notifyDataSetChanged();
    }

    public EventCategoryAdapter(Context context, ArrayList<AllCategoryQT5Data> mCategoryList,CategoryItemClickListener listeners) {
        this.context = context;
        this.mCategoryList = mCategoryList;
        this.listener=listeners;
    }

    @NonNull
    @Override
    public MyViewHodler onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.item_eventcategorylisting, viewGroup, false);
        return new EventCategoryAdapter.MyViewHodler(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHodler myViewHodler, int i) {
        if(selectedPosition == i){
            myViewHodler.iv_selectcat.setVisibility(View.VISIBLE);
        }else {
            myViewHodler.iv_selectcat.setVisibility(View.GONE);
        }
        myViewHodler.tv_categoryname.setText(mCategoryList.get(i).name);
        myViewHodler.ll_categoryroot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedPosition = myViewHodler.getAdapterPosition();
                listener.onClick(i, String.valueOf(mCategoryList.get(i).id),mCategoryList.get(i).name);
               notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mCategoryList.size();
    }

    public class MyViewHodler extends RecyclerView.ViewHolder {
        TextView tv_categoryname;
        ImageView iv_selectcat;
        LinearLayout ll_categoryroot;

        public MyViewHodler(@NonNull View itemView) {
            super(itemView);
            tv_categoryname = itemView.findViewById(R.id.tv_categoryname);
            ll_categoryroot = itemView.findViewById(R.id.ll_categoryroot);
            iv_selectcat = itemView.findViewById(R.id.iv_selectcat);
        }
    }
}
