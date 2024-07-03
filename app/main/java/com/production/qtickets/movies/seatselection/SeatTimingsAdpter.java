package com.production.qtickets.movies.seatselection;

import android.content.Context;
import android.content.DialogInterface;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.production.qtickets.R;
import com.production.qtickets.model.ShowDetailModel;

import java.util.List;



public class SeatTimingsAdpter extends RecyclerView.Adapter<SeatTimingsAdpter.ViewHolder> {
    private AdapterView.OnItemClickListener onItemClickListener;
    List<ShowDetailModel> timeList;
    private Context context;
    private String result_time = "";
    private String show_time = "";
    public int mSelectedItem;

    public SeatTimingsAdpter(Context context, List<ShowDetailModel> timeList, int show_time) {
        this.timeList = timeList;
        this.context = context;
        mSelectedItem= show_time;

    }

    private void getPostion(List<ShowDetailModel> timeList) {
        for (int i=0;i<timeList.size();i++) {
            if(timeList.get(i).showTime.equals(show_time)) {
               int m=timeList.get(i).showTime.indexOf(show_time);
               mSelectedItem=m;
            }

        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie_show_timimgs, parent, false);
        return new ViewHolder(view, this);
    }

    public void onBindViewHolder(final ViewHolder holder, int position) {
        final ShowDetailModel sList = timeList.get(position);

//        try {
//            if (!TextUtils.isEmpty(sList.showTime)) {
//                timeconvertionMethod(sList.showTime);
//                holder.txt_time.setText(result_time);
//            }
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
        holder.txt_time.setText(sList.showTime);
//        if (sList.showStatus.equals("1")) {
//            holder.txt_time.setVisibility(View.VISIBLE);
//            holder.txt_time.setBackground(context.getResources().getDrawable(R.drawable.available_round));
//        }
//        else if (sList.showStatus.equals("2")||sList.showStatus.equals("4")) {
//           // mSelectedItem=mSelectedItem+1;
//            holder.txt_time.setVisibility(View.GONE);
//        } else if (sList.showStatus.equals("3")) {
//            holder.txt_time.setVisibility(View.VISIBLE);
//            holder.txt_time.setBackground(context.getResources().getDrawable(R.drawable.filling_fast_round));
//        }
        if (sList.showStatus.equals("1")) {
            holder.txt_time.setVisibility(View.VISIBLE);
            double amount = Double.parseDouble(sList.totalSeats);
            double res = (amount / 100.0f) * 20;
            // Toast.makeText(context, ""+res, Toast.LENGTH_SHORT).show();
            if (res > Double.parseDouble(sList.seatsAvailable)) {
                holder.txt_time.setBackground(context.getResources().getDrawable(R.drawable.filling_fast_round));
                holder.txt_time.setTextColor(context.getResources().getColor(R.color.colorWhite));
            } else {
                holder.txt_time.setBackground(context.getResources().getDrawable(R.drawable.available_round));
                holder.txt_time.setTextColor(context.getResources().getColor(R.color.colorWhite));
            }
        } else if (sList.showStatus.equals("2")) {
            holder.txt_time.setVisibility(View.GONE);
            holder.txt_time.setBackground(context.getResources().getDrawable(R.drawable.sold_out_round_back));
            holder.txt_time.setTextColor(context.getResources().getColor(R.color.colorBlack));
        } else if (sList.showStatus.equals("3")) {
            holder.txt_time.setVisibility(View.VISIBLE);
            //midnight show
            holder.txt_time.setBackground(context.getResources().getDrawable(R.drawable.midnight_round));
            holder.txt_time.setTextColor(context.getResources().getColor(R.color.colorWhite));
        } else if (sList.showStatus.equals("4")) {
            holder.txt_time.setVisibility(View.GONE);
            holder.txt_time.setBackground(context.getResources().getDrawable(R.drawable.sold_out_round_back));
            holder.txt_time.setTextColor(context.getResources().getColor(R.color.colorBlack));
            holder.txt_time.setTextColor(context.getResources().getColor(R.color.colorWhite));
        }
        if(mSelectedItem==position){
            holder.txt_time.setBackground(context.getResources().getDrawable(R.drawable.seat_time_background) );
            holder.txt_time.setTextColor(context.getResources().getColor(R.color.colorBlack));
        }else {
            holder.txt_time.setTextColor(context.getResources().getColor(R.color.white));
        }

       /*if (show_time.equalsIgnoreCase(sList.showTime)) {
            holder.txt_time.setBackground(context.getResources().getDrawable(R.drawable.seat_time_background));
            holder.txt_time.setTextColor(context.getResources().getColor(R.color.white));
        }*/
       /* else{
            holder.txt_time.setBackground(context.getResources().getDrawable(R.drawable.available_round));
            holder.txt_time.setTextColor(context.getResources().getColor(R.color.black));
        }*/
    }

    @Override
    public int getItemCount() {
        return timeList.size();
    }

    public void setOnItemClickListener(AdapterView.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void onItemHolderClick(ViewHolder holder, int position) {
        try {
            if (onItemClickListener != null)
                onItemClickListener.onItemClick(null, holder.c1, holder.getAdapterPosition(), holder.getItemId());
        }catch (Exception e){
            e.printStackTrace();
        }


    }

    private void midnightdialog(final ViewHolder holder, final int adapterPosition, final SeatTimingsAdpter mAdapter) {
        android.app.AlertDialog mid_dialog = new android.app.AlertDialog.Builder(context)
                .setTitle("NOTE")
                .setMessage(context.getString(R.string.note))
                .setPositiveButton("Ok",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // Updating old as well as new positions
                                notifyItemChanged(mSelectedItem);
                                mSelectedItem = adapterPosition;
                                notifyItemRangeChanged(0, timeList.size());
                                notifyItemChanged(mSelectedItem);
                                try {
                                    mAdapter.onItemHolderClick(holder, mSelectedItem);
                                }catch (Exception e){
                                    e.printStackTrace();
                                }
                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).create();

        mid_dialog.show();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        View view;
        TextView txt_time;
        RelativeLayout c1;
        private SeatTimingsAdpter mAdapter;

        public ViewHolder(View v, final SeatTimingsAdpter mAdapter) {
            super(v);
            view = v;
            this.mAdapter = mAdapter;
            txt_time = v.findViewById(R.id.txt_time);
            c1 = v.findViewById(R.id.c1);
            c1.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            try {
                if (getAdapterPosition() == RecyclerView.NO_POSITION) return;
                if (timeList.get(getAdapterPosition()).showStatus.equals("3")) {
                    midnightdialog(ViewHolder.this, getAdapterPosition(), mAdapter);
                } else {
                    // Updating old as well as new positions
                    notifyItemChanged(mSelectedItem);
                    mSelectedItem = getAdapterPosition();
                    notifyItemRangeChanged(0, timeList.size());
                    notifyItemChanged(mSelectedItem);
                    mAdapter.onItemHolderClick(ViewHolder.this, mSelectedItem);
                }
            }catch (Exception e){
                e.printStackTrace();
            }

        }
    }
}
