package com.rstudio.pixa.retrofittest.Presenter;

import android.content.Context;

import com.rstudio.pixa.retrofittest.Utils.MovieClientIml;
import com.rstudio.pixa.retrofittest.View.MainActivity;

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
        client.fetchNowPlaying(new FetchDataCallback() {
            @Override
            public void onFetchSuccess(JSONArray data) {
                getParent().mainAdapter.setData(data);
            }

            @Override
            public void onFetchFault(Exception e) {
                e.printStackTrace();
            }
        });
    }



    private MainActivity getParent() {
        return (MainActivity) this.context;
    }
}
