package com.production.qtickets.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NewsFeedModel {
    @SerializedName("Id")
    @Expose
    public String Id;
    @SerializedName("Type")
    @Expose
    public String Type;
    @SerializedName("Cat")
    @Expose
    public String Cat;
    @SerializedName("ShortMsg")
    @Expose
    public String ShortMsg;
    @SerializedName("Msg")
    @Expose
    public String Msg;
    @SerializedName("imgpath")
    @Expose
    public String imgpath;
    @SerializedName("SourceType")
    @Expose
    public String SourceType;
    @SerializedName("SoruceID")
    @Expose
    public String SoruceID;
    @SerializedName("ReadStatus")
    @Expose
    public String ReadStatus;
    @SerializedName("InsertTime")
    @Expose
    public String InsertTime;

}
