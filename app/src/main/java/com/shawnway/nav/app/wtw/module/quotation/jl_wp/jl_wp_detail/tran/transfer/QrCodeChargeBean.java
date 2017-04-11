package com.shawnway.nav.app.wtw.module.quotation.jl_wp.jl_wp_detail.tran.transfer;

/**
 * Created by Cicinnus on 2017/1/24.
 */

public class QrCodeChargeBean {

    /**
     * state : 200
     * desc : OK
     * data : {"tradeNo":"b161230101747277878","payUrl":"https:uuid=weixin:OqY2"}
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
         * tradeNo : b161230101747277878
         * payUrl : https:uuid=weixin:OqY2
         */

        private String tradeNo;
        private String payUrl;

        public String getTradeNo() {
            return tradeNo;
        }

        public void setTradeNo(String tradeNo) {
            this.tradeNo = tradeNo;
        }

        public String getPayUrl() {
            return payUrl;
        }

        public void setPayUrl(String payUrl) {
            this.payUrl = payUrl;
        }
    }
}
