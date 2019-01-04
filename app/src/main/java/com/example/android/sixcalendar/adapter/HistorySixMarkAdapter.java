package com.example.android.sixcalendar.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.sixcalendar.R;
import com.example.android.sixcalendar.entries.HistorySixMark;
import com.example.android.sixcalendar.utils.CalendarUtil;

import java.util.List;

public class HistorySixMarkAdapter extends RecyclerView.Adapter<HistorySixMarkAdapter.MyViewHolder> {
    private static final String TAG = HistorySixMarkAdapter.class.getSimpleName();
    private Context mContext;
    private List<HistorySixMark> mValues;


    public HistorySixMarkAdapter(Context context, List<HistorySixMark> items) {
        mContext = context;
        mValues = items;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        HistorySixMark item = mValues.get(position);
        holder.tvYear.setText(item.getPreDrawDate());
        holder.tvIssue.setText(item.getIssue() + "期");

        showInfo(holder.pm1, holder.pmsx1, item.getPM1(), item.getPMSX1());
        showInfo(holder.pm2, holder.pmsx2, item.getPM2(), item.getPMSX2());
        showInfo(holder.pm3, holder.pmsx3, item.getPM3(), item.getPMSX3());
        showInfo(holder.pm4, holder.pmsx4, item.getPM4(), item.getPMSX4());
        showInfo(holder.pm5, holder.pmsx5, item.getPM5(), item.getPMSX5());
        showInfo(holder.pm6, holder.pmsx6, item.getPM6(), item.getPMSX6());
        showInfo(holder.tm, holder.tmsx, item.getTM(), item.getTMSX());
    }

    private void showInfo(TextView tv, TextView tvsx, String code, String animal) {
        tv.setText(code);
        tv.setBackgroundResource(CalendarUtil.getBoDuanRID(code));
        tvsx.setText(animal);
    }

    @Override
    public int getItemCount() {
        return mValues == null ? 0 : mValues.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvYear, tvIssue;
        TextView pm1, pm2, pm3, pm4, pm5, pm6, tm;
        TextView pmsx1, pmsx2, pmsx3, pmsx4, pmsx5, pmsx6, tmsx;

        public MyViewHolder(View itemView) {
            super(itemView);
            tvYear = itemView.findViewById(R.id.tv_year);
            tvIssue = itemView.findViewById(R.id.tv_issue);

            pm1 = itemView.findViewById(R.id.pm1);
            pm2 = itemView.findViewById(R.id.pm2);
            pm3 = itemView.findViewById(R.id.pm3);
            pm4 = itemView.findViewById(R.id.pm4);
            pm5 = itemView.findViewById(R.id.pm5);
            pm6 = itemView.findViewById(R.id.pm6);
            tm  = itemView.findViewById(R.id.tm);


            pmsx1 = itemView.findViewById(R.id.pmsx1);
            pmsx2 = itemView.findViewById(R.id.pmsx2);
            pmsx3 = itemView.findViewById(R.id.pmsx3);
            pmsx4 = itemView.findViewById(R.id.pmsx4);
            pmsx5 = itemView.findViewById(R.id.pmsx5);
            pmsx6 = itemView.findViewById(R.id.pmsx6);
            tmsx  = itemView.findViewById(R.id.tmsx );
        }
    }
}
