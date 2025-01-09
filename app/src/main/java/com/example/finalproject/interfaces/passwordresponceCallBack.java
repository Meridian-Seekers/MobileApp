package com.example.finalproject.interfaces;

import com.example.finalproject.models.LoginResponse;
import com.example.finalproject.models.PasswordResetResponse;

public interface passwordresponceCallBack {
    void onSuccess(PasswordResetResponse passwordResetResponse);
    void onError(Throwable t);
}
