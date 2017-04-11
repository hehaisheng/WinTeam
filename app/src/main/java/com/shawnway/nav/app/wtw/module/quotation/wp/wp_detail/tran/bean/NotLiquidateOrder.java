package com.shawnway.nav.app.wtw.module.quotation.wp.wp_detail.tran.bean;

import java.util.ArrayList;

/**
 * Created by Kevin on 2016/12/8
 * 实时盈亏和当前仓位bean
 */

public class NotLiquidateOrder {

    private String state;
    private String desc;
    private NotLiquidateBean data;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public NotLiquidateBean getData() {
        return data;
    }

    public void setData(NotLiquidateBean data) {
        this.data = data;
    }


    public static class NotLiquidateBean {

        /**
         * totalProfitLoss : 0.0  用户当前的实时盈亏，保留两位小数
         * buyamount :此字段值是一个数组，包含了当前系统所有支持的产品和相应的 产品规格。 每一个元素代表一个产品规格，此产品包含了此用 户当前已经购买了的手数，以及最大能够购买的手数。当已经购 买的手数等于最大购买手数，此用户就不能继续购买此产品了。
         */

        private double totalProfitLoss;
        private ArrayList<BuyamountBean> buyamount;

        public ArrayList<BuyamountBean> getBuyamount() {
            return buyamount;
        }

        public void setBuyamount(ArrayList<BuyamountBean> buyamount) {
            this.buyamount = buyamount;
        }

        public double getTotalProfitLoss() {
            return totalProfitLoss;
        }

        public void setTotalProfitLoss(double totalProfitLoss) {
            this.totalProfitLoss = totalProfitLoss;
        }

        public static class BuyamountBean {

            /**
             * id : 产品ID
             * productNo : 产品的合约代码
             * type : 购买的方向，即买涨还是买跌。1，代表涨;2 代表跌
             * buyAmount : 代表此产品规格已经购买了的手数
             * maxBuyAmout : 代表此产品规格最多能购买的手数
             */

            private int id;
            private String productNo;
            private int type;
            private int buyAmount;
            private int maxBuyAmout;

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

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public int getBuyAmount() {
                return buyAmount;
            }

            public void setBuyAmount(int buyAmount) {
                this.buyAmount = buyAmount;
            }

            public int getMaxBuyAmout() {
                return maxBuyAmout;
            }

            public void setMaxBuyAmout(int maxBuyAmout) {
                this.maxBuyAmout = maxBuyAmout;
            }
        }
    }
}
