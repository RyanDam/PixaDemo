package com.rstudio.pixa.instagramdemo.model;

import org.json.JSONObject;

/**
 * Created by Ryan on 6/9/16.
 */
public class InstaMedia {
    public String low_resolution;
    public String thumbnail;
    public String standard_resolution;

    public InstaMedia(String l, String t, String s) {
        low_resolution = l;
        thumbnail = t;
        standard_resolution = s;
    }

    public static InstaMedia getMedia(JSONObject o) {
        try {
            InstaMedia ret = new InstaMedia(o.getJSONObject("low_resolution").getString("url")
                    , o.getJSONObject("thumbnail").getString("url")
                    , o.getJSONObject("standard_resolution").getString("url"));
            return ret;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
