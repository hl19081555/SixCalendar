package com.example.android.sixcalendar.network;

import android.content.Context;
import android.util.Log;

import com.example.android.sixcalendar.entries.LastSixMark;
import com.example.android.sixcalendar.utils.MySharePreferece;
import com.example.android.sixcalendar.view.ShowProgressDialog;

import org.kymjs.kjframe.KJHttp;
import org.kymjs.kjframe.http.HttpCallBack;

/**
 * Created by jackie on 2018/12/27.
 */

public class LastSixMarkReqeust extends BaseReqeust {

    public static void getLastSixMark(Context context, final BaseResponse response) {
        KJHttp kjHttp = new KJHttp();
        ShowProgressDialog.showDialog(context, null, null);
        kjHttp.get("http://lddata1.vipsinaapp.com/r.php", new HttpCallBack() {
            @Override
            public void onSuccess(String t) {
                super.onSuccess(t);
                Log.d(TAG, "onSuccess t " + t);
                /*String lastStr = MySharePreferece.getInstance().getString(MySharePreferece.LAST_SIXMARK, "");
                if (lastStr.equalsIgnoreCase(t)) {
                    Log.e("LastSixMarkReqeust", "请求结果和最后一次请求结果一致，不处理!");
                    return;
                }*/
                MySharePreferece.getInstance().putString(MySharePreferece.LAST_SIXMARK, t);
                LastSixMark lastSixMark = new LastSixMark(t);
                Log.d(TAG, lastSixMark.toString());
                if (response != null) {
                    response.onSuccess(lastSixMark);
                }
            }

            @Override
            public void onFailure(int errorNo, String strMsg) {
                super.onFailure(errorNo, strMsg);
                Log.d(TAG, "onFailure errorNo " + errorNo + " strMsg " + strMsg);
                if (response != null) {
                    response.onFailure(errorNo, strMsg);
                }
            }

            @Override
            public void onFinish() {
                super.onFinish();
                Log.d(TAG, "onFinish");
                ShowProgressDialog.hideDialog();
            }
        });
    }
}
