package com.production.qtickets.dashboard;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.production.qtickets.R;
import com.production.qtickets.activity.ContactUsActivity;
import com.production.qtickets.activity.MainActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class headerAdapter extends RecyclerView.Adapter<headerAdapter.myViewHolder> {
    ArrayList<String> str;
    Context ctx;

    public headerAdapter(ArrayList<String> str, Context ctx) {
        this.str = str;
        this.ctx = ctx;
    }

    @Override
    public myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.heading_layout,parent,false);
        return new myViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(myViewHolder holder, int position) {
        holder.header.setText(str.get(position));
        holder.header.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int p = position;
                Intent i = new Intent(ctx, MainActivity.class);
                i.putExtra("categoryId","15");
                i.putStringArrayListExtra("headerList",str);
                i.putExtra("ImageType","others");
                i.putExtra("position",p);
                ctx.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return str.size();
    }

    public class myViewHolder extends RecyclerView.ViewHolder{
       TextView header;

        public myViewHolder(View itemView) {
            super(itemView);
            header = itemView.findViewById(R.id.l1);
        }
    }
}
