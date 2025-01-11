package com.example.finalproject;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

public class ResultData implements Parcelable {
    private float result;
    private String imageUrl;
    private String classname;
    private String correctName;
    private String correctDesc;
    private String correctImg;


    // Constructor
    public ResultData(float result, String imageUrl, String classname, String correctName, String correctDesc, String correctImg) {
        this.result = result;
        this.imageUrl = imageUrl;
        this.classname = classname;
        this.correctName = correctName;
        this.correctDesc = correctDesc;
        this.correctImg = correctImg;
    }

    protected ResultData(Parcel in) {
        result = in.readFloat();
        imageUrl = in.readString();
        classname = in.readString();
        correctName = in.readString();
        correctDesc = in.readString();
        correctImg = in.readString();
    }

    public static final Creator<ResultData> CREATOR = new Creator<ResultData>() {
        @Override
        public ResultData createFromParcel(Parcel in) {
            return new ResultData(in);
        }

        @Override
        public ResultData[] newArray(int size) {
            return new ResultData[size];
        }
    };

    public float getResult() {
        return result;
    }

    public void setResult(float result) {
        this.result = result;
    }

    public Uri getImageUrl() {
        return Uri.parse(imageUrl);
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

    public String getCorrectName() {
        return correctName;
    }

    public void setCorrectName(String correctName) {
        this.correctName = correctName;
    }

    public String getCorrectDesc() {
        return correctDesc;
    }

    public void setCorrectDesc(String correctDesc) {
        this.correctDesc = correctDesc;
    }

    public String getCorrectImg() {
        return correctImg;
    }

    public void setCorrectImg(String correctImg) {
        this.correctImg = correctImg;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeFloat(result);
        dest.writeString(imageUrl);
        dest.writeString(classname);
        dest.writeString(correctName);
        dest.writeString(correctDesc);
        dest.writeString(correctImg);
    }
}
