package com.dongduk.hangeul.hangeul_test1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NetworkTestActivity extends AppCompatActivity {

    //public final String TAG = "KJH";
    private NetworkService networkService;

    TextView tv1;
    TextView tv2;
    TextView tv3;
    TextView tv4;
    TextView tv5;
    Button bt1;
    Button bt2;
    Button bt4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_network_test);

        tv1 = (TextView) findViewById(R.id.tv1);
        tv2 = (TextView) findViewById(R.id.tv2);
        bt1 = (Button)findViewById(R.id.bt1);
        bt2 = (Button)findViewById(R.id.bt2);
        bt4 = (Button)findViewById(R.id.bt4);

        ApplicationController application = ApplicationController.getInstance();
        //application.buildNetworkService("ab2a6169.ngrok.io");
        application.buildNetworkService("54.237.215.221", 8000);
        networkService = ApplicationController.getInstance().getNetworkService();

        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Restaurant GET
                Call<Word> getCall = networkService.get_word();
                getCall.enqueue(new Callback<Word>() {
                    @Override
                    public void onResponse(Call<Word> call, Response<Word> response) {
                        if( response.isSuccessful()) {
                            Word word = response.body();

                            tv1.setText(word.getWord().toString() + " : " + word.getWordDesc());
                        } else {
                            int StatusCode = response.code();
                            Log.i(ApplicationController.TAG, "Status Code : " + StatusCode + " Error Message : " + response.errorBody());
                        }
                    }

                    @Override
                    public void onFailure(Call<Word> call, Throwable t) {
                        Log.i(ApplicationController.TAG, "Fail Message : " + t.getMessage());
                    }
                });
            }
        });

        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bt2_Click();
            }
        });

        bt4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bt4_click();

            }
        });
    }

//    @OnClick(R.id.bt1)
//    public void bt1_Click()
//    {
//        //GET
//        Call<List<MyWord>> versionCall = networkService.get_word();
//        versionCall.enqueue(new Callback<List<MyWord>>() {
//            @Override
//            public void onResponse(Call<List<MyWord>> call, Response<List<MyWord>> response) {
//                if(response.isSuccessful()) {
//                    List<MyWord> versionList = response.body();
//
//                    String word_txt = "";
//                    for(MyWord word : versionList){
//                        word_txt += word.getWord() + "\n";
//                    }
//
//                    tv1.setText(word_txt);
//                } else {
//                    int StatusCode = response.code();
//                    Log.i(ApplicationController.TAG, "Status Code : " + StatusCode);
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<MyWord>> call, Throwable t) {
//                Log.i(ApplicationController.TAG, "Fail Message : " + t.getMessage());
//            }
//        });
//    }
//
public void bt2_Click(){
    //POST
    Writing writing = new Writing();


    writing.setUid("test");
    writing.setWid(5);
    writing.setDate("2017-09-20");
    writing.setWriting("test");

    Call<Writing> postCall = networkService.post_writing(writing);
    postCall.enqueue(new Callback<Writing>() {
        @Override
        public void onResponse(Call<Writing> call, Response<Writing> response) {
            if( response.isSuccessful()) {
                tv2.setText("등록");

            } else {
                int StatusCode = response.code();
                Log.i(ApplicationController.TAG, "Status Code : " + StatusCode);
            }

        }

        @Override
        public void onFailure(Call<Writing> call, Throwable t) {
            Log.i(ApplicationController.TAG, "Fail Message : " + t.getMessage());
        }
    });
}

//    @OnClick(R.id.bt3)
//    public void bt3_click(){
//        //PATCH
//        Version version = new Version();
//        version.setVersion("1.0.0.0.1");
//        Call<Version> patchCall = networkService.patch_version(1, version);
//        patchCall.enqueue(new Callback<Version>() {
//            @Override
//            public void onResponse(Call<Version> call, Response<Version> response) {
//                if( response.isSuccessful()) {
//                    tv3.setText("업데이트");
//                } else {
//                    int StatusCode = response.code();
//                    Log.i(ApplicationController.TAG, "Status Code : " + StatusCode);
//                }
//            }
//
//            @Override
//            public void onFailure(Call<Version> call, Throwable t) {
//                Log.i(ApplicationController.TAG, "Fail Message : " + t.getMessage());
//            }
//        });
//    }

    public void bt4_click(){

        Call<List<Writing>> getCall = networkService.get_writings(3);

        getCall.enqueue(new Callback<List<Writing>>() {
            @Override
            public void onResponse(Call<List<Writing>> call, Response<List<Writing>> response) {
                if( response.isSuccessful()) {
                    List<Writing> writingList = response.body();

                    String word_txt = "";
                    for(Writing writing : writingList){
                        word_txt += writing.getDate() +
                                writing.getWriting() +
                                "\n";
                    }

                    tv1.setText(word_txt);

                } else {
                    int StatusCode = response.code();
                    Log.i(ApplicationController.TAG, "Status Code : " + StatusCode + " Error Message : " + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<List<Writing>> call, Throwable t) {
                Log.i(ApplicationController.TAG, "Fail Message : " + t.getMessage());
            }
        });
    }
//    public void bt1_click(){
//        //Restaurant GET
//        Call<List<Word>> getCall = networkService.get_word();
//        getCall.enqueue(new Callback<List<Word>>() {
//            @Override
//            public void onResponse(Call<List<Word>> call, Response<List<Word>> response) {
//                if( response.isSuccessful()) {
//                    List<Word> wordList = response.body();
//
//                    String word_txt = "";
//                    for(Word word : wordList){
//                        word_txt += word.getWord() +
//                                word.getWordDesc() +
//                                "\n";
//                    }
//
//                    tv1.setText(word_txt);
//                } else {
//                    int StatusCode = response.code();
//                    Log.i(ApplicationController.TAG, "Status Code : " + StatusCode + " Error Message : " + response.errorBody());
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<Word>> call, Throwable t) {
//                Log.i(ApplicationController.TAG, "Fail Message : " + t.getMessage());
//            }
//        });
//    }
//
//    @OnClick(R.id.bt2)
//    public void bt2_click(){
//        //Restaurant POST
//        Word word = new word();
//        word.set("음식점1");
//        restaurant.setAddress("장소1");
//        restaurant.setCategory(3);
//        restaurant.setWeather(3);
//        restaurant.setDistance(3);
//        restaurant.setDescription("설명1");
//
//        Call<Restaurant> postCall = networkService.post_restaruant(restaurant);
//        postCall.enqueue(new Callback<Restaurant>() {
//            @Override
//            public void onResponse(Call<Restaurant> call, Response<Restaurant> response) {
//                if( response.isSuccessful()) {
//                    tv6.setText("등록");
//                } else {
//                    int StatusCode = response.code();
//                    try {
//                        Log.i(ApplicationController.TAG, "Status Code : " + StatusCode + " Error Message : " + response.errorBody().string());
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(Call<Restaurant> call, Throwable t) {
//                Log.i(ApplicationController.TAG, "Fail Message : " + t.getMessage());
//            }
//        });
//    }
//
//    @OnClick(R.id.bt7)
//    public void bt7_click(){
//        //Restaurant PATCH
//        //Full or partial patch available
//        Restaurant restaurant = new Restaurant();
//        restaurant.setName("이름22");
//        restaurant.setAddress("장소22");
//        restaurant.setCategory(3);
//        restaurant.setWeather(1);
//        restaurant.setDistance(2);
//        restaurant.setDescription("장소22");
//
//        Call<Restaurant> patchCall = networkService.patch_restaruant(1, restaurant);
//        patchCall.enqueue(new Callback<Restaurant>() {
//            @Override
//            public void onResponse(Call<Restaurant> call, Response<Restaurant> response) {
//                if( response.isSuccessful()) {
//                    tv7.setText("업데이트");
//                } else {
//                    int StatusCode = response.code();
//                    try {
//                        Log.i(ApplicationController.TAG, "Status Code : " + StatusCode + " Error Message : " + response.errorBody().string());
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(Call<Restaurant> call, Throwable t) {
//                Log.i(ApplicationController.TAG, "Fail Message : " + t.getMessage());
//            }
//        });
//    }
//
//    @OnClick(R.id.bt8)
//    public void bt8_click(){
//        //Restaurant DELETE
//        Call<Restaurant> deleteCall = networkService.delete_restaruant(2);
//        deleteCall.enqueue(new Callback<Restaurant>() {
//
//            @Override
//            public void onResponse(Call<Restaurant> call, Response<Restaurant> response) {
//                if ( response.isSuccessful()) {
//                    tv8.setText("삭제");
//                } else {
//                    int StatusCode = response.code();
//                    try {
//                        Log.i(ApplicationController.TAG, "Status Code : " + StatusCode + " Error Message : " + response.errorBody().string());
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(Call<Restaurant> call, Throwable t) {
//                Log.i(ApplicationController.TAG, "Fail Message : " + t.getMessage());
//            }
//        });
//    }
//
//    @OnClick(R.id.bt9)
//    public void bt9_click(){
//        Call<List<Restaurant>> get_weather_pk_Call = networkService.get_weather_pk_restaruant(1);
//        get_weather_pk_Call.enqueue(new Callback<List<Restaurant>>() {
//            @Override
//            public void onResponse(Call<List<Restaurant>> call, Response<List<Restaurant>> response) {
//                if( response.isSuccessful()) {
//                    List<Restaurant> restaurantList = response.body();
//
//                    String restaurant_txt = "";
//                    for(Restaurant restaurant : restaurantList){
//                        restaurant_txt += restaurant.getName() +
//                                restaurant.getAddress() +
//                                restaurant.getCategory() +
//                                restaurant.getWeather() +
//                                restaurant.getDistance() +
//                                restaurant.getDescription() +
//                                "\n";
//                    }
//
//                    tv9.setText(restaurant_txt);
//                } else {
//                    int StatusCode = response.code();
//                    Log.i(ApplicationController.TAG, "Status Code : " + StatusCode + " Error Message : " + response.errorBody());
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<Restaurant>> call, Throwable t) {
//                Log.i(ApplicationController.TAG, "Fail Message : " + t.getMessage());
//            }
//        });
//    }



}


