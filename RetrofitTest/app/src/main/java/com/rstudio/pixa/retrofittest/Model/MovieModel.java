package com.rstudio.pixa.retrofittest.Model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Ryan on 6/8/16.
 */
public class MovieModel {
    public String imgPath;
    public String title;
    public String overview;

    public MovieModel() {
        this("Untitled", "No overview yet", "");
    }

    public MovieModel(String title, String overview, String imgPath) {
        this.imgPath = imgPath;
        this.title = title;
        this.overview = overview;
    }

    public static MovieModel getMovie(JSONObject obj) {
        try {
            return new MovieModel(obj.getString("original_title"), obj.getString("overview"), obj.getString("poster_path"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
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
