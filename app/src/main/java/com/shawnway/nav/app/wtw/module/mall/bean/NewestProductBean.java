package com.shawnway.nav.app.wtw.module.mall.bean;

import java.util.List;

/**
 * Created by Cicinnus on 2016/11/9.
 */

public class NewestProductBean {

    private List<AllProductEntityBean> allProductEntity;

    public List<AllProductEntityBean> getAllProductEntity() {
        return allProductEntity;
    }

    public void setAllProductEntity(List<AllProductEntityBean> allProductEntity) {
        this.allProductEntity = allProductEntity;
    }

    public static class AllProductEntityBean {
        /**
         * proId : 47
         * agentId : 1
         * isRecommend : 0
         * proAmount : 111
         * proCode : null
         * proDesc : 23123213213213
         * proDiscCsptPoint : 333
         * proExchangedAmount : 0
         * proImg1 :
         * proImg2 :
         * proImg3 :
         * proImg4 :
         * proImg5 :
         * proName : 不知道
         * proPrice : 10
         * proRealCsptPoint : 123
         * proStatus : 1
         * productTypeId : 2
         * updateBy : null
         * updateTime : 1478760789000
         * uploadBy : 1
         * uploadTime : 1478760382000
         */

        private int proId;
        private int agentId;
        private int isRecommend;
        private int proAmount;
        private Object proCode;
        private String proDesc;
        private int proDiscCsptPoint;
        private int proExchangedAmount;
        private String proImg1;
        private String proImg2;
        private String proImg3;
        private String proImg4;
        private String proImg5;
        private String proName;
        private int proPrice;
        private int proRealCsptPoint;
        private int proStatus;
        private int productTypeId;
        private Object updateBy;
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

        public Object getProCode() {
            return proCode;
        }

        public void setProCode(Object proCode) {
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

        public String getProImg2() {
            return proImg2;
        }

        public void setProImg2(String proImg2) {
            this.proImg2 = proImg2;
        }

        public String getProImg3() {
            return proImg3;
        }

        public void setProImg3(String proImg3) {
            this.proImg3 = proImg3;
        }

        public String getProImg4() {
            return proImg4;
        }

        public void setProImg4(String proImg4) {
            this.proImg4 = proImg4;
        }

        public String getProImg5() {
            return proImg5;
        }

        public void setProImg5(String proImg5) {
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

        public Object getUpdateBy() {
            return updateBy;
        }

        public void setUpdateBy(Object updateBy) {
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
