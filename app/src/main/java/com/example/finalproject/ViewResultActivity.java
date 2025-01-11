package com.example.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.finalproject.interfaces.SinglePoseResponse;
import com.example.finalproject.models.SinglePoses;
import com.example.finalproject.services.MangerService;

import java.util.ArrayList;
import java.util.List;

public class ViewResultActivity extends AppCompatActivity implements OnItemClickListener {

    private RecyclerView recyclerView;
    private RecyclerAdapter recyclerAdapter;
    private List<ResultData> resultsDataList;
    private ImageView homebtn;

    TextView kataname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results_recycler_page);

        recyclerView = findViewById(R.id.recyclerView_result);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        resultsDataList = new ArrayList<>();
        recyclerAdapter = new RecyclerAdapter(this, resultsDataList, this);

        Intent intent = getIntent();
        int resultId = intent.getIntExtra("resultId", -1);
        int resultCount = intent.getIntExtra("poseCount", -1);
        Toast.makeText(ViewResultActivity.this,"Poses : " + resultCount + "Result ID : " + resultId,Toast.LENGTH_LONG).show();


        if (resultId != -1) {
            MangerService mangerService = new MangerService();
            mangerService.get_Single_Poses(resultId, new SinglePoseResponse() {
                @Override
                public void onSuccess(List<SinglePoses> singlePosesList) {
                    for (SinglePoses singlePoses : singlePosesList) {
                        resultsDataList.add(new ResultData(singlePoses.getSingle_pose_result(),
                                singlePoses.getSingle_pose_image_link(),
                                singlePoses.getSingle_pose_name(),
                                singlePoses.getCorrect_pose_name(),
                                singlePoses.getCorrect_pose_details(),
                                singlePoses.getCorrect_pose_image_link()));
                    }
                    recyclerView.setAdapter(recyclerAdapter);
                }

                @Override
                public void onError(Throwable t) {
                    Log.e("Error", "Error while fetching poses: " + t.getMessage());
                }
            });
        }

        homebtn = findViewById(R.id.homebtn);
        homebtn.setOnClickListener(v -> {
            Intent homeIntent = new Intent(ViewResultActivity.this, HomepageActivity.class);
            startActivity(homeIntent);
        });

        // Intent for other buttons
        findViewById(R.id.imageView6).setOnClickListener(v -> startActivity(new Intent(ViewResultActivity.this, Guide_video_Activity.class)));
        findViewById(R.id.insertvideo).setOnClickListener(v -> startActivity(new Intent(ViewResultActivity.this, PopupUpload.class)));
        findViewById(R.id.imageView7).setOnClickListener(v -> startActivity(new Intent(ViewResultActivity.this, LeaderboardActivity.class)));
        findViewById(R.id.imageView8).setOnClickListener(v -> startActivity(new Intent(ViewResultActivity.this, ViewProfileActivity.class)));
    }

    @Override
    public void onItemClick(ResultData resultData) {
        Intent intent = new Intent(ViewResultActivity.this, CorrectPoseActivity.class);
        intent.putExtra("pose_name", resultData.getClassname());
        intent.putExtra("image_url", resultData.getImageUrl());
        intent.putExtra("pose_result", resultData.getResult());
        startActivity(intent);
    }
}
