package com.shawnway.nav.app.wtw.module.quotation.wp.wp_detail.KLine;

import android.os.Bundle;
import android.util.SparseArray;
import android.view.View;
import android.widget.ProgressBar;

import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.YAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.orhanobut.logger.Logger;
import com.shawnway.nav.app.wtw.R;
import com.shawnway.nav.app.wtw.base.BaseFragment;
import com.shawnway.nav.app.wtw.base.BaseSubscriber;
import com.shawnway.nav.app.wtw.bean.DataParse;
import com.shawnway.nav.app.wtw.bean.MinutesBean;
import com.shawnway.nav.app.wtw.bean.QuotationsWPBean;
import com.shawnway.nav.app.wtw.bean.WpTimeLineBean;
import com.shawnway.nav.app.wtw.mychart.MyBottomMarkerView;
import com.shawnway.nav.app.wtw.mychart.MyLeftMarkerView;
import com.shawnway.nav.app.wtw.mychart.MyLineChart;
import com.shawnway.nav.app.wtw.mychart.MyRightMarkerView;
import com.shawnway.nav.app.wtw.mychart.MyXAxis;
import com.shawnway.nav.app.wtw.mychart.MyYAxis;
import com.shawnway.nav.app.wtw.net.WPRetrofitClient;
import com.shawnway.nav.app.wtw.tool.SchedulersCompat;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Func1;


public class WpTimeLineFragment extends BaseFragment {

    private static final String TAG = "JlWpTimeLineFragment";


    @BindView(R.id.fragment_timesharing_linechart)
    MyLineChart mLineChart;
    @BindView(R.id.loading)
    ProgressBar loading;
    private QuotationsWPBean mGoods;

    private LineDataSet d1;
    private MyXAxis xAxisLine;
    private MyYAxis axisRightLine;
    private MyYAxis axisLeftLine;

    private SparseArray<String> stringSparseArray;
    private Subscription subscription;

    public static WpTimeLineFragment getInstance(QuotationsWPBean bean) {
        WpTimeLineFragment fragment = new WpTimeLineFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("bean", bean);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_timesharing;
    }

    @Override
    protected void initEventAndData() {
        mGoods = (QuotationsWPBean) getArguments().getSerializable("bean");
        initChart();
    }

    private void startSubscription() {
        subscription = Observable
                .interval(0, 40, TimeUnit.SECONDS)
                .compose(SchedulersCompat.<Long>applyIoSchedulers())
                .subscribe(new BaseSubscriber<Long>() {
                    @Override
                    public void onSuccess(Long aLong) {
                        initDatas();
                    }
                });
    }

    private void stopSubscription() {
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        startSubscription();
    }

    @Override
    public void onPause() {
        super.onPause();
        stopSubscription();
    }

    private void initDatas() {

        WPRetrofitClient
                .getInstance()
                .api()
                .getTimeLine(1, mGoods.getProductContract())
                .map(new Func1<WpTimeLineBean, ArrayList<MinutesBean>>() {
                    @Override
                    public ArrayList<MinutesBean> call(WpTimeLineBean wpTimeLineBean) {
                        DataParse mData = new DataParse();
                        mData.parseMinutes(wpTimeLineBean);
                        setMarkerView(mData);
                        stringSparseArray = setXLabels(wpTimeLineBean);
                        setShowLabels(stringSparseArray);
                        //设置y左右两轴最大最小值
                        axisLeftLine.setAxisMinValue(mData.getMin());
                        axisLeftLine.setAxisMaxValue(mData.getMax());
                        axisRightLine.setAxisMinValue(mData.getPercentMin());
                        axisRightLine.setAxisMaxValue(mData.getPercentMax());
                        return mData.getDatas();
                    }
                })
                .compose(SchedulersCompat.<ArrayList<MinutesBean>>applyIoSchedulers())
                .subscribe(new Subscriber<ArrayList<MinutesBean>>() {
                    @Override
                    public void onCompleted() {
                        //延时执行，否则会导致Y轴坐标显示错乱
                        Observable.timer(300, TimeUnit.MILLISECONDS)
                                .compose(SchedulersCompat.<Long>applyIoSchedulers())
                                .subscribe(new BaseSubscriber<Long>() {
                                    @Override
                                    public void onSuccess(Long aLong) {
                                        mLineChart.invalidate();//刷新图
                                        if (loading.getVisibility() == View.VISIBLE) {
                                            loading.setVisibility(View.GONE);
                                        }
                                        if (mLineChart.getVisibility() == View.INVISIBLE) {
                                            mLineChart.setVisibility(View.VISIBLE);
                                        }
                                    }
                                });
                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.e(e.getMessage());


                    }

                    @Override
                    public void onNext(ArrayList<MinutesBean> minutesBeen) {
                        if (minutesBeen.size() == 0) {
                            mLineChart.setNoDataText("暂无数据");
                            this.onCompleted();
                            return;
                        }
                        //基准线
                        LimitLine ll = new LimitLine(0);
                        ll.setLineColor(getResources().getColor(R.color.minute_jizhun));
                        ll.enableDashedLine(10f, 10f, 0f);
                        ll.setLineWidth(1);
                        axisRightLine.addLimitLine(ll);
                        axisRightLine.setBaseValue(0);
                        ArrayList<Entry> lineCJEntries = new ArrayList<>();
                        d1 = new LineDataSet(lineCJEntries, "成交价");
                        for (int i = 0, j = 0; i < minutesBeen.size(); i++, j++) {

                            MinutesBean t = minutesBeen.get(j);
                            if (t == null) {
                                lineCJEntries.add(new Entry(Float.NaN, i));
                                continue;
                            }

                            lineCJEntries.add(new Entry(minutesBeen.get(i).cjprice, i));
                        }
                        d1.setCircleRadius(0);
                        d1.setDrawCircleHole(false);
                        d1.setDrawValues(false);
                        d1.setColor(getResources().getColor(R.color.minute_blue));
                        d1.setHighLightColor(getResources().getColor(R.color.divider_normal));
                        d1.setDrawFilled(true);//区域图
                        //谁为基准
                        d1.setAxisDependency(YAxis.AxisDependency.LEFT);
                        ArrayList<ILineDataSet> sets = new ArrayList<>();
                        sets.add(d1);
        /*注老版本LineData参数可以为空，最新版本会报错，修改进入ChartData加入if判断*/
                        LineData cd = new LineData(getMinutesCount(), sets);
                        mLineChart.setData(cd);
                    }
                });
    }


    public String[] getMinutesCount() {
        return new String[100];
    }

    public void setShowLabels(SparseArray<String> labels) {
        xAxisLine.setXLabels(labels);
    }

    /**
     * 标注线
     *
     * @param mData
     */
    private void setMarkerView(DataParse mData) {
        MyLeftMarkerView leftMarkerView = new MyLeftMarkerView(getContext(), R.layout.mymarkerview);
        MyRightMarkerView rightMarkerView = new MyRightMarkerView(getContext(), R.layout.mymarkerview);
        MyBottomMarkerView bottomMarkerView = new MyBottomMarkerView(getContext(), R.layout.mymarkerview);
        mLineChart.setMarker(leftMarkerView, rightMarkerView, bottomMarkerView, mData);
    }

    private void initChart() {
        //背景色
        mLineChart.setBackgroundColor(getResources().getColor(android.R.color.white));
        //是否允许缩放
        mLineChart.setScaleEnabled(false);
        //边框
        mLineChart.setDrawBorders(true);
        //边框宽
        mLineChart.setBorderWidth(1f);
        //边框颜色
        mLineChart.setBorderColor(getResources().getColor(R.color.divider_normal));
        //描述
        mLineChart.setDescription("");
        Legend mLineChartLegend = mLineChart.getLegend();
        mLineChartLegend.setEnabled(false);

        //x轴
        xAxisLine = mLineChart.getXAxis();
        //描述
        xAxisLine.setDrawLabels(true);
        //位置
        xAxisLine.setPosition(XAxis.XAxisPosition.BOTTOM);
        //画虚线
        xAxisLine.setDrawGridLines(true);
        //画线的颜色
        xAxisLine.setAxisLineColor(getResources().getColor(R.color.divider_normal));
        //虚线
        xAxisLine.enableGridDashedLine(10f, 10f, 0f);


        //左边y
        axisLeftLine = mLineChart.getAxisLeft();
        axisLeftLine.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        /*折线图y轴左没有basevalue，调用系统的*/
        axisLeftLine.setLabelCount(5, true);
        axisLeftLine.setDrawLabels(true);
        axisLeftLine.setDrawGridLines(true);
        axisLeftLine.setDrawAxisLine(false);
        axisLeftLine.setAxisLineColor(getResources().getColor(R.color.divider_normal));
        axisLeftLine.enableGridDashedLine(10f, 10f, 0f);
        axisLeftLine.setValueFormatter(new YAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, YAxis yAxis) {
                DecimalFormat mFormat = new DecimalFormat("0.00");
                return mFormat.format(value);
            }
        });

        //右边y
        axisRightLine = mLineChart.getAxisRight();
        axisRightLine.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        axisRightLine.setLabelCount(2, true);
        axisRightLine.setDrawLabels(true);
        axisRightLine.setValueFormatter(new YAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, YAxis yAxis) {
                DecimalFormat mFormat = new DecimalFormat("#0.00%");
                return mFormat.format(value);
            }
        });
        axisRightLine.setStartAtZero(false);
        axisRightLine.setDrawGridLines(false);
        axisRightLine.setDrawAxisLine(false);

        //背景线
        xAxisLine.setGridColor(getResources().getColor(R.color.text_gray));
        xAxisLine.setTextColor(getResources().getColor(R.color.minute_zhoutv));
        axisLeftLine.setGridColor(getResources().getColor(R.color.text_gray));
        axisLeftLine.setTextColor(getResources().getColor(R.color.minute_zhoutv));
        axisRightLine.setAxisLineColor(getResources().getColor(R.color.text_gray));
        axisRightLine.setTextColor(getResources().getColor(R.color.minute_zhoutv));

        mLineChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {
                mLineChart.setHighlightValue(h);
            }

            @Override
            public void onNothingSelected() {
            }
        });
    }

    /**
     * 设置时间轴的长度和时间跨度
     *
     * @param response
     * @return Sparse集合
     */
    private SparseArray<String> setXLabels(WpTimeLineBean response) {
        SparseArray<String> xLabels = new SparseArray<>();
        int count = response.getTime().size();
        xLabels.put(0, response.getTime().get(0));
        xLabels.put(count / 4, response.getTime().get(count / 4));
        xLabels.put(count * 2 / 4, response.getTime().get(count * 2 / 4));
        xLabels.put(count * 3 / 4, response.getTime().get(count * 3 / 4));
        xLabels.put(count - 1, response.getTime().get(count - 1));
        return xLabels;
    }


}
