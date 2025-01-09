package com.example.finalproject;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LeaderboardData {
    @Expose
    @SerializedName("video_name")
    private String videoname;

    @Expose
    @SerializedName("final_score")
    private double score;

    public String getVideoname() {
        return videoname;
    }

    public void setVideoname(String videoname) {
        this.videoname = videoname;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }



    public LeaderboardData(String videoname, double score) {
        this.videoname = videoname;
        this.score = score;
    }


}
