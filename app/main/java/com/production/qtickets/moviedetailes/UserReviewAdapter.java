package com.production.qtickets.moviedetailes;

import android.content.Context;
import androidx.appcompat.widget.AppCompatRatingBar;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.production.qtickets.R;
import com.production.qtickets.model.UserReviewModel;
import com.production.qtickets.utils.TextviewBold;
import com.production.qtickets.utils.TextviewRegular;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Harsh on 5/30/2018.
 */
public class UserReviewAdapter extends RecyclerView.Adapter<UserReviewAdapter.ViewHolder> {

    //here we are listing up the user reviews and binding the fields for the views

    List<UserReviewModel> userReviewModels;
    Context context;

    public UserReviewAdapter(List<UserReviewModel> userReviewModels, Context context) {
        this.context = context;
        this.userReviewModels = userReviewModels;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View reviewlist = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user_review, parent, false);
        ViewHolder filterViewHolder = new ViewHolder(reviewlist);
        return filterViewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final UserReviewModel mList = userReviewModels.get(position);
        holder.rating.setRating(Float.parseFloat(mList.userRating));
        holder.txtReviewTitle.setText(mList.reviewTitle);
        holder.txtUserReview.setText(mList.userReview);
        if(!TextUtils.isEmpty(mList.userName)) {
            String upperString = mList.userName.substring(0,1).toUpperCase() + mList.userName.substring(1);
            holder.txtName.setText(upperString);
            char firstCharacter = holder.txtName.getText().toString().charAt(0);
            holder.txtFLName.setText(String.valueOf(firstCharacter));
        }
        holder.txtDate.setText("("+mList.updatedTs+")");

    }


    @Override
    public int getItemCount() {
        return userReviewModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.txt_f_l_name)
        TextviewBold txtFLName;
        @BindView(R.id.txt_name)
        TextviewBold txtName;
        @BindView(R.id.txt_review_title)
        TextviewBold txtReviewTitle;
        @BindView(R.id.rating)
        AppCompatRatingBar rating;
        @BindView(R.id.txt_date)
        TextviewRegular txtDate;
        @BindView(R.id.txt_user_review)
        TextviewRegular txtUserReview;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            }
    }
}