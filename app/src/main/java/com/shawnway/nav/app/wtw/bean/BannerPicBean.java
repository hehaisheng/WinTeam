package com.shawnway.nav.app.wtw.bean;

import java.util.ArrayList;


/**
 * Banner图的bean
 */
public class BannerPicBean {

    ArrayList<PicBean> bannerPicList;

    public ArrayList<PicBean> getBannerPicList() {
        return bannerPicList;
    }

    public void setBannerPicList(ArrayList<PicBean> bannerPicList) {
        this.bannerPicList = bannerPicList;
    }
    public static class PicBean{
        public int id;
        public String contentUrl;
        public int enabled;
        public String picDesc;
        public String picUrl;
        public String showOrder;
        public String updateBy;
        public Long updateTime;
        public String uploadBy;
        public Long uploadTime;
        public String startTime;
        public String endTime;

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

        public String getStartTime() {
            return startTime;
        }

        public void setStartTime(String startTime) {
            this.startTime = startTime;
        }

        public Long getUploadTime() {
            return uploadTime;
        }

        public void setUploadTime(Long uploadTime) {
            this.uploadTime = uploadTime;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getContentUrl() {
            return contentUrl;
        }

        public void setContentUrl(String contentUrl) {
            this.contentUrl = contentUrl;
        }

        public int getEnabled() {
            return enabled;
        }

        public void setEnabled(int enabled) {
            this.enabled = enabled;
        }

        public String getPicDesc() {
            return picDesc;
        }

        public void setPicDesc(String picDesc) {
            this.picDesc = picDesc;
        }

        public String getPicUrl() {
            return picUrl;
        }

        public void setPicUrl(String picUrl) {
            this.picUrl = picUrl;
        }

        public String getShowOrder() {
            return showOrder;
        }

        public void setShowOrder(String showOrder) {
            this.showOrder = showOrder;
        }

        public String getUpdateBy() {
            return updateBy;
        }

        public void setUpdateBy(String updateBy) {
            this.updateBy = updateBy;
        }

        public Long getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(Long updateTime) {
            this.updateTime = updateTime;
        }

        public String getUploadBy() {
            return uploadBy;
        }

        public void setUploadBy(String uploadBy) {
            this.uploadBy = uploadBy;
        }

    }
}
