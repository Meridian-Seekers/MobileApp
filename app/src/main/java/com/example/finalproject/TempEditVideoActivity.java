package com.example.finalproject;

import static android.content.ContentValues.TAG;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.gowtham.library.utils.LogMessage;
import com.gowtham.library.utils.TrimVideo;

public class TempEditVideoActivity extends AppCompatActivity {

    private Uri videoUri;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_temp_edit_video);

        Intent intent = getIntent();
        videoUri = intent.getData();
        if (videoUri != null) {
            Toast.makeText(this, "Done", Toast.LENGTH_LONG).show();
            //ActivityResultLauncher<Intent> startForResult = ;
            TrimVideo.activity(String.valueOf(videoUri))
                    .setHideSeekBar(true)
                    .start(this,registerForActivityResult(
                            new ActivityResultContracts.StartActivityForResult(),
                            result -> {
                                if (result.getResultCode() == Activity.RESULT_OK &&
                                        result.getData() != null) {
                                    Uri trimmedUri = Uri.parse(TrimVideo.getTrimmedVideoPath(result.getData()));
                                    Log.d(TAG, "Trimmed path:: " + trimmedUri);

                                    // Start ProcessingActivity with the trimmed video URI
                                    Intent processingIntent = new Intent(TempEditVideoActivity.this, ProcessingActivity.class);
                                    processingIntent.setData(trimmedUri);
                                    startActivity(processingIntent);
                                    finish(); // Close the current activity
                                } else {
                                    LogMessage.v("videoTrimResultLauncher data is null");
                                }
                            }));
        } else {
            Toast.makeText(this, "No video URI received", Toast.LENGTH_LONG).show();
        }





    }



}