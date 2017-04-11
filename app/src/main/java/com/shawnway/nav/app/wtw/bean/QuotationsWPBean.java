package com.shawnway.nav.app.wtw.bean;

import java.io.Serializable;


public class QuotationsWPBean implements Serializable{
    public boolean isSelect=false;
    public double latestPrice;//产品最新报价
    public double priceAtBeginning;//产品今日开盘价
    public double priceAtEndLastday;//产品昨日收盘价
    public double highestPrice;//产品今日最高价
    public double lowwestPrice;//产品今日最低价
    public String productContract;//产品合约代码,此值和productNo 一模一样
    public long dealStartTime;
    public int position;

    public long getDealStartTime() {
        return dealStartTime;
    }

    public void setDealStartTime(long dealStartTime) {
        this.dealStartTime = dealStartTime;
    }

    public double getLatestPrice() {
        return latestPrice;
    }

    public void setLatestPrice(double latestPrice) {
        this.latestPrice = latestPrice;
    }

    public double getPriceAtBeginning() {
        return priceAtBeginning;
    }

    public void setPriceAtBeginning(double priceAtBeginning) {
        this.priceAtBeginning = priceAtBeginning;
    }

    public double getPriceAtEndLastday() {
        return priceAtEndLastday;
    }

    public void setPriceAtEndLastday(double priceAtEndLastday) {
        this.priceAtEndLastday = priceAtEndLastday;
    }

    public double getHighestPrice() {
        return highestPrice;
    }

    public void setHighestPrice(double highestPrice) {
        this.highestPrice = highestPrice;
    }

    public double getLowwestPrice() {
        return lowwestPrice;
    }

    public void setLowwestPrice(double lowwestPrice) {
        this.lowwestPrice = lowwestPrice;
    }

    public String getProductContract() {
        return productContract;
    }

    public void setProductContract(String productContract) {
        this.productContract = productContract;
    }

}
