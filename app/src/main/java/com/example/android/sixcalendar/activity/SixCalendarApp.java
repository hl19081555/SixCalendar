package com.example.android.sixcalendar.activity;

import android.app.Application;
import android.content.Context;

import com.example.android.sixcalendar.libbaidu.BaiduAudioManager;

public class SixCalendarApp extends Application {
    private static Context sInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        BaiduAudioManager.getInstance();
    }

    public static Context getAppContext() {
        return sInstance;
    }

    @Override
    public void onTerminate() {
        BaiduAudioManager.getInstance().release();
        super.onTerminate();
    }
}
