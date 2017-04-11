package com.shawnway.nav.app.wtw.module.quotation.international;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2016/10/10.
 */

public class InternationalPriceBean implements Parcelable{

    /**
     * dataId : 0
     * securityId : 107
     * securitySymbol : HSIX6
     * brokerId : 0
     * openPrice : 23611.0
     * bidQty : 6
     * bidPrice : 23576.0
     * askPrice : 23580.0
     * askQty : 2
     * lastPrice : 23576.0
     * lastQty : 8
     * lastTime : 1477390521645
     * closePrice : 23605.0
     * highPrice : 23611.0
     * lowPrice : 23578.0
     * turnoverVol : 946
     * turnoverAmount : 0.0
     * priceTime : 1477390521645
     */

    private int dataId;
    private int securityId;
    private String securitySymbol;
    private int brokerId;
    private double openPrice;
    private int bidQty;
    private double bidPrice;
    private double askPrice;
    private int askQty;
    private double lastPrice;
    private int lastQty;
    private long lastTime;
    private double closePrice;
    private double highPrice;
    private double lowPrice;
    private int turnoverVol;
    private double turnoverAmount;
    private long priceTime;

    public int getDataId() {
        return dataId;
    }

    public void setDataId(int dataId) {
        this.dataId = dataId;
    }

    public int getSecurityId() {
        return securityId;
    }

    public void setSecurityId(int securityId) {
        this.securityId = securityId;
    }

    public String getSecuritySymbol() {
        return securitySymbol;
    }

    public void setSecuritySymbol(String securitySymbol) {
        this.securitySymbol = securitySymbol;
    }

    public int getBrokerId() {
        return brokerId;
    }

    public void setBrokerId(int brokerId) {
        this.brokerId = brokerId;
    }

    public double getOpenPrice() {
        return openPrice;
    }

    public void setOpenPrice(double openPrice) {
        this.openPrice = openPrice;
    }

    public int getBidQty() {
        return bidQty;
    }

    public void setBidQty(int bidQty) {
        this.bidQty = bidQty;
    }

    public double getBidPrice() {
        return bidPrice;
    }

    public void setBidPrice(double bidPrice) {
        this.bidPrice = bidPrice;
    }

    public double getAskPrice() {
        return askPrice;
    }

    public void setAskPrice(double askPrice) {
        this.askPrice = askPrice;
    }

    public int getAskQty() {
        return askQty;
    }

    public void setAskQty(int askQty) {
        this.askQty = askQty;
    }

    public double getLastPrice() {
        return lastPrice;
    }

    public void setLastPrice(double lastPrice) {
        this.lastPrice = lastPrice;
    }

    public int getLastQty() {
        return lastQty;
    }

    public void setLastQty(int lastQty) {
        this.lastQty = lastQty;
    }

    public long getLastTime() {
        return lastTime;
    }

    public void setLastTime(long lastTime) {
        this.lastTime = lastTime;
    }

    public double getClosePrice() {
        return closePrice;
    }

    public void setClosePrice(double closePrice) {
        this.closePrice = closePrice;
    }

    public double getHighPrice() {
        return highPrice;
    }

    public void setHighPrice(double highPrice) {
        this.highPrice = highPrice;
    }

    public double getLowPrice() {
        return lowPrice;
    }

    public void setLowPrice(double lowPrice) {
        this.lowPrice = lowPrice;
    }

    public int getTurnoverVol() {
        return turnoverVol;
    }

    public void setTurnoverVol(int turnoverVol) {
        this.turnoverVol = turnoverVol;
    }

    public double getTurnoverAmount() {
        return turnoverAmount;
    }

    public void setTurnoverAmount(double turnoverAmount) {
        this.turnoverAmount = turnoverAmount;
    }

    public long getPriceTime() {
        return priceTime;
    }

    public void setPriceTime(long priceTime) {
        this.priceTime = priceTime;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.dataId);
        dest.writeInt(this.securityId);
        dest.writeString(this.securitySymbol);
        dest.writeInt(this.brokerId);
        dest.writeDouble(this.openPrice);
        dest.writeInt(this.bidQty);
        dest.writeDouble(this.bidPrice);
        dest.writeDouble(this.askPrice);
        dest.writeInt(this.askQty);
        dest.writeDouble(this.lastPrice);
        dest.writeInt(this.lastQty);
        dest.writeLong(this.lastTime);
        dest.writeDouble(this.closePrice);
        dest.writeDouble(this.highPrice);
        dest.writeDouble(this.lowPrice);
        dest.writeInt(this.turnoverVol);
        dest.writeDouble(this.turnoverAmount);
        dest.writeLong(this.priceTime);
    }

    public InternationalPriceBean() {
    }

    protected InternationalPriceBean(Parcel in) {
        this.dataId = in.readInt();
        this.securityId = in.readInt();
        this.securitySymbol = in.readString();
        this.brokerId = in.readInt();
        this.openPrice = in.readDouble();
        this.bidQty = in.readInt();
        this.bidPrice = in.readDouble();
        this.askPrice = in.readDouble();
        this.askQty = in.readInt();
        this.lastPrice = in.readDouble();
        this.lastQty = in.readInt();
        this.lastTime = in.readLong();
        this.closePrice = in.readDouble();
        this.highPrice = in.readDouble();
        this.lowPrice = in.readDouble();
        this.turnoverVol = in.readInt();
        this.turnoverAmount = in.readDouble();
        this.priceTime = in.readLong();
    }

    public static final Creator<InternationalPriceBean> CREATOR = new Creator<InternationalPriceBean>() {
        @Override
        public InternationalPriceBean createFromParcel(Parcel source) {
            return new InternationalPriceBean(source);
        }

        @Override
        public InternationalPriceBean[] newArray(int size) {
            return new InternationalPriceBean[size];
        }
    };
}
