package com.example.android.sixcalendar.network;

import android.content.Context;
import android.util.Log;

import com.example.android.sixcalendar.entries.LotteryDate;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.kymjs.kjframe.KJHttp;
import org.kymjs.kjframe.http.HttpCallBack;
import org.kymjs.kjframe.http.HttpParams;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jackie on 2019/1/21.
 */

public class QueryLotteryDateRequest extends BaseReqeust {

    public static void getLotteryDate(Context context, final int year, final int month, final BaseResponse response) {
        if (!isNetworkConnected(context)) return;
        KJHttp kjHttp = new KJHttp();
        HttpParams httpParams = new HttpParams();
        httpParams.put("ym", String.format("%04d-%02d", year, month));
        kjHttp.get("https://1680660.com/smallSix/queryLotteryDate.do", httpParams, false, new HttpCallBack() {
            @Override
            public void onSuccess(String t) {
                super.onSuccess(t);
                Log.d(TAG, "onSuccess t " + t);
                List<LotteryDate> list = new ArrayList<>();
                if (response != null) {
                    if (parsing(t, year, month, list)) {
                        response.onSuccess(list);
                    } else {
                        response.onFailure(-1, "解析失败");
                    }
                }
            }

            @Override
            public void onFailure(int errorNo, String strMsg) {
                Log.e(TAG, "onFailure errorNo " + errorNo + " strMsg = " + strMsg);
                super.onFailure(errorNo, strMsg);
            }

            @Override
            public void onFinish() {
                super.onFinish();
            }
        });
    }

    private static boolean parsing(String info, final int year, final int month, List<LotteryDate> lotteryDateList) {
        try {
            JSONObject jsonObject = new JSONObject(info);
            int errorCode = jsonObject.optInt("errorCode");
            if (errorCode != 0) {
                return false;
            }
            JSONObject resultObj = jsonObject.optJSONObject("result");
            JSONObject dataObj = resultObj.optJSONObject("data");
            if (dataObj != null) {
                //Log.e("jackie", "dataObj = " + dataObj.toString());
                JSONArray kjDateObj = dataObj.optJSONArray("kjDate");
                if (kjDateObj != null && kjDateObj.length() > 0) {
                    //Log.e("jackie", "kjDateObj = " + kjDateObj.toString());
                    for (int i = 0; i < kjDateObj.length(); i++) {
                        JSONArray jsonArray = kjDateObj.optJSONArray(i);
                        if (jsonArray != null) {
                            //Log.e("jackie", "kjDateObj[" + i + "] = " + jsonArray.toString());
                            if (jsonArray != null && jsonArray.length() == 2) {
                                LotteryDate lotteryDate = new LotteryDate();
                                lotteryDate.setYear(year);
                                lotteryDate.setMonth(month);
                                lotteryDate.setDay(jsonArray.optInt(0));
                                lotteryDate.setValue(jsonArray.optInt(1));
                                lotteryDateList.add(lotteryDate);
                            }
                        }
                    }
                    return true;
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return false;
    }
}
