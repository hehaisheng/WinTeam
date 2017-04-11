package com.shawnway.nav.app.wtw.module.mall.address.bean;

/**
 * Created by Cicinnus on 2016/11/18.
 */

public class DefaultAddressResultBean {


    /**
     * orderAddress : {"id":57,"address":"广西壮族自治区北海市银海区-嗯呀","cellphone":"98765432100","consignee":"测试2","defaultAddress":1,"loginId":155}
     */

    private OrderAddressBean orderAddress;

    public OrderAddressBean getOrderAddress() {
        return orderAddress;
    }

    public void setOrderAddress(OrderAddressBean orderAddress) {
        this.orderAddress = orderAddress;
    }

    public static class OrderAddressBean {
        /**
         * id : 57
         * address : 广西壮族自治区北海市银海区-嗯呀
         * cellphone : 98765432100
         * consignee : 测试2
         * defaultAddress : 1
         * loginId : 155
         */

        private int id;
        private String address;
        private String cellphone;
        private String consignee;
        private int defaultAddress;
        private int loginId;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getCellphone() {
            return cellphone;
        }

        public void setCellphone(String cellphone) {
            this.cellphone = cellphone;
        }

        public String getConsignee() {
            return consignee;
        }

        public void setConsignee(String consignee) {
            this.consignee = consignee;
        }

        public int getDefaultAddress() {
            return defaultAddress;
        }

        public void setDefaultAddress(int defaultAddress) {
            this.defaultAddress = defaultAddress;
        }

        public int getLoginId() {
            return loginId;
        }

        public void setLoginId(int loginId) {
            this.loginId = loginId;
        }
    }
}
