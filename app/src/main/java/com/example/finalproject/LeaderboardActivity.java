package com.example.finalproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalproject.interfaces.LeaderboardResponse;
import com.example.finalproject.interfaces.LeaderboardResponseSingle;
import com.example.finalproject.models.GlobalLeaderboardEntry;
import com.example.finalproject.models.LogoutRequest;
import com.example.finalproject.models.SingleLeaderboardEntry;
import com.example.finalproject.services.MangerService;

import java.util.ArrayList;
import java.util.List;

public class LeaderboardActivity extends AppCompatActivity {

    private boolean isLeaderboard1Visible = true;
    private RecyclerView recyclerViewSinglePerformance;
    private RecyclerView recyclerViewGlobalRank;
    private LeaderboardAdapter adapterSinglePerformance;
    private Button switchButton;
    private ImageView homeButton;
    private Spinner timePeriodSpinner;
    private ViewSwitcher leaderboardSwitcher;
    private String selectedTimePeriod = "All Time";
    private List<LeaderboardData> leaderboardDataList;
    private  List<GlobalLeaderboardData> globalLeaderboardList;
    private DBHelper dbHelper;
    private String currentUserEmail;
    private GlobalLeaderboardAdapter adapterGlobalRank;
    private TextView textViewSinglePerformance;
    private TextView textViewGlobalRank;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leadboard);
        initializeViews();

        dbHelper = new DBHelper(this);
        currentUserEmail = getCurrentUserEmail();

        leaderboardDataList = new ArrayList<>();
        globalLeaderboardList=new ArrayList<>();


        // Set initial colors
        updateTextViewColors();

        switchButton.setOnClickListener(v -> {
            switchLeaderboards();
            updateTextViewColors();

        });

        setupNavigationButtons();

        adapterSinglePerformance = new LeaderboardAdapter(this, leaderboardDataList);
        recyclerViewSinglePerformance.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewSinglePerformance.setAdapter(adapterSinglePerformance);

        adapterGlobalRank = new GlobalLeaderboardAdapter(this, globalLeaderboardList);
        recyclerViewGlobalRank.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewGlobalRank.setAdapter(adapterGlobalRank);

        loadSingleLeaderboard();

        timePeriodSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedTimePeriod = parent.getItemAtPosition(position).toString();
//                setupRecyclerView(); // Reload data based on selected time period
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });
    }

    private void initializeViews() {
        recyclerViewSinglePerformance = findViewById(R.id.recyclerViewSinglePerformance);
        recyclerViewGlobalRank = findViewById(R.id.recyclerViewGlobalRank);
        switchButton = findViewById(R.id.switchButton);
        homeButton = findViewById(R.id.homebtn);
        timePeriodSpinner = findViewById(R.id.timePeriodSpinner);
        leaderboardSwitcher = findViewById(R.id.leaderboardSwitcher);
        textViewSinglePerformance = findViewById(R.id.textView15);
        textViewGlobalRank = findViewById(R.id.textView16);
    }



    private void loadSingleLeaderboard() {
        MangerService managerService = new MangerService();
        LogoutRequest req = new LogoutRequest();
        SharedPreferences preferences = getSharedPreferences("user_prefs", MODE_PRIVATE);
        req.setEmail(preferences.getString("user_email", ""));

        managerService.Leaderboard_Single(req, new LeaderboardResponseSingle() {
            @Override
            public void onSuccess(List<LeaderboardData> leaderboard) {
                new Handler(Looper.getMainLooper()).post(() -> {
                    leaderboardDataList.clear();
                    leaderboardDataList.addAll(leaderboard);
                    adapterSinglePerformance.notifyDataSetChanged();
                });
            }

            @Override
            public void onError(Throwable t) {
                // Log or show an error message
            }
        });
    }

    private void loadGlobalLeaderboard() {
        MangerService managerService = new MangerService();
        managerService.LeaderboardData(new LeaderboardResponse() {
            @Override
            public void onSuccess(List<GlobalLeaderboardEntry> leaderboard) {
                new Handler(Looper.getMainLooper()).post(() -> {
                    globalLeaderboardList.clear();
                    for (GlobalLeaderboardEntry entry : leaderboard) {
                        globalLeaderboardList.add(new GlobalLeaderboardData(entry.getF_name(), entry.getL_name(), entry.getScore()));
                    }
                    adapterGlobalRank.notifyDataSetChanged();
                });
            }

            @Override
            public void onError(Throwable t) {
                // Log or show an error message
            }
        });
    }

    private String getCurrentUserEmail() {
        SharedPreferences preferences = getSharedPreferences("user_prefs", MODE_PRIVATE);
        return preferences.getString("user_email", "");
    }

    private void switchLeaderboards() {
        isLeaderboard1Visible = !isLeaderboard1Visible;
        leaderboardSwitcher.showNext();
        if(!isLeaderboard1Visible){
            loadGlobalLeaderboard();
        }else{
            loadSingleLeaderboard();
        }
    }

    private void updateTextViewColors() {
        if (leaderboardSwitcher.getDisplayedChild() == 0) {
            textViewSinglePerformance.setTextColor(getResources().getColor(R.color.active_color));
            textViewGlobalRank.setTextColor(getResources().getColor(R.color.inactive_color));
        } else {
            textViewSinglePerformance.setTextColor(getResources().getColor(R.color.inactive_color));
            textViewGlobalRank.setTextColor(getResources().getColor(R.color.active_color));
        }
    }

    private void setupNavigationButtons() {
        homeButton.setOnClickListener(v -> navigateToActivity(HomepageActivity.class));

        findViewById(R.id.imageView6).setOnClickListener(v -> navigateToActivity(Guide_video_Activity.class));
        findViewById(R.id.imageView7).setOnClickListener(v -> navigateToActivity(LeaderboardActivity.class));

        findViewById(R.id.imageView8).setOnClickListener(v -> {
            Intent intent = new Intent(LeaderboardActivity.this, ViewProfileActivity.class);
            intent.putExtra("userRank", 6); // Replace with actual rank from leaderboard logic
            startActivity(intent);
        });

        findViewById(R.id.insertvideo).setOnClickListener(v -> navigateToActivity(PopupUpload.class));
    }

    private void navigateToActivity(Class<?> activityClass) {
        Intent intent = new Intent(LeaderboardActivity.this, activityClass);
        startActivity(intent);
        finish();
    }
}
