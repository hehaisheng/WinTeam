package com.shawnway.nav.app.wtw.module.quotation.international.positions;

import java.util.List;

/**
 * Created by Cicinnus on 2016/11/21.
 */

public class PointValueResultBean {


    private List<ListBean> list;

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * id : 9
         * securityGroupCode : GC
         * securityCode : *
         * value : 10
         * currencyCode : USD
         * tickSize : 0.1
         * securityDecimalInPlace : 1
         * exchangeRate : 0.85
         */

        private int id;
        private String securityGroupCode;
        private String securityCode;
        private double value;
        private String currencyCode;
        private double tickSize;
        private String securityDecimalInPlace;
        private double exchangeRate;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getSecurityGroupCode() {
            return securityGroupCode;
        }

        public void setSecurityGroupCode(String securityGroupCode) {
            this.securityGroupCode = securityGroupCode;
        }

        public String getSecurityCode() {
            return securityCode;
        }

        public void setSecurityCode(String securityCode) {
            this.securityCode = securityCode;
        }

        public double getValue() {
            return value;
        }

        public void setValue(double value) {
            this.value = value;
        }

        public String getCurrencyCode() {
            return currencyCode;
        }

        public void setCurrencyCode(String currencyCode) {
            this.currencyCode = currencyCode;
        }

        public double getTickSize() {
            return tickSize;
        }

        public void setTickSize(double tickSize) {
            this.tickSize = tickSize;
        }

        public String getSecurityDecimalInPlace() {
            return securityDecimalInPlace;
        }

        public void setSecurityDecimalInPlace(String securityDecimalInPlace) {
            this.securityDecimalInPlace = securityDecimalInPlace;
        }

        public double getExchangeRate() {
            return exchangeRate;
        }

        public void setExchangeRate(double exchangeRate) {
            this.exchangeRate = exchangeRate;
        }
    }
}
