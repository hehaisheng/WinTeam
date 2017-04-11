package com.shawnway.nav.app.wtw.module.quotation.jl_wp.jl_wp_detail.jl_wp_kline;

import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.SparseArray;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
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
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.orhanobut.logger.Logger;
import com.shawnway.nav.app.wtw.R;
import com.shawnway.nav.app.wtw.base.BaseFragment;
import com.shawnway.nav.app.wtw.bean.DataParse;
import com.shawnway.nav.app.wtw.bean.KLineBean;
import com.shawnway.nav.app.wtw.bean.QuotationsWPBean;
import com.shawnway.nav.app.wtw.bean.WpKlineBean;
import com.shawnway.nav.app.wtw.mychart.MyBottomMarkerView;
import com.shawnway.nav.app.wtw.mychart.MyCombineChart;
import com.shawnway.nav.app.wtw.mychart.MyLeftMarkerView;
import com.shawnway.nav.app.wtw.mychart.MyXAxis;
import com.shawnway.nav.app.wtw.net.JlWPRetrofitClient;
import com.shawnway.nav.app.wtw.tool.SchedulersCompat;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

import butterknife.BindView;
import rx.Subscriber;
import rx.functions.Func1;

public class JlWpKLineFragment extends BaseFragment {


    @BindView(R.id.combinedchart)
    MyCombineChart combinedchart;
    @BindView(R.id.ll_chart)
    LinearLayout ll_chart;
    @BindView(R.id.loading)
    ProgressBar loading;

    private QuotationsWPBean mGoods;
    private int type;

    private MyXAxis xAxisK;
    private YAxis axisLeftK, axisRightK;
    private ArrayList<CandleEntry> candleEntries;
    SparseArray<String> stringSparseArray;

    private int colorMa5;
    private int colorMa10;
    private int colorMa20;

    float sum = 0;
    private CandleData candleData;
    private ArrayList<String> xVals;

    public static JlWpKLineFragment getInstance(QuotationsWPBean bean, int i) {
        JlWpKLineFragment fragment = new JlWpKLineFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("bean", bean);
        bundle.putInt("type", i);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_kline;
    }

    @Override
    protected void initEventAndData() {
        Bundle bundle = getArguments();
        mGoods = (QuotationsWPBean) bundle.getSerializable("bean");
        type = bundle.getInt("type");
        initChart();
        FetchDataFromServer();
    }

    private void FetchDataFromServer() {
        JlWPRetrofitClient
                .getInstance()
                .api()
                .getWpKLine(type, mGoods.getProductContract())
                .map(new Func1<WpKlineBean, ArrayList<KLineBean>>() {
                    @Override
                    public ArrayList<KLineBean> call(WpKlineBean wpKlineBean) {
                        DataParse mDataParse = new DataParse();
                        mDataParse.parseKLine(wpKlineBean);
                        stringSparseArray = setXLabels(wpKlineBean);
                        setShowLabels(stringSparseArray);
                        setMarkerView(mDataParse);
                        return mDataParse.getKLineDatas();
                    }
                })
                .compose(SchedulersCompat.<ArrayList<KLineBean>>applyIoSchedulers())
                .subscribe(new Subscriber<ArrayList<KLineBean>>() {
                    @Override
                    public void onCompleted() {
                        combinedchart.setAutoScaleMinMaxEnabled(true);

                        combinedchart.notifyDataSetChanged();

                        combinedchart.invalidate();

                        if (loading.getVisibility() == View.VISIBLE) {
                            loading.setVisibility(View.GONE);
                        }
                        if (ll_chart.getVisibility() == View.INVISIBLE) {
                            ll_chart.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.e(e.getMessage());

                    }

                    @Override
                    public void onNext(ArrayList<KLineBean> kLineBeen) {
                        combinedchart.resetTracking();
                        xVals = new ArrayList<>();
                        candleEntries = new ArrayList<>();
                        candleEntries = new ArrayList<>();
                        ArrayList<Entry> line5Entries = new ArrayList<>();
                        ArrayList<Entry> line10Entries = new ArrayList<>();
                        ArrayList<Entry> line20Entries = new ArrayList<>();
                        for (int i = 0; i < kLineBeen.size(); i++) {
                            xVals.add(kLineBeen.get(i).date + "");
                            CandleEntry entry = new CandleEntry(i,
                                    kLineBeen.get(i).high,
                                    kLineBeen.get(i).low,
                                    kLineBeen.get(i).open,
                                    kLineBeen.get(i).close);
                            candleEntries.add(entry);
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

                        combinedchart.moveViewToX(kLineBeen.size() - 1);
                    }
                });

    }


    @SuppressWarnings("unchecked")
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


        //candle x 轴
        xAxisK = combinedchart.getXAxis();
        xAxisK.setDrawLabels(true);
        xAxisK.setDrawGridLines(true);
        xAxisK.setDrawAxisLine(false);
        xAxisK.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxisK.setTextColor(getResources().getColor(R.color.text_gray));
        xAxisK.setAxisLineColor(getResources().getColor(R.color.divider_normal));
        xAxisK.enableGridDashedLine(10f, 10f, 0f);

        //K线图的x y轴
        //左边Y轴
        axisLeftK = combinedchart.getAxisLeft();
        axisLeftK.setDrawLabels(true);
        axisLeftK.setLabelCount(5, true);
        axisLeftK.setDrawAxisLine(false);
        axisLeftK.setDrawGridLines(true);
        axisLeftK.setTextColor(getResources().getColor(R.color.text_gray));
        axisLeftK.setAxisLineColor(getResources().getColor(R.color.divider_normal));
        axisLeftK.enableGridDashedLine(10f, 10f, 0f);
        axisLeftK.setValueFormatter(new YAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, YAxis yAxis) {
                DecimalFormat decimalFormat = new DecimalFormat("0.00");
                return decimalFormat.format(value);
            }
        });


        /**
         * 蜡烛图右边
         */
        axisRightK = combinedchart.getAxisRight();
        axisRightK.setEnabled(false);
        //惯性滑动
        combinedchart.setDragDecelerationEnabled(true);

        combinedchart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {

                CandleEntry candleEntry = (CandleEntry)e;
                float change = (candleEntry.getOpen() - candleEntry.getClose()) / candleEntry.getClose();
                NumberFormat nf = NumberFormat.getPercentInstance();
                nf.setMaximumFractionDigits(2);
                String changePercentage = nf.format(Double.valueOf(String.valueOf(change)));
//                ((JlWpDetailActivity) mContext).wp_ll_kline_data.setVisibility(View.VISIBLE);
//                ((InternationalDetailActivity) mContext).klineOpenPrice.setText(String.format("开 %s", candleEntry.getOpen()));
//                ((InternationalDetailActivity) mContext).klineHighPrice.setText(String.format("高 %s", candleEntry.getHigh()));
//                ((InternationalDetailActivity) mContext).klineLowPrice.setText(String.format("低 %s", candleEntry.getOpen()));
//                ((InternationalDetailActivity) mContext).klineClosePrice.setText(String.format("收 %s", candleEntry.getClose()));
//                ((InternationalDetailActivity) mContext).klineRate.setText(String.format("涨跌(%s)", changePercentage));
//                ((InternationalDetailActivity) mContext).klineRate.setTextColor(change >= 0 ? getResources().getColor(R.color.appcolor) : getResources().getColor(R.color.lightgreen));

                LinearLayout linearLayout=(LinearLayout) getActivity().findViewById(R.id.wp_ll_kline_data);
                linearLayout.setVisibility(View.VISIBLE);
                ((TextView)linearLayout.findViewById(R.id.wp_kline_openPrice) ).setText(String.format("开 %s", candleEntry.getOpen()));
                ((TextView)linearLayout.findViewById(R.id.wp_kline_highPrice) ).setText(String.format("高 %s", candleEntry.getHigh()));
                ((TextView)linearLayout.findViewById(R.id.wp_kline_closePrice) ).setText(String.format("收 %s", candleEntry.getClose()));
                ((TextView)linearLayout.findViewById(R.id.wp_kline_lowPrice) ).setText(String.format("低 %s", candleEntry.getLow()));
                ((TextView)linearLayout.findViewById(R.id.wp_kline_rate) ).setText(String.format("涨跌(%s)", changePercentage));
                ((TextView)linearLayout.findViewById(R.id.wp_kline_rate) ).setTextColor(change >= 0 ? getResources().getColor(R.color.appcolor) : getResources().getColor(R.color.lightgreen));


            }

            @Override
            public void onNothingSelected() {
                getActivity().findViewById(R.id.wp_ll_kline_data).setVisibility(View.INVISIBLE);
            }
        });
    }

    private SparseArray<String> setXLabels(WpKlineBean bean) {
        SparseArray<String> xLabels = new SparseArray<>();
        //2016-10-12 00:07:59
        int count = bean.getTime().size();
        xLabels.put(0, bean.getTime().get(0));
        xLabels.put(count / 4, bean.getTime().get(count / 4));
        xLabels.put(count / 2, bean.getTime().get(count / 2));
        xLabels.put(count * 3 / 4, bean.getTime().get(count * 3 / 4));
        xLabels.put(count - 1, bean.getTime().get(count - 1));

        return xLabels;

    }


    private void setMarkerView(DataParse mData) {
        MyLeftMarkerView leftMarkerView = new MyLeftMarkerView(getActivity(), R.layout.mymarkerview);
        MyBottomMarkerView bottomMarkerView = new MyBottomMarkerView(getActivity(), R.layout.mymarkerview);
        combinedchart.setWpMarker(leftMarkerView, bottomMarkerView, mData);
    }

    private float getSum(Integer a, Integer b, ArrayList<KLineBean> kLineBeen) {

        for (int i = a; i <= b; i++) {
            sum += kLineBeen.get(i).close;
        }
        return sum;
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

    }

}
