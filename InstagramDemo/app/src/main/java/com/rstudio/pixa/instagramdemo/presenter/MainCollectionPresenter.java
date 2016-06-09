package com.rstudio.pixa.instagramdemo.presenter;

import com.rstudio.pixa.instagramdemo.client.GetAllUserPhotoCallback;
import com.rstudio.pixa.instagramdemo.client.InstaClientImp;
import com.rstudio.pixa.instagramdemo.model.InstaPost;
import com.rstudio.pixa.instagramdemo.utils.Utils;
import com.rstudio.pixa.instagramdemo.view.MainActivity;

import java.util.ArrayList;

/**
 * Created by Ryan on 6/9/16.
 */
public class MainCollectionPresenter {

    MainActivity activity;
    InstaClientImp client;

    public MainCollectionPresenter(MainActivity a) {
        this.activity = a;
        client = InstaClientImp.getInstance();
    }

    public void reloadUserPhoto() {
        client.getAllUserPhoto(activity, new GetAllUserPhotoCallback() {
            @Override
            public void onSuccess(ArrayList<InstaPost> data) {
                activity.data.clear();
                activity.data.addAll(data);
                activity.collAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Exception e) {
//                Utils.makeToast(activity, e.toString());
                e.printStackTrace();
            }
        });
    }
}
