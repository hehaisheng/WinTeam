package com.shawnway.nav.app.wtw.module.user.ticket.wp_ticket;

import java.util.List;

/**
 * Created by Cicinnus on 2016/12/6.
 */

public class WpTicketBean {

    /**
     * state : 200
     * desc : OK
     * data : [{"id":8295,"sum":200,"endDate":1467244800000,"addDate":1466467200000}]
     */

    private String state;
    private String desc;
    private List<DataBean> data;

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 8295
         * sum : 200
         * endDate : 1467244800000
         * addDate : 1466467200000
         */

        private int id;
        private int sum;
        private long endDate;
        private long addDate;

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

        public long getEndDate() {
            return endDate;
        }

        public void setEndDate(long endDate) {
            this.endDate = endDate;
        }

        public long getAddDate() {
            return addDate;
        }

        public void setAddDate(long addDate) {
            this.addDate = addDate;
        }
    }
}
