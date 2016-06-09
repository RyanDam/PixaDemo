package com.rstudio.pixa.retrofitdemo.presenter;

import android.content.Context;

import com.rstudio.pixa.retrofitdemo.utils.MovieClientIml;
import com.rstudio.pixa.retrofitdemo.view.MainActivity;

import org.json.JSONArray;

/**
 * Created by Ryan on 6/8/16.
 */
public class MainActivityPresenter extends BasePresenter {

    MovieClientIml client;

    public MainActivityPresenter(Context context) {
        super(context);
        client = MovieClientIml.getInstance();
    }

    public void fetchData() {
        FetchDataCallback cb = new FetchDataCallback() {
            @Override
            public void onFetchSuccess(JSONArray data) {
                getParent().mainAdapter.setData(data);
            }

            @Override
            public void onFetchFault(Exception e) {
                e.printStackTrace();
            }
        };
        client.fetchNowPlaying(cb);
    }

    private MainActivity getParent() {
        return (MainActivity) this.context;
    }
}
