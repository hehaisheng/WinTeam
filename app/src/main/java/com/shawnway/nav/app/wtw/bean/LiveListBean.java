package com.shawnway.nav.app.wtw.bean;

import java.util.List;


/**
 * 直播列表
 */
public class LiveListBean {


    /**
     * statusCode : SUCCESS
     * allLiveShows : [{"id":26,"liveName":"一号","picUrl":"http://120.76.153.154:9088/commom/file/a22cf1cbbanner2.png","liveUrl":"http://www.zhiniu8.com/live/1480552369","liveShortDesc":"微盘解读","liveFullDesc":"微盘解读","author":"Jason","createTime":1479270708000,"enabled":true,"showOrder":1,"updateBy":1,"updateTime":1479292533000,"firstShowEndTime":"11:30:00","firstShowStartTime":"10:00:00","secondShowEndTime":"15:30:00","secondShowStartTime":"14:00:00","thirdShowEndTime":"21:00:00","thirdShowStartTime":"20:00:00"},{"id":27,"liveName":"二号直播间","picUrl":"http://120.76.153.154:9088/commom/file/cc1bceaebanner3.png","liveUrl":"http://www.zhiniu8.com/live/1480552369","liveShortDesc":"微盘解读","liveFullDesc":"微盘解读","author":"sadsad","createTime":1479270709000,"enabled":true,"showOrder":2,"updateBy":1,"updateTime":1479278665000,"firstShowEndTime":"11:30:00","firstShowStartTime":"10:00:00","secondShowEndTime":"15:30:00","secondShowStartTime":"14:00:00","thirdShowEndTime":"21:00:00","thirdShowStartTime":"20:00:00"}]
     */

    private String statusCode;
    private List<AllLiveShowsBean> allLiveShows;

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public List<AllLiveShowsBean> getAllLiveShows() {
        return allLiveShows;
    }

    public void setAllLiveShows(List<AllLiveShowsBean> allLiveShows) {
        this.allLiveShows = allLiveShows;
    }

    public static class AllLiveShowsBean {
        /**
         * id : 26
         * liveName : 一号
         * picUrl : http://120.76.153.154:9088/commom/file/a22cf1cbbanner2.png
         * liveUrl : http://www.zhiniu8.com/live/1480552369
         * liveShortDesc : 微盘解读
         * liveFullDesc : 微盘解读
         * author : Jason
         * createTime : 1479270708000
         * enabled : true
         * showOrder : 1
         * updateBy : 1
         * updateTime : 1479292533000
         * firstShowEndTime : 11:30:00
         * firstShowStartTime : 10:00:00
         * secondShowEndTime : 15:30:00
         * secondShowStartTime : 14:00:00
         * thirdShowEndTime : 21:00:00
         * thirdShowStartTime : 20:00:00
         */

        private int id;
        private String liveName;
        private String picUrl;
        private String liveUrl;
        private String liveShortDesc;
        private String liveFullDesc;
        private String author;
        private long createTime;
        private boolean enabled;
        private int showOrder;
        private int updateBy;
        private long updateTime;
        private String firstShowEndTime;
        private String firstShowStartTime;
        private String secondShowEndTime;
        private String secondShowStartTime;
        private String thirdShowEndTime;
        private String thirdShowStartTime;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getLiveName() {
            return liveName;
        }

        public void setLiveName(String liveName) {
            this.liveName = liveName;
        }

        public String getPicUrl() {
            return picUrl;
        }

        public void setPicUrl(String picUrl) {
            this.picUrl = picUrl;
        }

        public String getLiveUrl() {
            return liveUrl;
        }

        public void setLiveUrl(String liveUrl) {
            this.liveUrl = liveUrl;
        }

        public String getLiveShortDesc() {
            return liveShortDesc;
        }

        public void setLiveShortDesc(String liveShortDesc) {
            this.liveShortDesc = liveShortDesc;
        }

        public String getLiveFullDesc() {
            return liveFullDesc;
        }

        public void setLiveFullDesc(String liveFullDesc) {
            this.liveFullDesc = liveFullDesc;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public boolean isEnabled() {
            return enabled;
        }

        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }

        public int getShowOrder() {
            return showOrder;
        }

        public void setShowOrder(int showOrder) {
            this.showOrder = showOrder;
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

        public String getFirstShowEndTime() {
            return firstShowEndTime;
        }

        public void setFirstShowEndTime(String firstShowEndTime) {
            this.firstShowEndTime = firstShowEndTime;
        }

        public String getFirstShowStartTime() {
            return firstShowStartTime;
        }

        public void setFirstShowStartTime(String firstShowStartTime) {
            this.firstShowStartTime = firstShowStartTime;
        }

        public String getSecondShowEndTime() {
            return secondShowEndTime;
        }

        public void setSecondShowEndTime(String secondShowEndTime) {
            this.secondShowEndTime = secondShowEndTime;
        }

        public String getSecondShowStartTime() {
            return secondShowStartTime;
        }

        public void setSecondShowStartTime(String secondShowStartTime) {
            this.secondShowStartTime = secondShowStartTime;
        }

        public String getThirdShowEndTime() {
            return thirdShowEndTime;
        }

        public void setThirdShowEndTime(String thirdShowEndTime) {
            this.thirdShowEndTime = thirdShowEndTime;
        }

        public String getThirdShowStartTime() {
            return thirdShowStartTime;
        }

        public void setThirdShowStartTime(String thirdShowStartTime) {
            this.thirdShowStartTime = thirdShowStartTime;
        }
    }
}
