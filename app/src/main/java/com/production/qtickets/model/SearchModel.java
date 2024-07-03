package com.production.qtickets.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.production.qtickets.model.SearchItem;

import java.util.List;

public class SearchModel {
    @SerializedName("items")
    @Expose
    public List<SearchItem> items = null;
}
