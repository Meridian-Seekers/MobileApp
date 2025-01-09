package com.example.finalproject.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SinglePoses {
    @Expose
    @SerializedName("Single_pose_name")
    private String single_pose_name;

    @Expose
    @SerializedName("Single_Pose_Img_link")
    private String single_pose_image_link;
    @Expose
    @SerializedName("Single_pose_result")
    private float single_pose_result;
    @Expose
    @SerializedName("Correct_Pose_name")
    private String correct_pose_name;
    @Expose
    @SerializedName("Pose_Details")
    private String correct_pose_details;
    @Expose
    @SerializedName("Correct_Pose_Img_link")
    private String correct_pose_image_link;

    public String getSingle_pose_name() {
        return single_pose_name;
    }

    public void setSingle_pose_name(String single_pose_name) {
        this.single_pose_name = single_pose_name;
    }

    public String getSingle_pose_image_link() {
        return single_pose_image_link;
    }

    public void setSingle_pose_image_link(String single_pose_image_link) {
        this.single_pose_image_link = single_pose_image_link;
    }

    public float getSingle_pose_result() {
        return single_pose_result;
    }

    public void setSingle_pose_result(float single_pose_result) {
        this.single_pose_result = single_pose_result;
    }

    public String getCorrect_pose_name() {
        return correct_pose_name;
    }

    public void setCorrect_pose_name(String correct_pose_name) {
        this.correct_pose_name = correct_pose_name;
    }

    public String getCorrect_pose_details() {
        return correct_pose_details;
    }

    public void setCorrect_pose_details(String correct_pose_details) {
        this.correct_pose_details = correct_pose_details;
    }

    public String getCorrect_pose_image_link() {
        return correct_pose_image_link;
    }

    public void setCorrect_pose_image_link(String correct_pose_image_link) {
        this.correct_pose_image_link = correct_pose_image_link;
    }
}
