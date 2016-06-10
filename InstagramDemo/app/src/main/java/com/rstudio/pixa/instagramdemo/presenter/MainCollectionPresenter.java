package com.rstudio.pixa.instagramdemo.presenter;

import android.graphics.Bitmap;
import android.support.annotation.IdRes;
import android.support.design.widget.BottomSheetBehavior;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.roughike.bottombar.OnMenuTabClickListener;
import com.rstudio.pixa.instagramdemo.R;
import com.rstudio.pixa.instagramdemo.client.GetAllUserPhotoCallback;
import com.rstudio.pixa.instagramdemo.client.InstaClientImp;
import com.rstudio.pixa.instagramdemo.model.InstaPost;
import com.rstudio.pixa.instagramdemo.utils.camera.CameraCaptureCallback;
import com.rstudio.pixa.instagramdemo.utils.camera.CameraHelper;
import com.rstudio.pixa.instagramdemo.utils.gallery.GalleryHelper;
import com.rstudio.pixa.instagramdemo.utils.gallery.GalleryPickCallback;
import com.rstudio.pixa.instagramdemo.view.MainActivity;

import java.util.ArrayList;

/**
 * Created by Ryan on 6/9/16.
 */
public class MainCollectionPresenter implements OnMenuTabClickListener {

    public static String TAG = "MainCollectionPresenter";

    MainActivity activity;
    InstaClientImp client;

    public CameraHelper cameraHelper;
    public GalleryHelper galleryHelper;

    public MainCollectionPresenter(MainActivity a) {
        this.activity = a;

        cameraHelper = new CameraHelper();
        galleryHelper = new GalleryHelper();

        client = InstaClientImp.getInstance();
        if (client.getAccessToken(activity) != null) {
            Log.d("sa", "LOAD");
            reloadUserPhoto();
        } else {
            Log.d("sa", "FAIL");
        }
    }

    public void takePhoto() {
        cameraHelper.takeAPicture(activity, new CameraCaptureCallback() {
            @Override
            public void onSuccess(Bitmap b) {
                Log.d(TAG, "onSuccess: Success");
            }

            @Override
            public void onFailure(Exception e) {
                e.printStackTrace();
            }
        });
    }

    public void takeFromGallery() {
        galleryHelper.pickPicture(activity, new GalleryPickCallback() {
            @Override
            public void onSuccess(Bitmap b) {
                Log.d(TAG, "onSuccess: Success");
            }

            @Override
            public void onFailure(Exception e) {
                e.printStackTrace();
            }
        });
    }

    public void reloadUserPhoto() {
        client.getAllUserPhoto(activity, new GetAllUserPhotoCallback() {
            @Override
            public void onSuccess(ArrayList<InstaPost> data) {
                activity.data.clear();
                activity.data.addAll(data);
                activity.collAdapter.notifyDataSetChanged();
                Glide.with(activity).load(data.get(0).images.standard_resolution.url).into(activity.bigImg);
                activity.bigText.setText(data.get(0).caption.text);
            }

            @Override
            public void onFailure(Exception e) {
//                Utils.makeToast(activity, e.toString());
                e.printStackTrace();
            }
        });
    }

    @Override
    public void onMenuTabSelected(@IdRes int menuItemId) {
        switch (menuItemId) {
            case R.id.recent:
                break;
            case R.id.camera:
                toggleBottomSheetState();
                break;
            case R.id.profile:
                break;
            default:
                break;
        }
    }

    @Override
    public void onMenuTabReSelected(@IdRes int menuItemId) {
        switch (menuItemId) {
            case R.id.recent:
                break;
            case R.id.camera:
                toggleBottomSheetState();
                break;
            case R.id.profile:
                break;
            default:
                break;
        }
    }

    private void toggleBottomSheetState() {
        if (activity.bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED)
            activity.bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        else
            activity.bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
    }
}
