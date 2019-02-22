package com.example.android.sixcalendar.network;

import android.util.Log;

import org.kymjs.kjframe.KJHttp;
import org.kymjs.kjframe.http.HttpCallBack;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by jackie on 2018/12/29.
 */

public class LaoHuangLiRequest extends BaseReqeust {
    public static final SimpleDateFormat sDateFormat = new SimpleDateFormat("y-M-d");

    public static void getCalendar(Date date, BaseResponse response) {
        String url;
        if (date == null) {
            url = "http://m.laohuangli.net";
        } else {
            String time = sDateFormat.format(date);
            url = "http://m.laohuangli.net/" + time.substring(0, 4) + "/" + time + ".html";
        }

        KJHttp kjHttp = new KJHttp();

        kjHttp.get(url, null, false, new HttpCallBack() {
            @Override
            public void onSuccess(byte[] t) {
                super.onSuccess(t);
                try {
                    String str = new String(t, "GB2312");
                    Log.e(TAG, "str = " + str);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int errorNo, String strMsg) {
                super.onFailure(errorNo, strMsg);
            }
        });
    }
}
