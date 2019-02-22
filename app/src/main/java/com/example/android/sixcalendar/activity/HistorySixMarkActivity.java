package com.example.android.sixcalendar.activity;

import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.android.sixcalendar.R;
import com.example.android.sixcalendar.adapter.HistorySixMarkAdapter;
import com.example.android.sixcalendar.database.SixMarkManager;
import com.example.android.sixcalendar.entries.HistorySixMark;
import com.example.android.sixcalendar.network.BaseResponse;
import com.example.android.sixcalendar.network.HistorySixMarkRequest;
import com.example.android.sixcalendar.utils.ContractUtil;
import com.example.android.sixcalendar.utils.MySharePreferece;

import org.kymjs.kjframe.ui.BindView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HistorySixMarkActivity extends BaseActivity {
    private static final String TAG = HistorySixMarkActivity.class.getSimpleName();
    @BindView(id = R.id.toolbar)
    private Toolbar mToolbar;
    @BindView(id = R.id.recyclerview)
    private RecyclerView mRecyclerView;
    private HistorySixMarkAdapter mHistorySixMarkAdapter;
    private List<HistorySixMark> mHistorySixMarks = new ArrayList<>();
    private LinearLayoutManager mLinearLayoutManager;
    private boolean isLoading = false;
    private int mLoadPage = 1;
    private int mYear;

    private boolean isFirstInit = false;
    private boolean isGetLastTwo = false;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Log.e("HistorySixMarkActivity", "mHandler : what = " + msg.what);
            switch (msg.what) {
                case ContractUtil.MSG_WHAT_GET_HISTORY_NEXT:
                    if (mYear > 2008) {
                        mYear--;
                        HistorySixMarkRequest.getHistoryListInfo(HistorySixMarkActivity.this, mYear, mHistorySixMarkBaseResponse);
                    } else {
                        Log.e("HistorySixMarkActivity", "初始化完成");
                        MySharePreferece.getInstance().putBoolean(MySharePreferece.IS_FIRST, false);
                        loadMoreDate();
                        isFirstInit = false;
                    }
                    break;
                case ContractUtil.MSG_WHAT_GET_HISTORY_AGAIN:
                    HistorySixMarkRequest.getHistoryListInfo(HistorySixMarkActivity.this, mYear, mHistorySixMarkBaseResponse);
                    break;
                case ContractUtil.MSG_WHAT_GET_HISTORY_LAST_TWO:
                    isGetLastTwo = false;
                    HistorySixMarkRequest.getHistoryListInfo(HistorySixMarkActivity.this, --mYear, mHistorySixMarkBaseResponse);
                    break;
            }
        }
    };

    @Override
    public void setRootView() {
        setContentView(R.layout.activity_history);
    }

    @Override
    public void initWidget() {
        super.initWidget();
        setSupportActionBar(mToolbar);
        mToolbar.setOnMenuItemClickListener(mMenuItemClickListener);

        mLinearLayoutManager = new LinearLayoutManager(this);
        //设置布局管理器
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        //设置为垂直布局，这也是默认的
        mLinearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        //设置Adapter
        mHistorySixMarkAdapter = new HistorySixMarkAdapter(this, mHistorySixMarks);
        mRecyclerView.setAdapter(mHistorySixMarkAdapter);
        //设置分隔线
        //mRecyclerView.addItemDecoration( new DividerGridItemDecoration(this ));
        //设置增加或删除条目的动画
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0) {//向下滚动
                    int visibleItemCount = mLinearLayoutManager.getChildCount();    //得到显示屏幕内的list数量
                    int totalItemCount = mLinearLayoutManager.getItemCount();    //得到list的总数量
                    int pastVisiblesItems = mLinearLayoutManager.findFirstVisibleItemPosition();//得到显示屏内的第一个list的位置数position

                    if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                        loadMoreDate();
                    }
                }
            }
        });
    }

    @Override
    public void initData() {
        super.initData();
        isFirstInit = MySharePreferece.getInstance().getBoolean(MySharePreferece.IS_FIRST, true);
        if (isFirstInit || !loadMoreDate()) {
            SixMarkManager.getInstance().clearData(); // 第一次加载时，删除所有旧数据
            Date date = new Date();
            mYear = date.getYear() + 1900;
            HistorySixMarkRequest.getHistoryListInfo(this, mYear, mHistorySixMarkBaseResponse);
        }
    }

    @Override
    protected void onDestroy() {
        HistorySixMarkRequest.cancle();
        if (mHandler != null) {
            mHandler.removeMessages(ContractUtil.MSG_WHAT_GET_HISTORY_NEXT);
            mHandler.removeMessages(ContractUtil.MSG_WHAT_GET_HISTORY_AGAIN);
            mHandler.removeMessages(ContractUtil.MSG_WHAT_GET_HISTORY_LAST_TWO);
        }
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_history, menu);
        return true;
    }

    private BaseResponse<List<HistorySixMark>> mHistorySixMarkBaseResponse = new BaseResponse<List<HistorySixMark>>() {
        @Override
        public void onSuccess(List<HistorySixMark> data) {
            Log.d(TAG, "onSuccess call");
            if (data != null && data.size() > 0) {
                Log.d(TAG, "onSuccess : year = " + data.get(0).getPreDrawDate().substring(0, 4));
                SixMarkManager.getInstance().deleteDataFromYear(data.get(0).getPreDrawDate().substring(0, 4));
                SixMarkManager.getInstance().bulkInsertHistorySixMark(data);
            }
            if (isFirstInit) {
                mHandler.sendEmptyMessageDelayed(ContractUtil.MSG_WHAT_GET_HISTORY_NEXT, 1000);
            } else if (isGetLastTwo) {
                mHandler.sendEmptyMessageDelayed(ContractUtil.MSG_WHAT_GET_HISTORY_LAST_TWO, 1000);
            } else {
                mHistorySixMarks.clear();
                mLoadPage = 1;
                loadMoreDate();
            }
        }

        @Override
        public void onFailure(int errorNo, String strMsg) {
            Log.d(TAG, "onFailure call errorNo " + errorNo + " strMsg " + strMsg);
            mHandler.sendEmptyMessageDelayed(ContractUtil.MSG_WHAT_GET_HISTORY_AGAIN, 1000);
        }
    };

    private Toolbar.OnMenuItemClickListener mMenuItemClickListener = new Toolbar.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem item) {
            switch (item.getItemId()) {
                case R.id.action_history:
                    isGetLastTwo = true;
                    Date date = new Date();
                    mYear = date.getYear() + 1900;
                    //SixMarkManager.getInstance().deleteLastTwoYear(mYear);
                    HistorySixMarkRequest.getHistoryListInfo(HistorySixMarkActivity.this, mYear, mHistorySixMarkBaseResponse);
                    break;
            }
            return false;
        }
    };

    private boolean loadMoreDate() {
        if (isLoading) return true;
        isLoading = true;
        List<HistorySixMark> list = SixMarkManager.getInstance().getHistorySixMark(mLoadPage);
        if (list == null || list.size() <= 0) {
            Log.d(TAG, "数据库数据为空，需要重新获取历史数据");
            isLoading = false;
            return false;
        } else {
            mHistorySixMarks.addAll(list);
            mHistorySixMarkAdapter.notifyDataSetChanged();
            mLoadPage++;
            isLoading = false;
        }
        return true;
    }
}
