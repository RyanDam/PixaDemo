package com.rstudio.pixa.instagramdemo.utils.gallery;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;

import java.io.IOException;

/**
 * Created by Ryan on 6/10/16.
 */
public class GalleryHelper implements GalleryPickProtocol{

    public static final int PICK_IMAGE_REQUEST = 989;

    public GalleryPickCallback cb;
    public Activity at;

    public void pickPicture(Activity at, GalleryPickCallback cb) {
        this.cb = cb;
        this.at = at;
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        at.startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    public void onSuccess(Intent data) {
        Uri uri = data.getData();
        new ReadImageFromGallery().execute(uri);
    }

    @Override
    public void onFailure(Exception e) {
        cb.onFailure(e);
    }

    public class ReadImageFromGallery extends AsyncTask<Uri, Void, Bitmap> {

        @Override
        protected Bitmap doInBackground(Uri... params) {
            Uri uri = params[0];
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(at.getContentResolver(), uri);
                cb.onSuccess(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            if (bitmap != null) {
                cb.onSuccess(bitmap);
            } else {
                cb.onFailure(new Exception(new Throwable("Bitmap return null, check ReadImageFromGallery in GalleryHelper class please")));
            }

        }
    }
}
