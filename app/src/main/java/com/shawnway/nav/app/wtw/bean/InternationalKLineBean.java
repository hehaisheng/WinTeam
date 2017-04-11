package com.shawnway.nav.app.wtw.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/10/9.
 * 国际期货K线bean
 */

public class InternationalKLineBean {

    /**
     * dataId : 50693
     * chartAvgPrice : 23833.03704
     * chartClosePrice : 23832
     * chartCurrency : HKD
     * chartHighPrice : 23835
     * chartLastPrice : 23832
     * chartLowPrice : 23831
     * chartOpenPrice : 23834
     * chartQty : 81
     * chartTime : 2016-10-09 14:20:59
     * chartType : 1
     * chartVolume : 1930476
     * securityId : 106
     * securitySymbol : HSIV6
     */

    private List<MarketDataCandleChartBean> marketDataCandleChart;

    public List<MarketDataCandleChartBean> getMarketDataCandleChart() {
        return marketDataCandleChart;
    }

    public void setMarketDataCandleChart(List<MarketDataCandleChartBean> marketDataCandleChart) {
        this.marketDataCandleChart = marketDataCandleChart;
    }

    public static class MarketDataCandleChartBean {
        private int dataId;
        private double chartAvgPrice;
        private Number chartClosePrice;
        private String chartCurrency;
        private Number chartHighPrice;
        private Number chartLastPrice;
        private Number chartLowPrice;
        private Number chartOpenPrice;
        private Number chartQty;
        private String chartTime;
        private Number chartType;
        private Number chartVolume;
        private Number securityId;
        private String securitySymbol;

        public int getDataId() {
            return dataId;
        }

        public void setDataId(int dataId) {
            this.dataId = dataId;
        }

        public double getChartAvgPrice() {
            return chartAvgPrice;
        }

        public void setChartAvgPrice(double chartAvgPrice) {
            this.chartAvgPrice = chartAvgPrice;
        }

        public Number getChartClosePrice() {
            return chartClosePrice;
        }

        public void setChartClosePrice(Number chartClosePrice) {
            this.chartClosePrice = chartClosePrice;
        }

        public String getChartCurrency() {
            return chartCurrency;
        }

        public void setChartCurrency(String chartCurrency) {
            this.chartCurrency = chartCurrency;
        }

        public Number getChartHighPrice() {
            return chartHighPrice;
        }

        public void setChartHighPrice(Number chartHighPrice) {
            this.chartHighPrice = chartHighPrice;
        }

        public Number getChartLastPrice() {
            return chartLastPrice;
        }

        public void setChartLastPrice(Number chartLastPrice) {
            this.chartLastPrice = chartLastPrice;
        }

        public Number getChartLowPrice() {
            return chartLowPrice;
        }

        public void setChartLowPrice(Number chartLowPrice) {
            this.chartLowPrice = chartLowPrice;
        }

        public Number getChartOpenPrice() {
            return chartOpenPrice;
        }

        public void setChartOpenPrice(Number chartOpenPrice) {
            this.chartOpenPrice = chartOpenPrice;
        }

        public Number getChartQty() {
            return chartQty;
        }

        public void setChartQty(Number chartQty) {
            this.chartQty = chartQty;
        }

        public String getChartTime() {
            return chartTime;
        }

        public void setChartTime(String chartTime) {
            this.chartTime = chartTime;
        }

        public Number getChartType() {
            return chartType;
        }

        public void setChartType(Number chartType) {
            this.chartType = chartType;
        }

        public Number getChartVolume() {
            return chartVolume;
        }

        public void setChartVolume(Number chartVolume) {
            this.chartVolume = chartVolume;
        }

        public Number getSecurityId() {
            return securityId;
        }

        public void setSecurityId(Number securityId) {
            this.securityId = securityId;
        }

        public String getSecuritySymbol() {
            return securitySymbol;
        }

        public void setSecuritySymbol(String securitySymbol) {
            this.securitySymbol = securitySymbol;
        }
    }
}
