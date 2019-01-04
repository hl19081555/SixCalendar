package com.example.android.sixcalendar.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.android.sixcalendar.R;
import com.example.android.sixcalendar.database.SixMarkManager;
import com.example.android.sixcalendar.entries.HistorySixMark;
import com.example.android.sixcalendar.entries.LastSixMark;
import com.example.android.sixcalendar.network.BaseResponse;
import com.example.android.sixcalendar.network.LastSixMarkReqeust;
import com.example.android.sixcalendar.utils.CalendarUtil;
import com.example.android.sixcalendar.utils.ContractUtil;
import com.example.android.sixcalendar.utils.MySharePreferece;

import org.kymjs.kjframe.ui.BindView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class LastSixMarkActivity extends BaseActivity {
    private static final String TAG = LastSixMarkActivity.class.getSimpleName();

    @BindView(id = R.id.toolbar)
    private Toolbar mToolbar;
    @BindView(id = R.id.tv_issue)
    private TextView mTVIssue;
    @BindView(id = R.id.pm1)
    private TextView mPM1;
    @BindView(id = R.id.pmsx1)
    private TextView mPMSX1;
    @BindView(id = R.id.pm2)
    private TextView mPM2;
    @BindView(id = R.id.pmsx2)
    private TextView mPMSX2;
    @BindView(id = R.id.pm3)
    private TextView mPM3;
    @BindView(id = R.id.pmsx3)
    private TextView mPMSX3;
    @BindView(id = R.id.pm4)
    private TextView mPM4;
    @BindView(id = R.id.pmsx4)
    private TextView mPMSX4;
    @BindView(id = R.id.pm5)
    private TextView mPM5;
    @BindView(id = R.id.pmsx5)
    private TextView mPMSX5;
    @BindView(id = R.id.pm6)
    private TextView mPM6;
    @BindView(id = R.id.pmsx6)
    private TextView mPMSX6;
    @BindView(id = R.id.tm)
    private TextView mTM;
    @BindView(id = R.id.tmsx)
    private TextView mTMSX;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case ContractUtil.MSG_WHAT_GET_LAST_INFO:
                    LastSixMarkReqeust.getLastSixMark(LastSixMarkActivity.this, mLastSixMarkBaseResponse);
                    break;
            }
        }
    };

    @Override
    public void setRootView() {
        setContentView(R.layout.activity_last);
    }

    @Override
    public void initWidget() {
        super.initWidget();
        setSupportActionBar(mToolbar);
        mToolbar.setOnMenuItemClickListener(mMenuItemClickListener);
    }

    @Override
    public void initData() {
        super.initData();
        String last = MySharePreferece.getInstance().getString(MySharePreferece.LAST_SIXMARK, "");
        Log.d(TAG, "initData : last = " + last);
        if (!TextUtils.isEmpty(last)) {
            showData(new LastSixMark(last));
            mHandler.removeMessages(ContractUtil.MSG_WHAT_GET_LAST_INFO);
            mHandler.sendEmptyMessageDelayed(ContractUtil.MSG_WHAT_GET_LAST_INFO, 3000);
        } else {
            mHandler.sendEmptyMessage(ContractUtil.MSG_WHAT_GET_LAST_INFO);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mHandler.removeMessages(ContractUtil.MSG_WHAT_GET_LAST_INFO);
        mHandler.sendEmptyMessageDelayed(ContractUtil.MSG_WHAT_GET_LAST_INFO, 3000);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mHandler.removeMessages(ContractUtil.MSG_WHAT_GET_LAST_INFO);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_last, menu);
        return true;
    }

    private BaseResponse<LastSixMark> mLastSixMarkBaseResponse = new BaseResponse<LastSixMark>() {
        @Override
        public void onSuccess(LastSixMark data) {
            Log.d(TAG, data.toString());
            if (data == null) return;
            showData(data);
            if (data.getITM() >= 1 && data.getITM() <= 49 &&
                    MySharePreferece.getInstance().getString(MySharePreferece.LAST_SIXMARK_YEAR, "").equals(data.getYear()) &&
                    MySharePreferece.getInstance().getString(MySharePreferece.LAST_SIXMARK_ISSUE, "").equals(data.getIssue())) {
                MySharePreferece.getInstance().putString(MySharePreferece.LAST_SIXMARK_YEAR, data.getYear());
                MySharePreferece.getInstance().putString(MySharePreferece.LAST_SIXMARK_ISSUE, data.getIssue());
                HistorySixMark item = new HistorySixMark();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                item.setPreDrawDate(simpleDateFormat.format(new Date()));
                item.setIssue(data.getIssue());
                item.setPreDrawCode(data.getSPM1() + "," + data.getSPM2() + "," + data.getSPM3() + "," + data.getSPM4() +
                        "," + data.getSPM5() + ", " + data.getSPM6() + "," + data.getSTM());
                item.setColor(CalendarUtil.getBoDuanStr(data.getSPM1(), data.getSPM2(), data.getSPM3(), data.getSPM4(),
                        data.getSPM5(), data.getSPM6(), data.getSTM()));
                SixMarkManager.getInstance().insertHistorySixMark(item);
            }
            mHandler.removeMessages(ContractUtil.MSG_WHAT_GET_LAST_INFO);
            mHandler.sendEmptyMessageDelayed(ContractUtil.MSG_WHAT_GET_LAST_INFO, 3000);
        }

        @Override
        public void onFailure(int errorNo, String strMsg) {
            Log.d(TAG, "errorNo " + errorNo + " strMsg " + strMsg);
            mHandler.removeMessages(ContractUtil.MSG_WHAT_GET_LAST_INFO);
            mHandler.sendEmptyMessageDelayed(ContractUtil.MSG_WHAT_GET_LAST_INFO, 3000);
        }
    };

    private Toolbar.OnMenuItemClickListener mMenuItemClickListener =
            new Toolbar.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.action_last:
                            startActivity(new Intent(LastSixMarkActivity.this, HistorySixMarkActivity.class));
                            break;
                    }
                    return false;
                }
            };

    private void showInfo(int num, String str, TextView tv1, TextView tv2) {
        if (num >= 1 && num <= 49) {
            tv1.setText(str);
            tv2.setText(CalendarUtil.getAnimal(num));
        } else {
            tv1.setText("");
            tv2.setText("");
        }
        tv1.setBackgroundResource(CalendarUtil.getBoDuanRID(str));
    }

    private void showData(LastSixMark data) {
        if (!TextUtils.isEmpty(data.getYear()) && !TextUtils.isEmpty(data.getIssue())) {
            mTVIssue.setText(data.getYear() + "年" + data.getIssue() + "期");
        } else {
            Date date = new Date();
            mTVIssue.setText(String.valueOf(date.getYear() + 1900));
        }
        showInfo(data.getIPM1(), data.getSPM1(), mPM1, mPMSX1);
        showInfo(data.getIPM2(), data.getSPM2(), mPM2, mPMSX2);
        showInfo(data.getIPM3(), data.getSPM3(), mPM3, mPMSX3);
        showInfo(data.getIPM4(), data.getSPM4(), mPM4, mPMSX4);
        showInfo(data.getIPM5(), data.getSPM5(), mPM5, mPMSX5);
        showInfo(data.getIPM6(), data.getSPM6(), mPM6, mPMSX6);
        showInfo(data.getITM(), data.getSTM(), mTM, mTMSX);
    }
}
