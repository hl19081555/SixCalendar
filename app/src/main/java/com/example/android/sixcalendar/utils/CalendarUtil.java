package com.example.android.sixcalendar.utils;

import com.example.android.sixcalendar.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CalendarUtil {
    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private final static String[] Animals = new String[]{"鼠", "牛", "虎", "兔", "龙", "蛇", "马", "羊", "猴", "鸡", "狗", "猪"};

    // 根据新历 年月日获取农历年月日
    public static String getNongli(String time) {
        Date date = null;
        try {
            date = simpleDateFormat.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return ChinaDate.getNongli(date.getYear() + 1900, date.getMonth() + 1, date.getDate());
    }

    // 根据号码计算当前的生肖
    public static String getAnimal(int code) {
        Date date = new Date();
        long[] nongli = ChinaDate.calElement(date.getYear() + 1900, date.getMonth() + 1, date.getDate());
        int finalIndex = (((int)nongli[0] -4) % 12 + 60 - (code - 1)) % 12;
        return Animals[finalIndex];
    }

    private static final String BODUAN_RED = "01.02.07.08.12.13.18.19.23.24.29.30.34.35.40.45.46";
    private static final String BODUAN_BLUE = "03.04.09.10.14.15.20.25.26.31.36.37.41.42.47.48";
    private static final String BODUAN_GREEN = "05.06.11.16.17.21.22.27.28.32.33.38.39.43.44.49";

    public enum ENUM_BODUAN {NULL, RED, GREEN, BLUE}

    public static ENUM_BODUAN getBoDuan(String code) {
        if (BODUAN_RED.contains(code)) {
            return ENUM_BODUAN.RED;
        } else if (BODUAN_BLUE.contains(code)) {
            return ENUM_BODUAN.BLUE;
        } else if (BODUAN_GREEN.contains(code)) {
            return ENUM_BODUAN.GREEN;
        } else {
            return ENUM_BODUAN.BLUE;
        }
    }

    public static int getBoDuanRID(String code) {
        switch (getBoDuan(code)) {
            case RED:
                return R.drawable.oval_red;
            case BLUE:
                return R.drawable.oval_blue;
            case GREEN:
                return R.drawable.oval_green;
            default:
                return R.drawable.oval_blue;
        }
    }

    public static String getBoDuanStr(String p1, String p2, String p3, String p4, String p5, String p6, String t) {
        return getBoDuan(p1).ordinal() + "," + getBoDuan(p2).ordinal() + "," + getBoDuan(p3).ordinal() + "," +
                getBoDuan(p4).ordinal() + "," + getBoDuan(p5).ordinal() + "," + getBoDuan(p6).ordinal() + "," +
                getBoDuan(t).ordinal();
    }
}
