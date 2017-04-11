package com.shawnway.nav.app.wtw.module.quotation.international.international_detail;

import android.graphics.Color;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.YAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.Utils;
import com.orhanobut.logger.Logger;
import com.shawnway.nav.app.wtw.R;
import com.shawnway.nav.app.wtw.base.BaseFragment;
import com.shawnway.nav.app.wtw.base.BaseSubscriber;
import com.shawnway.nav.app.wtw.bean.InternationalKLineBean;
import com.shawnway.nav.app.wtw.bean.MinutesBean;
import com.shawnway.nav.app.wtw.mychart.MyBarChart;
import com.shawnway.nav.app.wtw.mychart.MyBottomMarkerView;
import com.shawnway.nav.app.wtw.mychart.MyLeftMarkerView;
import com.shawnway.nav.app.wtw.mychart.MyLineChart;
import com.shawnway.nav.app.wtw.mychart.MyRightMarkerView;
import com.shawnway.nav.app.wtw.mychart.MyXAxis;
import com.shawnway.nav.app.wtw.mychart.MyYAxis;
import com.shawnway.nav.app.wtw.mychart.VolFormatter;
import com.shawnway.nav.app.wtw.net.JsonRequestBody;
import com.shawnway.nav.app.wtw.net.RetrofitClient;
import com.shawnway.nav.app.wtw.tool.KLineUtils;
import com.shawnway.nav.app.wtw.tool.SchedulersCompat;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Func1;

/**
 * Created by Administrator on 2016/10/9.
 * 国际期货分时图
 */

public class InternationalTimeLineFragment extends BaseFragment {


    @BindView(R.id.line_chart)
    MyLineChart lineChart;
    @BindView(R.id.bar_chart)
    MyBarChart barChart;
    @BindView(R.id.ll_chart)
    LinearLayout ll_chart;
    @BindView(R.id.loading)
    ProgressBar loading;
    MyXAxis xAxisLine;
    MyYAxis axisRightLine;
    MyYAxis axisLeftLine;
    SparseArray<String> stringSparseArray;
    private int chartType;
    private String security_symbol;
    private MyXAxis xAxisBar;
    private MyYAxis axisLeftBar;
    private MyYAxis axisRightBar;
    private BarDataSet barDataSet;
    private boolean isFirst = true;
    private Subscription subscription;


    public static InternationalTimeLineFragment newInstance(int chartType, String security_symbol) {

        Bundle args = new Bundle();
        args.putInt("chartType", chartType);
        args.putString("security_symbol", security_symbol);
        InternationalTimeLineFragment fragment = new InternationalTimeLineFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_international_timeline;
    }

    @Override
    protected void initEventAndData() {
        chartType = getArguments().getInt("chartType");
        security_symbol = getArguments().getString("security_symbol");
        initChart();
    }

    /**
     * 轮询数据
     */
    private void startSubscription() {
        subscription = Observable.interval(0, 40, TimeUnit.SECONDS)
                .compose(SchedulersCompat.<Long>applyIoSchedulers())
                .subscribe(new BaseSubscriber<Long>() {
                    @Override
                    public void onSuccess(Long aLong) {
                        FetchData(chartType, security_symbol);
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

    /**
     * 设置X轴lable
     *
     * @param bean
     * @return
     */
    private SparseArray<String> setXLabels(InternationalKLineBean bean) {
        SparseArray<String> xLabels = new SparseArray<>();
        int count = bean.getMarketDataCandleChart().size();
        for (int i = 1; i <= 6; i++) {
            int position;
            if (i == 1) {
                position = 0;
            } else if (i == 6) {
                position = count - 1;
            } else {
                position = (i - 1) * count / 5;
            }
            xLabels.put(position, bean.getMarketDataCandleChart().get(position).getChartTime().substring(11, 16));
        }
        return xLabels;
    }

    public String[] getMinutesCount() {
        return new String[1442];
    }

    /**
     * 设置初始化数据
     */
    private void initChart() {
        //背景色
        lineChart.setBackgroundColor(getResources().getColor(android.R.color.white));
        //是否允许缩放
        lineChart.setScaleEnabled(false);
        //边框
        lineChart.setDrawBorders(true);
        //边框宽
        lineChart.setBorderWidth(1f);
        //边框颜色
        lineChart.setBorderColor(getResources().getColor(R.color.divider_normal));
        //描述
        lineChart.setDescription("");
        //去掉底部描述
        lineChart.setNoDataText("暂无数据");
        Legend mLineChartLegend = lineChart.getLegend();
        mLineChartLegend.setEnabled(false);


        //柱状图不允许缩放
        barChart.setScaleEnabled(false);
        //柱状图边框
        barChart.setDrawBorders(true);
        //柱状图边框宽度
        barChart.setBorderWidth(1f);
        //边框颜色
        barChart.setBorderColor(getResources().getColor(R.color.divider_normal));
        //描述
        barChart.setDescription("");
        barChart.setNoDataText("暂无数据");

        //去掉底部描述
        Legend barChartLegend = barChart.getLegend();
        barChartLegend.setEnabled(false);

        //x轴
        xAxisLine = lineChart.getXAxis();
        //描述
        xAxisLine.setDrawLabels(true);
        //位置
        xAxisLine.setPosition(XAxis.XAxisPosition.BOTTOM);
        //画虚线
        xAxisLine.setDrawGridLines(true);
        //
        xAxisLine.setDrawAxisLine(false);
        //画线的颜色
        xAxisLine.setAxisLineColor(getResources().getColor(R.color.divider_normal));
        //虚线
        xAxisLine.enableGridDashedLine(10f, 10f, 0f);


        //左边y
        axisLeftLine = lineChart.getAxisLeft();
        axisLeftLine.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        /*折线图y轴左没有basevalue，调用系统的*/
        axisLeftLine.setLabelCount(5, true);
        axisLeftLine.setDrawLabels(true);
        axisLeftLine.setDrawAxisLine(false);
        axisLeftLine.setDrawGridLines(true);
        axisLeftLine.setTextColor(getResources().getColor(R.color.text_gray));
        axisLeftLine.setAxisLineColor(getResources().getColor(R.color.divider_normal));
        axisLeftLine.enableGridDashedLine(10f, 10f, 0f);


        //右边y
        axisRightLine = lineChart.getAxisRight();
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


        //柱状图的 x y轴
        xAxisBar = barChart.getXAxis();
        //底部标签
        xAxisBar.setDrawLabels(false);
        //网格
        xAxisBar.setDrawGridLines(true);
        //虚线网格
        xAxisBar.setAxisLineColor(getResources().getColor(R.color.divider_normal));
        xAxisBar.enableGridDashedLine(10f, 10f, 0f);


        axisLeftBar = barChart.getAxisLeft();
        //最小值
        axisLeftBar.setAxisMinValue(0);
        //画网格
        axisLeftBar.setDrawGridLines(true);
        //字体颜色
        axisLeftBar.setTextColor(getResources().getColor(R.color.minute_zhoutv));
        axisLeftBar.setAxisLineColor(getResources().getColor(R.color.divider_normal));
        axisLeftBar.enableGridDashedLine(10f, 10f, 0f);

        axisRightBar = barChart.getAxisRight();
        axisRightBar.setDrawLabels(false);
        axisRightBar.setDrawGridLines(false);
        axisRightBar.setDrawAxisLine(false);


        //y轴样式
        this.axisLeftLine.setValueFormatter(new YAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, YAxis yAxis) {
                DecimalFormat mFormat = new DecimalFormat("#0.00");
                return mFormat.format(value);
            }
        });

        lineChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {
                barChart.highlightValue(new Highlight(h.getXIndex(), 0));
            }

            @Override
            public void onNothingSelected() {
                barChart.highlightValue(null);

            }
        });

        barChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {
                lineChart.highlightValue(new Highlight(h.getXIndex(), 0));
            }

            @Override
            public void onNothingSelected() {
                lineChart.highlightValue(null);
            }
        });

    }

    /**
     * 获取K线数据并加载
     *
     * @param chartType
     * @param security_symbol
     */
    private void FetchData(int chartType, String security_symbol) {
        JSONObject jsonObject = new JSONObject();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.add(Calendar.HOUR, -2);
        try {
            jsonObject.put("chartType", chartType);
            jsonObject.put("security_symbol", security_symbol);
            jsonObject.put("requestType", 2);
            jsonObject.put("endTime", format.format(System.currentTimeMillis()));
            jsonObject.put("starttime", format.format(calendar.getTime()));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RetrofitClient
                .getInstance()
                .api()
                .getInternationalKLine(new JsonRequestBody().convertJsonContent(jsonObject.toString()))
                .map(new Func1<InternationalKLineBean, ArrayList<MinutesBean>>() {
                    @Override
                    public ArrayList<MinutesBean> call(InternationalKLineBean internationalKLineBean) {
                        final InternationalDataParse mData = new InternationalDataParse();

                        mData.parseMinutes(internationalKLineBean);
                        stringSparseArray = setXLabels(internationalKLineBean);
                        setMarkerView(mData);
                        setShowLabels(stringSparseArray);
                        //设置y左右两轴最大最小值
                        axisLeftLine.setAxisMinValue(mData.getMin());
                        axisLeftLine.setAxisMaxValue(mData.getMax());
                        axisRightLine.setAxisMinValue(mData.getPercentMin());
                        axisRightLine.setAxisMaxValue(mData.getPercentMax());

                        axisLeftBar.setAxisMaxValue(mData.getVolmax());
        /*单位*/
                        String unit = KLineUtils.getVolUnit(mData.getVolmax());
                        int u = 1;
                        if ("万手".equals(unit)) {
                            u = 4;
                        } else if ("亿手".equals(unit)) {
                            u = 8;
                        }

                        axisLeftBar.setValueFormatter(new VolFormatter((int) Math.pow(10, u)));
                        axisLeftBar.setShowMaxAndUnit(unit);
                        axisLeftBar.setDrawLabels(true);
                        axisLeftBar.setShowOnlyMinMax(true);
                        axisRightBar.setAxisMaxValue(mData.getVolmax());

                        //基准线
                        LimitLine ll = new LimitLine(0);
                        ll.setLineWidth(1f);
                        ll.setLineColor(getResources().getColor(R.color.minute_jizhun));
                        ll.enableDashedLine(10f, 10f, 0f);
                        ll.setLineWidth(1);
                        axisRightLine.addLimitLine(ll);
                        axisRightLine.setBaseValue(0);
                        return mData.getDatas();
                    }
                })
                .compose(SchedulersCompat.<ArrayList<MinutesBean>>applyIoSchedulers())
                .subscribe(new Subscriber<ArrayList<MinutesBean>>() {
                    @Override
                    public void onCompleted() {
                        if (isFirst) {
                            setOffset();
                            isFirst = false;
                        }
                        lineChart.invalidate();//刷新图
                        barChart.invalidate();
                        if (loading.getVisibility() == View.VISIBLE) {
                            loading.setVisibility(View.GONE);
                        }
                        if (ll_chart.getVisibility() == View.INVISIBLE) {
                            ll_chart.setVisibility(View.VISIBLE);
                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.d(e.getMessage());


                    }

                    @Override
                    public void onNext(ArrayList<MinutesBean> minutesBeen) {
                        //设置K线数据
                        ArrayList<Entry> lineCJEntries = new ArrayList<>();//成交价
                        ArrayList<Entry> lineJJEntries = new ArrayList<>();//均价
                        ArrayList<BarEntry> barEntries = new ArrayList<>();//成交量
                        ArrayList<String> xVals = new ArrayList<>();//X轴坐标
                        for (int i = 0, j = 0; i < minutesBeen.size(); i++, j++) {
                            MinutesBean t = minutesBeen.get(j);

                            if (t == null) {
                                lineCJEntries.add(new Entry(Float.NaN, i));
                                barEntries.add(new BarEntry(Float.NaN, i));
                                continue;
                            }
                            lineCJEntries.add(new Entry(minutesBeen.get(i).cjprice, i));
                            lineJJEntries.add(new Entry(minutesBeen.get(i).avprice, i));
                            barEntries.add(new BarEntry(minutesBeen.get(i).cjnum, i));
                            xVals.add(i, minutesBeen.get(i).time);
                        }
                        LineDataSet d1 = new LineDataSet(lineCJEntries, "成交价");
                        d1.setDrawValues(false);
                        barDataSet = new BarDataSet(barEntries, "成交量");


                        d1.setCircleRadius(0);
                        d1.setColor(getResources().getColor(R.color.minute_blue));
                        d1.setHighLightColor(getResources().getColor(R.color.text_gray));
                        d1.setDrawFilled(true);


                        barDataSet.setBarSpacePercent(50); //bar空隙
                        barDataSet.setHighLightColor(getResources().getColor(R.color.text_gray));
                        barDataSet.setHighLightAlpha(255);
                        barDataSet.setDrawValues(false);
                        barDataSet.setHighlightEnabled(true);
                        barDataSet.setColor(Color.parseColor("#FB5974"));
                        List<Integer> list = new ArrayList<>();
                        list.add(Color.parseColor("#FB5974"));
                        list.add(Color.parseColor("#4ED86A"));
                        barDataSet.setColors(list);

                        //谁为基准
                        d1.setAxisDependency(YAxis.AxisDependency.LEFT);
                        ArrayList<ILineDataSet> sets = new ArrayList<>();
                        sets.add(d1);
        /*注老版本LineData参数可以为空，最新版本会报错，修改进入ChartData加入if判断*/
                        LineData cd = new LineData(xVals, sets);
                        lineChart.setData(cd);
                        BarData barData = new BarData(xVals, barDataSet);
                        barChart.setData(barData);
                    }
                });
    }


    /**
     * 坐标轴标识
     * @param stringSparseArray
     */
    private void setShowLabels(SparseArray<String> stringSparseArray) {
        xAxisLine.setXLabels(stringSparseArray);
        xAxisBar.setXLabels(stringSparseArray);
    }

    /**
     * 高亮时候的文字显示
     * @param mData
     */
    private void setMarkerView(InternationalDataParse mData) {
        MyLeftMarkerView leftMarkerView = new MyLeftMarkerView(getActivity(), R.layout.mymarkerview);
        MyRightMarkerView rightMarkerView = new MyRightMarkerView(getActivity(), R.layout.mymarkerview);
        MyBottomMarkerView bottomMarkerView = new MyBottomMarkerView(getActivity(), R.layout.mymarkerview);

        lineChart.setInternationalMarker(leftMarkerView, rightMarkerView, bottomMarkerView, mData);
        barChart.setInternationalMarker(leftMarkerView, rightMarkerView, bottomMarkerView, mData);
    }

    /*设置量表对齐*/
    private void setOffset() {
        float lineLeft = lineChart.getViewPortHandler().offsetLeft();
        float barLeft = barChart.getViewPortHandler().offsetLeft();
        float lineRight = lineChart.getViewPortHandler().offsetRight();
        float barRight = barChart.getViewPortHandler().offsetRight();
        float barBottom = barChart.getViewPortHandler().offsetBottom();
        float offsetLeft, offsetRight;
        float transLeft = 0, transRight = 0;
 /*注：setExtraLeft...函数是针对图表相对位置计算，比如A表offLeftA=20dp,B表offLeftB=30dp,则A.setExtraLeftOffset(10),并不是30，还有注意单位转换*/
        if (barLeft < lineLeft) {
            transLeft = lineLeft;

        } else {
            offsetLeft = Utils.convertPixelsToDp(barLeft - lineLeft);
            lineChart.setExtraLeftOffset(offsetLeft);
            transLeft = barLeft;
        }

  /*注：setExtraRight...函数是针对图表绝对位置计算，比如A表offRightA=20dp,B表offRightB=30dp,则A.setExtraLeftOffset(30),并不是10，还有注意单位转换*/
        if (barRight < lineRight) {
            transRight = lineRight;
        } else {
            offsetRight = Utils.convertPixelsToDp(barRight);
            lineChart.setExtraRightOffset(offsetRight);
            transRight = barRight;
        }
        barChart.setViewPortOffsets(transLeft, 5, transRight, barBottom);
    }

}

