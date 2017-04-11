package com.shawnway.nav.app.wtw.module.quotation.international.international_detail;

import android.util.SparseArray;

import com.shawnway.nav.app.wtw.bean.InternationalKLineBean;
import com.shawnway.nav.app.wtw.bean.KLineBean;
import com.shawnway.nav.app.wtw.bean.MinutesBean;
import com.shawnway.nav.app.wtw.tool.NumberUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/10/9.
 */

public class InternationalDataParse {

    private static final String TAG = "DataParse";
    private ArrayList<MinutesBean> datas = new ArrayList<>();
    private ArrayList<KLineBean> kDatas = new ArrayList<>();
    private ArrayList<String> pointDatas = new ArrayList<>();
    private float baseValue;
    private float permaxmin;
    private float volmax;
    private SparseArray<String> xValuesLabel=new SparseArray<>();

    public void parseMinutes(InternationalKLineBean bean) {
        /**
         * public String time;//横坐标的时间点
         * public float cjprice;//左边显示的价格
         * public float cjnum;//后一个大的价格减去前一个大的价格
         * public float avprice = Float.NaN;//所有cjprice的平均值
         * public float per;//右边的百分比
         * public float cha;//差值
         * public float total;//总的差值
         */
        int count = bean.getMarketDataCandleChart().size();
        for(int i = 0;i<count;i++){
            pointDatas.add(bean.getMarketDataCandleChart().get(i).getChartLastPrice()+"");
        }
        for(int i = 0;i<count;i++){
            MinutesBean minutesData = new MinutesBean();
            baseValue = NumberUtils.avOfCollection(pointDatas);
            minutesData.time = bean.getMarketDataCandleChart().get(i).getChartTime();
            minutesData.cjprice = bean.getMarketDataCandleChart().get(i).getChartLastPrice().floatValue();
            minutesData.cha = minutesData.cjprice - baseValue;
            minutesData.per = minutesData.cha / baseValue;
            minutesData.avprice = (float) bean.getMarketDataCandleChart().get(i).getChartAvgPrice();
            minutesData.cjnum = bean.getMarketDataCandleChart().get(i).getChartVolume().floatValue();
            double cha = minutesData.cjprice - baseValue;
            if (Math.abs(cha) > permaxmin) {
                permaxmin = (float) Math.abs(cha);
            }
            volmax = Math.max(minutesData.cjnum,volmax);
            datas.add(minutesData);
        }

        if (permaxmin == 0) {
            permaxmin = (float) (baseValue * 0.02f);
        }
    }


    public void parseKLine(InternationalKLineBean bean){
        List<KLineBean> kLineBeanList = new ArrayList<>();
        for (int i = 0; i < bean.getMarketDataCandleChart().size(); i++) {
            KLineBean kLineBean = new KLineBean();
            kLineBean.date = bean.getMarketDataCandleChart().get(i).getChartTime();
            kLineBean.open = bean.getMarketDataCandleChart().get(i).getChartOpenPrice().floatValue();
            kLineBean.close = bean.getMarketDataCandleChart().get(i).getChartClosePrice().floatValue();
            kLineBean.high = bean.getMarketDataCandleChart().get(i).getChartHighPrice().floatValue();
            kLineBean.low = bean.getMarketDataCandleChart().get(i).getChartLowPrice().floatValue();
            kLineBean.vol = bean.getMarketDataCandleChart().get(i).getChartVolume().floatValue();
            volmax = Math.max(kLineBean.vol,volmax);
            xValuesLabel.put(i,kLineBean.date);
            kLineBeanList.add(kLineBean);
        }
        kDatas.addAll(kLineBeanList);
    }

    public float getMin() {
        return (float) (baseValue - permaxmin);
    }

    public float getMax() {
        return (float) (baseValue + permaxmin);
    }

    public float getPercentMax() {
        return (float) (permaxmin / baseValue);
    }

    public float getPercentMin() {
        return -getPercentMax();
    }

    public float getVolmax(){
        return volmax;
    }


    public ArrayList<MinutesBean> getDatas() {
        return datas;
    }

    public ArrayList<KLineBean> getKLineDatas() {
        return kDatas;
    }

}
