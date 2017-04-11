package com.shawnway.nav.app.wtw.module.quotation.jl_wp.jl_wp_detail.tran.transfer;

/**
 * Created by Administrator on 2017/1/16.
 */

public class JlWithDrawBean {

    /**
     * state : 200
     * desc : OK
     * data : {"userId":763,"bankName":null,"bankNo":"6228480088320716371","branchBank":null,"bindTime":1484211821000,"province":null,"city":null,"cardUserName":null,"mid":92,"orgId":99}
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
         * userId : 763
         * bankName : null
         * bankNo : 6228480088320716371
         * branchBank : null
         * bindTime : 1484211821000
         * province : null
         * city : null
         * cardUserName : null
         * mid : 92
         * orgId : 99
         */

        private int userId;
        private Object bankName;
        private String bankNo;
        private Object branchBank;
        private long bindTime;
        private Object province;
        private Object city;
        private Object cardUserName;
        private int mid;
        private int orgId;

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public Object getBankName() {
            return bankName;
        }

        public void setBankName(Object bankName) {
            this.bankName = bankName;
        }

        public String getBankNo() {
            return bankNo;
        }

        public void setBankNo(String bankNo) {
            this.bankNo = bankNo;
        }

        public Object getBranchBank() {
            return branchBank;
        }

        public void setBranchBank(Object branchBank) {
            this.branchBank = branchBank;
        }

        public long getBindTime() {
            return bindTime;
        }

        public void setBindTime(long bindTime) {
            this.bindTime = bindTime;
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

        public Object getCardUserName() {
            return cardUserName;
        }

        public void setCardUserName(Object cardUserName) {
            this.cardUserName = cardUserName;
        }

        public int getMid() {
            return mid;
        }

        public void setMid(int mid) {
            this.mid = mid;
        }

        public int getOrgId() {
            return orgId;
        }

        public void setOrgId(int orgId) {
            this.orgId = orgId;
        }
    }
}
