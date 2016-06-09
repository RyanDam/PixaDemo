package com.rstudio.pixa.instagramdemo.client;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by Ryan on 6/9/16.
 */
public interface InstaClient {
    @FormUrlEncoded
    @POST("access_token")
    Call<ResponseBody> getAccessToken(@Field("client_id") String clientId
            , @Field("client_secret") String clientSecret
            , @Field("grant_type") String grant_type
            , @Field("redirect_uri") String redirectURI
            , @Field("code") String code);

    @GET("users/self/media/recent/")
    Call<ResponseBody> getAllUserPhoto(@Query("access_token") String token, @Query("count") int count);
}
