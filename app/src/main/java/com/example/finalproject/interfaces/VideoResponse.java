package com.example.finalproject.interfaces;


import com.example.finalproject.models.Video;

import java.util.List;

public interface VideoResponse {
    void onSuccess(List<Video> ListVideo) throws Exception;
    void onError(Throwable t);
}
