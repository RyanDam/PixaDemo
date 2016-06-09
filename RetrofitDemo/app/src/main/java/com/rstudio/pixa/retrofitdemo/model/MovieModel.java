package com.rstudio.pixa.retrofitdemo.model;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Ryan on 6/8/16.
 */
public class MovieModel {
    public String poster_path;
    public String original_title;
    public String overview;

    public MovieModel() {
        this("Untitled", "No overview yet", "");
    }

    public MovieModel(String original_title, String overview, String poster_path) {
        this.poster_path = poster_path;
        this.original_title = original_title;
        this.overview = overview;
    }

    public static MovieModel getMovie(JSONObject obj) {
        return new Gson().fromJson(obj.toString(), MovieModel.class);
    }

    public static ArrayList<MovieModel> getMovieData(JSONArray data) {
        ArrayList<MovieModel> ret = new ArrayList<>();
        try {
            for (int i = 0; i < data.length(); i++) {
                ret.add(MovieModel.getMovie(data.getJSONObject(i)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }

}
