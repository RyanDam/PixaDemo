package com.rstudio.pixa.instagramdemo.view;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.rstudio.pixa.instagramdemo.R;
import com.rstudio.pixa.instagramdemo.adapter.CollectionAdapter;
import com.rstudio.pixa.instagramdemo.client.InstaClientImp;
import com.rstudio.pixa.instagramdemo.client.LoginCallback;
import com.rstudio.pixa.instagramdemo.model.InstaPost;
import com.rstudio.pixa.instagramdemo.presenter.MainCollectionPresenter;
import com.rstudio.pixa.instagramdemo.utils.CellOffsetDecoration;
import com.rstudio.pixa.instagramdemo.utils.Utils;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    MainCollectionPresenter presenter;

    TextView tv;
    RecyclerView collection;
    RecyclerView.LayoutManager collManager;
    public CollectionAdapter collAdapter;
    public ArrayList<InstaPost> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        data = new ArrayList<>();
        setContentView(R.layout.activity_main);
        collection = ((RecyclerView) findViewById(R.id.main_list));
        collAdapter = new CollectionAdapter(this, data);
        collManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        CellOffsetDecoration deco = new CellOffsetDecoration(this, R.dimen.item_offset);
        collection.addItemDecoration(deco);
        collection.setLayoutManager(collManager);
        collection.setAdapter(collAdapter);

        presenter = new MainCollectionPresenter(this);
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
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
