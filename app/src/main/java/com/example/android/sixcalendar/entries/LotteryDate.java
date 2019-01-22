package com.example.android.sixcalendar.entries;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by jackie on 2019/1/21.
 */

public class LotteryDate {
    private int year, month;
    private int day;
    private int value;

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public boolean isShowDay() {
        if (day > 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isLotteryDay() {
        if (value == 1) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isHistory() {
        Date date = new Date();
        Calendar curDate = Calendar.getInstance();
        curDate.set(date.getYear() + 1900, date.getMonth() + 1, date.getDate());
        Calendar selfDate = Calendar.getInstance();
        selfDate.set(year, month, day);
        return selfDate.getTimeInMillis() < curDate.getTimeInMillis();
    }

    public boolean isCurDay() {
        Date date = new Date();
        return (year == date.getYear() + 1900) && (month == date.getMonth() + 1) && (day == date.getDate());
    }

    @Override
    public String toString() {
        return "LotteryDate{" +
                "day=" + day +
                ", 开奖=" + isLotteryDay() +
                '}';
    }
}
