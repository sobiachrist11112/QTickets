package com.production.qtickets.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.production.qtickets.model.SearchItem;

import java.util.List;

/**
 * Created by Harris on 31-05-2018.
 */

public class SearchListItem {

    @SerializedName("items")
    @Expose
    public List<SearchItem> searchItems = null;
}
