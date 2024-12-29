package com.example.finalproject.interfaces;

import com.example.finalproject.LeaderboardData;
import com.example.finalproject.models.SingleLeaderboardEntry;

import java.util.List;

public interface LeaderboardResponseSingle {
    void onSuccess(List<LeaderboardData> Leaderboard);
    void onError(Throwable t);
}
