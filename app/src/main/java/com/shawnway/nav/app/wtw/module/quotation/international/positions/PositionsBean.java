package com.shawnway.nav.app.wtw.module.quotation.international.positions;

import java.util.List;

/**
 * Created by Administrator on 2016/10/10.
 */

public class PositionsBean {


    private List<WareHouseInfosBean> wareHouseInfos;

    public List<WareHouseInfosBean> getWareHouseInfos() {
        return wareHouseInfos;
    }

    public void setWareHouseInfos(List<WareHouseInfosBean> wareHouseInfos) {
        this.wareHouseInfos = wareHouseInfos;
    }

    public static class WareHouseInfosBean {
        /**
         * prodId : 736
         * prodCode : SIZ6
         * desc : 白银2016-12
         * orderQuantity : 1
         * orderPrice : 0
         * orderSide : 1
         * orderId : NYMEXSIZ63265bf8e07d34409ba6b73ae8fef7329
         * executionPrice : 16.415
         * executionQuantity : 1
         * closePositionexecutionPrice : null
         * closePositionexecutionQuantity : 0
         * orderDate : 1480059726000
         * lastexecutionDate : 1480059726000
         * orderPl : null
         * orderType : 1
         * orderStatus : 3
         * offset : 0
         * customerAutoStopLossPrice : 0
         * customerAutoStopWinPrice : 0
         * sytemAutoStopLossPrice : 15.16
         * sytemAutoStopWinPrice : 16.78
         */

        private int prodId;
        private String prodCode;
        private String securityGrpCode;
        private String desc;
        private int orderQuantity;
        private double orderPrice;
        private int orderSide;
        private String orderId;
        private double executionPrice;
        private int executionQuantity;
        private Object closePositionexecutionPrice;
        private int closePositionexecutionQuantity;
        private long orderDate;
        private long lastexecutionDate;
        private Object orderPl;
        private int orderType;
        private int orderStatus;
        private int offset;
        private double customerAutoStopLossPrice;
        private double customerAutoStopWinPrice;
        private double sytemAutoStopLossPrice;
        private double sytemAutoStopWinPrice;
        private double marketPrice;

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

        public int getOrderQuantity() {
            return orderQuantity;
        }

        public void setOrderQuantity(int orderQuantity) {
            this.orderQuantity = orderQuantity;
        }

        public double getOrderPrice() {
            return orderPrice;
        }

        public void setOrderPrice(double orderPrice) {
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

        public Object getClosePositionexecutionPrice() {
            return closePositionexecutionPrice;
        }

        public void setClosePositionexecutionPrice(Object closePositionexecutionPrice) {
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

        public Object getOrderPl() {
            return orderPl;
        }

        public void setOrderPl(Object orderPl) {
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

        public double getCustomerAutoStopLossPrice() {
            return customerAutoStopLossPrice;
        }

        public void setCustomerAutoStopLossPrice(double customerAutoStopLossPrice) {
            this.customerAutoStopLossPrice = customerAutoStopLossPrice;
        }

        public double getCustomerAutoStopWinPrice() {
            return customerAutoStopWinPrice;
        }

        public void setCustomerAutoStopWinPrice(double customerAutoStopWinPrice) {
            this.customerAutoStopWinPrice = customerAutoStopWinPrice;
        }

        public double getSytemAutoStopLossPrice() {
            return sytemAutoStopLossPrice;
        }

        public void setSytemAutoStopLossPrice(double sytemAutoStopLossPrice) {
            this.sytemAutoStopLossPrice = sytemAutoStopLossPrice;
        }

        public double getSytemAutoStopWinPrice() {
            return sytemAutoStopWinPrice;
        }

        public void setSytemAutoStopWinPrice(double sytemAutoStopWinPrice) {
            this.sytemAutoStopWinPrice = sytemAutoStopWinPrice;
        }

        public double getMarketPrice() {
            return marketPrice;
        }

        public void setMarketPrice(double marketPrice) {
            this.marketPrice = marketPrice;
        }

        public String getSecurityGrpCode() {
            return securityGrpCode;
        }

        public void setSecurityGrpCode(String securityGrpCode) {
            this.securityGrpCode = securityGrpCode;
        }
    }
}
