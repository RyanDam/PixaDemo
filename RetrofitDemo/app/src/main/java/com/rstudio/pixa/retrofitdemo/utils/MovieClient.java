package com.rstudio.pixa.retrofitdemo.utils;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

/**
 * Created by Ryan on 6/8/16.
 */
public interface MovieClient {
    @GET("movie/now_playing?api_key="+Config.API_KEY)
    Call<ResponseBody> nowPlaying();

    @GET("movie/popular?api_key="+Config.API_KEY)
    Call<ResponseBody> popular();

    @Headers("Accept: application/json")
    @GET("search/movie?api_key="+Config.API_KEY)
    Call<ResponseBody> searchWithKeyWord(@Query("query") String keyword);
}
