package com.example.android.sixcalendar.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;

import com.example.android.sixcalendar.activity.SixCalendarApp;

/**
 * Created by jackie on 2019/1/19.
 */

public class PhoneInfoUtil {
    private static int mVersionCode = -1;

    public static String getHandSetInfo() {
        String handSetInfo = "手机型号:" + android.os.Build.MODEL +
                ",SDK版本:" + android.os.Build.VERSION.SDK +
                ",系统版本:" + android.os.Build.VERSION.RELEASE +
                ",软件版本:" + getAppVersionName(SixCalendarApp.getAppContext());
        return handSetInfo;
    }

    //获取当前版本号
    public static String getAppVersionName(Context context) {
        String versionName = "";
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo("com.example.android.sixcalendar", 0);
            versionName = packageInfo.versionName;
            if (TextUtils.isEmpty(versionName)) {
                return "";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return versionName;
    }

    //获取当前版本号
    public static int getAppVersionCode(Context context) {
        if (mVersionCode != -1) return mVersionCode;
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo("com.example.android.sixcalendar", 0);
            mVersionCode = packageInfo.versionCode;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mVersionCode;
    }
}
