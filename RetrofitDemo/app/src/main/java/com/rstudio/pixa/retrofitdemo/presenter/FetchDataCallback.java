package com.rstudio.pixa.retrofitdemo.presenter;

import org.json.JSONArray;

/**
 * Created by Ryan on 6/8/16.
 */
public interface FetchDataCallback {
    void onFetchSuccess(JSONArray data);
    void onFetchFault(Exception e);
}
