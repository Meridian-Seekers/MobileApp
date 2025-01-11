package com.example.finalproject;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
            poseNameTextView.setText(resultData.getCorrectName());
            correctPoseDescriptionTextView.setText(resultData.getCorrectDesc());

            ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Loading image...");
            progressDialog.setCancelable(false);
            progressDialog.show();

            Picasso.get()
                    .load("https://drive.google.com/uc?id=" + extractFileId(String.valueOf(resultData.getCorrectImg())))
                    .into(poseImageView, new Callback() {
                        @Override
                        public void onSuccess() {
                            progressDialog.dismiss();
                        }

                        @Override
                        public void onError(Exception e) {
                            progressDialog.dismiss();
                        }
                    });
        } else {
            poseNameTextView.setText("Pose name not provided");
            correctPoseDescriptionTextView.setText("No description available");
        }

        closeButton.setOnClickListener(v -> finish());
    }

    public static String extractFileId(String url) {
        String regex = "drive\\.google\\.com/.*?/d/([a-zA-Z0-9_-]+)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(url);

        if (matcher.find()) {
            return matcher.group(1);
        } else {
            return null;
        }
    }
}
