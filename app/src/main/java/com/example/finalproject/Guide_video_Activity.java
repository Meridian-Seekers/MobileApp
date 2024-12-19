package com.example.finalproject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalproject.interfaces.VideoResponse;
import com.example.finalproject.models.Video;
import com.example.finalproject.services.MangerService;

import java.util.ArrayList;
import java.util.List;

public class Guide_video_Activity extends AppCompatActivity implements VideoAdapter.ItemClickListener {

    private RecyclerView recyclerView;
    private SearchView searchView;
    private TextView textView13;
    private VideoView videoView;
    private VideoAdapter adapter;
    private List<VideoItem> videoList;
    private List<VideoItem> filteredList;
    private ImageView homeButton;
    private boolean isNoResultsDisplayed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide_video);

        // Initialize views
        recyclerView = findViewById(R.id.recyclerView);
        searchView = findViewById(R.id.searchView);
        textView13 = findViewById(R.id.textView13);
        videoView = findViewById(R.id.videoView);
        homeButton = findViewById(R.id.homebtn);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize the video list and adapter
        videoList = new ArrayList<>();
        filteredList = new ArrayList<>();
        adapter = new VideoAdapter(this, videoList);

        // Set the adapter to the RecyclerView
        recyclerView.setAdapter(adapter);
        adapter.setClickListener(this);

        // Fetch videos using the MangerService
        MangerService mangerService = new MangerService();
        mangerService.getGuideVideos(new VideoResponse() {
            @Override
            public void onSuccess(List<Video> ListVideo) {
                Log.i("Load", "Successful");

                // Clear the existing videoList
                videoList.clear();

                // Convert ListVideo to VideoItem and add to videoList
                for (Video video : ListVideo) {
                    videoList.add(new VideoItem(video.getV_Name(), video.getURL(), video.getTURL()));
                    Log.i("VName", ListVideo.toString());
                }

                // Notify the adapter about data changes
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onError(Throwable t) {
                Log.e("Load", "Failed to load videos", t);
            }
        });

        // Set up SearchView
        /*
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                adapter.getFilter().filter(newText, new Filter.FilterListener() {
                    @Override
                    public void onFilterComplete(int count) {
                        if (count == 0) {
                            // Check if the newText is not empty to avoid showing error on empty input
                            if (!newText.isEmpty() && !isNoResultsDisplayed) {
                                Toast.makeText(Guide_video_Activity.this, "No search results found", Toast.LENGTH_SHORT).show();
                                isNoResultsDisplayed = true;
                            }
                        } else {
                            isNoResultsDisplayed = false;
                        }
                    }
                });
                return true;
            }
        });*/

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterVideos(newText);
                return true;
            }
        });


        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to home activity
                Intent intent = new Intent(Guide_video_Activity.this, HomepageActivity.class);
                startActivity(intent);
                finish();
            }
        });

        // Find the ImageViews and set OnClickListeners
        ImageView imageView6 = findViewById(R.id.imageView6);
        imageView6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Intent to start Guide_video_Activity
                Intent intent = new Intent(Guide_video_Activity.this, Guide_video_Activity.class);
                startActivity(intent);
            }
        });

        ImageView imageView7 = findViewById(R.id.imageView7);
        imageView7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Intent to start LeaderboardActivity
                Intent intent = new Intent(Guide_video_Activity.this, LeaderboardActivity.class);
                startActivity(intent);
            }
        });

        ImageView imageView8 = findViewById(R.id.imageView8);
        imageView8.setOnClickListener(v -> {
            // Pass the rank to ViewProfileActivity
            Intent intent = new Intent(Guide_video_Activity.this, ViewProfileActivity.class);
            intent.putExtra("userRank", 6); // Replace 6 with the actual rank from your leaderboard logic
            startActivity(intent);
        });

        ImageView insertVideo = findViewById(R.id.insertvideo);
        insertVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Intent to start PopupUpload
                Intent intent = new Intent(Guide_video_Activity.this, PopupUpload.class);
                startActivity(intent);
            }
        });
    }

    private void filterVideos(String query) {
        if (query.isEmpty()) {
            adapter = new VideoAdapter(this, videoList);
        } else {
            filteredList.clear();
            for (VideoItem video : videoList) {
                if (video.getTitle().toLowerCase().contains(query.toLowerCase())) {
                    filteredList.add(video);
                }
            }
            if (filteredList.isEmpty()) {
                Toast.makeText(this, "No search results found", Toast.LENGTH_SHORT).show();
            }
            adapter = new VideoAdapter(this, filteredList);
        }

        recyclerView.setAdapter(adapter);
        adapter.setClickListener(this);
    }


    @Override
    public void onItemClick(View view, int position) {
        VideoItem selectedItem = adapter.getItem(position);
        if (selectedItem != null) {
            Uri videoUri = Uri.parse(selectedItem.getVideoUri());
            ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Loading video...");
            progressDialog.setCancelable(false);
            progressDialog.show();

            videoView.setVideoURI(videoUri);
            videoView.setMediaController(new MediaController(this));
            videoView.requestFocus();

            videoView.setOnPreparedListener(mediaPlayer -> {
                progressDialog.dismiss();
                videoView.start();
            });
            videoView.setOnErrorListener((mediaPlayer, what, extra) -> {
                progressDialog.dismiss();
                Toast.makeText(this, "Error loading video. Please try again.", Toast.LENGTH_SHORT).show();
                return true;
            });
        }
    }


}