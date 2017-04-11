package com.shawnway.nav.app.wtw.module.user.ticket.jl_wp_ticket;

import java.util.List;

/**
 * Created by Cicinnus on 2016/12/6.
 */

public class JlWpTicketBean {


    /**
     * state : 200
     * desc : OK
     * data : {"range":[1,10],"page":{"pageno":1,"pagesize":10,"recordcount":20,"range":[1,10],"pagecount":2,"nextpage":2,"prevpage":1,"pageitems":[1,2],"lastpage":false},"recordcount":20,"pageno":1,"tickets":[{"validity":null,"id":5903,"sum":10,"endDate":"2017-01-24","addDate":1483691126000,"state":1,"useDate":null,"comment":null,"activityId":0,"ownerId":763,"sharerId":null,"usedInOrderId":null,"orgId":99,"memberId":92},{"validity":null,"id":5904,"sum":10,"endDate":"2017-01-24","addDate":1483691126000,"state":1,"useDate":null,"comment":null,"activityId":0,"ownerId":763,"sharerId":null,"usedInOrderId":null,"orgId":99,"memberId":92},{"validity":null,"id":5905,"sum":10,"endDate":"2017-01-24","addDate":1483691126000,"state":1,"useDate":null,"comment":null,"activityId":0,"ownerId":763,"sharerId":null,"usedInOrderId":null,"orgId":99,"memberId":92},{"validity":null,"id":5906,"sum":10,"endDate":"2017-01-24","addDate":1483691126000,"state":1,"useDate":null,"comment":null,"activityId":0,"ownerId":763,"sharerId":null,"usedInOrderId":null,"orgId":99,"memberId":92},{"validity":null,"id":5907,"sum":10,"endDate":"2017-01-24","addDate":1483691126000,"state":1,"useDate":null,"comment":null,"activityId":0,"ownerId":763,"sharerId":null,"usedInOrderId":null,"orgId":99,"memberId":92},{"validity":null,"id":5908,"sum":10,"endDate":"2017-01-24","addDate":1483691126000,"state":1,"useDate":null,"comment":null,"activityId":0,"ownerId":763,"sharerId":null,"usedInOrderId":null,"orgId":99,"memberId":92},{"validity":null,"id":5909,"sum":10,"endDate":"2017-01-24","addDate":1483691126000,"state":1,"useDate":null,"comment":null,"activityId":0,"ownerId":763,"sharerId":null,"usedInOrderId":null,"orgId":99,"memberId":92},{"validity":null,"id":5910,"sum":10,"endDate":"2017-01-24","addDate":1483691126000,"state":1,"useDate":null,"comment":null,"activityId":0,"ownerId":763,"sharerId":null,"usedInOrderId":null,"orgId":99,"memberId":92},{"validity":null,"id":5911,"sum":10,"endDate":"2017-01-24","addDate":1483691126000,"state":1,"useDate":null,"comment":null,"activityId":0,"ownerId":763,"sharerId":null,"usedInOrderId":null,"orgId":99,"memberId":92},{"validity":null,"id":5912,"sum":10,"endDate":"2017-01-24","addDate":1483691126000,"state":1,"useDate":null,"comment":null,"activityId":0,"ownerId":763,"sharerId":null,"usedInOrderId":null,"orgId":99,"memberId":92}]}
     * errorCode :
     */

    private String state;
    private String desc;
    private DataBean data;
    private String errorCode;

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

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public static class DataBean {
        /**
         * range : [1,10]
         * page : {"pageno":1,"pagesize":10,"recordcount":20,"range":[1,10],"pagecount":2,"nextpage":2,"prevpage":1,"pageitems":[1,2],"lastpage":false}
         * recordcount : 20
         * pageno : 1
         * tickets : [{"validity":null,"id":5903,"sum":10,"endDate":"2017-01-24","addDate":1483691126000,"state":1,"useDate":null,"comment":null,"activityId":0,"ownerId":763,"sharerId":null,"usedInOrderId":null,"orgId":99,"memberId":92},{"validity":null,"id":5904,"sum":10,"endDate":"2017-01-24","addDate":1483691126000,"state":1,"useDate":null,"comment":null,"activityId":0,"ownerId":763,"sharerId":null,"usedInOrderId":null,"orgId":99,"memberId":92},{"validity":null,"id":5905,"sum":10,"endDate":"2017-01-24","addDate":1483691126000,"state":1,"useDate":null,"comment":null,"activityId":0,"ownerId":763,"sharerId":null,"usedInOrderId":null,"orgId":99,"memberId":92},{"validity":null,"id":5906,"sum":10,"endDate":"2017-01-24","addDate":1483691126000,"state":1,"useDate":null,"comment":null,"activityId":0,"ownerId":763,"sharerId":null,"usedInOrderId":null,"orgId":99,"memberId":92},{"validity":null,"id":5907,"sum":10,"endDate":"2017-01-24","addDate":1483691126000,"state":1,"useDate":null,"comment":null,"activityId":0,"ownerId":763,"sharerId":null,"usedInOrderId":null,"orgId":99,"memberId":92},{"validity":null,"id":5908,"sum":10,"endDate":"2017-01-24","addDate":1483691126000,"state":1,"useDate":null,"comment":null,"activityId":0,"ownerId":763,"sharerId":null,"usedInOrderId":null,"orgId":99,"memberId":92},{"validity":null,"id":5909,"sum":10,"endDate":"2017-01-24","addDate":1483691126000,"state":1,"useDate":null,"comment":null,"activityId":0,"ownerId":763,"sharerId":null,"usedInOrderId":null,"orgId":99,"memberId":92},{"validity":null,"id":5910,"sum":10,"endDate":"2017-01-24","addDate":1483691126000,"state":1,"useDate":null,"comment":null,"activityId":0,"ownerId":763,"sharerId":null,"usedInOrderId":null,"orgId":99,"memberId":92},{"validity":null,"id":5911,"sum":10,"endDate":"2017-01-24","addDate":1483691126000,"state":1,"useDate":null,"comment":null,"activityId":0,"ownerId":763,"sharerId":null,"usedInOrderId":null,"orgId":99,"memberId":92},{"validity":null,"id":5912,"sum":10,"endDate":"2017-01-24","addDate":1483691126000,"state":1,"useDate":null,"comment":null,"activityId":0,"ownerId":763,"sharerId":null,"usedInOrderId":null,"orgId":99,"memberId":92}]
         */

        private PageBean page;
        private int recordcount;
        private int pageno;
        private List<Integer> range;
        private List<TicketsBean> tickets;

        public PageBean getPage() {
            return page;
        }

        public void setPage(PageBean page) {
            this.page = page;
        }

        public int getRecordcount() {
            return recordcount;
        }

        public void setRecordcount(int recordcount) {
            this.recordcount = recordcount;
        }

        public int getPageno() {
            return pageno;
        }

        public void setPageno(int pageno) {
            this.pageno = pageno;
        }

        public List<Integer> getRange() {
            return range;
        }

        public void setRange(List<Integer> range) {
            this.range = range;
        }

        public List<TicketsBean> getTickets() {
            return tickets;
        }

        public void setTickets(List<TicketsBean> tickets) {
            this.tickets = tickets;
        }

        public static class PageBean {
            /**
             * pageno : 1
             * pagesize : 10
             * recordcount : 20
             * range : [1,10]
             * pagecount : 2
             * nextpage : 2
             * prevpage : 1
             * pageitems : [1,2]
             * lastpage : false
             */

            private int pageno;
            private int pagesize;
            private int recordcount;
            private int pagecount;
            private int nextpage;
            private int prevpage;
            private boolean lastpage;
            private List<Integer> range;
            private List<Integer> pageitems;

            public int getPageno() {
                return pageno;
            }

            public void setPageno(int pageno) {
                this.pageno = pageno;
            }

            public int getPagesize() {
                return pagesize;
            }

            public void setPagesize(int pagesize) {
                this.pagesize = pagesize;
            }

            public int getRecordcount() {
                return recordcount;
            }

            public void setRecordcount(int recordcount) {
                this.recordcount = recordcount;
            }

            public int getPagecount() {
                return pagecount;
            }

            public void setPagecount(int pagecount) {
                this.pagecount = pagecount;
            }

            public int getNextpage() {
                return nextpage;
            }

            public void setNextpage(int nextpage) {
                this.nextpage = nextpage;
            }

            public int getPrevpage() {
                return prevpage;
            }

            public void setPrevpage(int prevpage) {
                this.prevpage = prevpage;
            }

            public boolean isLastpage() {
                return lastpage;
            }

            public void setLastpage(boolean lastpage) {
                this.lastpage = lastpage;
            }

            public List<Integer> getRange() {
                return range;
            }

            public void setRange(List<Integer> range) {
                this.range = range;
            }

            public List<Integer> getPageitems() {
                return pageitems;
            }

            public void setPageitems(List<Integer> pageitems) {
                this.pageitems = pageitems;
            }
        }

        public static class TicketsBean {
            /**
             * validity : null
             * id : 5903
             * sum : 10
             * endDate : 2017-01-24
             * addDate : 1483691126000
             * state : 1
             * useDate : null
             * comment : null
             * activityId : 0
             * ownerId : 763
             * sharerId : null
             * usedInOrderId : null
             * orgId : 99
             * memberId : 92
             */

            private Object validity;
            private int id;
            private int sum;
            private String endDate;
            private long addDate;
            private int state;
            private Object useDate;
            private Object comment;
            private int activityId;
            private int ownerId;
            private Object sharerId;
            private Object usedInOrderId;
            private int orgId;
            private int memberId;

            public Object getValidity() {
                return validity;
            }

            public void setValidity(Object validity) {
                this.validity = validity;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getSum() {
                return sum;
            }

            public void setSum(int sum) {
                this.sum = sum;
            }

            public String getEndDate() {
                return endDate;
            }

            public void setEndDate(String endDate) {
                this.endDate = endDate;
            }

            public long getAddDate() {
                return addDate;
            }

            public void setAddDate(long addDate) {
                this.addDate = addDate;
            }

            public int getState() {
                return state;
            }

            public void setState(int state) {
                this.state = state;
            }

            public Object getUseDate() {
                return useDate;
            }

            public void setUseDate(Object useDate) {
                this.useDate = useDate;
            }

            public Object getComment() {
                return comment;
            }

            public void setComment(Object comment) {
                this.comment = comment;
            }

            public int getActivityId() {
                return activityId;
            }

            public void setActivityId(int activityId) {
                this.activityId = activityId;
            }

            public int getOwnerId() {
                return ownerId;
            }

            public void setOwnerId(int ownerId) {
                this.ownerId = ownerId;
            }

            public Object getSharerId() {
                return sharerId;
            }

            public void setSharerId(Object sharerId) {
                this.sharerId = sharerId;
            }

            public Object getUsedInOrderId() {
                return usedInOrderId;
            }

            public void setUsedInOrderId(Object usedInOrderId) {
                this.usedInOrderId = usedInOrderId;
            }

            public int getOrgId() {
                return orgId;
            }

            public void setOrgId(int orgId) {
                this.orgId = orgId;
            }

            public int getMemberId() {
                return memberId;
            }

            public void setMemberId(int memberId) {
                this.memberId = memberId;
            }
        }
    }
}
