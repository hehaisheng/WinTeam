package com.shawnway.nav.app.wtw.module.quotation.jl_wp.jl_wp_detail.jl_wp_trade_history;

/**
 * Created by Cicinnus on 2016/12/12.
 */

public class JlWpTradeTotalBean {

    /**
     * state : 200
     * desc : OK
     * data : {"amount":1,"count":1,"totalprofitLoss":4.65}
     */

    private String state;
    private String desc;
    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * amount : 1
         * count : 1
         * totalprofitLoss : 4.65
         */

        private int amount;
        private int count;
        private double totalprofitLoss;

        public int getAmount() {
            return amount;
        }

        public void setAmount(int amount) {
            this.amount = amount;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public double getTotalprofitLoss() {
            return totalprofitLoss;
        }

        public void setTotalprofitLoss(double totalprofitLoss) {
            this.totalprofitLoss = totalprofitLoss;
        }
    }
}
