package com.production.qtickets.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Harsh on 5/21/2018.
 */
public class CategoryModel {
    //category
    @SerializedName("category_name")
    @Expose
    public String categoryName;
    @SerializedName("category_image")
    @Expose
    public String categoryImage;


    //location
    @SerializedName("CountryName")
    @Expose
    public String countryName;
    @SerializedName("CountryCode")
    @Expose
    public String countryCode;
    @SerializedName("CountryCurrency")
    @Expose
    public String countryCurrency;
}
