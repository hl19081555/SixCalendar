package com.example.android.sixcalendar.activity;

import android.app.Application;
import android.content.Context;

public class SixCalendarApp extends Application {
    private static Context sInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
    }

    public static Context getAppContext() {
        return sInstance;
    }
}
