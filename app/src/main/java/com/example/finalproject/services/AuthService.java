package com.example.finalproject.services;

import com.example.finalproject.interfaces.ApiService;
import com.example.finalproject.interfaces.DeleteCallBack;
import com.example.finalproject.interfaces.ResponseCallBack;
import com.example.finalproject.interfaces.ResponseCallBack2;
import com.example.finalproject.interfaces.passwordresponceCallBack;
import com.example.finalproject.models.DeleteAcc;
import com.example.finalproject.models.EmailRequest;
import com.example.finalproject.models.LoginResponse;
import com.example.finalproject.models.LogoutRequest;
import com.example.finalproject.models.PasswordResetRequest;
import com.example.finalproject.models.PasswordResetResponse;
import com.example.finalproject.models.User;
import com.example.finalproject.models.DeleteResponse;
import com.example.finalproject.models.ProcessingStatusResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AuthService {
    private String base_url="http://10.95.145.77:5000/api/";
    public AuthService(){

    }
    public void login(User user, final ResponseCallBack callback){
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(base_url)
                .addConverterFactory(GsonConverterFactory.create()).build();
        ApiService apiService=retrofit.create(ApiService.class);
        Call<LoginResponse> call= apiService.getLogin(user);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                try {
                    callback.onSuccess(response.body());
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                callback.onError(t);
            }
        });
    }
    public LoginResponse signup(User user, ResponseCallBack callBack){
        LoginResponse loginResponse=new LoginResponse();
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(base_url)
                .addConverterFactory(GsonConverterFactory.create()).build();
        ApiService apiService=retrofit.create(ApiService.class);
        Call<LoginResponse> call= apiService.getSignup(user);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                try {
                    callBack.onSuccess(response.body());
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
            callBack.onError(t);
            }
        });
        return loginResponse;
    }
    public void  deleteAcc(DeleteAcc deleteAcc,final DeleteCallBack callback){
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(base_url)
                .addConverterFactory(GsonConverterFactory.create()).build();
        ApiService apiService=retrofit.create(ApiService.class);
        Call<DeleteResponse> call= apiService.getDelete(deleteAcc);
        call.enqueue(new Callback<DeleteResponse>(){
            @Override
            public void onResponse(Call<DeleteResponse> call, Response<DeleteResponse> response) {
                callback.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<DeleteResponse> call, Throwable t) {
                callback.onError(t);
            }


        });


    }
    public LoginResponse update(User user, ResponseCallBack callBack){
        LoginResponse loginResponse=new LoginResponse();
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(base_url)
                .addConverterFactory(GsonConverterFactory.create()).build();
        ApiService apiService=retrofit.create(ApiService.class);
        Call<LoginResponse> call= apiService.getUpdate(user);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                try {
                    callBack.onSuccess(response.body());
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                callBack.onError(t);
            }
        });
        return loginResponse;
    }
    public void logout(LogoutRequest logoutRequest, DeleteCallBack callBack){

        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(base_url)
                .addConverterFactory(GsonConverterFactory.create()).build();
        ApiService apiService=retrofit.create(ApiService.class);
        Call<DeleteResponse> call= apiService.getLogout(logoutRequest);
        call.enqueue(new Callback<DeleteResponse>() {
            @Override
            public void onResponse(Call<DeleteResponse> call, Response<DeleteResponse> response) {
                callBack.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<DeleteResponse> call, Throwable t) {
                callBack.onError(t);
            }
        });

    }
//newly added
    public void validateEmail(String email, final ResponseCallBack callback) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(base_url)
                .addConverterFactory(GsonConverterFactory.create()).build();
        ApiService apiService = retrofit.create(ApiService.class);
        Call<Void> call = apiService.validateEmail(new EmailRequest(email));
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    try {
                        callback.onSuccess(null);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    callback.onError(new Throwable("Email not registered"));
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                callback.onError(t);
            }
        });
    }

    /*public void resetPassword(String email, String newPassword, String resetToken, final passwordresponceCallBack callback) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(base_url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiService apiService = retrofit.create(ApiService.class);
        PasswordResetRequest passwordResetRequest = new PasswordResetRequest(email, newPassword, resetToken);
        Call<PasswordResetResponse> call = apiService.resetPassword(passwordResetRequest);
        call.enqueue(new Callback<PasswordResetResponse>() {
            @Override
            public void onResponse(Call<PasswordResetResponse> call, Response<PasswordResetResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    try {
                        callback.onSuccess(response.body());
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    callback.onError(new Throwable("Password reset failed"));
                }
            }

            @Override
            public void onFailure(Call<PasswordResetResponse> call, Throwable t) {
                callback.onError(t);
            }
        });
    }*/

    /*public void resetPassword(String email, String newPassword, String resetToken, final passwordresponceCallBack callback) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(base_url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiService apiService = retrofit.create(ApiService.class);
        PasswordResetRequest passwordResetRequest = new PasswordResetRequest(email, newPassword, resetToken);
        Call<PasswordResetResponse> call = apiService.resetPassword(passwordResetRequest);
        call.enqueue(new Callback<PasswordResetResponse>() {
            @Override
            public void onResponse(Call<PasswordResetResponse> call, Response<PasswordResetResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    try {
                        callback.onSuccess(response.body());
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    callback.onError(new Throwable("Password reset failed"));
                }
            }

            @Override
            public void onFailure(Call<PasswordResetResponse> call, Throwable t) {
                callback.onError(t);
            }
        });
    }*/

    public void resetPassword(String email,String confirmpassword, String newPassword, final passwordresponceCallBack callback) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(base_url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);
        PasswordResetRequest passwordResetRequest = new PasswordResetRequest(email,confirmpassword, newPassword);
        Call<PasswordResetResponse> call = apiService.resetPassword(passwordResetRequest);
        call.enqueue(new Callback<PasswordResetResponse>() {
            @Override
            public void onResponse(Call<PasswordResetResponse> call, Response<PasswordResetResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    try {
                        callback.onSuccess(response.body());
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    callback.onError(new Throwable("Password reset failed"));
                }
            }

            @Override
            public void onFailure(Call<PasswordResetResponse> call, Throwable t) {
                callback.onError(t);
            }
        });


    }

    public void getProcessingStatus(final ResponseCallBack2<ProcessingStatusResponse> callback) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(base_url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);
        Call<ProcessingStatusResponse> call = apiService.getProcessingStatus();

        call.enqueue(new Callback<ProcessingStatusResponse>() {
            @Override
            public void onResponse(Call<ProcessingStatusResponse> call, Response<ProcessingStatusResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onError(new Throwable("Failed to fetch processing status"));
                }
            }

            @Override
            public void onFailure(Call<ProcessingStatusResponse> call, Throwable t) {
                callback.onError(t);
            }
        });
    }







}
