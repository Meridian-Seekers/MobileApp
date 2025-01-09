package com.example.finalproject.interfaces;

import com.example.finalproject.models.GlobalLeaderboardEntry;


import java.util.List;

public interface LeaderboardResponse {
    void onSuccess(List<GlobalLeaderboardEntry> Leaderboard) throws Exception;
    void onError(Throwable t);
}

