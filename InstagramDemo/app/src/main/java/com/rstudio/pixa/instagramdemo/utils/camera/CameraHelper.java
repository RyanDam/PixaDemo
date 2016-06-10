package com.rstudio.pixa.instagramdemo.utils.camera;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.OperationApplicationException;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.rstudio.pixa.instagramdemo.R;
import com.rstudio.pixa.instagramdemo.utils.Config;
import com.rstudio.pixa.instagramdemo.utils.Utils;
import com.rstudio.pixa.instagramdemo.view.MainActivity;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Ryan on 6/10/16.
 */
public class CameraHelper implements CameraCaptureProtocol{
    public static final String TAG = "CameraHelper";
    public static final int REQUEST_IMAGE_CAPTURE = 1;

    private CameraCaptureCallback cb;
    public MainActivity at;

    public void takeAPicture(MainActivity at, CameraCaptureCallback cb) {
        this.at = at;
        this.cb = cb;
        Intent startCam = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (startCam.resolveActivity(at.getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (Exception e) {
                cb.onFailure(e);
            }
            if (photoFile != null) {
                currentPhotoFile = photoFile;
                startCam.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
                at.startActivityForResult(startCam, REQUEST_IMAGE_CAPTURE);
            }
        } else {
            Throwable t = new OperationApplicationException("Can't start camera, check CameraHelper class please!");
            cb.onFailure(new Exception(t));
        }
    }

    @Override
    public void onSuccess(Intent data) {
        if (currentPhotoFile != null) {
            new ReadImageFromPhoneMemory().execute(currentPhotoFile);
        } else {
            Throwable t = new OperationApplicationException("Can't start camera, check CameraHelper class please!");
            cb.onFailure(new Exception(t));
        }
    }

    @Override
    public void onFailure(Exception e) {
        cb.onFailure(e);
    }

    String currentPhotoPath;
    File currentPhotoFile;

    private void saveCurrentPicToGallery(Activity at) {
        if (currentPhotoPath == null) return;
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(currentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        at.sendBroadcast(mediaScanIntent);
    }

    /**
     * check more information in https://developer.android.com/training/camera/photobasics.html#TaskPath
     *
     * @return Image file where camera can save their photo in
     * @throws IOException
     */
    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        File storageDirWithAppName = new File(storageDir, Config.APP_PICTURE_FOLDER_NAME);
        if (storageDirWithAppName.exists() == false) {
            storageDirWithAppName.mkdir();
        }
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDirWithAppName      /* directory */
        );
        currentPhotoPath = "file:" + image.getAbsolutePath();
        // Save a file: path for use with ACTION_VIEW intents
        Log.d(TAG, "createImageFile: " + "file:" + image.getAbsolutePath());
        return image;
    }

    public class ReadImageFromPhoneMemory extends AsyncTask<File,Void, Bitmap> {
        @Override
        protected Bitmap doInBackground(File... params) {
            File f = params[0];
            Bitmap b = Utils.getBitmapFromStream(f, Config.DESIRSE_PICTURE_WIDTH);
            return b;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);

            LayoutInflater inf = ((LayoutInflater) at.getSystemService(Context.LAYOUT_INFLATER_SERVICE));
            View view = inf.inflate(R.layout.handy_alert_dialog, null);
            Dialog dia = new Dialog(at);
            dia.setContentView(view);
            ImageView imgV = ((ImageView) view.findViewById(R.id.img));
            imgV.setImageBitmap(bitmap);
            dia.show();

            cb.onSuccess(bitmap);
        }
    }
}
