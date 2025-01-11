package com.example.finalproject.services;

import android.util.Log;

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
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MangerService {
    private String base_url=AppConfig.BASE_URL;
    private static Retrofit retrofit;

    public MangerService(){

    }

    public void get_Single_Poses(int resultId, final SinglePoseResponse callback) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(base_url) // Make sure this is your correct base URL
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiService apiService = retrofit.create(ApiService.class);

        // Pass the result_id as a query parameter to the API
        Call<List<SinglePoses>> listCall = apiService.getSinglePoses(resultId);

        listCall.enqueue(new Callback<List<SinglePoses>>() {
            @Override
            public void onResponse(Call<List<SinglePoses>> call, Response<List<SinglePoses>> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null && !response.body().isEmpty()) {
                        callback.onSuccess(response.body());
                    } else {
                        Log.e("API Response", "Response is empty or null");
                        callback.onError(new Exception("Empty response body"));
                    }
                } else {
                    Log.e("API Response", "Error: " + response.code() + " " + response.message());
                    callback.onError(new Exception("Error: " + response.code()));
                }
            }

            @Override
            public void onFailure(Call<List<SinglePoses>> call, Throwable t) {
                Log.e("API Request", "Request failed: " + t.getMessage());
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
            OkHttpClient client = new OkHttpClient.Builder()
                    .connectTimeout(120, TimeUnit.SECONDS)  // Increase connection timeout
                    .writeTimeout(120, TimeUnit.SECONDS)    // Increase write timeout
                    .readTimeout(120, TimeUnit.SECONDS)     // Increase read timeout
                    .addInterceptor(new LoggingInterceptor())  // Add the logging interceptor
                    .build();
            retrofit = new Retrofit.Builder()
                    .baseUrl(base_url)
                    .client(client)
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
