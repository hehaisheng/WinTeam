package com.shawnway.nav.app.wtw.module.mall.order;

/**
 * Created by Cicinnus on 2016/11/18.
 */

public class ExChangeProductResultBean {

    /**
     * statusCode : SUCCESS
     * desc : 订单兑换请求已提交，正在等待发货
     * orderId : 5D3D30C707D51432
     */

    private String statusCode;
    private String desc;
    private String orderId;

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}
