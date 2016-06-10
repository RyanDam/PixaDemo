package com.rstudio.pixa.instagramdemo.utils.camera;

import android.content.Intent;
import android.graphics.Bitmap;

/**
 * Created by Ryan on 6/10/16.
 */
public interface CameraCaptureProtocol {
    void onSuccess(Intent data);
    void onFailure(Exception e);
}
