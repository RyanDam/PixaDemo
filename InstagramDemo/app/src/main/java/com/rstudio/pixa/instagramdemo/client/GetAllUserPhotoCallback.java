package com.rstudio.pixa.instagramdemo.client;

import com.rstudio.pixa.instagramdemo.model.InstaPost;

import java.util.ArrayList;

/**
 * Created by Ryan on 6/9/16.
 */
public interface GetAllUserPhotoCallback {
    void onSuccess(ArrayList<InstaPost> data);
    void onFailure(Exception e);
}
