package com.example.android.sixcalendar.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.sixcalendar.R;
import com.example.android.sixcalendar.entries.LaoHuangLi;
import com.example.android.sixcalendar.entries.LaoHuangLi2;
import com.example.android.sixcalendar.libbaidu.BaiduAudioManager;
import com.example.android.sixcalendar.network.BaseResponse;
import com.example.android.sixcalendar.utils.ContractUtil;
import com.example.android.sixcalendar.utils.MySharePreferece;
import com.example.android.sixcalendar.view.ShowProgressDialog;
import com.google.gson.Gson;
import com.tencent.bugly.beta.Beta;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.kymjs.kjframe.ui.BindView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CalendarActivity extends BaseActivity {
    @BindView(id = R.id.toolbar)
    private Toolbar mToolbar;
    @BindView(id = R.id.tv_nongli)
    private TextView mTVNongli;
    @BindView(id = R.id.tv_lunar)
    private TextView mTVLunar;
    @BindView(id = R.id.tv_year)
    private TextView mTVYear;
    @BindView(id = R.id.tv_date)
    private TextView mTVDate;
    @BindView(id = R.id.tv_week)
    private TextView mTVWeek;
    @BindView(id = R.id.tv_yi)
    private TextView mTVYi;
    @BindView(id = R.id.tv_ji)
    private TextView mTVJi;
    @BindView(id = R.id.tv_chong)
    private TextView mTVChong;
    @BindView(id = R.id.tv_today, click = true)
    private TextView mTVToday;
    @BindView(id = R.id.tv_pre_ten, click = true)
    private TextView mTVPreTen;
    @BindView(id = R.id.tv_pre_five, click = true)
    private TextView mTVPreFive;
    @BindView(id = R.id.tv_pre_one, click = true)
    private TextView mTVPreOne;
    @BindView(id = R.id.tv_next_ten, click = true)
    private TextView mTVNextTen;
    @BindView(id = R.id.tv_next_five, click = true)
    private TextView mTVNextFive;
    @BindView(id = R.id.tv_next_one, click = true)
    private TextView mTVNextOne;

    private boolean isRuning = false;
    private LaoHuangLi2 mLaohuangli = new LaoHuangLi2();
    private static final String LunarUrl = "http://m.laohuangli.net";
    private boolean isCurDate = false;
    private SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case ContractUtil.MSG_WHAT_SHOW_LAOHUANGLI:
                    showInfo();
                    break;
            }
        }
    };

    @Override
    public void setRootView() {
        super.setRootView();
        setContentView(R.layout.activity_calendar);
    }

    @Override
    public void initWidget() {
        super.initWidget();
        setSupportActionBar(mToolbar);
        mToolbar.setTitle("日历");
        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_sixmark:
                        startActivity(new Intent(CalendarActivity.this, LastSixMarkActivity.class));
                        break;
                }
                return false;
            }
        });
        BaiduAudioManager.getInstance();
    }

    @Override
    public void initData() {
        super.initData();
        //LaoHuangLiRequest.getCalendar(null, mLaoHuangLiResponse);
        //LaoHuangLiRequest.getCalendar(new Date(), mLaoHuangLiResponse);
        String lastTime = MySharePreferece.getInstance().getString(MySharePreferece.CALENDAR_EXISTS, "");
        if (!TextUtils.isEmpty(lastTime) && lastTime.equalsIgnoreCase(mSimpleDateFormat.format(new Date()))) {
            Log.d(CalendarActivity.class.getSimpleName(), "已经加载过了，所以不再次请求！");
            String lastValue = MySharePreferece.getInstance().getString(MySharePreferece.CALENDAR_LAST_INFO, "");
            Gson gson = new Gson();
            mLaohuangli = gson.fromJson(lastValue, LaoHuangLi2.class);
            showInfo();
        } else {
            isCurDate = true;
            ShowProgressDialog.showDialog(CalendarActivity.this, null, null);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    jsoupData(LunarUrl);
                }
            }).start();
        }
        Beta.checkUpgrade();
    }

    @Override
    public void widgetClick(View v) {
        super.widgetClick(v);
        if (isRuning) {
            Log.d("jackie", "is Runing, please try again...");
            Toast.makeText(CalendarActivity.this, "正在加载数据，请稍后再试", Toast.LENGTH_SHORT).show();
            return;
        }
        String url = LunarUrl;
        switch (v.getId()) {
            case R.id.tv_today:
                Log.d("jackie", "load today");
                break;
            case R.id.tv_pre_ten:
                Log.d("jackie", "load pre ten day");
                url = LunarUrl + mLaohuangli.getPre10().replace("..", "");
                break;
            case R.id.tv_pre_five:
                Log.d("jackie", "load pre five day");
                url = LunarUrl + mLaohuangli.getPre05().replace("..", "");
                break;
            case R.id.tv_pre_one:
                Log.d("jackie", "load pre one day");
                url = LunarUrl + mLaohuangli.getPre01().replace("..", "");
                break;
            case R.id.tv_next_ten:
                Log.d("jackie", "load next ten day");
                url = LunarUrl + mLaohuangli.getNext10().replace("..", "");
                break;
            case R.id.tv_next_five:
                Log.d("jackie", "load next five day");
                url = LunarUrl + mLaohuangli.getNext05().replace("..", "");
                break;
            case R.id.tv_next_one:
                Log.d("jackie", "load next five one");
                url = LunarUrl + mLaohuangli.getNext01().replace("..", "");
                break;
            default:
                return;
        }
        Log.d("jackie", "url = " + url);
        final String u = url;
        ShowProgressDialog.showDialog(CalendarActivity.this, null,null);
        new Thread(new Runnable() {
            @Override
            public void run() {
                jsoupData(u);
            }
        }).start();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_calendar, menu);
        return true;
    }

    private BaseResponse<LaoHuangLi> mLaoHuangLiResponse = new BaseResponse<LaoHuangLi>() {
        @Override
        public void onSuccess(LaoHuangLi data) {
            Log.e("CalendarActivity", "data = " + data);
        }

        @Override
        public void onFailure(int errorNo, String strMsg) {

        }
    };

    private void jsoupData(String url) {
        //抓取的目标网址
        //String url = "http://www.jcodecraeer.com";
        isRuning = true;
        //String url = "http://m.laohuangli.net/2019/2019-1-1.html";

        try {//捕捉异常

            Document document = Jsoup.connect(url).get(); //这里可用get也可以post方式，具体区别请自行了解

            Elements bodys = document.select("body");

            // 1.获取头部两行农历信息
            Elements divNongli = bodys.select("div.neirong1");
            if (divNongli != null && divNongli.size() > 0) {
                //Log.d("jackie", "size1 = " + divNongli.size());
                Elements divs = divNongli.get(0).select("div");
                if (divs != null && divs.size() >= 4) {
                    mLaohuangli.setNongli(divs.get(1).text());
                    mLaohuangli.setLunar(divs.get(3).text());
                    Log.d("jackie", "nongli = " + divs.get(1).text() + " luner = " + divs.get(3).text());
                }
            }

            // 2.获取当前时间
            Elements divCenter = bodys.select("div.center");
            if (divCenter != null && divCenter.size() > 0) {
                //Log.d("jackie", "size2 = " + divCenter.size());
                Elements divs = divCenter.get(0).select("div");
                if (divs != null && divs.size() >= 5) {
                    mLaohuangli.setYear(divs.get(2).text());
                    mLaohuangli.setDate(divs.get(3).text());
                    mLaohuangli.setWeek(divs.get(4).text());
                    Log.d("jackie", "year = " + divs.get(2).text() + " date = " + divs.get(3).text() + " week = " + divs.get(4).text());
                }
            }

            // 获取宜忌
            Elements divYiJi = bodys.select("div.neirong_Yi_Ji");
            if (divYiJi != null && divYiJi.size() >= 2) {
                Log.d("jackie", "size3 = " + divYiJi.size() + " yi = " + divYiJi.get(0).text() + " ji = " + divYiJi.get(1).text());
                mLaohuangli.setYi(divYiJi.get(0).toString());
                mLaohuangli.setJi(divYiJi.get(1).toString());
            }

            // 获取冲煞
            Elements divChong = bodys.select("div.neirong_txt1");
            if (divChong != null && divChong.size() >= 2) {
                //Log.d("jackie", "size4 = " + divChong.size());
                Elements divs = divChong.get(0).select("div");
                if (divs != null && divs.size() >= 4) {
                    Log.d("jackie", "chong = " + divs.get(1).text() + " baiji = " + divs.get(3).text());
                    mLaohuangli.setChong(divs.get(1).text());
                    mLaohuangli.setBaiji(divs.get(3).text());
                }
                //mLaohuangli.setChong(divs.toString().replace("class=\"color1\"", "color=\"red\""));
            }

            // 设置 向前和向后的
            Elements divGoto = bodys.select("a");
            if (divGoto != null && divGoto.size() >= 7) {
                mLaohuangli.setPre10(divGoto.get(3).attr("href"));
                mLaohuangli.setPre05(divGoto.get(4).attr("href"));
                mLaohuangli.setPre01(divGoto.get(5).attr("href"));

                mLaohuangli.setNext10(divGoto.get(6).attr("href"));
                mLaohuangli.setNext05(divGoto.get(7).attr("href"));
                mLaohuangli.setNext01(divGoto.get(8).attr("href"));
            }

            Log.d("jackie", "laohuangli = " + mLaohuangli);
            if (isCurDate) {
                isCurDate = false;
                MySharePreferece.getInstance().putString(MySharePreferece.CALENDAR_EXISTS, mSimpleDateFormat.format(new Date()));
                MySharePreferece.getInstance().putString(MySharePreferece.CALENDAR_LAST_INFO, (new Gson()).toJson(mLaohuangli));
            }
        } catch (Exception e) {
            Log.e("wwwwwwwww==", e.toString());
        } finally {
            mHandler.sendEmptyMessage(ContractUtil.MSG_WHAT_SHOW_LAOHUANGLI);
            isRuning = false;
            ShowProgressDialog.hideDialog();
        }
    }

    private void showInfo() {
        if (mLaohuangli == null) return;
        mTVNongli.setText(mLaohuangli.getNongli());
        mTVLunar.setText(mLaohuangli.getLunar());
        mTVYear.setText(mLaohuangli.getYear());
        mTVDate.setText(mLaohuangli.getDate());
        mTVWeek.setText(mLaohuangli.getWeek());
        if (!TextUtils.isEmpty(mLaohuangli.getYi())) {
            mTVYi.setText(Html.fromHtml(mLaohuangli.getYi()));
        }
        if (!TextUtils.isEmpty(mLaohuangli.getJi())) {
            mTVJi.setText(Html.fromHtml(mLaohuangli.getJi()));
        }
        mTVChong.setText(mLaohuangli.getChong() + "\n" + mLaohuangli.getBaiji());
        //mTVChong.setText(Html.fromHtml(mLaohuangli.getChong()));
    }
}
