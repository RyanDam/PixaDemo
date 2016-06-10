package com.rstudio.pixa.instagramdemo.model;

import android.util.Log;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.annotations.SerializedName;
import com.rstudio.pixa.instagramdemo.utils.MyDeserializer;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.lang.reflect.Type;

/**
 * Created by Ryan on 6/9/16.
 */
public class InstaPost {

    public static Gson jsonParser;
    public static Gson getParser() {
        if (jsonParser == null) {
            jsonParser = new GsonBuilder()
                    .registerTypeAdapter(InstaMedia.class, new JsonDeserializer<InstaMedia>() {
                        @Override
                        public InstaMedia deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                            JsonElement content = json.getAsJsonObject().get("images");
                            return new Gson().fromJson(content, typeOfT);
                        }
                    })
                    .registerTypeAdapter(InstaCaption.class, new JsonDeserializer<InstaCaption>() {
                        @Override
                        public InstaCaption deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                            JsonElement content = json.getAsJsonObject().get("caption");
                            return new Gson().fromJson(content, typeOfT);
                        }
                    })
                    .create();
        }
        return jsonParser;
    }

    @SerializedName("images")
    public InstaMedia images;

    @SerializedName("caption")
    public InstaCaption caption;

    public InstaPost() {
        this(null, null);
    }

    public InstaPost(InstaMedia images, InstaCaption caption) {
        this.images = images;
        this.caption = caption;
    }

    public static InstaPost getPost(JSONObject ob) {
//        Gson gson = InstaPost.getParser();
//        try {
//            InstaPost p = new InstaPost(InstaMedia.getMedia(ob.getJSONObject("images"))
//                    , InstaCaption.getCaption(ob.getJSONObject("caption")));
//            return p;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        Gson gson = new Gson();
        InstaPost post = gson.fromJson(ob.toString(), InstaPost.class);
        return post;
    }

    public static ArrayList<InstaPost> getPosts(JSONArray arr) {
        ArrayList<InstaPost> ret = new ArrayList<>();
        try {
            for (int i = 0; i < arr.length(); i++) {
                ret.add(getPost(arr.getJSONObject(i)));
            }
            return ret;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }
}
