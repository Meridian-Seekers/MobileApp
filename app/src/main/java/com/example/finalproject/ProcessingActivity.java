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

        // Initialize Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(AppConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(ApiService.class);

        // Call the API once to get the initial result ID
        makeApiCall();

        handler = new Handler();

        // Runnable to make the API call every second
        runnable = new Runnable() {
            @Override
            public void run() {
                makeApiCall(); // Check for changes every second
                handler.postDelayed(this, 1000); // Repeat every 1 second
            }
        };

        // Start the clock process after the first API call
        handler.post(runnable);
    }

    private void makeApiCall() {
        // Use the correct RequestBody model here
        com.example.finalproject.models.RequestBody requestBody = new com.example.finalproject.models.RequestBody(userEmail);

        apiService.getResult(requestBody).enqueue(new Callback<ResultResponse>() {
            @Override
            public void onResponse(Call<ResultResponse> call, Response<ResultResponse> response) {
                if (response.isSuccessful()) {
                    // If the response is successful and the body is not null
                    if (response.body() != null) {
                        int latestResultId = response.body().Result_id(); // Assuming this is the correct getter

                        // If this is the first call, store the result ID
                        if (currentResultId == -1) {
                            currentResultId = latestResultId;
                            Log.e("API_INITIAL", "Initial Result ID: " + latestResultId);
                        }

                        // Check if result ID has changed
                        if (latestResultId != currentResultId) {
                            currentResultId = latestResultId;
                            // Show toast with the updated result ID
                            //Toast.makeText(ProcessingActivity.this, "Completed. Latest Result ID: " + latestResultId, Toast.LENGTH_SHORT).show();
                            Log.e("API_SUCCESS", "Completed. Latest Result ID: " + latestResultId);

                            // Optionally, you can trigger a new activity if desired:
                            //Intent intent = new Intent(ProcessingActivity.this, ViewResultActivity.class);
                            //intent.putExtra("resultId", latestResultId);
                            //startActivity(intent);
                            //finish();
                            startPoseCountTracking(latestResultId);
                        } else {
                            // Optionally show a toast for no change in ID
                            Log.e("API_ERROR", "Result ID is the same: " + latestResultId);
                        }
                    } else {
                        // Handle case where body is null
                        Log.e("API_ERROR", "Response body is null");
                    }
                } else {
                    // If the response is not successful, show the error message
                    String errorMessage = response.message() != null ? response.message() : "Unknown error";
                    Log.e("API_ERROR", "API call failed: " + errorMessage);
                }
            }

            @Override
            public void onFailure(Call<ResultResponse> call, Throwable t) {
                // Handle failure (e.g., network issues)
                Log.e("API_CALL_FAILURE", "API call failed: " + t.getMessage());
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Stop the runnable when the activity is destroyed
        handler.removeCallbacks(runnable);
    }

    private String getCurrentUserEmail() {
        SharedPreferences preferences = getSharedPreferences("user_prefs", MODE_PRIVATE);
        return preferences.getString("user_email", null); // Return null if email not found
    }

    private void startPoseCountTracking(int resultId) {
        Handler poseCountHandler = new Handler(); // Separate handler for pose count

        final Runnable[] poseCountRunnable = new Runnable[1]; // Use an array to initialize it later

        poseCountRunnable[0] = new Runnable() {
            private int previousCount = -1; // To track the last count

            @Override
            public void run() {
                fetchPoseCount(resultId, new PoseCountCallback() {
                    @Override
                    public void onCountFetched(int currentCount) {
                        if (currentCount == previousCount) {
                            // Stop tracking and show a toast when the number stops increasing
                            poseCountHandler.removeCallbacks(poseCountRunnable[0]); // Use the array reference
                            Toast.makeText(ProcessingActivity.this,
                                    "Now the number is not increasing: " + currentCount, Toast.LENGTH_SHORT).show();
                            Log.e("POSE_COUNT", "Number stopped increasing: " + currentCount);
                            //Toast.makeText(ProcessingActivity.this,"Poses : " + currentCount + "Result ID : " + resultId,Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(ProcessingActivity.this, ViewResultActivity.class);
                            intent.putExtra("resultId", resultId);
                            intent.putExtra("poseCount", currentCount);
                            startActivity(intent);
                            finish();
                        } else {
                            previousCount = currentCount;
                            Log.e("POSE_COUNT", "Current count: " + currentCount);
                            poseCountHandler.postDelayed(poseCountRunnable[0], 1000); // Use the array reference
                        }
                    }

                    @Override
                    public void onError(String error) {
                        Log.e("POSE_COUNT_ERROR", "Error fetching count: " + error);
                    }
                });
            }
        };

        // Start the runnable
        poseCountHandler.post(poseCountRunnable[0]);
    }


    private void fetchPoseCount(int resultId, PoseCountCallback callback) {
        // Request body for pose count
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
