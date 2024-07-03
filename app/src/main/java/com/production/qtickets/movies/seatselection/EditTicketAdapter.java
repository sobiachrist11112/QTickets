package com.production.qtickets.movies.seatselection;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import com.production.qtickets.R;


public class EditTicketAdapter extends RecyclerView.Adapter<EditTicketAdapter.MyViewHolder> {
    String[] nList;
    Context context;
    private int mSelectedItem=-1;
    private AdapterView.OnItemClickListener onItemClickListener;
    public EditTicketAdapter(String[] nList, Context context) {
        this.context=context;
        this.nList=nList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.edit_movie_date, parent, false);

        return new MyViewHolder(itemView,this);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.tvDate.setText(nList[position]);
        if(mSelectedItem==position) {
            holder.tvDate.setTextColor(context.getResources().getColor(R.color.colorBlack));
            holder.c2.setBackground(context.getResources().getDrawable(R.drawable.seat_time_background));
        }else {
            holder.tvDate.setTextColor(context.getResources().getColor(R.color.white));
            holder.c2.setBackground(context.getResources().getDrawable(R.drawable.transparent_background));
        }

    }

    @Override
    public int getItemCount() {
        return nList.length;
    }

    public void setOnItemClickListener(AdapterView.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void onItemHolderClick(MyViewHolder holder) {
        if (onItemClickListener != null)
            onItemClickListener.onItemClick(null, holder.c2, holder.getAdapterPosition(), holder.getItemId());


    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView tvDate;
       private EditTicketAdapter mAdapter;
       ConstraintLayout c2;
        public MyViewHolder(View itemView,final EditTicketAdapter mAdapter) {
            super(itemView);
            this.mAdapter = mAdapter;
            tvDate = itemView.findViewById(R.id.tv_date);
            c2 = itemView.findViewById(R.id.c2);
            c2.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            if (getAdapterPosition() == RecyclerView.NO_POSITION) return;
            // Updating old as well as new positions
            notifyItemChanged(mSelectedItem);
            mSelectedItem = getAdapterPosition();
            notifyItemRangeChanged(0,  nList.length);
            notifyItemChanged(mSelectedItem);
            mAdapter.onItemHolderClick(MyViewHolder.this);
        }
    }
}
