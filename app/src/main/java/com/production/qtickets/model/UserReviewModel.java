package com.production.qtickets.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Harsh on 5/30/2018.
 */
public class UserReviewModel {
    /*get user reviews*/
    @SerializedName("id")
    @Expose
    public String id;
    @SerializedName("movie_id")
    @Expose
    public String movieId;
    @SerializedName("user_id")
    @Expose
    public String userId;
    @SerializedName("user_name")
    @Expose
    public String userName;
    @SerializedName("review_title")
    @Expose
    public String reviewTitle;
    @SerializedName("user_review")
    @Expose
    public String userReview;
    @SerializedName("Inserted_Ts")
    @Expose
    public String insertedTs;
    @SerializedName("inserted_by")
    @Expose
    public String insertedBy;
    @SerializedName("Updated_Ts")
    @Expose
    public String updatedTs;
    @SerializedName("Update_by")
    @Expose
    public String updateBy;
    @SerializedName("User_Rating")
    @Expose
    public String userRating;
    @SerializedName("IsApproved")
    @Expose
    public String isApproved;
    @SerializedName("Display")
    @Expose
    public String display;

    /*add user reviews*/
    @SerializedName("Response")
    @Expose
    public String response;
    @SerializedName("Status")
    @Expose
    public String status;
}
