package com.rstudio.pixa.instagramdemo.view;

import android.app.Activity;
import android.content.Intent;
import android.media.Image;
import android.support.annotation.IdRes;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.roughike.bottombar.BottomBar;
import com.rstudio.pixa.instagramdemo.R;
import com.rstudio.pixa.instagramdemo.adapter.CollectionAdapter;
import com.rstudio.pixa.instagramdemo.client.InstaClientImp;
import com.rstudio.pixa.instagramdemo.client.LoginCallback;
import com.rstudio.pixa.instagramdemo.model.InstaPost;
import com.rstudio.pixa.instagramdemo.presenter.MainCollectionPresenter;
import com.rstudio.pixa.instagramdemo.utils.CellOffsetDecoration;
import com.rstudio.pixa.instagramdemo.utils.Utils;
import com.rstudio.pixa.instagramdemo.utils.camera.CameraHelper;
import com.rstudio.pixa.instagramdemo.utils.gallery.GalleryHelper;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    MainCollectionPresenter presenter;

    public ImageView bigImg;
    public TextView bigText;
    public RecyclerView collection;
    public RecyclerView.LayoutManager collManager;
    public CollectionAdapter collAdapter;
    public ArrayList<InstaPost> data;
    Toolbar toolbar;

    public BottomBar bottomBar;
    public BottomSheetBehavior bottomSheetBehavior;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = ((Toolbar) findViewById(R.id.tool));
        if (toolbar == null) {
            Log.d("TOOL", "null");
        } else {
            setSupportActionBar(toolbar);
            toolbar.setTitle("");

        }

        data = new ArrayList<>();
        bigText = ((TextView) findViewById(R.id.big_post_text));
        bigImg = ((ImageView) findViewById(R.id.big_post_img));
        collection = ((RecyclerView) findViewById(R.id.main_list));
        collAdapter = new CollectionAdapter(this, data);
        collManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        CellOffsetDecoration deco = new CellOffsetDecoration(this, R.dimen.item_offset);
        collection.addItemDecoration(deco);
        collection.setLayoutManager(collManager);
        collection.setAdapter(collAdapter);

        bottomBar = BottomBar.attach(this, savedInstanceState);
        bottomBar.setItems(R.menu.main_bottom_bar);
        bottomBar.setActiveTabColor(getResources().getColor(R.color.activeTabColor));

        presenter = new MainCollectionPresenter(this);
        bottomBar.setOnMenuTabClickListener(presenter);

        View bottomSheet = findViewById(R.id.bottom_sheet);
        Button takeFromCamera = ((Button) bottomSheet.findViewById(R.id.sheet_camera));
        Button takeFromGallery = ((Button) bottomSheet.findViewById(R.id.sheet_gallery));
        takeFromCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.takePhoto();
            }
        });
        takeFromGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.takeFromGallery();
            }
        });
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        final InstaClientImp client = InstaClientImp.getInstance();
        final Activity c = this;
        switch (item.getItemId()) {
            case R.id.menu_login:
                client.login(c, new LoginCallback() {
                    @Override
                    public void onSuccess(String access_token) {
                        presenter.reloadUserPhoto();
                    }

                    @Override
                    public void onFailure(String err_str) {
                        Utils.makeToast(c, err_str);
                    }
                });
                return true;
            case R.id.menu_logout:
                client.logout(this);
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CameraHelper.REQUEST_IMAGE_CAPTURE) {
            if (resultCode == RESULT_OK) {
                presenter.cameraHelper.onSuccess(data);
            } else {
                presenter.cameraHelper.onFailure(new Exception(new Throwable("Camera capture fail, check CameraHelper class please")));
            }
        } else if (requestCode == GalleryHelper.PICK_IMAGE_REQUEST) {
            if (resultCode == RESULT_OK) {
                presenter.galleryHelper.onSuccess(data);
            } else {
                presenter.galleryHelper.onFailure(new Exception(new Throwable("Camera capture fail, check GalleryHelper class please")));
            }
        }

    }
}
