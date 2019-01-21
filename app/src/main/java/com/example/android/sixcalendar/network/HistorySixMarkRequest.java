package com.example.android.sixcalendar.network;

import android.content.Context;
import android.util.Log;

import com.example.android.sixcalendar.entries.HistorySixMark;
import com.example.android.sixcalendar.view.ShowProgressDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.kymjs.kjframe.KJHttp;
import org.kymjs.kjframe.http.HttpCallBack;
import org.kymjs.kjframe.http.HttpParams;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jackie on 2018/12/28.
 */

public class HistorySixMarkRequest extends BaseReqeust {

    public static void getHistoryListInfo(Context context, int year, final BaseResponse response) {
        if (!isNetworkConnected(context)) return;
        Log.e("HistorySixMarkRequest", "getHistoryListInfo : year " + year);
        ShowProgressDialog.showDialog(context, "当前加载年份：" + year, null);
        KJHttp kjHttp = new KJHttp();
        HttpParams httpParams = new HttpParams();
        httpParams.put("year", year);
        httpParams.put("type", 1);
        kjHttp.get("https://1680660.com/smallSix/findSmallSixHistory.do", httpParams, false, new HttpCallBack() {
            @Override
            public void onSuccess(String t) {
                super.onSuccess(t);
                //Log.e("jackie", t);
                if (response != null) {
                    response.onSuccess(parsing(t));
                }
            }

            @Override
            public void onFailure(int errorNo, String strMsg) {
                super.onFailure(errorNo, strMsg);
                if (response != null) {
                    response.onFailure(errorNo, strMsg);
                }
            }

            @Override
            public void onFinish() {
                super.onFinish();
                ShowProgressDialog.hideDialog();
            }
        });
    }

    public static void cancle() {
        KJHttp kjHttp = new KJHttp();
        kjHttp.cancelAll();
        kjHttp.cancleAll();
    }

    private static List<HistorySixMark> parsing(String info) {
        List<HistorySixMark> list = null;
        try {
            JSONObject jsonObject = new JSONObject(info);
            JSONObject resultObj = jsonObject.optJSONObject("result");
            JSONObject dataObj = resultObj.optJSONObject("data");
            if (dataObj != null) {
                JSONArray bodyListObj = dataObj.optJSONArray("bodyList");
                if (bodyListObj != null && bodyListObj.length() > 0) {
                    list = new ArrayList<>();
                    for (int i = 0; i < bodyListObj.length(); i++) {
                        JSONObject item = bodyListObj.optJSONObject(i);
                        HistorySixMark historySixMark = new HistorySixMark();
                        historySixMark.setPreDrawDate(item.optString("preDrawDate"));
                        historySixMark.setColor(item.optString("color"));
                        historySixMark.setPreDrawCode(item.optString("preDrawCode"));
                        historySixMark.setIssue(String.format("%03d", item.optInt("issue")));
                        // Log.e(TAG, "historySixMark " + historySixMark);
                        list.add(historySixMark);
                    }
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }
}
