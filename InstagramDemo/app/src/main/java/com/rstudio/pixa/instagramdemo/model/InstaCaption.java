package com.rstudio.pixa.instagramdemo.model;

import org.json.JSONObject;

/**
 * Created by Ryan on 6/9/16.
 */
public class InstaCaption {
    public String text;

    public InstaCaption(String t) {
        text = t;
    }

    public static InstaCaption getCaption(JSONObject o) {
        try {
            return new InstaCaption(o.getString("text"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
