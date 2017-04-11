package com.shawnway.nav.app.wtw.module.mall.bean;

import java.util.List;

/**
 * Created by Kevin on 2016/11/24
 */

public class ExpressDeliver {

    private List<ExpressTracesBean> expressTraces;

    public List<ExpressTracesBean> getExpressTraces() {
        return expressTraces;
    }

    public void setExpressTraces(List<ExpressTracesBean> expressTraces) {
        this.expressTraces = expressTraces;
    }

    public static class ExpressTracesBean {
        /**
         * acceptTime : 2016-11-15 20:32:25
         * acceptStation : 【上海市嘉定区安亭新镇公司】 已收件
         */

        private String acceptTime;
        private String acceptStation;

        public String getAcceptTime() {
            return acceptTime;
        }

        public void setAcceptTime(String acceptTime) {
            this.acceptTime = acceptTime;
        }

        public String getAcceptStation() {
            return acceptStation;
        }

        public void setAcceptStation(String acceptStation) {
            this.acceptStation = acceptStation;
        }
    }
}
