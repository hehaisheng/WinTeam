package com.shawnway.nav.app.wtw.module.quotation.wp.wp_detail.tran.bean;

/**
 * Created by Kevin on 2016/12/12
 * 用户绑定银行卡信息
 */

public class UserBalanceBean {

    /**
     * state : 200
     * desc : OK
     * data : {"userId":406,"rechargeBankName":"招商银行","rechargeBankNo":"6226090000000048","rechargeBindTime":1466097030000,"withdrawBankName":"招商银行","withdrawBankCardNo":"6226090000000048","province":null,"city":null,"withdrawBranchBank":null,"withdrawCardUsername":null,"withdrawBindTime":null}
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
         * userId : 406
         * rechargeBankName : 招商银行
         * rechargeBankNo : 6226090000000048
         * rechargeBindTime : 1466097030000
         * withdrawBankName : 招商银行
         * withdrawBankCardNo : 6226090000000048
         * province : null
         * city : null
         * withdrawBranchBank : null
         * withdrawCardUsername : null
         * withdrawBindTime : null
         */

        private int userId;
        private String rechargeBankName;
        private String rechargeBankNo;
        private long rechargeBindTime;
        private String withdrawBankName;
        private String withdrawBankCardNo;
        private Object province;
        private Object city;
        private Object withdrawBranchBank;
        private Object withdrawCardUsername;
        private Object withdrawBindTime;

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public String getRechargeBankName() {
            return rechargeBankName;
        }

        public void setRechargeBankName(String rechargeBankName) {
            this.rechargeBankName = rechargeBankName;
        }

        public String getRechargeBankNo() {
            return rechargeBankNo;
        }

        public void setRechargeBankNo(String rechargeBankNo) {
            this.rechargeBankNo = rechargeBankNo;
        }

        public long getRechargeBindTime() {
            return rechargeBindTime;
        }

        public void setRechargeBindTime(long rechargeBindTime) {
            this.rechargeBindTime = rechargeBindTime;
        }

        public String getWithdrawBankName() {
            return withdrawBankName;
        }

        public void setWithdrawBankName(String withdrawBankName) {
            this.withdrawBankName = withdrawBankName;
        }

        public String getWithdrawBankCardNo() {
            return withdrawBankCardNo;
        }

        public void setWithdrawBankCardNo(String withdrawBankCardNo) {
            this.withdrawBankCardNo = withdrawBankCardNo;
        }

        public Object getProvince() {
            return province;
        }

        public void setProvince(Object province) {
            this.province = province;
        }

        public Object getCity() {
            return city;
        }

        public void setCity(Object city) {
            this.city = city;
        }

        public Object getWithdrawBranchBank() {
            return withdrawBranchBank;
        }

        public void setWithdrawBranchBank(Object withdrawBranchBank) {
            this.withdrawBranchBank = withdrawBranchBank;
        }

        public Object getWithdrawCardUsername() {
            return withdrawCardUsername;
        }

        public void setWithdrawCardUsername(Object withdrawCardUsername) {
            this.withdrawCardUsername = withdrawCardUsername;
        }

        public Object getWithdrawBindTime() {
            return withdrawBindTime;
        }

        public void setWithdrawBindTime(Object withdrawBindTime) {
            this.withdrawBindTime = withdrawBindTime;
        }
    }
}
