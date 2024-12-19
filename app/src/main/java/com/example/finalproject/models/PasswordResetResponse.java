package com.example.finalproject.models;

public class PasswordResetResponse {
    public User player;
    private boolean success;
    private String message;

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public void onSuccess(PasswordResetResponse body) {
    }

    public void onError(Throwable passwordResetFailed) {
    }
}