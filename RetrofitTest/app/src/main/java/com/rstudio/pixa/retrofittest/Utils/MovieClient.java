package com.rstudio.pixa.retrofittest.Utils;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

/**
 * Created by Ryan on 6/8/16.
 */
public interface MovieClient {
    @GET("movie/now_playing?api_key=81aa9a51608be1593d53b27562ddabf7")
    Call<ResponseBody> nowPlaying();

    @GET("movie/popular?api_key=81aa9a51608be1593d53b27562ddabf7")
    Call<ResponseBody> popular();

    @Headers("Accept: application/json")
    @GET("search/movie?api_key=81aa9a51608be1593d53b27562ddabf7")
    Call<ResponseBody> searchWithKeyWord(@Query("query") String keyword);
}
