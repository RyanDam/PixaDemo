package com.rstudio.pixa.instagramdemo.utils.gallery;

import android.content.Intent;

/**
 * Created by Ryan on 6/10/16.
 */
public interface GalleryPickProtocol {
    void onSuccess(Intent data);
    void onFailure(Exception e);
}
