package com.example.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalproject.interfaces.SinglePoseResponse;
import com.example.finalproject.models.LogoutRequest;
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

        MangerService mangerService=new MangerService();
        mangerService.get_Single_Poses(new SinglePoseResponse() {
            @Override
            public void onSuccess(List<SinglePoses> singlePosesList) {


                for (SinglePoses singlePoses: singlePosesList){
                    resultsDataList.add(new ResultData(singlePoses.getSingle_pose_result(),singlePoses.getSingle_pose_image_link(),singlePoses.getSingle_pose_name()));
                }

                recyclerView.setAdapter(recyclerAdapter);
            }

            @Override
            public void onError(Throwable t) {

            }
        });




        homebtn = findViewById(R.id.homebtn);
        homebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewResultActivity.this, HomepageActivity.class);
                startActivity(intent);
            }
        });

        // Find the ImageViews and set OnClickListeners
        ImageView imageView6 = findViewById(R.id.imageView6);
        imageView6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Intent to start Guide_video_Activity
                Intent intent = new Intent(ViewResultActivity.this, Guide_video_Activity.class);
                startActivity(intent);
            }
        });
        ImageView insertVideo = findViewById(R.id.insertvideo);
        insertVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Intent to start PopupUpload
                Intent intent = new Intent(ViewResultActivity.this, PopupUpload.class);
                startActivity(intent);
            }
        });

        ImageView imageView7 = findViewById(R.id.imageView7);
        imageView7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Intent to start LeaderboardActivity
                Intent intent = new Intent(ViewResultActivity.this, LeaderboardActivity.class);
                startActivity(intent);
            }
        });

        ImageView imageView8 = findViewById(R.id.imageView8);
        imageView8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Intent to start ViewProfileActivity
                Intent intent = new Intent(ViewResultActivity.this, ViewProfileActivity.class);
                startActivity(intent);
            }
        });
    }



    @Override
    public void onItemClick(ResultData resultData) {
        Intent intent = new Intent(ViewResultActivity.this, CorrectPoseActivity.class);
        intent.putExtra("pose_name", resultData.getClassname());
        startActivity(intent);
    }
}
