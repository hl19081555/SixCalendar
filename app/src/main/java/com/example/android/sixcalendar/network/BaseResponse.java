package com.example.android.sixcalendar.network;

/**
 * Created by jackie on 2018/12/27.
 */

public interface BaseResponse<T> {
    public void onSuccess(T data);
    public void onFailure(int errorNo, String strMsg);
}
