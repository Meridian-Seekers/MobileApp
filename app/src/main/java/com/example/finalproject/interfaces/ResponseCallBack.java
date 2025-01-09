package com.example.finalproject.interfaces;

import com.example.finalproject.models.LoginResponse;

public interface ResponseCallBack {
        void onSuccess(LoginResponse loginResponse);
        void onError(Throwable t);
}
