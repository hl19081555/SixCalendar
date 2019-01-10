package com.example.android.sixcalendar.view;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;

/**
 * Created by jackie on 2019/1/3.
 */

public class ShowProgressDialog {
    private static ProgressDialog mProgressDialog;

    public static final void showDialog(Context context, String msg, final OnCancleListener onCancleListener) {
        if (context == null) return;
        mProgressDialog = new ProgressDialog(context);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);// 设置进度条的形式为圆形转动的进度条
        mProgressDialog.setCancelable(true);// 设置是否可以通过点击Back键取消
        mProgressDialog.setCanceledOnTouchOutside(false);// 设置在点击Dialog外是否取消Dialog进度条
        mProgressDialog.setTitle("正在加载数据，请稍后...!");
        if (!TextUtils.isEmpty(msg)) {
            mProgressDialog.setMessage(msg);
        }
        mProgressDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                if (onCancleListener != null) onCancleListener.onCancel();
            }
        });

        mProgressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                mProgressDialog.dismiss();
            }
        });

        mProgressDialog.show();
    }

    public static void hideDialog() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
    }

    public interface OnCancleListener {
        public void onCancel();
    }
}
