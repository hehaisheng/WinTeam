package com.shawnway.nav.app.wtw.bean;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/7/22 0022.
 * 日历的bean
 */
public class EconCalenBean {

    public ArrayList<ItemBean> financialCalendarList;

    public ArrayList<ItemBean> getFinancialCalendarList() {
        return financialCalendarList;
    }

    public void setFinancialCalendarList(ArrayList<ItemBean> financialCalendarList) {
        this.financialCalendarList = financialCalendarList;
    }

    public class ItemBean{
        public int id;
        public String accessUrl;
        public String activity;//活动，标题
        public int createBy;
        public Long createTime;
        public String date;
        public String region;//国家
        public String time;
        public int significance;

        public int getStartCount() {
            return significance;
        }

        public void setStartCount(int significance) {
            this.significance = significance;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getAccessUrl() {
            return accessUrl;
        }

        public void setAccessUrl(String accessUrl) {
            this.accessUrl = accessUrl;
        }

        public String getActivity() {
            return activity;
        }

        public void setActivity(String activity) {
            this.activity = activity;
        }

        public int getCreateBy() {
            return createBy;
        }

        public void setCreateBy(int createBy) {
            this.createBy = createBy;
        }

        public Long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(Long createTime) {
            this.createTime = createTime;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getRegion() {
            return region;
        }

        public void setRegion(String region) {
            this.region = region;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

    }

}
