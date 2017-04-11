package com.shawnway.nav.app.wtw.module.quotation.international.international_detail;

import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.SparseArray;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.CandleData;
import com.github.mikephil.charting.data.CandleDataSet;
import com.github.mikephil.charting.data.CandleEntry;
import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.YAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.Utils;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.orhanobut.logger.Logger;
import com.shawnway.nav.app.wtw.R;
import com.shawnway.nav.app.wtw.base.BaseFragment;
import com.shawnway.nav.app.wtw.base.BaseSubscriber;
import com.shawnway.nav.app.wtw.bean.InternationalKLineBean;
import com.shawnway.nav.app.wtw.bean.KLineBean;
import com.shawnway.nav.app.wtw.mychart.CoupleChartGestureListener;
import com.shawnway.nav.app.wtw.mychart.MyBarChart;
import com.shawnway.nav.app.wtw.mychart.MyBottomMarkerView;
import com.shawnway.nav.app.wtw.mychart.MyCombineChart;
import com.shawnway.nav.app.wtw.mychart.MyLeftMarkerView;
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
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;

/**
 * Created by Administrator on 2016/9/26.
 * 国际期货K线
 */

@SuppressWarnings("unchecked")
public class InternationalKLineFragment extends BaseFragment {


    @BindView(R.id.combinedchart)
    MyCombineChart combinedchart;
    @BindView(R.id.barchart)
    MyBarChart barChart;
    @BindView(R.id.ll_chart)
    LinearLayout ll_chart;
    @BindView(R.id.loading)
    ProgressBar loading;
    private ArrayList<CandleEntry> candleEntries;
    private CandleData candleData;
    private ArrayList<String> xVals;
    private float sum;
    private MyXAxis xAxisBar;
    private MyXAxis xAxisK;
    private MyYAxis axisLeftBar, axisRightBar;
    private YAxis axisLeftK, axisRightK;
    BarDataSet barDataSet;
    SparseArray<String> stringSparseArray;

    private int colorMa5;
    private int colorMa10;
    private int colorMa20;
    private boolean isFirst = true;
    private int chartType;


    public static InternationalKLineFragment newInstance(int chartType, String security_symbol) {

        Bundle args = new Bundle();
        args.putInt("chartType", chartType);
        args.putString("security_symbol", security_symbol);
        InternationalKLineFragment fragment = new InternationalKLineFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_international_kline;
    }

    @Override
    protected void initEventAndData() {
        chartType = getArguments().getInt("chartType");
        String security_symbol = getArguments().getString("security_symbol");
        initChart();
        FetchData(chartType, security_symbol);

    }

    private void FetchData(int chartType, String security_symbol) {
        JSONObject jsonObject = new JSONObject();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
        try {
            jsonObject.put("chartType", chartType);
            jsonObject.put("security_symbol", security_symbol);
            jsonObject.put("requestType", 1);
            jsonObject.put("endTime", format.format(System.currentTimeMillis()));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RetrofitClient
                .getInstance()
                .api()
                .getInternationalKLine(new JsonRequestBody().convertJsonContent(jsonObject.toString()))
                //call方法执行在IO线程，由于数据量过大时会导致主界面卡顿，所以将数据的转换放在IO线程
                .map(new Func1<InternationalKLineBean, ArrayList<KLineBean>>() {
                    @Override
                    public ArrayList<KLineBean> call(InternationalKLineBean internationalKLineBean) {
                        InternationalDataParse mData = new InternationalDataParse();
                        stringSparseArray = setXLabels(internationalKLineBean);
                        //K线的坐标
                        setShowLabels(stringSparseArray);
                        //设置数据
                        mData.parseKLine(internationalKLineBean);
                        //设置高亮Marker
                        setMarkerView(mData);
                        //柱状图Y轴坐标
                        String unit = KLineUtils.getVolUnit(mData.getVolmax());
                        int u = 1;
                        if ("万手".equals(unit)) {
                            u = 4;
                        } else if ("亿手".equals(unit)) {
                            u = 8;
                        }
                        axisLeftBar.setValueFormatter(new VolFormatter((int) Math.pow(10, u)));
                        return mData.getKLineDatas();
                    }
                })
                .compose(SchedulersCompat.<ArrayList<KLineBean>>applyIoSchedulers())
                .subscribe(new Subscriber<ArrayList<KLineBean>>() {
                    @Override
                    public void onCompleted() {
                        if (isFirst) {
                            //第一次对其量表，多次调用会导致图表变形
                            setOffset();
                            isFirst = false;
                        }
                        //延时执行，否则会导致Y轴坐标显示错乱
                        Observable.timer(100, TimeUnit.MILLISECONDS)
                                .compose(SchedulersCompat.<Long>applyIoSchedulers())
                                .subscribe(new BaseSubscriber<Long>() {
                                    @Override
                                    public void onSuccess(Long aLong) {
                                        barChart.setAutoScaleMinMaxEnabled(true);
                                        combinedchart.setAutoScaleMinMaxEnabled(true);

                                        combinedchart.notifyDataSetChanged();
                                        barChart.notifyDataSetChanged();

                                        combinedchart.invalidate();
                                        barChart.invalidate();
                                        if (loading.getVisibility() == View.VISIBLE) {
                                            loading.setVisibility(View.GONE);
                                        }
                                        if (ll_chart.getVisibility() == View.INVISIBLE) {
                                            ll_chart.setVisibility(View.VISIBLE);
                                        }
                                    }
                                });

                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.d(e.getMessage());
                        Observable.timer(300, TimeUnit.MILLISECONDS)
                                .compose(SchedulersCompat.<Long>applyIoSchedulers())
                                .subscribe(new BaseSubscriber<Long>() {
                                    @Override
                                    public void onSuccess(Long aLong) {
                                        barChart.setAutoScaleMinMaxEnabled(true);
                                        combinedchart.setAutoScaleMinMaxEnabled(true);

                                        combinedchart.notifyDataSetChanged();
                                        barChart.notifyDataSetChanged();

                                        combinedchart.invalidate();
                                        barChart.invalidate();
                                        if (loading.getVisibility() == View.VISIBLE) {
                                            loading.setVisibility(View.GONE);
                                        }
                                        if (ll_chart.getVisibility() == View.INVISIBLE) {
                                            ll_chart.setVisibility(View.VISIBLE);
                                        }
                                    }
                                });
                    }

                    @Override
                    public void onNext(ArrayList<KLineBean> kLineBeen) {
                        combinedchart.resetTracking();
                        xVals = new ArrayList<>();
                        candleEntries = new ArrayList<>();


                        ArrayList<Entry> line5Entries = new ArrayList<>();
                        ArrayList<Entry> line10Entries = new ArrayList<>();
                        ArrayList<Entry> line20Entries = new ArrayList<>();
                        List<CandleEntry> entries = new ArrayList<>();
                        ArrayList<BarEntry> barEntries = new ArrayList<>();

                        for (int i = 0; i < kLineBeen.size(); i++) {
                            xVals.add(kLineBeen.get(i).date.substring(11, 16));
                            barEntries.add(new BarEntry(kLineBeen.get(i).vol, i));
                            CandleEntry entry = new CandleEntry(i,
                                    kLineBeen.get(i).high,
                                    kLineBeen.get(i).low,
                                    kLineBeen.get(i).open,
                                    kLineBeen.get(i).close);
                            entries.add(entry);
                            if (i >= 4) {
                                sum = 0;
                                line5Entries.add(new Entry(getSum(i - 4, i, kLineBeen) / 5, i));
                            }
                            if (i >= 9) {
                                sum = 0;
                                line10Entries.add(new Entry(getSum(i - 9, i, kLineBeen) / 10, i));
                            }
                            if (i >= 19) {
                                sum = 0;
                                line20Entries.add(new Entry(getSum(i - 19, i, kLineBeen) / 20, i));
                            }
                        }

                        barDataSet = new BarDataSet(barEntries, "成交量");
                        barDataSet.setBarSpacePercent(50); //bar空隙
                        barDataSet.setHighLightColor(getResources().getColor(R.color.text_gray));
                        barDataSet.setHighLightAlpha(255);
                        barDataSet.setDrawValues(false);
                        barDataSet.setHighlightEnabled(true);
                        barDataSet.setColor(getResources().getColor(R.color.kLine_red));
                        List<Integer> list = new ArrayList<>();
                        list.add(getResources().getColor(R.color.kLine_red));
                        list.add(getResources().getColor(R.color.kLine_green));
                        barDataSet.setColors(list);
                        BarData barData = new BarData(xVals, barDataSet);
                        barChart.setData(barData);
                        final ViewPortHandler viewPortHandlerBar = barChart.getViewPortHandler();

                        viewPortHandlerBar.setMaximumScaleX(culcMaxscale(xVals.size()));
                        Matrix touchmatrix = viewPortHandlerBar.getMatrixTouch();
                        touchmatrix.postScale(3, 1f);

                        candleEntries.addAll(entries);
                        ArrayList<ILineDataSet> sets = new ArrayList<>();
                        sets.add(setMaLine(5, xVals, line5Entries));//5分钟走势
                        sets.add(setMaLine(10, xVals, line10Entries));//10分钟走势
                        sets.add(setMaLine(20, xVals, line20Entries));//20分钟走势


                        CombinedData combinedData = new CombinedData(xVals);
                        candleData = generateCandleData();


                        LineData lineData = new LineData(xVals, sets);
                        combinedData.setData(candleData);
                        combinedData.setData(lineData);
                        combinedchart.setData(combinedData);
                        combinedchart.moveViewToX(candleEntries.size() - 1);
                        final ViewPortHandler viewPortHandlerCombin = combinedchart.getViewPortHandler();
                        viewPortHandlerCombin.setMaximumScaleX(culcMaxscale(xVals.size()));
                        Matrix matrixCombin = viewPortHandlerCombin.getMatrixTouch();

                        matrixCombin.postScale(3, 1f);
                        //移动到最后
                        combinedchart.moveViewToX(kLineBeen.size() - 1);
                        barChart.moveViewToX(kLineBeen.size() - 1);
                    }
                });
    }

    private void initChart() {


        colorMa5 = getResources().getColor(R.color.ma5);
        colorMa10 = getResources().getColor(R.color.ma10);
        colorMa20 = getResources().getColor(R.color.ma20);

        combinedchart.setPinchZoom(true);//允许缩放
        combinedchart.setDrawBorders(true);//边线
        combinedchart.setBorderWidth(1);//边线宽度
        combinedchart.setAutoScaleMinMaxEnabled(true);
        combinedchart.setBorderColor(getResources().getColor(R.color.divider_normal));//边线颜色
        combinedchart.setDescription("");
        combinedchart.setDragEnabled(true);
        combinedchart.setScaleYEnabled(false);//不允许Y轴缩放

        combinedchart.setDrawGridBackground(false);
        combinedchart.setDrawValueAboveBar(false);
        combinedchart.setNoDataText("暂无数据");
        combinedchart.setDrawOrder(new CombinedChart.DrawOrder[]{CombinedChart.DrawOrder.CANDLE, CombinedChart.DrawOrder.LINE});
        //禁止双击缩放
        combinedchart.setDoubleTapToZoomEnabled(false);

        int[] colors = {colorMa5, colorMa10, colorMa20};
        String[] labels = {"MA5", "MA10", "MA20"};
        Legend legend = combinedchart.getLegend();//MA线标识
        legend.setEnabled(true);//显示颜色标识
        legend.setCustom(colors, labels);
        legend.setPosition(Legend.LegendPosition.ABOVE_CHART_RIGHT);
        legend.setTextColor(Color.BLACK);

        combinedchart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry entry, int i, Highlight highlight) {
                CandleEntry candleEntry = (CandleEntry) entry;
                float change = (candleEntry.getOpen() - candleEntry.getClose()) / candleEntry.getClose();
                NumberFormat nf = NumberFormat.getPercentInstance();
                nf.setMaximumFractionDigits(2);
                String changePercentage = nf.format(Double.valueOf(String.valueOf(change)));
               ((InternationalDetailActivity) mContext).ll_kline_data.setVisibility(View.VISIBLE);

                ((InternationalDetailActivity) mContext).klineOpenPrice.setText(String.format("开 %s", candleEntry.getOpen()));
                ((InternationalDetailActivity) mContext).klineHighPrice.setText(String.format("高 %s", candleEntry.getHigh()));
                ((InternationalDetailActivity) mContext).klineLowPrice.setText(String.format("低 %s", candleEntry.getOpen()));
                ((InternationalDetailActivity) mContext).klineClosePrice.setText(String.format("收 %s", candleEntry.getClose()));
                ((InternationalDetailActivity) mContext).klineRate.setText(String.format("涨跌(%s)", changePercentage));
                ((InternationalDetailActivity) mContext).klineRate.setTextColor(change >= 0 ? getResources().getColor(R.color.appcolor) : getResources().getColor(R.color.lightgreen));

                barChart.highlightValue(new Highlight(highlight.getXIndex(), 0));
            }

            @Override
            public void onNothingSelected() {
                barChart.highlightValue(null);
                ((InternationalDetailActivity) mContext).ll_kline_data.setVisibility(View.INVISIBLE);

            }
        });

        barChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {
                combinedchart.highlightValue(new Highlight(h.getXIndex(), 0));
            }

            @Override
            public void onNothingSelected() {
                combinedchart.highlightValue(null);
            }
        });

        //candle x 轴
        xAxisK = combinedchart.getXAxis();
        xAxisK.setDrawLabels(true);
        xAxisK.setDrawGridLines(true);
        xAxisK.setDrawAxisLine(false);
        xAxisK.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxisK.setTextColor(getResources().getColor(R.color.text_gray));
        xAxisK.setAxisLineColor(getResources().getColor(R.color.divider_normal));
        xAxisK.enableGridDashedLine(10f, 10f, 0f);

        //左边Y轴
        axisLeftK = combinedchart.getAxisLeft();
        axisLeftK.setDrawLabels(true);
        axisLeftK.setLabelCount(5, true);
        axisLeftK.setDrawGridLines(true);
        axisLeftK.setTextColor(getResources().getColor(R.color.text_gray));
        axisLeftK.setAxisLineColor(getResources().getColor(R.color.divider_normal));
        axisLeftK.enableGridDashedLine(10f, 10f, 0f);
        axisLeftK.setValueFormatter(new YAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, YAxis yAxis) {
                DecimalFormat decimalFormat = new DecimalFormat("0.000");
                return decimalFormat.format(value);
            }
        });
        /**
         * 蜡烛图右边
         */
        axisRightK = combinedchart.getAxisRight();
        axisRightK.setEnabled(false);

        /************************柱状图*************************/
        barChart.setNoDataText("暂无数据");
        barChart.setDrawBorders(true);
        barChart.setBorderWidth(1);
        barChart.setBorderColor(getResources().getColor(R.color.divider_normal));
        barChart.setDescription("");
        barChart.setDragEnabled(true);
        barChart.setScaleYEnabled(false);

        Legend barChartLegend = barChart.getLegend();
        barChartLegend.setEnabled(false);

        //bar x 轴
        xAxisBar = barChart.getXAxis();
        xAxisBar.setDrawLabels(true);
        xAxisBar.setDrawGridLines(false);
        xAxisBar.setDrawAxisLine(false);
        xAxisBar.setTextColor(getResources().getColor(R.color.minute_zhoutv));
        xAxisBar.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxisBar.setGridColor(getResources().getColor(R.color.divider_normal));
        xAxisBar.setAxisLineColor(getResources().getColor(R.color.divider_normal));

        //bar y 轴
        axisLeftBar = barChart.getAxisLeft();
        axisLeftBar.setAxisMinValue(0);
        axisLeftBar.setDrawGridLines(true);
        axisLeftBar.setDrawAxisLine(true);
        axisLeftBar.setDrawLabels(true);
        axisLeftBar.setSpaceTop(0);
        axisLeftBar.setShowOnlyMinMax(true);
        axisLeftBar.setTextColor(getResources().getColor(R.color.text_gray));
        axisLeftBar.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        axisLeftBar.setAxisLineColor(getResources().getColor(R.color.divider_normal));
        axisLeftBar.enableGridDashedLine(10f, 10f, 0f);
        //柱状图右边
        axisRightBar = barChart.getAxisRight();
        axisRightBar.setEnabled(false);

        // 将K线控的滑动事件传递给交易量控件
        combinedchart.setOnChartGestureListener(new CoupleChartGestureListener(combinedchart, new Chart[]{barChart}));
        // 将交易量控件的滑动事件传递给K线控件
        barChart.setOnChartGestureListener(new CoupleChartGestureListener(barChart, new Chart[]{combinedchart}));


    }


    private SparseArray<String> setXLabels(InternationalKLineBean bean) {
        SparseArray<String> xLabels = new SparseArray<>();
        int count = bean.getMarketDataCandleChart().size();
        xLabels.put(0, bean.getMarketDataCandleChart().get(0).getChartTime().substring(11, 16));
        xLabels.put(count / 4, bean.getMarketDataCandleChart().get(count / 4).getChartTime().substring(11, 16));
        xLabels.put(count / 2, bean.getMarketDataCandleChart().get(count / 2).getChartTime().substring(11, 16));
        xLabels.put(count * 3 / 4, bean.getMarketDataCandleChart().get(count * 3 / 4).getChartTime().substring(11, 16));
        xLabels.put(count - 1, bean.getMarketDataCandleChart().get(count - 1).getChartTime().substring(11, 16));

        return xLabels;

    }

    private void setMarkerView(InternationalDataParse mData) {
        MyLeftMarkerView leftMarkerView = new MyLeftMarkerView(getActivity(), R.layout.mymarkerview);
        MyRightMarkerView rightMarkerView = new MyRightMarkerView(getActivity(), R.layout.mymarkerview);
        MyBottomMarkerView bottomMarkerView = new MyBottomMarkerView(getActivity(), R.layout.mymarkerview);
        combinedchart.setInternationalMarker(leftMarkerView, bottomMarkerView, mData);
        barChart.setInternationalMarker(leftMarkerView, rightMarkerView, bottomMarkerView, mData);
    }


    /**
     * 计算最大缩放
     *
     * @param count
     * @return
     */
    private float culcMaxscale(float count) {
        float max;
        max = count / 127 * 5;
        return max;
    }


    /**
     * 设置均线
     *
     * @param ma
     * @param xVals
     * @param lineEntries
     * @return
     */
    @NonNull
    private LineDataSet setMaLine(int ma, ArrayList<String> xVals, ArrayList<Entry> lineEntries) {
        LineDataSet lineDataSetMa = new LineDataSet(lineEntries, "ma" + ma);
        lineDataSetMa.setHighlightEnabled(false);
        lineDataSetMa.setDrawValues(false);
        if (ma == 5) {
            lineDataSetMa.setColor(colorMa5);
        } else if (ma == 10) {
            lineDataSetMa.setColor(colorMa10);
        } else {
            lineDataSetMa.setColor(colorMa20);
        }
        lineDataSetMa.setLineWidth(1f);
        lineDataSetMa.setDrawCircles(false);
        lineDataSetMa.setDrawCircleHole(false);
        lineDataSetMa.setDrawCubic(true);
        lineDataSetMa.setAxisDependency(YAxis.AxisDependency.LEFT);
        return lineDataSetMa;
    }

    private float getSum(Integer a, Integer b, ArrayList<KLineBean> kLineBeen) {

        for (int i = a; i <= b; i++) {
            sum += kLineBeen.get(i).close;
        }
        return sum;
    }

    /**
     * 蜡烛图数据
     *
     * @return
     */
    private CandleData generateCandleData() {

        CandleDataSet set = new CandleDataSet(candleEntries, "KLine");
        set.setHighlightEnabled(true);
        set.setHighLightColor(getResources().getColor(R.color.text_gray));
        set.setIncreasingColor(Color.parseColor("#FB5974"));//涨的颜色
        set.setIncreasingPaintStyle(Paint.Style.FILL_AND_STROKE);//实心涨
        set.setDecreasingColor(Color.parseColor("#4ED86A"));//跌的颜色
        set.setDecreasingPaintStyle(Paint.Style.FILL_AND_STROKE);//空心跌
        set.setNeutralColor(Color.parseColor("#FB5974"));//当天价格不涨不跌（一字线）颜色
        set.setShadowColorSameAsCandle(true);//上下的线和candle一样颜色
        set.setValueTextSize(10f);
        set.setDrawValues(false);
        set.setShadowWidth(1f);
        set.setAxisDependency(YAxis.AxisDependency.LEFT);
        candleData = new CandleData(xVals);
        candleData.addDataSet(set);

        return candleData;
    }

    private void setShowLabels(SparseArray<String> stringSparseArray) {
        xAxisK.setXLabels(stringSparseArray);
        xAxisBar.setXLabels(stringSparseArray);
    }

    /*设置量表对齐*/
    private void setOffset() {
        float lineLeft = combinedchart.getViewPortHandler().offsetLeft();
        float barLeft = barChart.getViewPortHandler().offsetLeft();
        float lineRight = combinedchart.getViewPortHandler().offsetRight();
        float barRight = barChart.getViewPortHandler().offsetRight();
        float barBottom = barChart.getViewPortHandler().offsetBottom();
        float offsetLeft, offsetRight;
        float transLeft = 0, transRight = 0;
 /*注：setExtraLeft...函数是针对图表相对位置计算，比如A表offLeftA=20dp,B表offLeftB=30dp,则A.setExtraLeftOffset(10),并不是30，还有注意单位转换*/
        if (barLeft < lineLeft) {
            transLeft = lineLeft;
        } else {
            offsetLeft = Utils.convertPixelsToDp(barLeft - lineLeft);
            combinedchart.setExtraLeftOffset(offsetLeft);
            transLeft = barLeft;
        }
  /*注：setExtraRight...函数是针对图表绝对位置计算，比如A表offRightA=20dp,B表offRightB=30dp,则A.setExtraLeftOffset(30),并不是10，还有注意单位转换*/
        if (barRight < lineRight) {
            transRight = lineRight;
        } else {
            offsetRight = Utils.convertPixelsToDp(barRight);
            combinedchart.setExtraRightOffset(offsetRight);
            transRight = barRight;
        }
        barChart.setViewPortOffsets(transLeft, 15, transRight, barBottom);
    }
}
