package com.rstudio.pixa.retrofittest.Presenter;

/**
 * Created by Ryan on 6/8/16.
 */
public interface FetchDataIF {
    void fetchNowPlaying(FetchDataCallback callback);
    void fetchPopularPlaying(FetchDataCallback callback);
    void searchMovieWithTerm(String term, FetchDataCallback callback);
}
