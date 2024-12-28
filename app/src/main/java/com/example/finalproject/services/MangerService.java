package com.example.finalproject.services;

import com.example.finalproject.interfaces.ApiService;
import com.example.finalproject.interfaces.LeaderboardResponse;
import com.example.finalproject.interfaces.ResponseCallBack;
import com.example.finalproject.interfaces.VideoResponse;
import com.example.finalproject.models.LeaderboardEntry;
import com.example.finalproject.models.LoginResponse;
import com.example.finalproject.models.Video;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Part;

public class MangerService {
    private String base_url="http://192.168.1.10:5000/api/";
    private static Retrofit retrofit;

    public MangerService(){

    }
    public void getGuideVideos(final VideoResponse callback){
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(base_url)
                .addConverterFactory(GsonConverterFactory.create()).build();
        ApiService apiService=retrofit.create(ApiService.class);
        Call<List<Video>> listCall= apiService.getGuideVideo();
        listCall.enqueue(new Callback<List<Video>>() {
            @Override
            public void onResponse(Call<List<Video>> call, Response<List<Video>> response) {
                try {
                    callback.onSuccess(response.body());
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public void onFailure(Call<List<Video>> call, Throwable t) {
                callback.onError(t);
            }
        });



    }
    public Retrofit getRetrofitInstance() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(base_url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
    public void LeaderboardData(final LeaderboardResponse callback) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(base_url)
                .addConverterFactory(GsonConverterFactory.create()).build();
        ApiService apiService=retrofit.create(ApiService.class);
        Call<List<LeaderboardEntry>> leaderboardEntryCall= apiService.getLeaderboard();
        leaderboardEntryCall.enqueue(new Callback<List<LeaderboardEntry>>() {
            @Override
            public void onResponse(Call<List<LeaderboardEntry>> call, Response<List<LeaderboardEntry>> response) {
                try {
                    callback.onSuccess(response.body());
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public void onFailure(Call<List<LeaderboardEntry>> call, Throwable t) {
                callback.onError(t);
            }
        });
    }

}
