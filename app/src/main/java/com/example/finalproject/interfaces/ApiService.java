package com.example.finalproject.interfaces;

import com.example.finalproject.LeaderboardData;
import com.example.finalproject.models.DeleteAcc;
import com.example.finalproject.models.DeleteResponse;
import com.example.finalproject.models.EmailRequest;
import com.example.finalproject.models.GlobalLeaderboardEntry;
import com.example.finalproject.models.LoginResponse;
import com.example.finalproject.models.LogoutRequest;
import com.example.finalproject.models.PasswordResetRequest;
import com.example.finalproject.models.PasswordResetResponse;
import com.example.finalproject.models.ProcessingStatusResponse;
import com.example.finalproject.models.SingleLeaderboardEntry;
import com.example.finalproject.models.SinglePoses;
import com.example.finalproject.models.User;
import com.example.finalproject.models.Video;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;

public interface ApiService {
    @POST("login")
    Call<LoginResponse> getLogin(@Body User user);

    @POST("signup")
    Call<LoginResponse> getSignup(@Body User user);
    @POST("logout")
    Call<DeleteResponse> getLogout(@Body LogoutRequest logoutRequest);
    @POST("profile/delete")
    Call<DeleteResponse> getDelete(@Body DeleteAcc deleteAcc);
    @PUT("profile")
    Call<LoginResponse> getUpdate(@Body User user);

    //newly added
    @POST("validate_email")
    Call<Void> validateEmail(@Body EmailRequest emailRequest);

    @GET("all_guide_videos")
    Call<List<Video>> getGuideVideo();

    @POST("password_reset")
    Call<PasswordResetResponse> resetPassword(@Body PasswordResetRequest passwordResetRequest);

    @Multipart
    @POST("upload_video")
    Call<ResponseBody> uploadVideo(
            @Part("email") RequestBody email,
            @Part("video_name") RequestBody videoName,
            @Part MultipartBody.Part videoFile
    );
    @GET("main_model_api/global_leaderboard")
    Call<List<GlobalLeaderboardEntry>> getLeaderboard();

    @POST("main_model_api/single_leaderboard")
    Call<List<LeaderboardData>> getSLeaderboard(@Body LogoutRequest logoutRequest);

    @GET("status") // Replace "status" with your actual endpoint
    Call<ProcessingStatusResponse> getProcessingStatus();


    @GET("get_single_pose_details")
    Call<List<SinglePoses>> getSinglePoses();

}
