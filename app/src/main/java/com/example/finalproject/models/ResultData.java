package com.example.finalproject.models;

public class ResultData {

    private float result;
    private String imageUrl;
    private String classname;

    public ResultData(float result, String imageUrl, String classname) {
        this.result = result;
        this.imageUrl = imageUrl;
        this.classname = classname;
    }

    public float getResult() {
        return result;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getClassname() {
        return classname;
    }
}

