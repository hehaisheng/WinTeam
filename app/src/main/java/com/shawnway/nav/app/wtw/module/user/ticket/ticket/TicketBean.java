package com.shawnway.nav.app.wtw.module.user.ticket.ticket;

import java.util.List;

/**
 * Created by Cicinnus on 2016/12/19.
 */

public class TicketBean {

    private List<ListBean> list;

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * id : 15
         * agentId : 1
         * comment : null
         * couponDesc : 现金券200元
         * couponId : 5ECE5B8CDE574D2B0D3DF0
         * couponSum : 200
         * couponType : 3
         * createBy : 1
         * createDate : 1482136609000
         * endDate : 2016-12-27
         * loginId : 9335
         * loginName : 15521322713
         * startDate : 2016-12-19
         * status : 1
         * useDate : null
         * usedInOrderId : null
         */

        private int id;
        private int agentId;
        private Object comment;
        private String couponDesc;
        private String couponId;
        private int couponSum;
        private int couponType;
        private int createBy;
        private long createDate;
        private String endDate;
        private int loginId;
        private String loginName;
        private String startDate;
        private int status;
        private Object useDate;
        private Object usedInOrderId;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getAgentId() {
            return agentId;
        }

        public void setAgentId(int agentId) {
            this.agentId = agentId;
        }

        public Object getComment() {
            return comment;
        }

        public void setComment(Object comment) {
            this.comment = comment;
        }

        public String getCouponDesc() {
            return couponDesc;
        }

        public void setCouponDesc(String couponDesc) {
            this.couponDesc = couponDesc;
        }

        public String getCouponId() {
            return couponId;
        }

        public void setCouponId(String couponId) {
            this.couponId = couponId;
        }

        public int getCouponSum() {
            return couponSum;
        }

        public void setCouponSum(int couponSum) {
            this.couponSum = couponSum;
        }

        public int getCouponType() {
            return couponType;
        }

        public void setCouponType(int couponType) {
            this.couponType = couponType;
        }

        public int getCreateBy() {
            return createBy;
        }

        public void setCreateBy(int createBy) {
            this.createBy = createBy;
        }

        public long getCreateDate() {
            return createDate;
        }

        public void setCreateDate(long createDate) {
            this.createDate = createDate;
        }

        public String getEndDate() {
            return endDate;
        }

        public void setEndDate(String endDate) {
            this.endDate = endDate;
        }

        public int getLoginId() {
            return loginId;
        }

        public void setLoginId(int loginId) {
            this.loginId = loginId;
        }

        public String getLoginName() {
            return loginName;
        }

        public void setLoginName(String loginName) {
            this.loginName = loginName;
        }

        public String getStartDate() {
            return startDate;
        }

        public void setStartDate(String startDate) {
            this.startDate = startDate;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public Object getUseDate() {
            return useDate;
        }

        public void setUseDate(Object useDate) {
            this.useDate = useDate;
        }

        public Object getUsedInOrderId() {
            return usedInOrderId;
        }

        public void setUsedInOrderId(Object usedInOrderId) {
            this.usedInOrderId = usedInOrderId;
        }
    }
}
