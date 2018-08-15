package com.dongduk.hangeul.hangeul_test1;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by jiyoungwon on 2017. 9. 17..
 */


public interface NetworkService {
    @POST("/writings/")
    Call<Writing> post_writing(@Body Writing writing);

    @GET("/wiritings/{pk}")
    Call<List<Writing>> get_writings(@Path("pk") int pk);

    @PATCH("/api/words/{pk}/")
    Call<Word> patch_word(@Path("pk") int pk, @Body MyWord word);

    @DELETE("/api/words/{pk}/")
    Call<Word> delete_word(@Path("pk") int pk);

    @GET("/helloWorld/")
    Call<String> get_word_id();

    @GET("/api/versions/{pk}/")
    Call<Word> get_pk_word(@Path("pk") int pk);


}
