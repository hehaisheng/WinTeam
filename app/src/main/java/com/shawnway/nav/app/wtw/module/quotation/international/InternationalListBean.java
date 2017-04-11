package com.shawnway.nav.app.wtw.module.quotation.international;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by Administrator on 2016/9/26.
 * 国际期货
 */

public class InternationalListBean {


    private List<InstrumentRealmarketBean> instrumentRealmarket;

    public List<InstrumentRealmarketBean> getInstrumentRealmarket() {
        return instrumentRealmarket;
    }

    public void setInstrumentRealmarket(List<InstrumentRealmarketBean> instrumentRealmarket) {
        this.instrumentRealmarket = instrumentRealmarket;
    }

    public static class InstrumentRealmarketBean implements Parcelable{
        /**
         * id : 16229
         * instrumentid : CLF7
         * securityDesc : 纽约原油 2017-01
         * securityGrpCode : CL
         * serviceDeposit : 2640
         * serviceCharge : 198
         * currencyCode : CNY
         * bidprice1 : 45.72
         * bidvolume1 : 20
         * bidprice2 : 45.71
         * bidvolume2 : 28
         * bidprice3 : 45.7
         * bidvolume3 : 34
         * bidprice4 : 45.69
         * bidvolume4 : 35
         * bidprice5 : 45.68
         * bidvolume5 : 23
         * askprice1 : 45.73
         * askvolume1 : 6
         * askprice2 : 45.74
         * askvolume2 : 14
         * askprice3 : 45.75
         * askvolume3 : 24
         * askprice4 : 45.76
         * askvolume4 : 26
         * askprice5 : 45.77
         * askvolume5 : 27
         * lastprice : 45.72
         * lastvolume : 1
         * openprice : 45.43
         * closeprice : 46.06
         * turnovervolume : 88967
         * turnoveramount : 0
         * priceupdatetime : 1480319047000
         * highprice : 46.3
         * lowprice : 45.14
         * hlupdatetime : 1480319047000
         */

        private int id;
        private String instrumentid;
        private String securityDesc;
        private String securityGrpCode;
        private double serviceDeposit;
        private double serviceCharge;
        private String currencyCode;
        private double bidprice1;
        private int bidvolume1;
        private double bidprice2;
        private int bidvolume2;
        private double bidprice3;
        private int bidvolume3;
        private double bidprice4;
        private int bidvolume4;
        private double bidprice5;
        private int bidvolume5;
        private double askprice1;
        private int askvolume1;
        private double askprice2;
        private int askvolume2;
        private double askprice3;
        private int askvolume3;
        private double askprice4;
        private int askvolume4;
        private double askprice5;
        private int askvolume5;
        private double lastprice;
        private int lastvolume;
        private double openprice;
        private double closeprice;
        private int turnovervolume;
        private int turnoveramount;
        private long priceupdatetime;
        private double highprice;
        private double lowprice;
        private long hlupdatetime;
        private boolean isChecked;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getInstrumentid() {
            return instrumentid;
        }

        public void setInstrumentid(String instrumentid) {
            this.instrumentid = instrumentid;
        }

        public String getSecurityDesc() {
            return securityDesc;
        }

        public void setSecurityDesc(String securityDesc) {
            this.securityDesc = securityDesc;
        }

        public String getSecurityGrpCode() {
            return securityGrpCode;
        }

        public void setSecurityGrpCode(String securityGrpCode) {
            this.securityGrpCode = securityGrpCode;
        }

        public double getServiceDeposit() {
            return serviceDeposit;
        }

        public void setServiceDeposit(double serviceDeposit) {
            this.serviceDeposit = serviceDeposit;
        }

        public double getServiceCharge() {
            return serviceCharge;
        }

        public void setServiceCharge(double serviceCharge) {
            this.serviceCharge = serviceCharge;
        }

        public String getCurrencyCode() {
            return currencyCode;
        }

        public void setCurrencyCode(String currencyCode) {
            this.currencyCode = currencyCode;
        }

        public double getBidprice1() {
            return bidprice1;
        }

        public void setBidprice1(double bidprice1) {
            this.bidprice1 = bidprice1;
        }

        public int getBidvolume1() {
            return bidvolume1;
        }

        public void setBidvolume1(int bidvolume1) {
            this.bidvolume1 = bidvolume1;
        }

        public double getBidprice2() {
            return bidprice2;
        }

        public void setBidprice2(double bidprice2) {
            this.bidprice2 = bidprice2;
        }

        public int getBidvolume2() {
            return bidvolume2;
        }

        public void setBidvolume2(int bidvolume2) {
            this.bidvolume2 = bidvolume2;
        }

        public double getBidprice3() {
            return bidprice3;
        }

        public void setBidprice3(double bidprice3) {
            this.bidprice3 = bidprice3;
        }

        public int getBidvolume3() {
            return bidvolume3;
        }

        public void setBidvolume3(int bidvolume3) {
            this.bidvolume3 = bidvolume3;
        }

        public double getBidprice4() {
            return bidprice4;
        }

        public void setBidprice4(double bidprice4) {
            this.bidprice4 = bidprice4;
        }

        public int getBidvolume4() {
            return bidvolume4;
        }

        public void setBidvolume4(int bidvolume4) {
            this.bidvolume4 = bidvolume4;
        }

        public double getBidprice5() {
            return bidprice5;
        }

        public void setBidprice5(double bidprice5) {
            this.bidprice5 = bidprice5;
        }

        public int getBidvolume5() {
            return bidvolume5;
        }

        public void setBidvolume5(int bidvolume5) {
            this.bidvolume5 = bidvolume5;
        }

        public double getAskprice1() {
            return askprice1;
        }

        public void setAskprice1(double askprice1) {
            this.askprice1 = askprice1;
        }

        public int getAskvolume1() {
            return askvolume1;
        }

        public void setAskvolume1(int askvolume1) {
            this.askvolume1 = askvolume1;
        }

        public double getAskprice2() {
            return askprice2;
        }

        public void setAskprice2(double askprice2) {
            this.askprice2 = askprice2;
        }

        public int getAskvolume2() {
            return askvolume2;
        }

        public void setAskvolume2(int askvolume2) {
            this.askvolume2 = askvolume2;
        }

        public double getAskprice3() {
            return askprice3;
        }

        public void setAskprice3(double askprice3) {
            this.askprice3 = askprice3;
        }

        public int getAskvolume3() {
            return askvolume3;
        }

        public void setAskvolume3(int askvolume3) {
            this.askvolume3 = askvolume3;
        }

        public double getAskprice4() {
            return askprice4;
        }

        public void setAskprice4(double askprice4) {
            this.askprice4 = askprice4;
        }

        public int getAskvolume4() {
            return askvolume4;
        }

        public void setAskvolume4(int askvolume4) {
            this.askvolume4 = askvolume4;
        }

        public double getAskprice5() {
            return askprice5;
        }

        public void setAskprice5(double askprice5) {
            this.askprice5 = askprice5;
        }

        public int getAskvolume5() {
            return askvolume5;
        }

        public void setAskvolume5(int askvolume5) {
            this.askvolume5 = askvolume5;
        }

        public double getLastprice() {
            return lastprice;
        }

        public void setLastprice(double lastprice) {
            this.lastprice = lastprice;
        }

        public int getLastvolume() {
            return lastvolume;
        }

        public void setLastvolume(int lastvolume) {
            this.lastvolume = lastvolume;
        }

        public double getOpenprice() {
            return openprice;
        }

        public void setOpenprice(double openprice) {
            this.openprice = openprice;
        }

        public double getCloseprice() {
            return closeprice;
        }

        public void setCloseprice(double closeprice) {
            this.closeprice = closeprice;
        }

        public int getTurnovervolume() {
            return turnovervolume;
        }

        public void setTurnovervolume(int turnovervolume) {
            this.turnovervolume = turnovervolume;
        }

        public int getTurnoveramount() {
            return turnoveramount;
        }

        public void setTurnoveramount(int turnoveramount) {
            this.turnoveramount = turnoveramount;
        }

        public long getPriceupdatetime() {
            return priceupdatetime;
        }

        public void setPriceupdatetime(long priceupdatetime) {
            this.priceupdatetime = priceupdatetime;
        }

        public double getHighprice() {
            return highprice;
        }

        public void setHighprice(double highprice) {
            this.highprice = highprice;
        }

        public double getLowprice() {
            return lowprice;
        }

        public void setLowprice(double lowprice) {
            this.lowprice = lowprice;
        }

        public long getHlupdatetime() {
            return hlupdatetime;
        }

        public void setHlupdatetime(long hlupdatetime) {
            this.hlupdatetime = hlupdatetime;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.id);
            dest.writeString(this.instrumentid);
            dest.writeString(this.securityDesc);
            dest.writeString(this.securityGrpCode);
            dest.writeDouble(this.serviceDeposit);
            dest.writeDouble(this.serviceCharge);
            dest.writeString(this.currencyCode);
            dest.writeDouble(this.bidprice1);
            dest.writeInt(this.bidvolume1);
            dest.writeDouble(this.bidprice2);
            dest.writeInt(this.bidvolume2);
            dest.writeDouble(this.bidprice3);
            dest.writeInt(this.bidvolume3);
            dest.writeDouble(this.bidprice4);
            dest.writeInt(this.bidvolume4);
            dest.writeDouble(this.bidprice5);
            dest.writeInt(this.bidvolume5);
            dest.writeDouble(this.askprice1);
            dest.writeInt(this.askvolume1);
            dest.writeDouble(this.askprice2);
            dest.writeInt(this.askvolume2);
            dest.writeDouble(this.askprice3);
            dest.writeInt(this.askvolume3);
            dest.writeDouble(this.askprice4);
            dest.writeInt(this.askvolume4);
            dest.writeDouble(this.askprice5);
            dest.writeInt(this.askvolume5);
            dest.writeDouble(this.lastprice);
            dest.writeInt(this.lastvolume);
            dest.writeDouble(this.openprice);
            dest.writeDouble(this.closeprice);
            dest.writeInt(this.turnovervolume);
            dest.writeInt(this.turnoveramount);
            dest.writeLong(this.priceupdatetime);
            dest.writeDouble(this.highprice);
            dest.writeDouble(this.lowprice);
            dest.writeLong(this.hlupdatetime);
        }

        public InstrumentRealmarketBean() {
        }

        protected InstrumentRealmarketBean(Parcel in) {
            this.id = in.readInt();
            this.instrumentid = in.readString();
            this.securityDesc = in.readString();
            this.securityGrpCode = in.readString();
            this.serviceDeposit = in.readDouble();
            this.serviceCharge = in.readDouble();
            this.currencyCode = in.readString();
            this.bidprice1 = in.readDouble();
            this.bidvolume1 = in.readInt();
            this.bidprice2 = in.readDouble();
            this.bidvolume2 = in.readInt();
            this.bidprice3 = in.readDouble();
            this.bidvolume3 = in.readInt();
            this.bidprice4 = in.readDouble();
            this.bidvolume4 = in.readInt();
            this.bidprice5 = in.readDouble();
            this.bidvolume5 = in.readInt();
            this.askprice1 = in.readDouble();
            this.askvolume1 = in.readInt();
            this.askprice2 = in.readDouble();
            this.askvolume2 = in.readInt();
            this.askprice3 = in.readDouble();
            this.askvolume3 = in.readInt();
            this.askprice4 = in.readDouble();
            this.askvolume4 = in.readInt();
            this.askprice5 = in.readDouble();
            this.askvolume5 = in.readInt();
            this.lastprice = in.readDouble();
            this.lastvolume = in.readInt();
            this.openprice = in.readDouble();
            this.closeprice = in.readDouble();
            this.turnovervolume = in.readInt();
            this.turnoveramount = in.readInt();
            this.priceupdatetime = in.readLong();
            this.highprice = in.readDouble();
            this.lowprice = in.readDouble();
            this.hlupdatetime = in.readLong();
        }

        public static final Creator<InstrumentRealmarketBean> CREATOR = new Creator<InstrumentRealmarketBean>() {
            @Override
            public InstrumentRealmarketBean createFromParcel(Parcel source) {
                return new InstrumentRealmarketBean(source);
            }

            @Override
            public InstrumentRealmarketBean[] newArray(int size) {
                return new InstrumentRealmarketBean[size];
            }
        };

        public boolean isChecked() {
            return isChecked;
        }

        public void setChecked(boolean checked) {
            isChecked = checked;
        }
    }

}
