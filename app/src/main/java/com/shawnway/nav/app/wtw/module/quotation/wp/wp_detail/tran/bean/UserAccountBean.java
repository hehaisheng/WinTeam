package com.shawnway.nav.app.wtw.module.quotation.wp.wp_detail.tran.bean;

/**
 * Created by Kevin on 2016/12/9
 * 用户资金信息bean
 */

public class UserAccountBean {
    public String state;
    public String desc;
    public AccBanBean data;
    public String error;

    public static class AccBanBean {
        public double frozenBalance;//冻结资金
        public double useableBalance;//用户可用余额
        public double totalBalance;//用户余额

        public double getFrozenBalance() {
            return frozenBalance;
        }

        public void setFrozenBalance(double frozenBalance) {
            this.frozenBalance = frozenBalance;
        }

        public double getUseableBalance() {
            return useableBalance;
        }

        public void setUseableBalance(double useableBalance) {
            this.useableBalance = useableBalance;
        }

        public double getTotalBalance() {
            return totalBalance;
        }

        public void setTotalBalance(double totalBalance) {
            this.totalBalance = totalBalance;
        }
    }
}
