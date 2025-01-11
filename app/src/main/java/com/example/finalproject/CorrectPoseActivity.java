package com.example.finalproject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class CorrectPoseActivity extends AppCompatActivity {

    ImageButton closeButton;
    TextView correctPoseDescriptionTextView;
    TextView poseNameTextView;

    ImageView poseImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_correct_pose_details);

        closeButton = findViewById(R.id.closebtn);
        correctPoseDescriptionTextView = findViewById(R.id.poseDesc);
        poseNameTextView = findViewById(R.id.posename);
        poseImageView = findViewById(R.id.imageview);

        ResultData resultData = getIntent().getParcelableExtra("resultData");

        if (resultData != null) {
            // Display the values in a Toast
            String message = "Result: " + resultData.getResult() +
                    ", Class: " + resultData.getClassname() +
                    ", Image URL: " + resultData.getImageUrl().toString() +
                    ", Correct Name: " + resultData.getCorrectName() +
                    ", Correct Description: " + resultData.getCorrectDesc() +
                    ", Correct Img: " + resultData.getCorrectImg();
            //Toast.makeText(this, message, Toast.LENGTH_LONG).show();

            String googleDriveUrl = resultData.getCorrectImg();
            convertToDirectLink(googleDriveUrl);

            // Set the values to the corresponding TextViews
            poseNameTextView.setText(resultData.getCorrectName());
            correctPoseDescriptionTextView.setText(resultData.getCorrectDesc());

            Glide.with(CorrectPoseActivity.this)
                    .load("https://drive.google.com/uc?export=download&id=1G44Q3D17pGOQOiUunRobkpfaFj1RYQlc")
                    .into(poseImageView);


        }

        if (resultData != null) {




        } else {
            poseNameTextView.setText("Pose name not provided");
            correctPoseDescriptionTextView.setText("No description available");
        }

        closeButton.setOnClickListener(v -> finish());
    }

    private void convertToDirectLink(String driveUrl) {
        if (driveUrl.contains("drive.google.com")) {
            String[] parts = driveUrl.split("/");
            if (parts.length > 5) {
                driveUrl = "https://drive.google.com/uc?id=" + parts[5];
                Glide.with(CorrectPoseActivity.this)
                        .load(driveUrl)
                        .into(poseImageView);
            }
        }
    }
}
