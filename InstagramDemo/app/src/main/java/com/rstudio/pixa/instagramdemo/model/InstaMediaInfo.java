package com.rstudio.pixa.instagramdemo.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Ryan on 6/10/16.
 */
public class InstaMediaInfo {
    @SerializedName("url")
    public String url;

    @SerializedName("width")
    public String width;

    @SerializedName("height")
    public String height;
}
