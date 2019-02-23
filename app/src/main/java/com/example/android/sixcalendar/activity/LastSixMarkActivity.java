package com.example.android.sixcalendar.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.android.sixcalendar.R;
import com.example.android.sixcalendar.adapter.LotteryDateAdapter;
import com.example.android.sixcalendar.database.SixMarkManager;
import com.example.android.sixcalendar.entries.HistorySixMark;
import com.example.android.sixcalendar.entries.LastSixMark;
import com.example.android.sixcalendar.entries.LotteryDate;
import com.example.android.sixcalendar.libbaidu.BaiduAudioManager;
import com.example.android.sixcalendar.network.BaseResponse;
import com.example.android.sixcalendar.network.LastSixMarkReqeust;
import com.example.android.sixcalendar.network.QueryLotteryDateRequest;
import com.example.android.sixcalendar.utils.CalendarUtil;
import com.example.android.sixcalendar.utils.ContractUtil;
import com.example.android.sixcalendar.utils.FileUtils;
import com.example.android.sixcalendar.utils.MySharePreferece;
import com.example.android.sixcalendar.utils.PhoneInfoUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.kymjs.kjframe.ui.BindView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class LastSixMarkActivity extends BaseActivity {
    private static final String TAG = LastSixMarkActivity.class.getSimpleName();

    @BindView(id = R.id.toolbar)
    private Toolbar mToolbar;
    @BindView(id = R.id.progress_bar)
    private ProgressBar mProgressBar;
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
    private LastSixMark mLastSixMark;
    private boolean isPlayAudio = false;

    @BindView(id = R.id.layout_item)
    private View mLayoutItem;
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

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case ContractUtil.MSG_WHAT_GET_LAST_INFO:
                    mProgressBar.setVisibility(View.VISIBLE);
                    LastSixMarkReqeust.getLastSixMark(LastSixMarkActivity.this, mLastSixMarkBaseResponse);
                    break;
            }
        }
    };

    @Override
    public void setRootView() {
        super.setRootView();
        setContentView(R.layout.activity_last);
    }

    @Override
    public void initWidget() {
        super.initWidget();
        setSupportActionBar(mToolbar);
        mToolbar.setOnMenuItemClickListener(mMenuItemClickListener);
        // 获取开奖日期
        adapter = new LotteryDateAdapter(this, mLotteryDateList);
        mGridView.setAdapter(adapter);
    }

    @Override
    public void initData() {
        super.initData();
        String last = MySharePreferece.getInstance().getString(MySharePreferece.LAST_SIXMARK, "");
        Log.d(TAG, "initData : last = " + last);
        if (!TextUtils.isEmpty(last)) {
            mLastSixMark = new LastSixMark(last);
            showData(mLastSixMark);
        }
        mHandler.sendEmptyMessage(ContractUtil.MSG_WHAT_GET_LAST_INFO);
        String ldate = MySharePreferece.getInstance().getString(MySharePreferece.LOTTERY_DATE, "");
        if (!TextUtils.isEmpty(ldate)) {
            Gson gson = new Gson();
            List<LotteryDate> list = gson.fromJson(ldate, new TypeToken<List<LotteryDate>>(){}.getType());
            if (list != null && list.size() > 0) {
                LotteryDate l1 = list.get(0);
                mTVYM.setText(String.format("%04d 年 %02d 月", l1.getYear(), l1.getMonth()));
                mLotteryDateList.clear();
                mLotteryDateList.addAll(list);
                adapter.notifyDataSetChanged();
                mLayoutItem.setVisibility(View.VISIBLE);
            }
        }
        // 请求开奖日期数据
        Date date = new Date();
        year = date.getYear() + 1900;
        month = date.getMonth() + 1;
        QueryLotteryDateRequest.getLotteryDate(this, year, month, mLotteryDateBaseResponse);
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

    private SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private BaseResponse<LastSixMark> mLastSixMarkBaseResponse = new BaseResponse<LastSixMark>() {
        @Override
        public void onSuccess(LastSixMark data) {
            if (PhoneInfoUtil.getAppVersionCode(LastSixMarkActivity.this) == 19 &&
                    FileUtils.isSDCardMounted()) {
                FileUtils.createFile(FileUtils.LOG_FILE);
                String log = mSimpleDateFormat.format(new Date()) + "\r\npreData = " + mLastSixMark + "\r\n curData = " + data + "\r\n\r\n";
                FileUtils.writeFile(log, FileUtils.LOG_FILE, true);
            }
            if (data != null && !data.equals(mLastSixMark)) {
                showData(data);
                mLastSixMark = data;
                playAudio(data);
                if (data.getITM() >= 1 && data.getITM() <= 49) {
                    HistorySixMark item = new HistorySixMark();
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    item.setPreDrawDate(simpleDateFormat.format(new Date()));
                    item.setIssue(data.getIssue());
                    item.setPreDrawCode(data.getSPM1() + "," + data.getSPM2() + "," + data.getSPM3() + "," + data.getSPM4() +
                            "," + data.getSPM5() + "," + data.getSPM6() + "," + data.getSTM());
                    item.setColor(CalendarUtil.getBoDuanStr(data.getSPM1(), data.getSPM2(), data.getSPM3(), data.getSPM4(),
                            data.getSPM5(), data.getSPM6(), data.getSTM()));
                    SixMarkManager.getInstance().insertHistorySixMark(item);
                }
            }
            mHandler.removeMessages(ContractUtil.MSG_WHAT_GET_LAST_INFO);
            mHandler.sendEmptyMessageDelayed(ContractUtil.MSG_WHAT_GET_LAST_INFO, 3000);
            mProgressBar.setVisibility(View.GONE);
        }

        @Override
        public void onFailure(int errorNo, String strMsg) {
            Log.d(TAG, "errorNo " + errorNo + " strMsg " + strMsg);
            mHandler.removeMessages(ContractUtil.MSG_WHAT_GET_LAST_INFO);
            mHandler.sendEmptyMessageDelayed(ContractUtil.MSG_WHAT_GET_LAST_INFO, 3000);
            mProgressBar.setVisibility(View.GONE);
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
                        /*case R.id.action_date:
                            startActivity(new Intent(LastSixMarkActivity.this, LotteryDateActivity.class));
                            break;*/
                    }
                    return false;
                }
            };

    private void showInfo(int num, String str, TextView tv1, TextView tv2, int point) {
        if (num >= 1 && num <= 49) {
            tv1.setText(str);
            tv2.setText(CalendarUtil.getAnimal(num));
        } else {
            String aa = "";
            switch (point) {
                case 1:
                    aa = getString(R.string.null_of_1);
                    break;
                case 2:
                    aa = getString(R.string.null_of_2);
                    break;
                case 3:
                    aa = getString(R.string.null_of_3);
                    break;
                case 4:
                    aa = getString(R.string.null_of_4);
                    break;
                case 5:
                    aa = getString(R.string.null_of_5);
                    break;
                case 6:
                    aa = getString(R.string.null_of_6);
                    break;
                case 7:
                    aa = getString(R.string.null_of_7);
                    break;

            }
            tv1.setText(aa);
            tv2.setText("");
        }
        tv1.setBackgroundResource(CalendarUtil.getBoDuanRID(str));
    }

    private void showData(LastSixMark data) {
        if (data == null) return;
        if (!TextUtils.isEmpty(data.getYear()) && !TextUtils.isEmpty(data.getIssue())) {
            mTVIssue.setText(data.getYear() + "年" + data.getIssue() + "期");
        } else {
            Date date = new Date();
            mTVIssue.setText(String.valueOf(date.getYear() + 1900));
        }
        showInfo(data.getIPM1(), data.getSPM1(), mPM1, mPMSX1, 1);
        showInfo(data.getIPM2(), data.getSPM2(), mPM2, mPMSX2, 2);
        showInfo(data.getIPM3(), data.getSPM3(), mPM3, mPMSX3, 3);
        showInfo(data.getIPM4(), data.getSPM4(), mPM4, mPMSX4, 4);
        showInfo(data.getIPM5(), data.getSPM5(), mPM5, mPMSX5, 5);
        showInfo(data.getIPM6(), data.getSPM6(), mPM6, mPMSX6, 6);
        showInfo(data.getITM(), data.getSTM(), mTM, mTMSX, 7);
    }

    private void playAudio(LastSixMark data) {
        // 如果当前状态非播放，并且数据不完整，则开启播放功能
        if (!isPlayAudio && !data.isIntact()) {
            isPlayAudio = true;
        }
        // 如果当前状态是播放的，那么就开始播放最后一个内容
        if (isPlayAudio) {
            // 播放声音
            if (checkValue(data.getITM(), true)) {
            } else if (checkValue(data.getIPM6(), false)) {
            } else if (checkValue(data.getIPM5(), false)) {
            } else if (checkValue(data.getIPM4(), false)) {
            } else if (checkValue(data.getIPM3(), false)) {
            } else if (checkValue(data.getIPM2(), false)) {
            } else if (checkValue(data.getIPM1(), false)) {
            }
        }
        // 上诉都处理后，判断当前是否是完整的，如果是就关闭播放
        if (isPlayAudio && data.isIntact()) {
            isPlayAudio = false;
        }
    }

    private boolean checkValue(int value, boolean isTM) {
        if (value >= 1 && value <= 49) {
            // 播放，并返回true
            String str;
            if (isTM) {
                str = ("特码  " + value + "  " + CalendarUtil.getAnimal(value));
            } else {
                str = ("平码  " + value + "  " + CalendarUtil.getAnimal(value));
            }
            BaiduAudioManager.getInstance().speak(str);
            return true;
        }
        return false;
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

    private BaseResponse<List<LotteryDate>> mLotteryDateBaseResponse = new BaseResponse<List<LotteryDate>>() {
        @Override
        public void onSuccess(List<LotteryDate> data) {
            //Log.e(TAG, data.toString());
            if (data != null && data.size() > 0) {
                Gson gson = new Gson();
                String ldate = gson.toJson(data);
                MySharePreferece.getInstance().putString(MySharePreferece.LOTTERY_DATE, ldate);
                LotteryDate l1 = data.get(0);
                mTVYM.setText(String.format("%04d 年 %02d 月", l1.getYear(), l1.getMonth()));
                mLotteryDateList.clear();
                mLotteryDateList.addAll(data);
                adapter.notifyDataSetChanged();
                mLayoutItem.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onFailure(int errorNo, String strMsg) {
            Log.e(TAG, "errorNo = " + errorNo + "; strMsg = " + strMsg);
        }
    };
}
