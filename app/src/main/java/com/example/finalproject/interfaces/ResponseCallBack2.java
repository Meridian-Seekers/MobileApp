package com.example.finalproject.interfaces;

import com.example.finalproject.models.ProcessingStatusResponse;

public interface ResponseCallBack2<T> {
    void onSuccess(T response);
    void onError(Throwable t);
}
