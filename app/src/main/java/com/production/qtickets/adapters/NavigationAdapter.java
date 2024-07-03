package com.production.qtickets.adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.production.qtickets.R;
import com.production.qtickets.activity.NavigationModel;

import java.util.List;

/**
 * Created by Harsh on 12/21/2017.
 */

public class NavigationAdapter extends RecyclerView.Adapter<NavigationAdapter.MyViewHolder> {

    // in this class we will list the items of the navigation drawer, based on the user status, this is whether the user is logged in or not


    private List<NavigationModel> nList;
    private Context context;

    SharedPreferences prefmPrefs;
    SharedPreferences.Editor editor;
    int notificationIDCount = 0;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public TextView txt_notif_count;
        public ImageView imageView;
        public RelativeLayout rel_header;


        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.txt_title);
            txt_notif_count = (TextView) view.findViewById(R.id.txt_notif_count);
            imageView = (ImageView) view.findViewById(R.id.img_icon);
            //rel_header = view.findViewById(R.id.rel_header);
        }
    }

    public NavigationAdapter(Context context, List<NavigationModel> nList) {
        this.nList = nList;
        this.context = context;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_navigation_adapter, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        NavigationModel m_list = nList.get(position);
        holder.title.setText(m_list.getName());
        holder.imageView.setImageResource(Integer.parseInt(m_list.getIcon()));
        prefmPrefs = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
        editor = prefmPrefs.edit();
      //  notificationIDCount = prefmPrefs.getInt("notificationId", 0);
//        if (position == 3) {
//            holder.txt_notif_count.setVisibility(View.VISIBLE);
//            holder.txt_notif_count.setText(String.valueOf(notificationIDCount));
//        } else {
//            holder.txt_notif_count.setVisibility(View.GONE);
//        }


    }


    @Override
    public int getItemCount() {
        return nList.size();
    }


}