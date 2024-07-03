package com.production.qtickets.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Item {
    @SerializedName("id")
    @Expose
    public String id;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("Imagetype")
    @Expose
    public String imagetype;
    @SerializedName("ActionType")
    @Expose
    public String actionType;
    @SerializedName("imagepath")
    @Expose
    public String imagepath;
    @SerializedName("Language")
    @Expose
    public String language;
    @SerializedName("Genre")
    @Expose
    public String genre;
    @SerializedName("Location")
    @Expose
    public String location;
    @SerializedName("RedirectLink")
    @Expose
    public String redirectLink;
    @SerializedName("trailer_link")
    @Expose
    public String trailerLink;
    @SerializedName("Description")
    @Expose
    public String description;
    @SerializedName("Duration")
    @Expose
    public String duration;
    @SerializedName("Rating")
    @Expose
    public String rating;
    @SerializedName("Censor_Rating")
    @Expose
    public String censorRating;

}
