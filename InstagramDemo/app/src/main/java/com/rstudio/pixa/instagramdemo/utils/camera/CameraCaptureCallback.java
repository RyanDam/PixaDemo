package com.rstudio.pixa.instagramdemo.utils.camera;

import android.graphics.Bitmap;

/**
 * Created by Ryan on 6/10/16.
 */
public interface CameraCaptureCallback {
    void onSuccess(Bitmap b);
    void onFailure(Exception e);
}
