package com.rstudio.pixa.instagramdemo.client;

/**
 * Created by Ryan on 6/9/16.
 */
public interface GetTokenCallback {
    void onSuccess(String access_token);
    void onFailure(String err_str);
}
