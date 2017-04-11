package com.shawnway.nav.app.wtw.bean;

import android.util.SparseArray;

import com.shawnway.nav.app.wtw.tool.NumberUtils;

import java.util.ArrayList;

/**
 * 将K线数据进行转化
 */
public class DataParse {
    private static final String TAG = "DataParse";
    private ArrayList<MinutesBean> datas = new ArrayList<>();
    private ArrayList<KLineBean> kDatas = new ArrayList<>();
    private float baseValue;//左边中间点的值
    private float permaxmin;//左边以中间点的值为准上下浮动多大
    private SparseArray<String> xValuesLabel = new SparseArray<>();

    public void parseMinutes(WpTimeLineBean bean) {
        /**
         * public String time;//横坐标的时间点
         * public float cjprice;//左边显示的价格
         * public float avprice = Float.NaN;//所有cjprice的平均值
         * public float per;//右边的百分比
         * public float cha;//差值
         * public float total;//总的差值
         */
        baseValue = NumberUtils.avOfCollection(bean.getData());
        int count = bean.getData().size();
        for (int i = 0; i < count; i++) {
            MinutesBean minutesData = new MinutesBean();
            minutesData.time = bean.getTime().get(i);
            minutesData.cjprice = Float.parseFloat(bean.getData().get(i));
            minutesData.cha = minutesData.cjprice - baseValue;
            minutesData.per = (minutesData.cha / baseValue);
            double cha = minutesData.cjprice - baseValue;
            if (Math.abs(cha) > permaxmin) {
                permaxmin = (float) Math.abs(cha);
            }
            datas.add(minutesData);
        }

    }

    public void parseKLine(WpKlineBean bean) {
        ArrayList<KLineBean> kLineBeans = new ArrayList<>();

        int count = bean.getTime().size();
        for (int i = 0; i < count; i++) {
            KLineBean kLineData = new KLineBean();
            kLineData.date = bean.getTime().get(i);
            kLineData.open = Float.parseFloat(bean.getData().get(i).get(0));
            kLineData.close = Float.parseFloat(bean.getData().get(i).get(1));
            kLineData.high = Float.parseFloat(bean.getData().get(i).get(3));
            kLineData.low = Float.parseFloat(bean.getData().get(i).get(2));
            kLineBeans.add(kLineData);
            xValuesLabel.put(i, kLineData.date);

        }
        kDatas.addAll(kLineBeans);
    }

    public float getMin() {
        return baseValue - permaxmin;
    }

    public float getMax() {
        return baseValue + permaxmin;
    }

    public float getPercentMax() {
        return permaxmin / baseValue;
    }

    public float getPercentMin() {
        return -getPercentMax();
    }


    public ArrayList<MinutesBean> getDatas() {
        return datas;
    }

    public ArrayList<KLineBean> getKLineDatas() {
        return kDatas;
    }
}
