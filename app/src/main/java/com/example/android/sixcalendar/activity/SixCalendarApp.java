package com.example.android.sixcalendar.activity;

import android.app.Application;
import android.content.Context;

import com.example.android.sixcalendar.libbaidu.BaiduAudioManager;
import com.tencent.bugly.crashreport.CrashReport;

public class SixCalendarApp extends Application {
    private static Context sInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;

        /* Bugly SDK初始化
        * 参数1：上下文对象
        * 参数2：APPID，平台注册时得到,注意替换成你的appId
        * 参数3：是否开启调试模式，调试模式下会输出'CrashReport'tag的日志
        */
        CrashReport.initCrashReport(getApplicationContext(), "bc8f4b07ff", true);

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
