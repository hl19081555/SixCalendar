package com.example.android.sixcalendar.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.android.sixcalendar.activity.SixCalendarApp;

public class MySharePreferece {

    private static MySharePreferece sInstance;
    private SharedPreferences sp;
    private static final String sName = "sixcalendar";

    public static final String IS_FIRST = "isFirst";
    public static final String LAST_SIXMARK = "lastSixMark";
    public static final String LAST_SIXMARK_YEAR = "lastSixMarkYear";
    public static final String LAST_SIXMARK_ISSUE = "lastSixMarkIssue";
    public static final String CALENDAR_EXISTS = "calendarExists";
    public static final String CALENDAR_LAST_INFO = "calendarLastInfo";

    private MySharePreferece() {
        sp = SixCalendarApp.getAppContext().getSharedPreferences(sName, Context.MODE_PRIVATE);
    }

    public static synchronized MySharePreferece getInstance() {
        if (sInstance == null) {
            sInstance = new MySharePreferece();
        }
        return sInstance;
    }

    public void putBoolean(String var1, boolean var2) {
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean(var1, var2);
        editor.commit();
    }

    public void putString(String var1, String var2) {
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(var1, var2);
        editor.commit();
    }

    public void putInt(String var1, int var2) {
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt(var1, var2);
        editor.commit();
    }

    public void putLong(String var1, long var2) {
        SharedPreferences.Editor editor = sp.edit();
        editor.putLong(var1, var2);
        editor.commit();
    }

    public boolean getBoolean(String var1, boolean defaultVar2) {
        return sp.getBoolean(var1, defaultVar2);
    }

    public String getString(String var1, String defaultVar2) {
        return sp.getString(var1, defaultVar2);
    }

    public int getInt(String var1, int defaultVar2) {
        return sp.getInt(var1, defaultVar2);
    }

    public long getLong(String var1, long defaultVar2) {
        return sp.getLong(var1, defaultVar2);
    }
}
