package com.example.android.sixcalendar.activity;

import android.app.Application;
import android.content.Context;

import com.example.android.sixcalendar.entries.ChatbotDingding;
import com.example.android.sixcalendar.libbaidu.BaiduAudioManager;
import com.example.android.sixcalendar.network.ChatbotRequest;
import com.example.android.sixcalendar.utils.PhoneInfoUtil;
import com.tencent.bugly.Bugly;
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
        // 版本自动更新
        Bugly.init(this, "bc8f4b07ff", false);

        BaiduAudioManager.getInstance();

        new Thread(new Runnable() {
            @Override
            public void run() {
                // 增加获取当前系统信息，同时发送到钉钉
                sendToChatbot();
            }
        }).start();
    }

    public static Context getAppContext() {
        return sInstance;
    }

    @Override
    public void onTerminate() {
        BaiduAudioManager.getInstance().release();
        super.onTerminate();
    }

    private void sendToChatbot() {
        // 1.获取系统信息
        String phoneInfo = PhoneInfoUtil.getHandSetInfo();
        // 2.将数据提交到钉钉
        ChatbotDingding item = new ChatbotDingding();
        item.setMsgtype(ChatbotDingding.MESSAGE_TYPE.link);
        item.getLink().setTitle("登录提示");
        item.getLink().setText(phoneInfo);
        item.getLink().setMessageUrl("null");
        ChatbotRequest.postChatbot(this, item, null);
    }
}
