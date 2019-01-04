package com.example.android.sixcalendar.entries;

import android.text.TextUtils;

import com.example.android.sixcalendar.utils.CalendarUtil;

/**
 * Created by jackie on 2018/12/28.
 */

public class HistorySixMark {
    private String preDrawDate;
    private String preDrawCode;
    private String issue;
    private String color;
    //*******************************************************
    private String mPM1, mPM2, mPM3, mPM4, mPM5, mPM6, mTM;
    private String mPMSX1, mPMSX2, mPMSX3, mPMSX4, mPMSX5, mPMSX6, mTMSX;

    public String getPreDrawDate() {
        return preDrawDate;
    }

    public void setPreDrawDate(String preDrawDate) {
        this.preDrawDate = preDrawDate;
    }

    public String getPreDrawCode() {
        return preDrawCode;
    }

    public void setPreDrawCode(String preDrawCode) {
        this.preDrawCode = preDrawCode;
        if (TextUtils.isEmpty(preDrawCode)) return;
        String[] temp = preDrawCode.split(",");
        if (temp == null || temp.length != 7) return;
        int pm1 = Integer.parseInt(temp[0]);
        int pm2 = Integer.parseInt(temp[1]);
        int pm3 = Integer.parseInt(temp[2]);
        int pm4 = Integer.parseInt(temp[3]);
        int pm5 = Integer.parseInt(temp[4]);
        int pm6 = Integer.parseInt(temp[5]);
        int tm  = Integer.parseInt(temp[6]);

        mPM1 = String.format("%02d", pm1);
        mPM2 = String.format("%02d", pm2);
        mPM3 = String.format("%02d", pm3);
        mPM4 = String.format("%02d", pm4);
        mPM5 = String.format("%02d", pm5);
        mPM6 = String.format("%02d", pm6);
        mTM  = String.format("%02d", tm );

        mPMSX1 = CalendarUtil.getAnimal(pm1);
        mPMSX2 = CalendarUtil.getAnimal(pm2);
        mPMSX3 = CalendarUtil.getAnimal(pm3);
        mPMSX4 = CalendarUtil.getAnimal(pm4);
        mPMSX5 = CalendarUtil.getAnimal(pm5);
        mPMSX6 = CalendarUtil.getAnimal(pm6);
        mTMSX  = CalendarUtil.getAnimal(tm );
    }

    public String getIssue() {
        return issue;
    }

    public void setIssue(String issue) {
        this.issue = issue;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color.replace("[", "").replace("]", "");
    }

    public String getPM1() {
        return mPM1;
    }

    public String getPM2() {
        return mPM2;
    }

    public String getPM3() {
        return mPM3;
    }

    public String getPM4() {
        return mPM4;
    }

    public String getPM5() {
        return mPM5;
    }

    public String getPM6() {
        return mPM6;
    }

    public String getTM() {
        return mTM;
    }

    public String getPMSX1() {
        return mPMSX1;
    }

    public String getPMSX2() {
        return mPMSX2;
    }

    public String getPMSX3() {
        return mPMSX3;
    }

    public String getPMSX4() {
        return mPMSX4;
    }

    public String getPMSX5() {
        return mPMSX5;
    }

    public String getPMSX6() {
        return mPMSX6;
    }

    public String getTMSX() {
        return mTMSX;
    }

    @Override
    public String toString() {
        return "HistorySixMark{" +
                "preDrawDate='" + preDrawDate + '\'' +
                ", issue='" + issue + '\'' +
                ", color='" + color + '\'' +
                ", PM1='" + mPM1 + '\'' +
                ", PM2='" + mPM2 + '\'' +
                ", PM3='" + mPM3 + '\'' +
                ", PM4='" + mPM4 + '\'' +
                ", PM5='" + mPM5 + '\'' +
                ", PM6='" + mPM6 + '\'' +
                ", TM='" + mTM + '\'' +
                ", PMSX1='" + mPMSX1 + '\'' +
                ", PMSX2='" + mPMSX2 + '\'' +
                ", PMSX3='" + mPMSX3 + '\'' +
                ", PMSX4='" + mPMSX4 + '\'' +
                ", PMSX5='" + mPMSX5 + '\'' +
                ", PMSX6='" + mPMSX6 + '\'' +
                ", TMSX='" + mTMSX + '\'' +
                '}';
    }
}