package com.example.android.sixcalendar.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.android.sixcalendar.R;
import com.example.android.sixcalendar.entries.LotteryDate;

import java.util.List;

/**
 * Created by jackie on 2019/1/22.
 */

public class LotteryDateAdapter extends BaseAdapter {
    private Context mContext;
    private List<LotteryDate> listValue;
    private LayoutInflater mLayoutInflater;

    public LotteryDateAdapter(Context context, List<LotteryDate> list) {
        mContext = context;
        listValue = list;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return listValue == null ? 7 : listValue.size() + 7;
    }

    @Override
    public Object getItem(int position) {
        if (listValue != null) {
            int count = listValue.size();
            if (position < count + 7) {
                return listValue.get(position - 7);
            }
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.item_lottery_date, null);
            holder = new ViewHolder();
            holder.text = convertView.findViewById(R.id.tv_day);
            holder.layout_item = convertView.findViewById(R.id.layout_item);
            holder.lunar = convertView.findViewById(R.id.tv_lunar);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if (position < 7) {
            holder.text.setText(getWeekName(position));
            holder.text.setTextColor(mContext.getResources().getColor(R.color.black));
            holder.text.setBackgroundResource(R.drawable.oval_white);
            holder.lunar.setVisibility(View.GONE);
        } else {
            LotteryDate item = listValue.get(position - 7);
            if (item != null) {
                if (item.isShowDay()) {
                    if (item.isLotteryDay()) {
                        holder.text.setText(String.valueOf(item.getDay()));
                        holder.text.setTextColor(mContext.getResources().getColor(R.color.white));
                        if (item.isHistory()) {
                            holder.text.setBackgroundResource(R.drawable.oval_gray);
                        } else {
                            holder.text.setBackgroundResource(R.drawable.oval_red);
                        }
                    } else {
                        holder.text.setText(String.valueOf(item.getDay()));
                        holder.text.setTextColor(mContext.getResources().getColor(R.color.black));
                        holder.text.setBackgroundResource(R.drawable.oval_white);
                    }
                    holder.lunar.setVisibility(View.VISIBLE);
                    holder.lunar.setText(item.getLunarMonth());
                } else {
                    holder.text.setText("");
                    holder.text.setTextColor(mContext.getResources().getColor(R.color.black));
                    holder.text.setBackgroundResource(R.drawable.oval_white);
                    holder.lunar.setVisibility(View.INVISIBLE);
                }
                if (item.isCurDay()) {
                    holder.layout_item.setBackgroundResource(R.drawable.rect_yellow);
                } else {
                    holder.layout_item.setBackgroundResource(R.color.white);
                }
            }
        }
        return convertView;
    }

    private String getWeekName(int position) {
        switch (position) {
            case 0:
                return "日";
            case 1:
                return "一";
            case 2:
                return "二";
            case 3:
                return "三";
            case 4:
                return "四";
            case 5:
                return "五";
            case 6:
                return "六";
            default:
                return "";
        }
    }

    class ViewHolder {
        TextView text;
        View layout_item;
        TextView lunar;
    }
}
