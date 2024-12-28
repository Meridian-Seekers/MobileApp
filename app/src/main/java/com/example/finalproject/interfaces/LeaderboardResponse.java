package com.example.finalproject.interfaces;

import com.example.finalproject.models.LeaderboardEntry;


import java.util.List;

public interface LeaderboardResponse {
    void onSuccess(List<LeaderboardEntry> Leaderboard) throws Exception;
    void onError(Throwable t);
}

