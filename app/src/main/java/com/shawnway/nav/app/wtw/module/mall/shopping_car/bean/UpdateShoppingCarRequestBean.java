package com.shawnway.nav.app.wtw.module.mall.shopping_car.bean;

import java.util.List;

/**
 * Created by Cicinnus on 2016/11/17.
 */

public class UpdateShoppingCarRequestBean {

    private List<ListBean> list;

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * proId : 10
         * quantity : 5
         */

        private int proId;
        private int quantity;

        public int getProId() {
            return proId;
        }

        public void setProId(int proId) {
            this.proId = proId;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }
    }
}
