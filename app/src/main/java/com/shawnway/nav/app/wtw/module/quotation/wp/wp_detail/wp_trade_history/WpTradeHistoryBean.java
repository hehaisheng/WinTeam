package com.shawnway.nav.app.wtw.module.quotation.wp.wp_detail.wp_trade_history;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by Cicinnus on 2016/12/12.
 */

public class WpTradeHistoryBean {

    /**
     * state : 200
     * desc : OK
     * data : {"page":{"pageno":1,"pagesize":10,"recordcount":1,"range":[1,1],"pagecount":1,"nextpage":1,"prevpage":1,"pageitems":[1],"lastpage":true},"list":[{"orderId":"1ff82006-6c46-4468-8e73-66c3f5a9ee28","orderNo":"161209162318695859","productName":"粤银150g","buildPositionPrice":3754,"liquidatePositionPrice":3785,"amount":1,"tradeType":1,"liquidateTime":1481276163000,"buildTime":1481271798000,"profitOrLoss":4.65,"actualProfitLoss":4.65,"profitOrLossPercent":0.46,"liquidateType":2,"tradeFee":0,"useTicket":1,"tradeDeposit":10,"liquidateIncome":4.65}]}
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
         * page : {"pageno":1,"pagesize":10,"recordcount":1,"range":[1,1],"pagecount":1,"nextpage":1,"prevpage":1,"pageitems":[1],"lastpage":true}
         * list : [{"orderId":"1ff82006-6c46-4468-8e73-66c3f5a9ee28","orderNo":"161209162318695859","productName":"粤银150g","buildPositionPrice":3754,"liquidatePositionPrice":3785,"amount":1,"tradeType":1,"liquidateTime":1481276163000,"buildTime":1481271798000,"profitOrLoss":4.65,"actualProfitLoss":4.65,"profitOrLossPercent":0.46,"liquidateType":2,"tradeFee":0,"useTicket":1,"tradeDeposit":10,"liquidateIncome":4.65}]
         */

        private PageBean page;
        private List<ListBean> list;

        public PageBean getPage() {
            return page;
        }

        public void setPage(PageBean page) {
            this.page = page;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class PageBean {
            /**
             * pageno : 1
             * pagesize : 10
             * recordcount : 1
             * range : [1,1]
             * pagecount : 1
             * nextpage : 1
             * prevpage : 1
             * pageitems : [1]
             * lastpage : true
             */

            private int pageno;
            private int pagesize;
            private int recordcount;
            private int pagecount;
            private int nextpage;
            private int prevpage;
            private boolean lastpage;
            private List<Integer> range;
            private List<Integer> pageitems;

            public int getPageno() {
                return pageno;
            }

            public void setPageno(int pageno) {
                this.pageno = pageno;
            }

            public int getPagesize() {
                return pagesize;
            }

            public void setPagesize(int pagesize) {
                this.pagesize = pagesize;
            }

            public int getRecordcount() {
                return recordcount;
            }

            public void setRecordcount(int recordcount) {
                this.recordcount = recordcount;
            }

            public int getPagecount() {
                return pagecount;
            }

            public void setPagecount(int pagecount) {
                this.pagecount = pagecount;
            }

            public int getNextpage() {
                return nextpage;
            }

            public void setNextpage(int nextpage) {
                this.nextpage = nextpage;
            }

            public int getPrevpage() {
                return prevpage;
            }

            public void setPrevpage(int prevpage) {
                this.prevpage = prevpage;
            }

            public boolean isLastpage() {
                return lastpage;
            }

            public void setLastpage(boolean lastpage) {
                this.lastpage = lastpage;
            }

            public List<Integer> getRange() {
                return range;
            }

            public void setRange(List<Integer> range) {
                this.range = range;
            }

            public List<Integer> getPageitems() {
                return pageitems;
            }

            public void setPageitems(List<Integer> pageitems) {
                this.pageitems = pageitems;
            }
        }

        public static class ListBean implements Parcelable{
            /**
             * orderId : 1ff82006-6c46-4468-8e73-66c3f5a9ee28
             * orderNo : 161209162318695859
             * productName : 粤银150g
             * buildPositionPrice : 3754
             * liquidatePositionPrice : 3785
             * amount : 1
             * tradeType : 1
             * liquidateTime : 1481276163000
             * buildTime : 1481271798000
             * profitOrLoss : 4.65
             * actualProfitLoss : 4.65
             * profitOrLossPercent : 0.46
             * liquidateType : 2
             * tradeFee : 0
             * useTicket : 1
             * tradeDeposit : 10
             * liquidateIncome : 4.65
             */

            private String orderId;
            private String orderNo;
            private String productName;
            private double buildPositionPrice;
            private double liquidatePositionPrice;
            private int amount;
            private int tradeType;//单子方向
            private long liquidateTime;//平仓时间
            private long buildTime;//建仓时间
            private double profitOrLoss;//单子盈亏
            private double actualProfitLoss;//实际盈亏
            private double profitOrLossPercent;//盈亏百分比
            private int liquidateType;//平仓类型
            private double tradeFee;//手续费
            private int useTicket;//是否使用赢家券
            private double tradeDeposit;//建仓保证金，即单子费用
            private double liquidateIncome;

            public String getOrderId() {
                return orderId;
            }

            public void setOrderId(String orderId) {
                this.orderId = orderId;
            }

            public String getOrderNo() {
                return orderNo;
            }

            public void setOrderNo(String orderNo) {
                this.orderNo = orderNo;
            }

            public String getProductName() {
                return productName;
            }

            public void setProductName(String productName) {
                this.productName = productName;
            }

            public double getBuildPositionPrice() {
                return buildPositionPrice;
            }

            public void setBuildPositionPrice(double buildPositionPrice) {
                this.buildPositionPrice = buildPositionPrice;
            }

            public double getLiquidatePositionPrice() {
                return liquidatePositionPrice;
            }

            public void setLiquidatePositionPrice(double liquidatePositionPrice) {
                this.liquidatePositionPrice = liquidatePositionPrice;
            }

            public int getAmount() {
                return amount;
            }

            public void setAmount(int amount) {
                this.amount = amount;
            }

            public int getTradeType() {
                return tradeType;
            }

            public void setTradeType(int tradeType) {
                this.tradeType = tradeType;
            }

            public long getLiquidateTime() {
                return liquidateTime;
            }

            public void setLiquidateTime(long liquidateTime) {
                this.liquidateTime = liquidateTime;
            }

            public long getBuildTime() {
                return buildTime;
            }

            public void setBuildTime(long buildTime) {
                this.buildTime = buildTime;
            }

            public double getProfitOrLoss() {
                return profitOrLoss;
            }

            public void setProfitOrLoss(double profitOrLoss) {
                this.profitOrLoss = profitOrLoss;
            }

            public double getActualProfitLoss() {
                return actualProfitLoss;
            }

            public void setActualProfitLoss(double actualProfitLoss) {
                this.actualProfitLoss = actualProfitLoss;
            }

            public double getProfitOrLossPercent() {
                return profitOrLossPercent;
            }

            public void setProfitOrLossPercent(double profitOrLossPercent) {
                this.profitOrLossPercent = profitOrLossPercent;
            }

            public int getLiquidateType() {
                return liquidateType;
            }

            public void setLiquidateType(int liquidateType) {
                this.liquidateType = liquidateType;
            }

            public double getTradeFee() {
                return tradeFee;
            }

            public void setTradeFee(double tradeFee) {
                this.tradeFee = tradeFee;
            }

            public int getUseTicket() {
                return useTicket;
            }

            public void setUseTicket(int useTicket) {
                this.useTicket = useTicket;
            }

            public double getTradeDeposit() {
                return tradeDeposit;
            }

            public void setTradeDeposit(double tradeDeposit) {
                this.tradeDeposit = tradeDeposit;
            }

            public double getLiquidateIncome() {
                return liquidateIncome;
            }

            public void setLiquidateIncome(double liquidateIncome) {
                this.liquidateIncome = liquidateIncome;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(this.orderId);
                dest.writeString(this.orderNo);
                dest.writeString(this.productName);
                dest.writeDouble(this.buildPositionPrice);
                dest.writeDouble(this.liquidatePositionPrice);
                dest.writeInt(this.amount);
                dest.writeInt(this.tradeType);
                dest.writeLong(this.liquidateTime);
                dest.writeLong(this.buildTime);
                dest.writeDouble(this.profitOrLoss);
                dest.writeDouble(this.actualProfitLoss);
                dest.writeDouble(this.profitOrLossPercent);
                dest.writeInt(this.liquidateType);
                dest.writeDouble(this.tradeFee);
                dest.writeInt(this.useTicket);
                dest.writeDouble(this.tradeDeposit);
                dest.writeDouble(this.liquidateIncome);
            }

            public ListBean() {
            }

            protected ListBean(Parcel in) {
                this.orderId = in.readString();
                this.orderNo = in.readString();
                this.productName = in.readString();
                this.buildPositionPrice = in.readDouble();
                this.liquidatePositionPrice = in.readDouble();
                this.amount = in.readInt();
                this.tradeType = in.readInt();
                this.liquidateTime = in.readLong();
                this.buildTime = in.readLong();
                this.profitOrLoss = in.readDouble();
                this.actualProfitLoss = in.readDouble();
                this.profitOrLossPercent = in.readDouble();
                this.liquidateType = in.readInt();
                this.tradeFee = in.readDouble();
                this.useTicket = in.readInt();
                this.tradeDeposit = in.readDouble();
                this.liquidateIncome = in.readDouble();
            }

            public static final Creator<ListBean> CREATOR = new Creator<ListBean>() {
                @Override
                public ListBean createFromParcel(Parcel source) {
                    return new ListBean(source);
                }

                @Override
                public ListBean[] newArray(int size) {
                    return new ListBean[size];
                }
            };
        }
    }
}
