package com.shawnway.nav.app.wtw.module.home;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.shawnway.nav.app.wtw.R;
import com.shawnway.nav.app.wtw.base.BaseFragment;
import com.shawnway.nav.app.wtw.base.BaseSubscriber;
import com.shawnway.nav.app.wtw.bean.EconCalenBean;
import com.shawnway.nav.app.wtw.net.JsonRequestBody;
import com.shawnway.nav.app.wtw.net.RetrofitClient;
import com.shawnway.nav.app.wtw.tool.SchedulersCompat;
import com.shawnway.nav.app.wtw.tool.StringUtils;
import com.shawnway.nav.app.wtw.view.MyListView;

import org.joda.time.DateTime;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

import butterknife.BindView;
import noman.weekcalendar.WeekCalendar;
import noman.weekcalendar.listener.OnDateClickListener;

/**
 * 日历页面
 */
public class EconomyCalenFragment extends BaseFragment implements View.OnClickListener {

    private static final String TAG = "EconomyCalenFragment";
    @BindView(R.id.home_month)
    TextView mMonth;
    @BindView(R.id.fragment_econcalen_lastweek)
    TextView lastWeek;
    @BindView(R.id.fragment_econcalen_nextweek)
    TextView nextWeek;
    @BindView(R.id.fragment_econcalen_lv)
    MyListView mLv;
    @BindView(R.id.fragment_econcalen_weekcalendar)
    WeekCalendar weekCalendar;
    private ArrayList<EconCalenBean.ItemBean> mDatas;

    private String day;
    private Date date;
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_econcalen;
    }

    public void initDatas() {

        if (StringUtils.isEmpty(day)) {
            Date date = new Date();
            int year = date.getYear() + 1900;
            int month = date.getMonth() + 1;
            int dayInt = date.getDay() + 1;
            String monthstr = "", daystr = "";
            if (month < 10) {
                monthstr = "0" + month;
            }
            if (dayInt < 10) {
                daystr = "0" + dayInt;
            }
            day = year + "-" + monthstr + "-" + daystr;
        }
        JSONObject json = new JSONObject();
        try {
            json.put("date", day);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RetrofitClient
                .getInstance()
                .api()
                .getFinancial(JsonRequestBody.getInstance().convertJsonContent(json.toString()))
                .compose(SchedulersCompat.<EconCalenBean>applyIoSchedulers())
                .subscribe(new BaseSubscriber<EconCalenBean>() {
                    @Override
                    public void onSuccess(EconCalenBean econCalenBean) {
                        mDatas = econCalenBean.getFinancialCalendarList();
                        mLv.setAdapter(new MyAdapter());
                    }
                });

    }


    @Override
    protected void initEventAndData() {
        initView();
    }

    private void initView() {
        lastWeek.setOnClickListener(this);
        nextWeek.setOnClickListener(this);
        date = new Date();
        int month = date.getMonth() + 1;
        mMonth.setText(month + "月");
        day = DateTime.now().toString().substring(0, 10);
        JSONObject json = new JSONObject();
        try {
            json.put("date", day);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RetrofitClient
                .getInstance()
                .api()
                .getFinancial(JsonRequestBody.getInstance().convertJsonContent(json.toString()))
                .compose(SchedulersCompat.<EconCalenBean>applyIoSchedulers())
                .subscribe(new BaseSubscriber<EconCalenBean>() {
                    @Override
                    public void onSuccess(EconCalenBean econCalenBean) {
                        mDatas = econCalenBean.getFinancialCalendarList();
                        mLv.setAdapter(new MyAdapter());
                    }
                });

//        weekCalendar.setStartDate(DateTime.now());
//        weekCalendar.setSelectedDate(DateTime.now());
        weekCalendar.setOnDateClickListener(new OnDateClickListener() {
            @Override
            public void onDateClick(DateTime dateTime) {
                day = dateTime.toString().substring(0, 10);
                initDatas();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fragment_econcalen_lastweek:
                weekCalendar.moveToPrevious();
                weekCalendar.moveToPrevious();
                weekCalendar.moveToPrevious();
                weekCalendar.moveToPrevious();
                weekCalendar.moveToPrevious();
                weekCalendar.moveToPrevious();
                weekCalendar.moveToPrevious();
                long lasttime = date.getTime() - 7 * 24 * 60 * 60 * 1000;
                date.setTime(lasttime);
                int month = date.getMonth() + 1;
                mMonth.setText(month + "月");
                break;
            case R.id.fragment_econcalen_nextweek:
                weekCalendar.moveToNext();
                weekCalendar.moveToNext();
                weekCalendar.moveToNext();
                weekCalendar.moveToNext();
                weekCalendar.moveToNext();
                weekCalendar.moveToNext();
                weekCalendar.moveToNext();
                long nexttime = date.getTime() + 7 * 24 * 60 * 60 * 1000;
                date.setTime(nexttime);
                int month2 = date.getMonth() + 1;
                mMonth.setText(month2 + "月");
                break;

        }
    }

    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mDatas.size();
        }

        @Override
        public Object getItem(int position) {
            return mDatas.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final ViewHolder holder;
            if (convertView == null) {
                convertView = View.inflate(getContext(), R.layout.item_econcalen, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            final EconCalenBean.ItemBean bean = mDatas.get(position);
            if (StringUtils.isEmpty(bean.time)) {
                holder.tvTime.setText("--:--");
            } else {
                holder.tvTime.setText(bean.time);
            }
            holder.tvCountry.setText(String.format("%s", bean.region.substring(bean.region.indexOf("-") + 1)));
            holder.tvTopic.setText(bean.activity);
            holder.wrapper.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    NewsActivity.getInstance(getContext(),bean.getAccessUrl());
                }
            });
            switch (bean.significance) {
                case 1:
                    holder.ivStar1.setImageResource(R.mipmap.star);
                    break;
                case 2:
                    holder.ivStar1.setImageResource(R.mipmap.star);
                    holder.ivStar2.setImageResource(R.mipmap.star);
                    break;
                case 3:
                    holder.ivStar1.setImageResource(R.mipmap.star);
                    holder.ivStar2.setImageResource(R.mipmap.star);
                    holder.ivStar3.setImageResource(R.mipmap.star);
                    break;
                default:
                    break;
            }
            return convertView;
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTime;
        TextView tvCountry;
        TextView tvTopic;
        View wrapper;

        ImageView ivStar1;
        ImageView ivStar2;
        ImageView ivStar3;

        public ViewHolder(View convertView) {
            super(convertView);
            wrapper = convertView.findViewById(R.id.item_econcalen_wrapper);
            tvTime = (TextView) convertView.findViewById(R.id.item_econcalen_tv_time);
            tvCountry = (TextView) convertView.findViewById(R.id.item_econcalen_tv_country);
            tvTopic = (TextView) convertView.findViewById(R.id.item_econcalen_tv_topic);
            ivStar1 = (ImageView) convertView.findViewById(R.id.item_econcalen_iv_star1);
            ivStar2 = (ImageView) convertView.findViewById(R.id.item_econcalen_iv_star2);
            ivStar3 = (ImageView) convertView.findViewById(R.id.item_econcalen_iv_star3);
        }
    }
}
