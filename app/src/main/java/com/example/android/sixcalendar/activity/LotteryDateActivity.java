package com.example.android.sixcalendar.activity;

import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.sixcalendar.R;
import com.example.android.sixcalendar.adapter.LotteryDateAdapter;
import com.example.android.sixcalendar.entries.LotteryDate;
import com.example.android.sixcalendar.network.BaseResponse;
import com.example.android.sixcalendar.network.QueryLotteryDateRequest;

import org.kymjs.kjframe.ui.BindView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by jackie on 2019/1/21.
 */

public class LotteryDateActivity extends BaseActivity {
    private static final String TAG = LotteryDateActivity.class.getSimpleName();
    private WebView mWebView;

    @BindView(id = R.id.iv_left, click = true)
    private ImageView mIVLeft;
    @BindView(id = R.id.iv_right, click = true)
    private ImageView mIVRight;
    @BindView(id = R.id.tv_ym)
    private TextView mTVYM;
    @BindView(id = R.id.grid_view)
    private GridView mGridView;

    private List<LotteryDate> mLotteryDateList = new ArrayList<>();
    private LotteryDateAdapter adapter;

    private int year, month;

    @Override
    public void setRootView() {
        super.setRootView();
        setContentView(R.layout.activity_lottery_date);
    }

    @Override
    public void initWidget() {
        super.initWidget();

        adapter = new LotteryDateAdapter(this, mLotteryDateList);
        mGridView.setAdapter(adapter);
    }

    @Override
    public void initData() {
        super.initData();
        Date date = new Date();
        year = date.getYear() + 1900;
        month = date.getMonth() + 1;
        QueryLotteryDateRequest.getLotteryDate(this, year, month, mLotteryDateBaseResponse);
        // webview 可以直接请求的地址 ： https://m.6hch.com/html/kaijiDate.html
    }

    @Override
    public void widgetClick(View v) {
        super.widgetClick(v);
        switch (v.getId()) {
            case R.id.iv_left:
                loadData(false);
                break;
            case R.id.iv_right:
                loadData(true);
                break;
        }
    }

    private void loadData(boolean isNext) {
        if (isNext) {
            if (month == 12) {
                year++;
                month = 1;
            } else {
                month++;
            }
        } else {
            if (month == 1) {
                year--;
                month = 12;
            } else {
                month--;
            }
        }
        QueryLotteryDateRequest.getLotteryDate(this, year, month, mLotteryDateBaseResponse);
    }

    private void loadWebView() {
        //mWebView.setWebViewClient(new WebViewClient());
        //mWebView.getSettings().setDomStorageEnabled(true);
        //mWebView.loadUrl("https://m.6hch.com/html/kaijiDate.html");

        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptCanOpenWindowsAutomatically(true);//设置js可以直接打开窗口，如window.open()，默认为false
        settings.setJavaScriptEnabled(true);//是否允许执行js，默认为false。设置true时，会提醒可能造成XSS漏洞
        settings.setSupportZoom(true);//是否可以缩放，默认true
        settings.setBuiltInZoomControls(true);//是否显示缩放按钮，默认false
        settings.setUseWideViewPort(true);//设置此属性，可任意比例缩放。大视图模式
        settings.setLoadWithOverviewMode(true);//和setUseWideViewPort(true)一起解决网页自适应问题
        settings.setAppCacheEnabled(true);//是否使用缓存
        settings.setDomStorageEnabled(true);//DOM Storage
        mWebView.loadUrl("https://m.6hch.com/html/kaijiDate.html");

        //该方法解决的问题是打开浏览器不调用系统浏览器，直接用webview打开
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
    }

    private BaseResponse<List<LotteryDate>> mLotteryDateBaseResponse = new BaseResponse<List<LotteryDate>>() {
        @Override
        public void onSuccess(List<LotteryDate> data) {
            //Log.e(TAG, data.toString());
            if (data != null && data.size() > 0) {
                LotteryDate l1 = data.get(0);
                mTVYM.setText(String.format("%04d 年 %02d 月", l1.getYear(), l1.getMonth()));
                mLotteryDateList.clear();
                mLotteryDateList.addAll(data);
                adapter.notifyDataSetChanged();
            }
        }

        @Override
        public void onFailure(int errorNo, String strMsg) {
            Log.e(TAG, "errorNo = " + errorNo + "; strMsg = " + strMsg);
        }
    };
}
