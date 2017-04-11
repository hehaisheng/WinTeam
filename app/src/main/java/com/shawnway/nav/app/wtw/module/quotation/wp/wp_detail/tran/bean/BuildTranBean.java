package com.shawnway.nav.app.wtw.module.quotation.wp.wp_detail.tran.bean;

/**
 * Created by Kevin on 2016/12/9
 * 建仓bean
 */

public class BuildTranBean {
    public String state;
    public String desc;
    public BuildBean data;
    public String error;

    public BuildBean getData() {
        return data;
    }

    public void setData(BuildBean data) {
        this.data = data;
    }

    public static class BuildBean {
        public String id;//单子ID
        public String orderNo;//订单号
        public int prodectId;//产品规格ID
        public String productName;//产品规格名称
        public String productNo;//产品合约代码
        public int amount;//手数
        public double buildPositionPrice;//建仓价格
        public double liquidatePositionPrice;//平仓价格，如果单子没有平仓，此值为null
        public double tradeFee;//建仓手续费
        public int tradeType;//单子方向：1 多单，即买涨；2 空单，即买跌。以下其他接口一致
        public double tradeDeposit;//建仓支出金额
        public double brokenPrice;//爆仓价格
        public int useTicket;//单子是否使用赢家券 1 使用， 其他值，未使用
        public int ticketCount;//【可以忽略】
        public int ticketAmount;//【可以忽略】
        public double targetProfit;//止赢百分比
        public double targetProfitPrice;//【可以忽略】止赢目标价格
        public double stopLoss;//止损百分比
        public double stopLossPrice;//【可以忽略】止损目标价格
        public long buildPositionTime;//建仓时间， 请自行转换时间格式
        public int liquidateType;//平仓类型。如果没有平仓，此值为null
        public int status;//【可以忽略】单子状态

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getOrderNo() {
            return orderNo;
        }

        public void setOrderNo(String orderNo) {
            this.orderNo = orderNo;
        }

        public int getProdectId() {
            return prodectId;
        }

        public void setProdectId(int prodectId) {
            this.prodectId = prodectId;
        }

        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public String getProductNo() {
            return productNo;
        }

        public void setProductNo(String productNo) {
            this.productNo = productNo;
        }

        public int getAmount() {
            return amount;
        }

        public void setAmount(int amount) {
            this.amount = amount;
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

        public double getTradeFee() {
            return tradeFee;
        }

        public void setTradeFee(double tradeFee) {
            this.tradeFee = tradeFee;
        }

        public int getTradeType() {
            return tradeType;
        }

        public void setTradeType(int tradeType) {
            this.tradeType = tradeType;
        }

        public double getTradeDeposit() {
            return tradeDeposit;
        }

        public void setTradeDeposit(double tradeDeposit) {
            this.tradeDeposit = tradeDeposit;
        }

        public double getBrokenPrice() {
            return brokenPrice;
        }

        public void setBrokenPrice(double brokenPrice) {
            this.brokenPrice = brokenPrice;
        }

        public int getUseTicket() {
            return useTicket;
        }

        public void setUseTicket(int useTicket) {
            this.useTicket = useTicket;
        }

        public int getTicketCount() {
            return ticketCount;
        }

        public void setTicketCount(int ticketCount) {
            this.ticketCount = ticketCount;
        }

        public int getTicketAmount() {
            return ticketAmount;
        }

        public void setTicketAmount(int ticketAmount) {
            this.ticketAmount = ticketAmount;
        }

        public double getTargetProfit() {
            return targetProfit;
        }

        public void setTargetProfit(double targetProfit) {
            this.targetProfit = targetProfit;
        }

        public double getTargetProfitPrice() {
            return targetProfitPrice;
        }

        public void setTargetProfitPrice(double targetProfitPrice) {
            this.targetProfitPrice = targetProfitPrice;
        }

        public double getStopLoss() {
            return stopLoss;
        }

        public void setStopLoss(double stopLoss) {
            this.stopLoss = stopLoss;
        }

        public double getStopLossPrice() {
            return stopLossPrice;
        }

        public void setStopLossPrice(double stopLossPrice) {
            this.stopLossPrice = stopLossPrice;
        }

        public long getBuildPositionTime() {
            return buildPositionTime;
        }

        public void setBuildPositionTime(long buildPositionTime) {
            this.buildPositionTime = buildPositionTime;
        }

        public int getLiquidateType() {
            return liquidateType;
        }

        public void setLiquidateType(int liquidateType) {
            this.liquidateType = liquidateType;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

    }

}
