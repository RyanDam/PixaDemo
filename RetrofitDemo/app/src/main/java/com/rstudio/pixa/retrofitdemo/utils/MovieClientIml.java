package com.rstudio.pixa.retrofitdemo.utils;

import com.rstudio.pixa.retrofitdemo.presenter.FetchDataCallback;
import com.rstudio.pixa.retrofitdemo.presenter.FetchData;

import org.json.JSONArray;
import org.json.JSONObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by Ryan on 6/8/16.
 */
public class MovieClientIml implements FetchData {

    public static MovieClientIml instance;
    public static MovieClientIml getInstance() {
        if (instance == null) {
            return new MovieClientIml();
        }
        else return instance;
    }

    private Retrofit retro;
    private MovieClient client;

    public MovieClientIml() {
        retro = new Retrofit.Builder().baseUrl(Config.BASE_API_URL).build();
        client = retro.create(MovieClient.class);
    }

    @Override
    public void fetchNowPlaying(final FetchDataCallback callback) {
        Call<ResponseBody> ret = client.nowPlaying();
        ret.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    JSONObject obj = new JSONObject(response.body().string());
                    JSONArray arr = obj.getJSONArray("results");
                    callback.onFetchSuccess(arr);
                } catch (Exception e) {
                    callback.onFetchFault(e);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                callback.onFetchFault(new Exception(t));
            }
        });
    }

    @Override
    public void fetchPopularPlaying(final FetchDataCallback callback) {
        Call<ResponseBody> ret = client.popular();
        ret.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    JSONObject obj = new JSONObject(response.body().string());
                    JSONArray arr = obj.getJSONArray("results");
                    callback.onFetchSuccess(arr);
                } catch (Exception e) {
                    callback.onFetchFault(e);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                callback.onFetchFault(new Exception(t));
            }
        });
    }

    @Override
    public void searchMovieWithTerm(String term, final FetchDataCallback callback) {
        Call<ResponseBody> ret = client.searchWithKeyWord(term);
        ret.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    JSONObject obj = new JSONObject(response.body().string());
                    JSONArray arr = obj.getJSONArray("results");
                    callback.onFetchSuccess(arr);
                } catch (Exception e) {
                    callback.onFetchFault(e);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                callback.onFetchFault(new Exception(t));
            }
        });
    }
}
