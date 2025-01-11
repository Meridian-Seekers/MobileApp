package com.example.finalproject;

import static android.content.ContentValues.TAG;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.example.finalproject.interfaces.ApiService;
import com.example.finalproject.models.Up_Video;
import com.example.finalproject.services.MangerService;
import com.gowtham.library.utils.LogMessage;
import com.gowtham.library.utils.TrimVideo;

import java.io.File;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TempEditVideoActivity extends AppCompatActivity {

    private Uri videoUri;
    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temp_edit_video);

        Intent intent = getIntent();
        videoUri = intent.getData();
        if (videoUri != null) {
            Toast.makeText(this, "Video received, starting trim", Toast.LENGTH_LONG).show();

            TrimVideo.activity(String.valueOf(videoUri))
                    .setHideSeekBar(true)
                    .start(this, registerForActivityResult(
                            new ActivityResultContracts.StartActivityForResult(),
                            result -> {
                                if (result.getResultCode() == Activity.RESULT_OK &&
                                        result.getData() != null) {
                                    Uri trimmedUri = Uri.parse(TrimVideo.getTrimmedVideoPath(result.getData()));
                                    Log.d(TAG, "Trimmed path:: " + trimmedUri);
                                    SharedPreferences preferences = getSharedPreferences("user_prefs", MODE_PRIVATE);
                                    email= preferences.getString("user_email", null);
                                    // Upload the trimmed video to the backend
                                    uploadTrimmedVideo(trimmedUri, "Sample Title", email);
                                    Intent intsent = new Intent(TempEditVideoActivity.this, ProcessingActivity.class);
                                    startActivity(intsent);
                                    finish();
                                } else {
                                    LogMessage.v("videoTrimResultLauncher data is null");
                                }
                            }));
        } else {
            Toast.makeText(this, "No video URI received", Toast.LENGTH_LONG).show();
        }
    }

    private void uploadTrimmedVideo(Uri videoUri, String title, String email) {
        // Convert Uri to File
        File videoFile = new File(videoUri.getPath());
        if (!videoFile.exists()) {
            Toast.makeText(this, "Video file not found", Toast.LENGTH_SHORT).show();
            return;
        }

        // Prepare video file
        RequestBody videoRequestBody = RequestBody.create(MediaType.parse("video/*"), videoFile);
        MultipartBody.Part videoPart = MultipartBody.Part.createFormData("video", videoFile.getName(), videoRequestBody);

        // Prepare title and description
        RequestBody titleRequestBody = RequestBody.create(MediaType.parse("text/plain"), title);
        RequestBody emailRequestBody = RequestBody.create(MediaType.parse("text/plain"), email);

        // Make the API call
        ApiService apiService = new MangerService().getRetrofitInstance().create(ApiService.class);
        Call<ResponseBody> call = apiService.uploadVideo(emailRequestBody,titleRequestBody, videoPart);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        // Log the response body
                        String responseBody = response.body() != null ? response.body().string() : "Response body is null";
                        Log.d(TAG, "Video uploaded successfully: " + responseBody);

                        Toast.makeText(TempEditVideoActivity.this, "Upload successful!", Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        Log.e(TAG, "Error reading response body: " + e.getMessage());
                    }
                } else {
                    try {
                        // Log the error response body
                        String errorBody = response.errorBody() != null ? response.errorBody().string() : "Error body is null";
                        Log.e(TAG, "Upload failed with error: " + errorBody);

                        Toast.makeText(TempEditVideoActivity.this, "Upload failed!", Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        Log.e(TAG, "Error reading error body: " + e.getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                // Log the failure message
                Log.e(TAG, "Error during API call: " + t.getMessage(), t);

                Toast.makeText(TempEditVideoActivity.this, "Error during upload", Toast.LENGTH_SHORT).show();
            }
        });
        
    }
}
