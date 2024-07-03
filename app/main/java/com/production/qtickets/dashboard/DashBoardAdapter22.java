package com.production.qtickets.dashboard;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.production.qtickets.R;
import com.production.qtickets.activity.MainActivity;
import com.production.qtickets.model.AllEventQT5Data;
import com.production.qtickets.model.Data;
import com.production.qtickets.model.ListData;

import java.util.ArrayList;
import java.util.List;

public class DashBoardAdapter22  extends RecyclerView.Adapter<DashBoardAdapter22.MyViewHolder> {

    List<ListData> dLists;
    ArrayList<String> headers = new ArrayList<>();
    Context context;
    public static RecyclerView[] arrayRecycleView;
    public static TextView[] seeAllBtn;
    public static TextView[] viewAllBtn;
    ListDataAdapter adapter;
    String eventHeading;
    ArrayList<AllEventQT5Data> mEventsList;


    public DashBoardAdapter22(List<ListData> dList, Context c) {
        this.dLists = dList;
        this.context = c;
        this.mEventsList=mEventsList;
    }


    @NonNull
    @Override
    public DashBoardAdapter22.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.dashboard_detail_layout, parent, false);
        return new DashBoardAdapter22.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull DashBoardAdapter22.MyViewHolder holder, int position) {
        ListData mlist = dLists.get(position);
        arrayRecycleView = new RecyclerView[dLists.size()];
        seeAllBtn = new TextView[dLists.size()];
        viewAllBtn = new TextView[dLists.size()];
        holder.mainLayout.setPadding(0, (int) context.getResources().getDimension(R.dimen.ten), 0, 0);
        if (mlist.heading.equals("Movies")) {
            holder.heading.setVisibility(View.VISIBLE);
            holder.heading.setText(mlist.heading);
            headers.add(mlist.heading);
            List<Data> movieModels = mlist.data;
            holder.see_all.setVisibility(View.GONE);
            holder.view_all.setVisibility(View.VISIBLE);
            setRecyclerView(context, movieModels, holder.recyclerView_type, holder.getAdapterPosition(), mlist.type, holder.see_all, holder.view_all, mlist.heading);
        }else {
            holder.see_all.setVisibility(View.GONE);
            holder.view_all.setVisibility(View.GONE);
        }
//        if (mlist.data.size() >= 3) {
//            holder.see_all.setVisibility(View.GONE);
//            if (mlist.heading.equals("Movies")) {
//                holder.see_all.setVisibility(View.GONE);
//                holder.view_all.setVisibility(View.VISIBLE);
//            } else {
//                holder.see_all.setVisibility(View.GONE);
//                holder.view_all.setVisibility(View.VISIBLE);
//            }
//        } else {
//            holder.see_all.setVisibility(View.VISIBLE);
//            holder.view_all.setVisibility(View.GONE);
//        }

//         if (mlist.type.equals("event")) {
//             holder.heading.setVisibility(View.VISIBLE);
//             holder.heading.setText(mlist.heading);
////            headers.add(mlist.heading);
//             if (mlist.data.size() > 0) {
//                 headers.add(mlist.heading);
//                 List<Data> movieModels = mlist.data;
//                 setRecyclerView(context, movieModels, holder.recyclerView_type, holder.getAdapterPosition(), mlist.type, holder.see_all, holder.view_all, mlist.heading);
//             } else {
//                 holder.heading.setVisibility(View.GONE);
//             }
//
//         }


    }

    @Override
    public int getItemCount() {
        return dLists.size();
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
                if (headers.get(i) == heading) {
                    actualPos = i;
                    break;
                }
            }

            Intent i = new Intent(context, MainActivity.class);
            i.putExtra("categoryId", "15");
            i.putStringArrayListExtra("headerList", headers);
            i.putExtra("position", actualPos);
            i.putExtra("ImageType", "others");
            context.startActivity(i);
        });

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
}
