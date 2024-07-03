package com.production.qtickets.newsfeed;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.production.qtickets.R;
import com.production.qtickets.model.NewsFeedModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NewsFeedAdapter extends RecyclerView.Adapter<NewsFeedAdapter.MyViewHolder> {

    //bind the views based on the data coming from the server
    List<NewsFeedModel> nList;
    Context context;

    public NewsFeedAdapter(Context context, List<NewsFeedModel> nList) {
        this.context = context;
        this.nList = nList;
    }

    @Override
    public NewsFeedAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.news_feed_item_row, parent, false);

        return new NewsFeedAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(NewsFeedAdapter.MyViewHolder holder, int position) {
        NewsFeedModel newsFeedModel = nList.get(position);
        holder.tv_newsTitle.setText(newsFeedModel.ShortMsg);
        holder.tv_newsMessage.setText(newsFeedModel.Msg);

        /*String imagePath = newsFeedModel.imgpath;
        if (imagePath.isEmpty()){
            holder.imageLayout.setVisibility(View.GONE);
        }else if (!imagePath.isEmpty()){

            holder.imageLayout.setVisibility(View.VISIBLE);
            Glide.with(context).load(newsFeedModel.imgpath)
                    .thumbnail(0.5f)
                    .into(holder.banner_image);
        }*/

        Glide.with(context).load(newsFeedModel.imgpath)
                .thumbnail(0.5f)
                .into(holder.iv_newsImage);
    }


    @Override
    public int getItemCount() {
        return nList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_newsTitle)
        TextView tv_newsTitle;
        @BindView(R.id.tv_newsMessage)
        TextView tv_newsMessage;
        @BindView(R.id.iv_newsImage)
        ImageView iv_newsImage;
        public MyViewHolder(View itemView) {
            super(itemView);
            // binding view
            ButterKnife.bind(this, itemView);
        }
    }
}
