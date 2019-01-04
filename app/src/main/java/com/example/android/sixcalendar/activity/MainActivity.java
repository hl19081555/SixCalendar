package com.example.android.sixcalendar.activity;

import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.android.sixcalendar.R;
import com.example.android.sixcalendar.entries.HistorySixMark;
import com.example.android.sixcalendar.entries.LastSixMark;
import com.example.android.sixcalendar.network.BaseResponse;
import com.example.android.sixcalendar.network.HistorySixMarkRequest;
import com.example.android.sixcalendar.network.LaoHuangLiRequest;
import com.example.android.sixcalendar.network.LastSixMarkReqeust;

import org.kymjs.kjframe.ui.BindView;

import java.util.Date;
import java.util.List;

public class MainActivity extends BaseActivity {
    private String TAG = MainActivity.class.getSimpleName();

    @BindView(id = R.id.btn_last, click = true)
    private Button mBtnLast;
    @BindView(id = R.id.btn_history, click = true)
    private Button mBtnHistory;
    @BindView(id = R.id.btn_laohuangli, click = true)
    private Button mBtnLaohuangli;

    private int mYear;

    @Override
    public void setRootView() {
        setContentView(R.layout.activity_main);
    }

    @Override
    public void initWidget() {
        super.initWidget();
    }

    @Override
    public void initData() {
        super.initData();
        Date date = new Date();
        mYear = date.getYear() + 1900;
    }

    @Override
    public void widgetClick(View v) {
        super.widgetClick(v);
        switch (v.getId()){
            case R.id.btn_last:
                LastSixMarkReqeust.getLastSixMark(MainActivity.this, mLastSixMarkBaseResponse);
                break;
            case R.id.btn_history:
                HistorySixMarkRequest.getHistoryListInfo(this, mYear, mHistorySixMarkBaseResponse);
                break;
            case R.id.btn_laohuangli:
                LaoHuangLiRequest.getCalendar(new Date(), mLaoHuangLiBaseResponse);
                break;
        }
    }

    private BaseResponse<LastSixMark> mLastSixMarkBaseResponse = new BaseResponse<LastSixMark>() {
        @Override
        public void onSuccess(LastSixMark data) {
            Log.d(TAG, data.toString());
        }

        @Override
        public void onFailure(int errorNo, String strMsg) {
            Log.d(TAG, "errorNo " + errorNo + " strMsg " + strMsg);
        }
    };

    private BaseResponse<List<HistorySixMark>> mHistorySixMarkBaseResponse = new BaseResponse<List<HistorySixMark>>() {
        @Override
        public void onSuccess(List<HistorySixMark> data) {
            Log.d(TAG, "onSuccess call");
        }

        @Override
        public void onFailure(int errorNo, String strMsg) {
            Log.d(TAG, "onFailure call errorNo " + errorNo + " strMsg " + strMsg);
        }
    };

    private BaseResponse mLaoHuangLiBaseResponse = new BaseResponse() {
        @Override
        public void onSuccess(Object data) {

        }

        @Override
        public void onFailure(int errorNo, String strMsg) {

        }
    };
}
