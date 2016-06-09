package com.rstudio.pixa.retrofitdemo.presenter;

/**
 * Created by Ryan on 6/8/16.
 */
public interface FetchData {
    void fetchNowPlaying(FetchDataCallback callback);
    void fetchPopularPlaying(FetchDataCallback callback);
    void searchMovieWithTerm(String term, FetchDataCallback callback);
}
