package com.production.qtickets.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.production.qtickets.model.MovieModel;

import java.util.ArrayList;
import java.util.List;




public class Items {
        //bookings
        //topmovies
        @SerializedName("items")
        @Expose
        public ArrayList<MovieModel> data = null;


}
