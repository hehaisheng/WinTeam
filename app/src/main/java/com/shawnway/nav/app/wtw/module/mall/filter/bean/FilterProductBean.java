package com.shawnway.nav.app.wtw.module.mall.filter.bean;

import java.util.List;

/**
 * Created by Cicinnus on 2016/11/10.
 */

public class FilterProductBean {

    private List<AllProductEntityBean> allProductEntity;

    public List<AllProductEntityBean> getAllProductEntity() {
        return allProductEntity;
    }

    public void setAllProductEntity(List<AllProductEntityBean> allProductEntity) {
        this.allProductEntity = allProductEntity;
    }

    public static class AllProductEntityBean {
        /**
         * proId : 21
         * agentId : 1
         * isRecommend : 1
         * proAmount : 3333
         * proCode : G21
         * proDesc : wwww
         * proDiscCsptPoint : 500
         * proExchangedAmount : 0
         * proImg1 : http://120.76.153.154:9088/commom/file/edb33f1dbike.png
         * proImg2 : null
         * proImg3 : null
         * proImg4 : null
         * proImg5 : null
         * proName : wwww
         * proPrice : 22
         * proRealCsptPoint : 500
         * proStatus : 0
         * productTypeId : 3
         * updateBy : 1
         * updateTime : 1478676712000
         * uploadBy : 14
         * uploadTime : 1476845128000
         */

        private int proId;
        private int agentId;
        private int isRecommend;
        private int proAmount;
        private String proCode;
        private String proDesc;
        private int proDiscCsptPoint;
        private int proExchangedAmount;
        private String proImg1;
        private Object proImg2;
        private Object proImg3;
        private Object proImg4;
        private Object proImg5;
        private String proName;
        private int proPrice;
        private int proRealCsptPoint;
        private int proStatus;
        private int productTypeId;
        private int updateBy;
        private long updateTime;
        private int uploadBy;
        private long uploadTime;

        public int getProId() {
            return proId;
        }

        public void setProId(int proId) {
            this.proId = proId;
        }

        public int getAgentId() {
            return agentId;
        }

        public void setAgentId(int agentId) {
            this.agentId = agentId;
        }

        public int getIsRecommend() {
            return isRecommend;
        }

        public void setIsRecommend(int isRecommend) {
            this.isRecommend = isRecommend;
        }

        public int getProAmount() {
            return proAmount;
        }

        public void setProAmount(int proAmount) {
            this.proAmount = proAmount;
        }

        public String getProCode() {
            return proCode;
        }

        public void setProCode(String proCode) {
            this.proCode = proCode;
        }

        public String getProDesc() {
            return proDesc;
        }

        public void setProDesc(String proDesc) {
            this.proDesc = proDesc;
        }

        public int getProDiscCsptPoint() {
            return proDiscCsptPoint;
        }

        public void setProDiscCsptPoint(int proDiscCsptPoint) {
            this.proDiscCsptPoint = proDiscCsptPoint;
        }

        public int getProExchangedAmount() {
            return proExchangedAmount;
        }

        public void setProExchangedAmount(int proExchangedAmount) {
            this.proExchangedAmount = proExchangedAmount;
        }

        public String getProImg1() {
            return proImg1;
        }

        public void setProImg1(String proImg1) {
            this.proImg1 = proImg1;
        }

        public Object getProImg2() {
            return proImg2;
        }

        public void setProImg2(Object proImg2) {
            this.proImg2 = proImg2;
        }

        public Object getProImg3() {
            return proImg3;
        }

        public void setProImg3(Object proImg3) {
            this.proImg3 = proImg3;
        }

        public Object getProImg4() {
            return proImg4;
        }

        public void setProImg4(Object proImg4) {
            this.proImg4 = proImg4;
        }

        public Object getProImg5() {
            return proImg5;
        }

        public void setProImg5(Object proImg5) {
            this.proImg5 = proImg5;
        }

        public String getProName() {
            return proName;
        }

        public void setProName(String proName) {
            this.proName = proName;
        }

        public int getProPrice() {
            return proPrice;
        }

        public void setProPrice(int proPrice) {
            this.proPrice = proPrice;
        }

        public int getProRealCsptPoint() {
            return proRealCsptPoint;
        }

        public void setProRealCsptPoint(int proRealCsptPoint) {
            this.proRealCsptPoint = proRealCsptPoint;
        }

        public int getProStatus() {
            return proStatus;
        }

        public void setProStatus(int proStatus) {
            this.proStatus = proStatus;
        }

        public int getProductTypeId() {
            return productTypeId;
        }

        public void setProductTypeId(int productTypeId) {
            this.productTypeId = productTypeId;
        }

        public int getUpdateBy() {
            return updateBy;
        }

        public void setUpdateBy(int updateBy) {
            this.updateBy = updateBy;
        }

        public long getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(long updateTime) {
            this.updateTime = updateTime;
        }

        public int getUploadBy() {
            return uploadBy;
        }

        public void setUploadBy(int uploadBy) {
            this.uploadBy = uploadBy;
        }

        public long getUploadTime() {
            return uploadTime;
        }

        public void setUploadTime(long uploadTime) {
            this.uploadTime = uploadTime;
        }
    }
}
