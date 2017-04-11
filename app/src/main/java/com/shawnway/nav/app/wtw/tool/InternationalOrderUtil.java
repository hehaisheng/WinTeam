package com.shawnway.nav.app.wtw.tool;

/**
 * Created by Cicinnus on 2016/11/4.
 */

public class InternationalOrderUtil {


    public static String getOrderStatus(int orderStatus) {
        String order = "";
        switch (orderStatus) {
            case 0:
                order = "工作中";
                break;
            case 1:
                order = "等待交易工作中";
                break;
            case 2:
                order = "部分成交且工作中";
                break;
            case 3:
                order = "已成交";
                break;
            case 4:
                order = "已被拒绝";
                break;
            case 5:
                order = "已被取消";
                break;
            case 6:
                order = "更改中";
                break;
            case 7:
                order = "部分下单取消";
                break;
            case 8:
                order = "桶错误拒绝下单";
                break;
            case 9:
                order = "系统错误取消订单失败";
                break;
            case 10:
                order = "扣除手续费按金失败";
                break;
        }
        return order;
    }

    public static String getOrderType(int type){
        String orderType = "";
        switch (type) {
            case 1:
                orderType = "普通下单";
                break;
            case 2:
                orderType = "普通下单取消";
                break;
            case 3:
                orderType = "普通跟单下单";
                break;
            case 4:
                orderType = "普通跟单取消";
                break;
            case 5:
                orderType = "客户强平仓下单";
                break;
            case 6:
                orderType = "普通跟单强平仓下单";
                break;
            case 7:
                orderType = "普通下单系统强平仓下单";
                break;
            case 8:
                orderType = "普通跟单系统强平仓下单";
                break;
        }
        return orderType;
    }
}
