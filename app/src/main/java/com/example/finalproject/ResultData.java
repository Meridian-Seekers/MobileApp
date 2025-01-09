package com.example.finalproject;

import android.net.Uri;

public class ResultData {
    private float result;
    private String imageUrl;
    private String classname;
    private Uri uri;

    public ResultData(float result, String imageUrl, String classname) {
        this.result = result;
        this.uri  = Uri.parse(imageUrl);
        this.classname = classname;
    }

    public float getResult() {
        return result;
    }

    public void setResult(float result) {
        this.result = result;
    }

    public Uri getImageUrl() {

        return uri;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getClassname() {
        return classname;
    }

    public void setClassname(String classname) {
        this.classname = classname;
    }
}
