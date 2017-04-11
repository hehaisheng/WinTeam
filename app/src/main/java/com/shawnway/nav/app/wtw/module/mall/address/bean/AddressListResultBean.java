package com.shawnway.nav.app.wtw.module.mall.address.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by Cicinnus on 2016/11/16.
 */

public class AddressListResultBean {

    private List<OrderAddressBean> orderAddress;

    public List<OrderAddressBean> getOrderAddress() {
        return orderAddress;
    }

    public void setOrderAddress(List<OrderAddressBean> orderAddress) {
        this.orderAddress = orderAddress;
    }

    public static class OrderAddressBean implements Parcelable{
        /**
         * id : 52
         * address : 广东省广州市海珠区哈哈哈
         * cellphone : 15521214395
         * consignee : 钟嵘光
         * defaultAddress : 0
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

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.id);
            dest.writeString(this.address);
            dest.writeString(this.cellphone);
            dest.writeString(this.consignee);
            dest.writeInt(this.defaultAddress);
            dest.writeInt(this.loginId);
        }

        public OrderAddressBean() {
        }

        protected OrderAddressBean(Parcel in) {
            this.id = in.readInt();
            this.address = in.readString();
            this.cellphone = in.readString();
            this.consignee = in.readString();
            this.defaultAddress = in.readInt();
            this.loginId = in.readInt();
        }

        public static final Creator<OrderAddressBean> CREATOR = new Creator<OrderAddressBean>() {
            @Override
            public OrderAddressBean createFromParcel(Parcel source) {
                return new OrderAddressBean(source);
            }

            @Override
            public OrderAddressBean[] newArray(int size) {
                return new OrderAddressBean[size];
            }
        };
    }
}
