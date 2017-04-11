package com.shawnway.nav.app.wtw.module.quotation.wp.wp_detail.wp_positions;

import java.util.List;

/**
 * Created by Cicinnus on 2016/12/9.
 */

public class WpPositionsBean {

    /**
     * state : 200
     * desc : OK
     * data : {"totalProfitLoss":0.15,"list":[{"id":"1ff82006-6c46-4468-8e73-66c3f5a9ee28","orderNo":"161209162318695859","productId":1,"productName":"粤银","productNo":"XAG1","amount":1,"buildPositionPrice":3754,"liquidatePositionPrice":0,"tradeFee":0,"tradeType":1,"tradeDeposit":10,"brokenPrice":3687,"useTicket":1,"ticketCount":0,"ticketAmount":0,"targetProfit":0,"targetProfitPrice":0,"stopLoss":0,"stopLossPrice":0,"profitOrLoss":0.15,"actualProfitLoss":0,"profitOrLossPercent":0,"liquidateIncome":0,"userId":498,"brokerId":0,"orgId":41,"status":1,"liquidateType":0,"buildPositionTime":1481271798000,"liquidatePositionTime":null,"floatProfit":0.15,"floatUnit":1,"unionMark":"4982016-12-09 16:23:18","punitprice":10,"punit":"g","pamount":150,"currentprice":3755,"currentProfitPercent":0,"specifications":"150g"}]}
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
         * totalProfitLoss : 0.15
         * list : [{"id":"1ff82006-6c46-4468-8e73-66c3f5a9ee28","orderNo":"161209162318695859","productId":1,"productName":"粤银","productNo":"XAG1","amount":1,"buildPositionPrice":3754,"liquidatePositionPrice":0,"tradeFee":0,"tradeType":1,"tradeDeposit":10,"brokenPrice":3687,"useTicket":1,"ticketCount":0,"ticketAmount":0,"targetProfit":0,"targetProfitPrice":0,"stopLoss":0,"stopLossPrice":0,"profitOrLoss":0.15,"actualProfitLoss":0,"profitOrLossPercent":0,"liquidateIncome":0,"userId":498,"brokerId":0,"orgId":41,"status":1,"liquidateType":0,"buildPositionTime":1481271798000,"liquidatePositionTime":null,"floatProfit":0.15,"floatUnit":1,"unionMark":"4982016-12-09 16:23:18","punitprice":10,"punit":"g","pamount":150,"currentprice":3755,"currentProfitPercent":0,"specifications":"150g"}]
         */

        private double totalProfitLoss;
        private List<ListBean> list;

        public double getTotalProfitLoss() {
            return totalProfitLoss;
        }

        public void setTotalProfitLoss(double totalProfitLoss) {
            this.totalProfitLoss = totalProfitLoss;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * id : 1ff82006-6c46-4468-8e73-66c3f5a9ee28
             * orderNo : 161209162318695859
             * productId : 1
             * productName : 粤银
             * productNo : XAG1
             * amount : 1
             * buildPositionPrice : 3754
             * liquidatePositionPrice : 0
             * tradeFee : 0
             * tradeType : 1
             * tradeDeposit : 10
             * brokenPrice : 3687
             * useTicket : 1
             * ticketCount : 0
             * ticketAmount : 0
             * targetProfit : 0
             * targetProfitPrice : 0
             * stopLoss : 0
             * stopLossPrice : 0
             * profitOrLoss : 0.15
             * actualProfitLoss : 0
             * profitOrLossPercent : 0
             * liquidateIncome : 0
             * userId : 498
             * brokerId : 0
             * orgId : 41
             * status : 1
             * liquidateType : 0
             * buildPositionTime : 1481271798000
             * liquidatePositionTime : null
             * floatProfit : 0.15
             * floatUnit : 1
             * unionMark : 4982016-12-09 16:23:18
             * punitprice : 10
             * punit : g
             * pamount : 150
             * currentprice : 3755
             * currentProfitPercent : 0
             * specifications : 150g
             */

            private String id;
            private String orderNo;
            private int productId;
            private String productName;
            private String productNo;
            private Number amount;
            private Number buildPositionPrice;
            private Number liquidatePositionPrice;
            private Number tradeFee;
            private Number tradeType;
            private Number tradeDeposit;
            private Number brokenPrice;
            private Number useTicket;
            private Number ticketCount;
            private Number ticketAmount;
            private Number targetProfit;
            private Number targetProfitPrice;
            private Number stopLoss;
            private Number stopLossPrice;
            private double profitOrLoss;
            private Number actualProfitLoss;
            private Number profitOrLossPercent;
            private Number liquidateIncome;
            private Number userId;
            private Number brokerId;
            private Number orgId;
            private Number status;
            private Number liquidateType;
            private long buildPositionTime;
            private Object liquidatePositionTime;
            private double floatProfit;
            private Number floatUnit;
            private String unionMark;
            private Number punitprice;
            private String punit;
            private Number pamount;
            private Number currentprice;
            private Number currentProfitPercent;
            private String specifications;

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

            public int getProductId() {
                return productId;
            }

            public void setProductId(int productId) {
                this.productId = productId;
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

            public Number getAmount() {
                return amount;
            }

            public void setAmount(Number amount) {
                this.amount = amount;
            }

            public Number getBuildPositionPrice() {
                return buildPositionPrice;
            }

            public void setBuildPositionPrice(Number buildPositionPrice) {
                this.buildPositionPrice = buildPositionPrice;
            }

            public Number getLiquidatePositionPrice() {
                return liquidatePositionPrice;
            }

            public void setLiquidatePositionPrice(Number liquidatePositionPrice) {
                this.liquidatePositionPrice = liquidatePositionPrice;
            }

            public Number getTradeFee() {
                return tradeFee;
            }

            public void setTradeFee(Number tradeFee) {
                this.tradeFee = tradeFee;
            }

            public Number getTradeType() {
                return tradeType;
            }

            public void setTradeType(Number tradeType) {
                this.tradeType = tradeType;
            }

            public Number getTradeDeposit() {
                return tradeDeposit;
            }

            public void setTradeDeposit(Number tradeDeposit) {
                this.tradeDeposit = tradeDeposit;
            }

            public Number getBrokenPrice() {
                return brokenPrice;
            }

            public void setBrokenPrice(Number brokenPrice) {
                this.brokenPrice = brokenPrice;
            }

            public Number getUseTicket() {
                return useTicket;
            }

            public void setUseTicket(Number useTicket) {
                this.useTicket = useTicket;
            }

            public Number getTicketCount() {
                return ticketCount;
            }

            public void setTicketCount(Number ticketCount) {
                this.ticketCount = ticketCount;
            }

            public Number getTicketAmount() {
                return ticketAmount;
            }

            public void setTicketAmount(Number ticketAmount) {
                this.ticketAmount = ticketAmount;
            }

            public Number getTargetProfit() {
                return targetProfit;
            }

            public void setTargetProfit(Number targetProfit) {
                this.targetProfit = targetProfit;
            }

            public Number getTargetProfitPrice() {
                return targetProfitPrice;
            }

            public void setTargetProfitPrice(Number targetProfitPrice) {
                this.targetProfitPrice = targetProfitPrice;
            }

            public Number getStopLoss() {
                return stopLoss;
            }

            public void setStopLoss(Number stopLoss) {
                this.stopLoss = stopLoss;
            }

            public Number getStopLossPrice() {
                return stopLossPrice;
            }

            public void setStopLossPrice(Number stopLossPrice) {
                this.stopLossPrice = stopLossPrice;
            }

            public double getProfitOrLoss() {
                return profitOrLoss;
            }

            public void setProfitOrLoss(double profitOrLoss) {
                this.profitOrLoss = profitOrLoss;
            }

            public Number getActualProfitLoss() {
                return actualProfitLoss;
            }

            public void setActualProfitLoss(Number actualProfitLoss) {
                this.actualProfitLoss = actualProfitLoss;
            }

            public Number getProfitOrLossPercent() {
                return profitOrLossPercent;
            }

            public void setProfitOrLossPercent(Number profitOrLossPercent) {
                this.profitOrLossPercent = profitOrLossPercent;
            }

            public Number getLiquidateIncome() {
                return liquidateIncome;
            }

            public void setLiquidateIncome(Number liquidateIncome) {
                this.liquidateIncome = liquidateIncome;
            }

            public Number getUserId() {
                return userId;
            }

            public void setUserId(Number userId) {
                this.userId = userId;
            }

            public Number getBrokerId() {
                return brokerId;
            }

            public void setBrokerId(Number brokerId) {
                this.brokerId = brokerId;
            }

            public Number getOrgId() {
                return orgId;
            }

            public void setOrgId(Number orgId) {
                this.orgId = orgId;
            }

            public Number getStatus() {
                return status;
            }

            public void setStatus(Number status) {
                this.status = status;
            }

            public Number getLiquidateType() {
                return liquidateType;
            }

            public void setLiquidateType(Number liquidateType) {
                this.liquidateType = liquidateType;
            }

            public long getBuildPositionTime() {
                return buildPositionTime;
            }

            public void setBuildPositionTime(long buildPositionTime) {
                this.buildPositionTime = buildPositionTime;
            }

            public Object getLiquidatePositionTime() {
                return liquidatePositionTime;
            }

            public void setLiquidatePositionTime(Object liquidatePositionTime) {
                this.liquidatePositionTime = liquidatePositionTime;
            }

            public double getFloatProfit() {
                return floatProfit;
            }

            public void setFloatProfit(double floatProfit) {
                this.floatProfit = floatProfit;
            }

            public Number getFloatUnit() {
                return floatUnit;
            }

            public void setFloatUnit(Number floatUnit) {
                this.floatUnit = floatUnit;
            }

            public String getUnionMark() {
                return unionMark;
            }

            public void setUnionMark(String unionMark) {
                this.unionMark = unionMark;
            }

            public Number getPunitprice() {
                return punitprice;
            }

            public void setPunitprice(Number punitprice) {
                this.punitprice = punitprice;
            }

            public String getPunit() {
                return punit;
            }

            public void setPunit(String punit) {
                this.punit = punit;
            }

            public Number getPamount() {
                return pamount;
            }

            public void setPamount(Number pamount) {
                this.pamount = pamount;
            }

            public Number getCurrentprice() {
                return currentprice;
            }

            public void setCurrentprice(Number currentprice) {
                this.currentprice = currentprice;
            }

            public Number getCurrentProfitPercent() {
                return currentProfitPercent;
            }

            public void setCurrentProfitPercent(Number currentProfitPercent) {
                this.currentProfitPercent = currentProfitPercent;
            }

            public String getSpecifications() {
                return specifications;
            }

            public void setSpecifications(String specifications) {
                this.specifications = specifications;
            }
        }
    }
}
