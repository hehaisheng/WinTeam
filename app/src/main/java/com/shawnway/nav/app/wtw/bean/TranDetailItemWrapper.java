package com.shawnway.nav.app.wtw.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/8/4 0004.
 */
public  class TranDetailItemWrapper implements Parcelable {

    public String state;
    public String desc;

    public ItemList data;
    public String error;

    public ItemList getData() {
        return data;
    }

    public void setData(ItemList data) {
        this.data = data;
    }

    public static class ItemList implements Parcelable {
        public PageBean page;//此字段APP端可以使用也可以忽略，用于提示相对于当前页，下一页的信息，还有记录的总数

        public ArrayList<ProductBean> list;//一个数组，具体的历史单子明细，每一个元素即一个单子

        public ArrayList<ProductBean> getList() {
            return list;
        }

        public void setList(ArrayList<ProductBean> list) {
            this.list = list;
        }

        public PageBean getPage() {
            return page;
        }

        public void setPage(PageBean page) {
            this.page = page;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeParcelable(this.page, flags);
            dest.writeTypedList(this.list);
        }

        public ItemList() {
        }

        protected ItemList(Parcel in) {
            this.page = in.readParcelable(PageBean.class.getClassLoader());
            this.list = in.createTypedArrayList(ProductBean.CREATOR);
        }

        public static final Creator<ItemList> CREATOR = new Creator<ItemList>() {
            @Override
            public ItemList createFromParcel(Parcel source) {
                return new ItemList(source);
            }

            @Override
            public ItemList[] newArray(int size) {
                return new ItemList[size];
            }
        };
    }

    public static class PageBean implements Parcelable {
        public int pageno;
        public int pagesize;
        public int recordcount;//明细总数
        public ArrayList<Integer> range;
        public int pagecount;
        public int nextpage;
        public int prevpage;
        public ArrayList<Integer> pageitems;
        public boolean lastpage;//是否是最后一页，true 是；false 不是

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.pageno);
            dest.writeInt(this.pagesize);
            dest.writeInt(this.recordcount);
            dest.writeList(this.range);
            dest.writeInt(this.pagecount);
            dest.writeInt(this.nextpage);
            dest.writeInt(this.prevpage);
            dest.writeList(this.pageitems);
            dest.writeByte(this.lastpage ? (byte) 1 : (byte) 0);
        }

        public PageBean() {
        }

        protected PageBean(Parcel in) {
            this.pageno = in.readInt();
            this.pagesize = in.readInt();
            this.recordcount = in.readInt();
            this.range = new ArrayList<Integer>();
            in.readList(this.range, Integer.class.getClassLoader());
            this.pagecount = in.readInt();
            this.nextpage = in.readInt();
            this.prevpage = in.readInt();
            this.pageitems = new ArrayList<Integer>();
            in.readList(this.pageitems, Integer.class.getClassLoader());
            this.lastpage = in.readByte() != 0;
        }

        public static final Parcelable.Creator<PageBean> CREATOR = new Parcelable.Creator<PageBean>() {
            @Override
            public PageBean createFromParcel(Parcel source) {
                return new PageBean(source);
            }

            @Override
            public PageBean[] newArray(int size) {
                return new PageBean[size];
            }
        };
    }

    public List<ProductBean> getList() {
        return list;
    }

    public void setList(List<ProductBean> productBeanList) {
        this.list = productBeanList;
    }

    public  List<ProductBean> list;

    public static class ProductBean implements Parcelable {
        public String orderId;//单子ID
        public String orderNo;//订单号
        public String productName;//产品规格名称
        public double buildPositionPrice;//建仓时产品价格
        public double liquidatePositionPrice;//平仓时产品价格
        public int amount;//手数
        public int tradeType;//单子方向
        public Long liquidateTime;//平仓时间，请自行根据需要格式化
        public long buildTime;//建仓时间，请自行根据需要格式化
        public double profitOrLoss;//单子盈亏
        public double actualProfitLoss;//单子实际盈亏，此值是profitOrLoss 减去建仓费用之后的数字
        public double profitOrLossPercent;//盈亏百分比
        public int liquidateType;//平仓类型：1 爆仓；2 客户手动平仓；3 止赢平仓； 4 止损平仓；5结算平仓。其他接口一致
        public double tradeFee;//单子手续费
        public int useTicket;//是否使用赢家券做的单：1 是；0 否
        public double tradeDeposit;//建仓保证金，即单子的费用
        public double liquidateIncome;//建仓保证金，即单子的费用

        public double getLiquidateIncome() {
            return liquidateIncome;
        }

        public void setLiquidateIncome(double liquidateIncome) {
            this.liquidateIncome = liquidateIncome;
        }

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

        public Long getLiquidateTime() {
            return liquidateTime;
        }

        public void setLiquidateTime(Long liquidateTime) {
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

        @Override
        public String toString() {
            return "{" +
                    "\"orderId\":\"" + orderId + '\"' +
                    ",\"orderNo\":\"" + orderNo + '\"' +
                    ",\"productName\":\"" + productName + '\"' +
                    ",\"buildPositionPrice\":" + buildPositionPrice +
                    ",\"liquidatePositionPrice\":" + liquidatePositionPrice +
                    ",\"amount\":" + amount +
                    ",\"tradeType\":" + tradeType +
                    ",\"liquidateTime\":" + liquidateTime +
                    ",\"buildTime\":" + buildTime +
                    ",\"profitOrLoss\":" + profitOrLoss +
                    ",\"actualProfitLoss\":" + actualProfitLoss +
                    ",\"profitOrLossPercent\":" + profitOrLossPercent +
                    ",\"liquidateType\":" + liquidateType +
                    ",\"tradeFee\":" + tradeFee +
                    ",\"useTicket\":" + useTicket +
                    ",\"tradeDeposit\":" + tradeDeposit +
                    ",\"liquidateIncome\":" + liquidateIncome +
                    '}';
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
            dest.writeValue(this.liquidateTime);
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

        public ProductBean() {
        }

        protected ProductBean(Parcel in) {
            this.orderId = in.readString();
            this.orderNo = in.readString();
            this.productName = in.readString();
            this.buildPositionPrice = in.readDouble();
            this.liquidatePositionPrice = in.readDouble();
            this.amount = in.readInt();
            this.tradeType = in.readInt();
            this.liquidateTime = (Long) in.readValue(Long.class.getClassLoader());
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

        public static final Parcelable.Creator<ProductBean> CREATOR = new Parcelable.Creator<ProductBean>() {
            @Override
            public ProductBean createFromParcel(Parcel source) {
                return new ProductBean(source);
            }

            @Override
            public ProductBean[] newArray(int size) {
                return new ProductBean[size];
            }
        };
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.state);
        dest.writeString(this.desc);
        dest.writeParcelable(this.data, flags);
        dest.writeString(this.error);
        dest.writeTypedList(this.list);
    }

    public TranDetailItemWrapper() {
    }

    protected TranDetailItemWrapper(Parcel in) {
        this.state = in.readString();
        this.desc = in.readString();
        this.data = in.readParcelable(ItemList.class.getClassLoader());
        this.error = in.readString();
        this.list = in.createTypedArrayList(ProductBean.CREATOR);
    }

    public static final Parcelable.Creator<TranDetailItemWrapper> CREATOR = new Parcelable.Creator<TranDetailItemWrapper>() {
        @Override
        public TranDetailItemWrapper createFromParcel(Parcel source) {
            return new TranDetailItemWrapper(source);
        }

        @Override
        public TranDetailItemWrapper[] newArray(int size) {
            return new TranDetailItemWrapper[size];
        }
    };
}
