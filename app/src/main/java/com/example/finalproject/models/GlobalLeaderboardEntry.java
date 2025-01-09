package com.example.finalproject.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GlobalLeaderboardEntry {

    @Expose
    @SerializedName("rank_p")
    private int rank;

    @Expose
    @SerializedName("first_name")
    private String F_name;

    @Expose
    @SerializedName("last_name")
    private String L_name;

    @Expose
    @SerializedName("final_score")
    private float Score;


    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }
    public String getF_name() {
        return F_name;
    }

    public void setF_name(String f_name) {
        F_name = f_name;
    }

    public String getL_name() {
        return L_name;
    }

    public void setL_name(String l_name) {
        L_name = l_name;
    }

    public float getScore() {
        return Score;
    }

    public void setScore(int score) {
        Score = score;
    }


}
