package com.shawnway.nav.app.wtw.module.quotation.wp.wp_detail.tran.bean;

import java.util.ArrayList;

/**
 * Created by Kevin on 2016/12/9
 * 产品bean
 */
public class TranGoodsWrapper {
    public String state;
    public String desc;

    public ArrayList<TranGoodsBean> data;

    public ArrayList<TranGoodsBean> getData() {
        return data;
    }

    public void setData(ArrayList<TranGoodsBean> data) {
        this.data = data;
    }

    public static class TranGoodsBean {

        public int id;//产品规格的ID
        public String productNo;//产品的合约代码
        public String productName;//产品名称
        public double amount;//产品规格
        public String unit;//产品规格单位
        public double unitPrice;//产品规格价格
        public double tradeFee;//产品规格购买时的手续费比例
        public float floatProfit;//产品规格浮动盈亏比
        public double floatUnit;//APP 端可以忽略此字段
        public int maxBuyAmout;//最大仓位
        public String specifications;//规格描述
        public boolean duringTradingTime;//当前是否可以交易。APP 客户端需要通过此字段提示用户此产品当前是否在交易时间内。 False，代表非交易时间；true， 代表交易时间
        private boolean isChecked;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getProductNo() {
            return productNo;
        }

        public void setProductNo(String productNo) {
            this.productNo = productNo;
        }

        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public double getAmount() {
            return amount;
        }

        public void setAmount(double amount) {
            this.amount = amount;
        }

        public String getUnit() {
            return unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }

        public double getUnitPrice() {
            return unitPrice;
        }

        public void setUnitPrice(double unitPrice) {
            this.unitPrice = unitPrice;
        }

        public double getTradeFee() {
            return tradeFee;
        }

        public void setTradeFee(double tradeFee) {
            this.tradeFee = tradeFee;
        }

        public float getFloatProfit() {
            return floatProfit;
        }

        public void setFloatProfit(float floatProfit) {
            this.floatProfit = floatProfit;
        }

        public double getFloatUnit() {
            return floatUnit;
        }

        public void setFloatUnit(double floatUnit) {
            this.floatUnit = floatUnit;
        }

        public int getMaxBuyAmout() {
            return maxBuyAmout;
        }

        public void setMaxBuyAmout(int maxBuyAmout) {
            this.maxBuyAmout = maxBuyAmout;
        }

        public String getSpecifications() {
            return specifications;
        }

        public void setSpecifications(String specifications) {
            this.specifications = specifications;
        }

        public boolean isDuringTradingTime() {
            return duringTradingTime;
        }

        public void setDuringTradingTime(boolean duringTradingTime) {
            this.duringTradingTime = duringTradingTime;
        }

        public boolean isChecked() {
            return isChecked;
        }

        public void setChecked(boolean checked) {
            isChecked = checked;
        }
    }
}
