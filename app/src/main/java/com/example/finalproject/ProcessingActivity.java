package com.example.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.finalproject.models.ProcessingStatusResponse;
import com.example.finalproject.services.AuthService;
import com.example.finalproject.interfaces.ResponseCallBack2;

public class ProcessingActivity extends AppCompatActivity {

    ProgressBar progress;
    Handler handler = new Handler();
    AuthService authService;

    private volatile boolean isProcessingComplete = false; // Ensure thread safety

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_process);

        progress = findViewById(R.id.progressBar);
        authService = new AuthService();

        // Start backend processing check
        performBackendProcessing();
    }

    private void performBackendProcessing() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (!isProcessingComplete) {
                    checkBackendProcessingStatus(new ResponseCallBack2<ProcessingStatusResponse>() {
                        @Override
                        public void onSuccess(ProcessingStatusResponse response) {
                            if (response.isComplete()) {
                                isProcessingComplete = true;
                            }
                        }

                        @Override
                        public void onError(Throwable t) {
                            t.printStackTrace(); // Log error or handle it appropriately
                        }
                    });

                    if (!isProcessingComplete) {
                        android.os.SystemClock.sleep(1000); // Wait for 1 second before next check
                    }
                }

                // Processing complete; navigate to next activity
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(ProcessingActivity.this, ViewResultActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
            }
        }).start();
    }

    private void checkBackendProcessingStatus(ResponseCallBack2<ProcessingStatusResponse> callback) {
        authService.getProcessingStatus(callback);
    }
}
