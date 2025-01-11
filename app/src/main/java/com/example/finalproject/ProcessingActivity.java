package com.example.finalproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.finalproject.interfaces.ApiService;
import com.example.finalproject.models.PoseCountResponse;
import com.example.finalproject.models.RequestBody;
import com.example.finalproject.models.RequestBodyForPoseCount;
import com.example.finalproject.models.ResultResponse;
import com.example.finalproject.services.AppConfig;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProcessingActivity extends AppCompatActivity {

    private int currentResultId = -1; // Initialize with an invalid ID
    private ApiService apiService;
    private Handler handler;
    private Runnable runnable;
    private String userEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_process);

        userEmail = getCurrentUserEmail();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(AppConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(ApiService.class);
        makeApiCall();

        handler = new Handler();

        runnable = new Runnable() {
            @Override
            public void run() {
                makeApiCall();
                handler.postDelayed(this, 1000);
            }
        };

        handler.post(runnable);
    }

    private void makeApiCall() {
        com.example.finalproject.models.RequestBody requestBody = new com.example.finalproject.models.RequestBody(userEmail);

        apiService.getResult(requestBody).enqueue(new Callback<ResultResponse>() {
            @Override
            public void onResponse(Call<ResultResponse> call, Response<ResultResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        int latestResultId = response.body().Result_id();
                        if (currentResultId == -1) {
                            currentResultId = latestResultId;
                            Log.e("API_INITIAL", "Initial Result ID: " + latestResultId);
                        }
                        if (latestResultId != currentResultId) {
                            currentResultId = latestResultId;
                            Log.e("API_SUCCESS", "Completed. Latest Result ID: " + latestResultId);
                            startPoseCountTracking(latestResultId);
                        } else {
                            Log.e("API_ERROR", "Result ID is the same: " + latestResultId);
                        }
                    } else {
                        Log.e("API_ERROR", "Response body is null");
                    }
                } else {
                    String errorMessage = response.message() != null ? response.message() : "Unknown error";
                    Log.e("API_ERROR", "API call failed: " + errorMessage);
                }
            }

            @Override
            public void onFailure(Call<ResultResponse> call, Throwable t) {
                Log.e("API_CALL_FAILURE", "API call failed: " + t.getMessage());
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(runnable);
    }

    private String getCurrentUserEmail() {
        SharedPreferences preferences = getSharedPreferences("user_prefs", MODE_PRIVATE);
        return preferences.getString("user_email", null);
    }

    private void startPoseCountTracking(int resultId) {
        Handler poseCountHandler = new Handler();

        final Runnable[] poseCountRunnable = new Runnable[1];

        poseCountRunnable[0] = new Runnable() {
            private int previousCount = -1;

            @Override
            public void run() {
                fetchPoseCount(resultId, new PoseCountCallback() {
                    @Override
                    public void onCountFetched(int currentCount) {
                        if (currentCount == previousCount) {
                            poseCountHandler.removeCallbacks(poseCountRunnable[0]);
                            Log.e("POSE_COUNT", "Number stopped increasing: " + currentCount);
                            Intent intent = new Intent(ProcessingActivity.this, ViewResultActivity.class);
                            intent.putExtra("resultId", resultId);
                            intent.putExtra("poseCount", currentCount);
                            startActivity(intent);
                            finish();
                        } else {
                            previousCount = currentCount;
                            Log.e("POSE_COUNT", "Current count: " + currentCount);
                            poseCountHandler.postDelayed(poseCountRunnable[0], 1000);
                        }
                    }

                    @Override
                    public void onError(String error) {
                        Log.e("POSE_COUNT_ERROR", "Error fetching count: " + error);
                    }
                });
            }
        };
        poseCountHandler.post(poseCountRunnable[0]);
    }


    private void fetchPoseCount(int resultId, PoseCountCallback callback) {
        RequestBodyForPoseCount requestBody = new RequestBodyForPoseCount(resultId);

        apiService.getPoseCount(requestBody).enqueue(new Callback<PoseCountResponse>() {
            @Override
            public void onResponse(Call<PoseCountResponse> call, Response<PoseCountResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    int count = response.body().getCount();
                    callback.onCountFetched(count);
                } else {
                    String errorMessage = response.message() != null ? response.message() : "Unknown error";
                    callback.onError(errorMessage);
                }
            }

            @Override
            public void onFailure(Call<PoseCountResponse> call, Throwable t) {
                callback.onError(t.getMessage());
            }
        });
    }

    interface PoseCountCallback {
        void onCountFetched(int currentCount);

        void onError(String error);
    }

}
