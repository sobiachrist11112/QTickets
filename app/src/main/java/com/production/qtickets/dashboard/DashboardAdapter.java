package com.production.qtickets.dashboard;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.production.qtickets.R;
import com.production.qtickets.activity.MainActivity;
import com.production.qtickets.eventQT5.EventHomeActivity;
import com.production.qtickets.model.CustomModel;
import com.production.qtickets.model.Data;
import com.production.qtickets.model.ListData;

import java.util.ArrayList;
import java.util.List;

public class DashboardAdapter extends RecyclerView.Adapter<DashboardAdapter.MyViewHolder> {

    List<ListData> dLists;
    ArrayList<CustomModel> headers = new ArrayList<>();
    Context context;
    public static RecyclerView[] arrayRecycleView;
    public static TextView[] seeAllBtn;
    public static TextView[] viewAllBtn;
    ListDataAdapter adapter;
    String eventHeading;

    public DashboardAdapter(List<ListData> dList, ArrayList<CustomModel> headers, Context c) {
        this.dLists = dList;
        this.context = c;
        this.headers=headers;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.dashboard_detail_layout, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        ListData mlist = dLists.get(position);
        int dataList = mlist.data.size();
        arrayRecycleView = new RecyclerView[dLists.size()];
        seeAllBtn = new TextView[dLists.size()];
        viewAllBtn = new TextView[dLists.size()];
        holder.mainLayout.setPadding(0, (int) context.getResources().getDimension(R.dimen.ten), 0, 0);
        if (mlist.heading.equals("Movies")) {
        //    headers.add(mlist.heading);
            List<Data> movieModels = mlist.data;
            if(movieModels.size()>0){
                holder.heading.setText(mlist.heading);
                setRecyclerView(context, movieModels, holder.recyclerView_type, holder.getAdapterPosition(), mlist.type, holder.see_all, holder.view_all, mlist.heading);
                holder.mainLayout.setVisibility(View.VISIBLE);
            }else {
               holder.mainLayout.setVisibility(View.GONE);
            }
        } else if (mlist.heading.equals("Eventsss")) {
            holder.heading.setText(mlist.heading);
            if (mlist.data.size() > 0) {
              //  headers.add(mlist.heading);
                List<Data> movieModels = mlist.data;
                setRecyclerView(context, movieModels, holder.recyclerView_type, holder.getAdapterPosition(), mlist.type, holder.see_all, holder.view_all, mlist.heading);
            } else {
                holder.heading.setVisibility(View.GONE);
            }
        } else {
            holder.see_all.setVisibility(View.VISIBLE);
            holder.see_all.setVisibility(View.GONE);
        }
        if (mlist.data.size() >= 3) {
            holder.see_all.setVisibility(View.GONE);
            holder.view_all.setVisibility(View.VISIBLE);
            holder.see_all.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                  // String category_id = mlist.data.get(mlist.data.size()-1).categoryId;
                    int p = position;
                    Intent i = new Intent(context, MainActivity.class);
                    i.putExtra("categoryId","7");
                    i.putParcelableArrayListExtra("headerList",headers);
                    i.putExtra("position",p);
                    i.putExtra("ImageType","others");
                    context.startActivity(i);
                }
            });
            if (mlist.heading.equals("Movies")) {
                holder.see_all.setVisibility(View.GONE);
                holder.view_all.setVisibility(View.VISIBLE);
            } else {
                holder.see_all.setVisibility(View.GONE);
                holder.view_all.setVisibility(View.GONE);
            }
        } else {
            holder.see_all.setVisibility(View.GONE);
            holder.view_all.setVisibility(View.GONE);
        }


    }

    @Override
    public int getItemCount() {
        return dLists.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView heading, see_all, view_all;
        RecyclerView recyclerView_type;
        ConstraintLayout mainLayout;

        public MyViewHolder(View itemView) {
            super(itemView);
            heading = itemView.findViewById(R.id.heading);
            recyclerView_type = itemView.findViewById(R.id.recycler_type);
            see_all = itemView.findViewById(R.id.see_all);
            view_all = itemView.findViewById(R.id.view_all);
            mainLayout = itemView.findViewById(R.id.main_layout);

        }
    }

    public void setRecyclerView(Context context, List<Data> moviemodel, RecyclerView recyclerView, int pos, String type, TextView btn, TextView btnViewAll, String heading) {
        arrayRecycleView[pos] = recyclerView;
        seeAllBtn[pos] = btn;
        viewAllBtn[pos] = btnViewAll;
        arrayRecycleView[pos].setPadding(0, 20, 0, 0);
        arrayRecycleView[pos].setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        if (type.equals("movie")) {
            adapter = new ListDataAdapter(moviemodel, type, context, 100);
        } else {
            if (eventHeading != null && eventHeading.equals("Free to go events")) {
                adapter = new ListDataAdapter(moviemodel, type, context, 100, eventHeading);
            } else {
                adapter = new ListDataAdapter(moviemodel, type, context, 100);
            }

        }
        arrayRecycleView[pos].setAdapter(adapter);
        seeAllBtn[pos].setOnClickListener(v -> {
            int actualPos = 0;
            for (int i = 0; i < headers.size(); i++) {
                if (headers.get(i).getHeading() == heading) {
                    actualPos = i;
                    break;
                }
            }

            Intent i = new Intent(context, MainActivity.class);
            i.putExtra("categoryId", "15");
            i.putParcelableArrayListExtra("headerList", headers);
            i.putExtra("position", actualPos);
            i.putExtra("ImageType", "others");
            context.startActivity(i);
        });


        viewAllBtn[pos].setOnClickListener(v -> {
            int actualPos = 0;
            for (int i = 0; i < headers.size(); i++) {
                if (headers.get(i).getHeading() == heading) {
                    actualPos = i;
                    break;
                }
            }

            Intent intentssss = new Intent(context, MainActivity.class);
            intentssss.putExtra("categoryId", "15");
            intentssss.putParcelableArrayListExtra("headerList", headers);
            intentssss.putExtra("position", 0);
            intentssss.putExtra("ImageType", "others");
            context.startActivity(intentssss);

//            Intent i = new Intent(context, EventHomeActivity.class);
//            i.putExtra("categoryId", "15");
//            i.putStringArrayListExtra("headerList", headers);
//            i.putExtra("position", actualPos);
//            i.putExtra("ImageType", "others");
//            context.startActivity(i);
            return;

        });
    }
}
