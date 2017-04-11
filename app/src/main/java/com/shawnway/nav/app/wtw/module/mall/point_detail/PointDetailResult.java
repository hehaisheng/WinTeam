package com.shawnway.nav.app.wtw.module.mall.point_detail;

import java.util.List;

/**
 * Created by Administrator on 2016/10/28.
 * Administrator github = "https://github.com/Cicinnus0407"
 */

public class PointDetailResult {

    /**
     * transactionId : 89
     * accountId : 13
     * agentId : 1
     * currentPointsBalance : 1064816
     * transactionAmount : 100
     * transactionDate : 1477622965000
     * transactionDesc : 签到
     * transactionType : 6
     */

    private List<ListBean> list;

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        private int transactionId;
        private int accountId;
        private int agentId;
        private int currentPointsBalance;
        private int transactionAmount;
        private long transactionDate;
        private String transactionDesc;
        private int transactionType;

        public int getTransactionId() {
            return transactionId;
        }

        public void setTransactionId(int transactionId) {
            this.transactionId = transactionId;
        }

        public int getAccountId() {
            return accountId;
        }

        public void setAccountId(int accountId) {
            this.accountId = accountId;
        }

        public int getAgentId() {
            return agentId;
        }

        public void setAgentId(int agentId) {
            this.agentId = agentId;
        }

        public int getCurrentPointsBalance() {
            return currentPointsBalance;
        }

        public void setCurrentPointsBalance(int currentPointsBalance) {
            this.currentPointsBalance = currentPointsBalance;
        }

        public int getTransactionAmount() {
            return transactionAmount;
        }

        public void setTransactionAmount(int transactionAmount) {
            this.transactionAmount = transactionAmount;
        }

        public long getTransactionDate() {
            return transactionDate;
        }

        public void setTransactionDate(long transactionDate) {
            this.transactionDate = transactionDate;
        }

        public String getTransactionDesc() {
            return transactionDesc;
        }

        public void setTransactionDesc(String transactionDesc) {
            this.transactionDesc = transactionDesc;
        }

        public int getTransactionType() {
            return transactionType;
        }

        public void setTransactionType(int transactionType) {
            this.transactionType = transactionType;
        }
    }
}
