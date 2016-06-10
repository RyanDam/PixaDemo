package com.rstudio.pixa.instagramdemo.client;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.rstudio.pixa.instagramdemo.model.InstaPost;
import com.rstudio.pixa.instagramdemo.utils.Config;
import com.rstudio.pixa.instagramdemo.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.Array;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by Ryan on 6/9/16.
 */
public class InstaClientImp {

    public static InstaClientImp instance;
    public static InstaClientImp getInstance() {
        if (instance == null) {
            instance = new InstaClientImp();
            return instance;
        } else return instance;
    }

    Retrofit retrofitAuth;
    InstaClient clientAuth;
    Retrofit retrofitAPI;
    InstaClient clientAPI;

    public InstaClientImp() {
        retrofitAuth = new Retrofit.Builder()
                .baseUrl(Config.BASE_API_AUTH)
                .build();
        clientAuth = retrofitAuth.create(InstaClient.class);

        retrofitAPI = new Retrofit.Builder()
                .baseUrl(Config.BASE_API)
                .build();
        clientAPI = retrofitAPI.create(InstaClient.class);
    }

    public void logout(Activity at) {
        SharedPreferences.Editor e = at.getPreferences(Context.MODE_PRIVATE).edit();
        e.remove(Config.SHPREF_KEY_ACCESS_TOKEN);
    }

    public void login(final Activity ct, final LoginCallback cb) {
        String s = getAccessToken(ct);
        if (s == null) {
            LayoutInflater inf = ((LayoutInflater) ct.getSystemService(Context.LAYOUT_INFLATER_SERVICE));
            View v = inf.inflate(R.layout.insta_login, null);

            final Dialog dia = new Dialog(ct);
            dia.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dia.setContentView(v);

            WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
            lp.copyFrom(dia.getWindow().getAttributes());
            lp.width = WindowManager.LayoutParams.MATCH_PARENT;
            lp.height = WindowManager.LayoutParams.MATCH_PARENT;
            lp.verticalMargin = -20f;

            WebView webView = ((WebView) v.findViewById(R.id.wv));

            webView.setWebViewClient(new WebViewClient() {
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    if (url.startsWith(Config.REDIRECT_URI)) {
                        if (url.indexOf("code=") != -1) {
                            String code = url.split("code=")[1];

                            SharedPreferences.Editor e = ct.getPreferences(Context.MODE_PRIVATE).edit();
                            e.putString(Config.SHPREF_KEY_CODE_TOKEN, code);
                            e.commit();

                            // we have the code, so now we ask for access token
                            getToken(code, new GetTokenCallback() {
                                @Override
                                public void onSuccess(String access_token) {
                                    SharedPreferences.Editor e = ct.getPreferences(Context.MODE_PRIVATE).edit();
                                    e.putString(Config.SHPREF_KEY_ACCESS_TOKEN, access_token);
                                    e.commit();
                                    cb.onSuccess(access_token);
                                    dia.dismiss();
                                }

                                @Override
                                public void onFailure(String err_str) {
                                    cb.onFailure(err_str);
                                    dia.dismiss();
                                }
                            });
                        } else {
                            String code = url.split("'?'")[1];
                            cb.onFailure(code);
                            dia.dismiss();
                            // don't go to redirectUri
                            return true;
                        }
                    }
                    // load the webpage from url: login and grant access
                    return super.shouldOverrideUrlLoading(view, url); // return false;
                }
            });

            String url = "https://api.instagram.com/oauth/authorize/?client_id="
                    + Config.CLIENT_ID
                    + "&redirect_uri="
                    + Config.REDIRECT_URI
                    + "&response_type="
                    + "code";

            webView.loadUrl(url);

            dia.show();
            dia.getWindow().setAttributes(lp);
        } else {
            cb.onSuccess(s);
        }
    }

    public void getToken(String code, final GetTokenCallback cb) {
        Call<ResponseBody> c = clientAuth.getAccessToken(Config.CLIENT_ID
                , Config.CLIENT_SECRET
                , Config.GRAND_TYPE
                , Config.REDIRECT_URI
                , code);
        c.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String s = response.body().string();
                    JSONObject o = new JSONObject(s);
                    String key = o.getString("access_token");
                    cb.onSuccess(key);
                } catch (Exception e) {
                    cb.onFailure(e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                cb.onFailure(t.getMessage());
            }
        });
    }

    public void getAllUserPhoto(Activity at, final GetAllUserPhotoCallback cb) {
        getAllUserPhotoJSON(at, new GetAllPhotoJSONCallback() {
            @Override
            public void onSuccess(JSONArray arr) {
                cb.onSuccess(InstaPost.getPosts(arr));
            }

            @Override
            public void onFailure(Exception e) {
                cb.onFailure(e);
            }
        });
    }

    public void getAllUserPhotoJSON(Activity at, final GetAllPhotoJSONCallback cb) {
        String s = getAccessToken(at);
        Log.d("ACCESS_TOKEN", s);
        Call<ResponseBody> c = clientAPI.getAllUserPhoto(s, 20);
        c.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String s = response.body().string();
                    JSONObject b = new JSONObject(s);
                    JSONArray ret = b.getJSONArray("data");
                    cb.onSuccess(ret);
                } catch (Exception e) {
                    cb.onFailure(e);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                cb.onFailure(new Exception(t));
            }
        });
    }

    public String getAccessToken(Activity at) {
        SharedPreferences e = at.getPreferences(Context.MODE_PRIVATE);
        return e.getString(Config.SHPREF_KEY_ACCESS_TOKEN, null);
    }
}
