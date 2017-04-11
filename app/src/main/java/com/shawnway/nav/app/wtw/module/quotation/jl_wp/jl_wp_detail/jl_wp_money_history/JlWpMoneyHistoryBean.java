package com.shawnway.nav.app.wtw.module.quotation.jl_wp.jl_wp_detail.jl_wp_money_history;

import java.util.List;

/**
 * Created by Cicinnus on 2016/12/11.
 */

public class JlWpMoneyHistoryBean {

    /**
     * state : 200
     * desc : OK
     * data : {"page":{"pageno":1,"pagesize":20,"recordcount":1,"range":[1,1],"pagecount":1,"nextpage":1,"prevpage":1,"pageitems":[1],"lastpage":true},"list":[{"id":721251,"order_no":"161209162318695859","pay":null,"income":4.65,"balanceAfter":4.65,"createTime":1481276163000,"type":2,"remark":"平仓操作入金4.65"}]}
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
         * page : {"pageno":1,"pagesize":20,"recordcount":1,"range":[1,1],"pagecount":1,"nextpage":1,"prevpage":1,"pageitems":[1],"lastpage":true}
         * list : [{"id":721251,"order_no":"161209162318695859","pay":null,"income":4.65,"balanceAfter":4.65,"createTime":1481276163000,"type":2,"remark":"平仓操作入金4.65"}]
         */

        private PageBean page;
        private List<ListBean> list;

        public PageBean getPage() {
            return page;
        }

        public void setPage(PageBean page) {
            this.page = page;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class PageBean {
            /**
             * pageno : 1
             * pagesize : 20
             * recordcount : 1
             * range : [1,1]
             * pagecount : 1
             * nextpage : 1
             * prevpage : 1
             * pageitems : [1]
             * lastpage : true
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

        public static class ListBean {
            /**
             * id : 721251
             * order_no : 161209162318695859
             * pay : null
             * income : 4.65
             * balanceAfter : 4.65
             * createTime : 1481276163000
             * type : 2
             * remark : 平仓操作入金4.65
             */

            private int id;
            private String order_no;
            private Double pay;
            private Double income;
            private double balanceAfter;
            private long createTime;
            private int type;
            private String remark;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getOrder_no() {
                return order_no;
            }

            public void setOrder_no(String order_no) {
                this.order_no = order_no;
            }

            public Double getPay() {
                return pay;
            }

            public void setPay(double pay) {
                this.pay = pay;
            }

            public Double getIncome() {
                return income;
            }

            public void setIncome(double income) {
                this.income = income;
            }

            public double getBalanceAfter() {
                return balanceAfter;
            }

            public void setBalanceAfter(double balanceAfter) {
                this.balanceAfter = balanceAfter;
            }

            public long getCreateTime() {
                return createTime;
            }

            public void setCreateTime(long createTime) {
                this.createTime = createTime;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public String getRemark() {
                return remark;
            }

            public void setRemark(String remark) {
                this.remark = remark;
            }
        }
    }
}
