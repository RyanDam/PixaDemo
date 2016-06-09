package com.rstudio.pixa.instagramdemo.client;

import org.json.JSONArray;

/**
 * Created by Ryan on 6/9/16.
 */
public interface GetAllPhotoJSONCallback {
    void onSuccess(JSONArray arr);
    void onFailure(Exception e);
}
