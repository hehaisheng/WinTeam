package com.shawnway.nav.app.wtw.module.quotation.international.international_detail;

import java.util.List;

/**
 * Created by R0ger on 2016/10/31.
 * R0ger github = "https://github.com/cicinnus0407"
 */

public class UserAccountList {

    /**
     * tradingAccountId : 10209
     * accountCurrency : CNY
     * accountStatus : 0
     * customerId : 100
     * floatingUsableAmount : 0
     * marginAmount : 0
     * tradingAccountNonusableAccount : 0
     * tradingAccountUsableAmount : 0
     * tradingBrokerId : 1
     * isRealTrading : 1
     */

    private List<ListBean> list;

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        private int tradingAccountId;
        private String accountCurrency;
        private int accountStatus;
        private int customerId;
        private int floatingUsableAmount;
        private int marginAmount;
        private Number tradingAccountNonusableAccount;
        private Number tradingAccountUsableAmount;
        private int tradingBrokerId;
        private int isRealTrading;

        public int getTradingAccountId() {
            return tradingAccountId;
        }

        public void setTradingAccountId(int tradingAccountId) {
            this.tradingAccountId = tradingAccountId;
        }

        public String getAccountCurrency() {
            return accountCurrency;
        }

        public void setAccountCurrency(String accountCurrency) {
            this.accountCurrency = accountCurrency;
        }

        public int getAccountStatus() {
            return accountStatus;
        }

        public void setAccountStatus(int accountStatus) {
            this.accountStatus = accountStatus;
        }

        public int getCustomerId() {
            return customerId;
        }

        public void setCustomerId(int customerId) {
            this.customerId = customerId;
        }

        public int getFloatingUsableAmount() {
            return floatingUsableAmount;
        }

        public void setFloatingUsableAmount(int floatingUsableAmount) {
            this.floatingUsableAmount = floatingUsableAmount;
        }

        public int getMarginAmount() {
            return marginAmount;
        }

        public void setMarginAmount(int marginAmount) {
            this.marginAmount = marginAmount;
        }

        public Number getTradingAccountNonusableAccount() {
            return tradingAccountNonusableAccount;
        }

        public void setTradingAccountNonusableAccount(Number tradingAccountNonusableAccount) {
            this.tradingAccountNonusableAccount = tradingAccountNonusableAccount;
        }

        public Number getTradingAccountUsableAmount() {
            return tradingAccountUsableAmount;
        }

        public void setTradingAccountUsableAmount(Number tradingAccountUsableAmount) {
            this.tradingAccountUsableAmount = tradingAccountUsableAmount;
        }

        public int getTradingBrokerId() {
            return tradingBrokerId;
        }

        public void setTradingBrokerId(int tradingBrokerId) {
            this.tradingBrokerId = tradingBrokerId;
        }

        public int getIsRealTrading() {
            return isRealTrading;
        }

        public void setIsRealTrading(int isRealTrading) {
            this.isRealTrading = isRealTrading;
        }
    }
}
