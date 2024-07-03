package com.production.qtickets.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.production.qtickets.model.MovieModel;

import java.util.List;

public class MetaData {

    @SerializedName("odata.metadata")
    @Expose
    public String odataMetadata;
    @SerializedName("value")
    @Expose
    //public List<MovieValueList> value = null;
    public List<MovieModel> value = null;
}
