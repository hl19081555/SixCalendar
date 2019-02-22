package com.example.android.sixcalendar.activity;

import android.app.Application;
import android.content.Context;
import android.os.Environment;

import com.example.android.sixcalendar.R;
import com.example.android.sixcalendar.entries.ChatbotDingding;
import com.example.android.sixcalendar.libbaidu.BaiduAudioManager;
import com.example.android.sixcalendar.network.ChatbotRequest;
import com.example.android.sixcalendar.utils.PhoneInfoUtil;
import com.tencent.bugly.Bugly;
import com.tencent.bugly.beta.Beta;

public class SixCalendarApp extends Application {
    private static Context sInstance;
    public static final String APP_ID = "bc8f4b07ff"; // TODO 替换成bugly上注册的appid

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;

        /* Bugly SDK初始化
        * 参数1：上下文对象
        * 参数2：APPID，平台注册时得到,注意替换成你的appId
        * 参数3：是否开启调试模式，调试模式下会输出'CrashReport'tag的日志
        */
        // CrashReport.initCrashReport(getApplicationContext(), "bc8f4b07ff", true);
        // 版本自动更新
        initBugly();

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

    private void initBugly() {
        /**
         * 已经接入Bugly用户改用上面的初始化方法,不影响原有的crash上报功能;
         * init方法会自动检测更新，不需要再手动调用Beta.checkUpdate(),如需增加自动检查时机可以使用Beta.checkUpdate(false,false);
         * 参数1： applicationContext
         * 参数2：appId
         * 参数3：是否开启debug
         */
        Bugly.init(getApplicationContext(), APP_ID, true);

        /**** Beta高级设置*****/
        Beta.autoInit = true; // true表示app启动自动初始化升级模块； false不好自动初始化 开发者如果担心sdk初始化影响app启动速度，可以设置为false  在后面某个时刻手动调用
        Beta.autoCheckUpgrade = true; // true表示初始化时自动检查升级 false表示不会自动检查升级，需要手动调用Beta.checkUpgrade()方法
        Beta.initDelay = 1 * 1000; // 设置升级周期为60s（默认检查周期为0s），60s内SDK不重复向后天请求策略
        Beta.largeIconId = R.mipmap.ic_launcher; // 设置通知栏大图标，largeIconId为项目中的图片资源；
        Beta.smallIconId = R.mipmap.ic_launcher; // 设置状态栏小图标，smallIconId为项目中的图片资源id;
        Beta.defaultBannerId = R.mipmap.ic_launcher; // 设置更新弹窗默认展示的banner，defaultBannerId为项目中的图片资源Id; 当后台配置的banner拉取失败时显示此banner，默认不设置则展示“loading“;
        Beta.storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS); // 设置sd卡的Download为更新资源保存目录; 后续更新资源会保存在此目录，需要在manifest中添加WRITE_EXTERNAL_STORAGE权限;
        Beta.showInterruptedStrategy = false; // 点击过确认的弹窗在APP下次启动自动检查更新时会再次显示;
        // Beta.canShowUpgradeActs.add(CalendarActivity.class); // 只允许在MainActivity上显示更新弹窗，其他activity上不显示弹窗;不设置会默认所有activity都可以显示弹窗;
    }
}
