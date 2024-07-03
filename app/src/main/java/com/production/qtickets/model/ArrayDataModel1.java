package com.production.qtickets.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Harsh on 7/19/2018.
 */
public class ArrayDataModel1 {
    @SerializedName("Table")
    @Expose
    public List<TableModel1> table = null;

}
