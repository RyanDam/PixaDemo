package com.rstudio.pixa.instagramdemo.model;

import com.google.gson.annotations.SerializedName;

import org.json.JSONObject;

/**
 * Created by Ryan on 6/9/16.
 */
public class InstaMedia {

    @SerializedName("low_resolution")
    public InstaMediaInfo low_resolution;

    @SerializedName("thumbnail")
    public InstaMediaInfo thumbnail;

    @SerializedName("standard_resolution")
    public InstaMediaInfo standard_resolution;

    public InstaMedia(InstaMediaInfo l, InstaMediaInfo t, InstaMediaInfo s) {
        low_resolution = l;
        thumbnail = t;
        standard_resolution = s;
    }

//    public static InstaMedia getMedia(JSONObject o) {
//        try {
////            InstaMedia ret = new InstaMedia(o.getJSONObject("low_resolution").getString("url")
////                    , o.getJSONObject("thumbnail").getString("url")
////                    , o.getJSONObject("standard_resolution").getString("url"));
////            return ret;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
}
