package com.example.finalproject.services;

import com.example.finalproject.LeaderboardData;
import com.example.finalproject.interfaces.ApiService;
import com.example.finalproject.interfaces.LeaderboardResponse;
import com.example.finalproject.interfaces.LeaderboardResponseSingle;
import com.example.finalproject.interfaces.SinglePoseResponse;
import com.example.finalproject.interfaces.VideoResponse;
import com.example.finalproject.models.GlobalLeaderboardEntry;
import com.example.finalproject.models.LogoutRequest;
import com.example.finalproject.models.SingleLeaderboardEntry;
import com.example.finalproject.models.SinglePoses;
import com.example.finalproject.models.Video;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MangerService {
    private String base_url="http://172.31.98.218:5000/api/";
    private static Retrofit retrofit;

    public MangerService(){

    }

    public void get_Single_Poses(final SinglePoseResponse callback){
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(base_url)
                .addConverterFactory(GsonConverterFactory.create()).build();
        ApiService apiService=retrofit.create(ApiService.class);
        Call<List<SinglePoses>> listCall=apiService.getSinglePoses();
        listCall.enqueue(new Callback<List<SinglePoses>>() {
            @Override
            public void onResponse(Call<List<SinglePoses>> call, Response<List<SinglePoses>> response) {
                try {
                    callback.onSuccess(response.body());
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public void onFailure(Call<List<SinglePoses>> call, Throwable t) {
                callback.onError(t);
            }
        });
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
        Call<List<GlobalLeaderboardEntry>> leaderboardEntryCall= apiService.getLeaderboard();
        leaderboardEntryCall.enqueue(new Callback<List<GlobalLeaderboardEntry>>() {
            @Override
            public void onResponse(Call<List<GlobalLeaderboardEntry>> call, Response<List<GlobalLeaderboardEntry>> response) {
                try {
                    callback.onSuccess(response.body());
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public void onFailure(Call<List<GlobalLeaderboardEntry>> call, Throwable t) {
                callback.onError(t);
            }
        });
    }
    public void Leaderboard_Single(LogoutRequest logoutRequest,final LeaderboardResponseSingle callback){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(base_url)
                .addConverterFactory(GsonConverterFactory.create()).build();
        ApiService apiService=retrofit.create(ApiService.class);
        Call<List<LeaderboardData>> leaderboardEntryCall= apiService.getSLeaderboard(logoutRequest);

        leaderboardEntryCall.enqueue(new Callback<List<LeaderboardData>>() {
            @Override
            public void onResponse(Call<List<LeaderboardData>> call, Response<List<LeaderboardData>> response) {
                try {
                    callback.onSuccess(response.body());
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public void onFailure(Call<List<LeaderboardData>> call, Throwable t) {
                callback.onError(t);
            }
        });

    }

}
