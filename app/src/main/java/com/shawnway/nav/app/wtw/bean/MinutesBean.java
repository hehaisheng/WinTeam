package com.shawnway.nav.app.wtw.bean;


/**
 * 分时图的bean
 */
public class MinutesBean {
    public String time;//横坐标的时间点
    public float cjprice;//左边显示的价格
    public float cjnum;//成交量
    public float avprice = Float.NaN;//所有cjprice的平均值
    public float per;//右边的百分比
    public float cha;//差值
    public float total;//总的差值
    public int color = 0xff000000;
}
