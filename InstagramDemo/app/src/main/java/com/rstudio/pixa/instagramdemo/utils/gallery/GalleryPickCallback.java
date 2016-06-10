package com.rstudio.pixa.instagramdemo.utils.gallery;

import android.graphics.Bitmap;

/**
 * Created by Ryan on 6/10/16.
 */
public interface GalleryPickCallback {
    void onSuccess(Bitmap b);
    void onFailure(Exception e);
}
