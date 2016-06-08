package com.rstudio.pixa.retrofittest.Presenter;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by Ryan on 6/8/16.
 */
public interface FetchDataCallback {
    void onFetchSuccess(JSONArray data);
    void onFetchFault(Exception e);
}
