package com.shawnway.nav.app.wtw.module.quotation.international.billingRecord;

import java.util.List;

/**
 * Created by Administrator on 2016/10/12.
 */

public class BillRecordBean {


    private List<WareHouseInfosBean> wareHouseInfos;

    public List<WareHouseInfosBean> getWareHouseInfos() {
        return wareHouseInfos;
    }

    public void setWareHouseInfos(List<WareHouseInfosBean> wareHouseInfos) {
        this.wareHouseInfos = wareHouseInfos;
    }

    public static class WareHouseInfosBean {
        /**
         * prodId : 738
         * prodCode : HGZ6
         * desc : 铜2016-12
         * orderQuantity : 1
         * orderPrice : 0
         * orderSide : 1
         * orderId : NYMEXHGZ6c89900248c40430e9d55b0a0c95676b6
         * executionPrice : 266
         * executionQuantity : 1
         * closePositionexecutionPrice : 266.3
         * closePositionexecutionQuantity : 1
         * orderDate : 1480054129000
         * lastexecutionDate : 1480055513000
         * orderPl : 495
         * orderType : 5
         * orderStatus : 3
         * offset : 2
         * customerAutoStopLossPrice : null
         * customerAutoStopWinPrice : null
         * sytemAutoStopLossPrice : null
         * sytemAutoStopWinPrice : null
         */

        private int prodId;
        private String prodCode;
        private String desc;
        private double orderQuantity;
        private double orderPrice;
        private int orderSide;
        private String orderId;
        private double executionPrice;
        private int executionQuantity;
        private double closePositionexecutionPrice;
        private int closePositionexecutionQuantity;
        private long orderDate;
        private long lastexecutionDate;
        private double orderPl;
        private int orderType;
        private int orderStatus;
        private int offset;
        private Object customerAutoStopLossPrice;
        private Object customerAutoStopWinPrice;
        private Object sytemAutoStopLossPrice;
        private Object sytemAutoStopWinPrice;

        public int getProdId() {
            return prodId;
        }

        public void setProdId(int prodId) {
            this.prodId = prodId;
        }

        public String getProdCode() {
            return prodCode;
        }

        public void setProdCode(String prodCode) {
            this.prodCode = prodCode;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public double getOrderQuantity() {
            return orderQuantity;
        }

        public void setOrderQuantity(double orderQuantity) {
            this.orderQuantity = orderQuantity;
        }

        public double getOrderPrice() {
            return orderPrice;
        }

        public void setOrderPrice(int orderPrice) {
            this.orderPrice = orderPrice;
        }

        public int getOrderSide() {
            return orderSide;
        }

        public void setOrderSide(int orderSide) {
            this.orderSide = orderSide;
        }

        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }

        public double getExecutionPrice() {
            return executionPrice;
        }

        public void setExecutionPrice(double executionPrice) {
            this.executionPrice = executionPrice;
        }

        public int getExecutionQuantity() {
            return executionQuantity;
        }

        public void setExecutionQuantity(int executionQuantity) {
            this.executionQuantity = executionQuantity;
        }

        public double getClosePositionexecutionPrice() {
            return closePositionexecutionPrice;
        }

        public void setClosePositionexecutionPrice(double closePositionexecutionPrice) {
            this.closePositionexecutionPrice = closePositionexecutionPrice;
        }

        public int getClosePositionexecutionQuantity() {
            return closePositionexecutionQuantity;
        }

        public void setClosePositionexecutionQuantity(int closePositionexecutionQuantity) {
            this.closePositionexecutionQuantity = closePositionexecutionQuantity;
        }

        public long getOrderDate() {
            return orderDate;
        }

        public void setOrderDate(long orderDate) {
            this.orderDate = orderDate;
        }

        public long getLastexecutionDate() {
            return lastexecutionDate;
        }

        public void setLastexecutionDate(long lastexecutionDate) {
            this.lastexecutionDate = lastexecutionDate;
        }

        public double getOrderPl() {
            return orderPl;
        }

        public void setOrderPl(double orderPl) {
            this.orderPl = orderPl;
        }

        public int getOrderType() {
            return orderType;
        }

        public void setOrderType(int orderType) {
            this.orderType = orderType;
        }

        public int getOrderStatus() {
            return orderStatus;
        }

        public void setOrderStatus(int orderStatus) {
            this.orderStatus = orderStatus;
        }

        public int getOffset() {
            return offset;
        }

        public void setOffset(int offset) {
            this.offset = offset;
        }

        public Object getCustomerAutoStopLossPrice() {
            return customerAutoStopLossPrice;
        }

        public void setCustomerAutoStopLossPrice(Object customerAutoStopLossPrice) {
            this.customerAutoStopLossPrice = customerAutoStopLossPrice;
        }

        public Object getCustomerAutoStopWinPrice() {
            return customerAutoStopWinPrice;
        }

        public void setCustomerAutoStopWinPrice(Object customerAutoStopWinPrice) {
            this.customerAutoStopWinPrice = customerAutoStopWinPrice;
        }

        public Object getSytemAutoStopLossPrice() {
            return sytemAutoStopLossPrice;
        }

        public void setSytemAutoStopLossPrice(Object sytemAutoStopLossPrice) {
            this.sytemAutoStopLossPrice = sytemAutoStopLossPrice;
        }

        public Object getSytemAutoStopWinPrice() {
            return sytemAutoStopWinPrice;
        }

        public void setSytemAutoStopWinPrice(Object sytemAutoStopWinPrice) {
            this.sytemAutoStopWinPrice = sytemAutoStopWinPrice;
        }
    }
}
