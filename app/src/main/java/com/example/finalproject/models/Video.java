package com.example.finalproject.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Video {

    @Expose
    @SerializedName("Guide_video_name")
    private String V_Name;

    @Expose
    @SerializedName("Video_link")
    private String URL;

    @Expose
    @SerializedName("Thumbnail_link")
    private String TURL;


    public String getTURL() {
        return TURL;
    }

    public void setTURL(String TURL) {
        this.TURL = TURL;
    }

    public Video(){
    }

    public String getV_Name() {
        return V_Name;
    }

    public void setV_Name(String v_Name) {
        V_Name = v_Name;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }
}
