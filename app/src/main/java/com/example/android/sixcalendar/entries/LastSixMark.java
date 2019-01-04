package com.example.android.sixcalendar.entries;

import android.text.TextUtils;
import android.util.Log;

/**
 * Created by jackie on 2018/12/27.
 */

public class LastSixMark {
    private String TAG = LastSixMark.class.getSimpleName();
    private String mLastInfo;
    private String mYear, mIssue, mSPM1, mSPM2, mSPM3, mSPM4, mSPM5, mSPM6, mSTM;
    private int mIPM1, mIPM2, mIPM3, mIPM4, mIPM5, mIPM6, mITM;
    private int[] cc = new int[]{4, 6, 2, 7, 3, 8, 1, 9, 5};

    public LastSixMark(String lastInfo) {
        mLastInfo = lastInfo; // 1|14|32|47|2018|23|147|16|2|41 // 32,16,47,02,14,41, 23
        if (TextUtils.isEmpty(mLastInfo)) {
            Log.d(TAG, "String is null");
            return;
        }
        String[] split = mLastInfo.split("\\|");
        Log.d(TAG, "split.length " + split.length);
        if (split == null || split.length < 7 || split.length > 10) {
            Log.d(TAG, "String is error --> " + mLastInfo);
            return;
        }
        switch (split.length) {
            case 10:
                if (!TextUtils.isEmpty(split[cc[7]])) {
                    mIPM6 = Integer.parseInt(split[cc[7]]);
                    mSPM6 = String.format("%02d", mIPM6);
                }
                if (!TextUtils.isEmpty(split[cc[8]])) {
                    mITM  = Integer.parseInt(split[cc[8]]);
                    mSTM  = String.format("%02d", mITM );
                }
            case 9:
                if (!TextUtils.isEmpty(split[cc[5]])) {
                    mIPM4 = Integer.parseInt(split[cc[5]]);
                    mSPM4 = String.format("%02d", mIPM4);
                }
                if (!TextUtils.isEmpty(split[cc[6]])) {
                    mIPM5 = Integer.parseInt(split[cc[6]]);
                    mSPM5 = String.format("%02d", mIPM5);
                }
            case 8:
                if (!TextUtils.isEmpty(split[cc[3]])) {
                    mIPM2 = Integer.parseInt(split[cc[3]]);
                    mSPM2 = String.format("%02d", mIPM2);
                }
                if (!TextUtils.isEmpty(split[cc[4]])) {
                    mIPM3 = Integer.parseInt(split[cc[4]]);
                    mSPM3 = String.format("%02d", mIPM3);
                }
            case 7:
                if (!TextUtils.isEmpty(split[cc[0]])) mYear = split[cc[0]];
                if (!TextUtils.isEmpty(split[cc[1]])) mIssue = String.format("%03d", Integer.parseInt(split[cc[1]]));
                if (!TextUtils.isEmpty(split[cc[2]])) {
                    mIPM1 = Integer.parseInt(split[cc[2]]);
                    mSPM1 = String.format("%02d", mIPM1);
                }
                break;
        }
    }

    @Override
    public String toString() {
        return "LastSixMark{" +
                "LastInfo='" + mLastInfo + '\'' +
                ", Year='" + mYear + '\'' +
                ", Issue='" + mIssue + '\'' +
                ", PM1='" + mSPM1 + '\'' +
                ", PM2='" + mSPM2 + '\'' +
                ", PM3='" + mSPM3 + '\'' +
                ", PM4='" + mSPM4 + '\'' +
                ", PM5='" + mSPM5 + '\'' +
                ", PM6='" + mSPM6 + '\'' +
                ", TM='" + mSTM + '\'' +
                '}';
    }

    public String getYear() {
        return mYear;
    }

    public String getIssue() {
        return mIssue;
    }

    public String getSPM1() {
        return mSPM1;
    }

    public String getSPM2() {
        return mSPM2;
    }

    public String getSPM3() {
        return mSPM3;
    }

    public String getSPM4() {
        return mSPM4;
    }

    public String getSPM5() {
        return mSPM5;
    }

    public String getSPM6() {
        return mSPM6;
    }

    public String getSTM() {
        return mSTM;
    }

    public int getIPM1() {
        return mIPM1;
    }

    public int getIPM2() {
        return mIPM2;
    }

    public int getIPM3() {
        return mIPM3;
    }

    public int getIPM4() {
        return mIPM4;
    }

    public int getIPM5() {
        return mIPM5;
    }

    public int getIPM6() {
        return mIPM6;
    }

    public int getITM() {
        return mITM;
    }
}
