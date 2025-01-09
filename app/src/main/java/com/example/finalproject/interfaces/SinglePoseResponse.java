package com.example.finalproject.interfaces;

import com.example.finalproject.models.SinglePoses;

import java.util.List;

public interface SinglePoseResponse {
    void onSuccess(List<SinglePoses> singlePosesList);
    void onError(Throwable t);
}
