package com.shawnway.nav.app.wtw.module.mall.order;

import java.util.List;

/**
 * Created by Cicinnus on 2016/11/17.
 */

public class ExChangeProductRequestBean {


    /**
     * productOrderList : [{"proId":"25","amount":"10"},{"proId":"59","amount":"1"}]
     * shoppingCart : 1
     * remark : 发顺丰
     */

    private int shoppingCart;
    private String remark;
    private List<ProductOrderListBean> productOrderList;

    public int getShoppingCart() {
        return shoppingCart;
    }

    public void setShoppingCart(int shoppingCart) {
        this.shoppingCart = shoppingCart;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public List<ProductOrderListBean> getProductOrderList() {
        return productOrderList;
    }

    public void setProductOrderList(List<ProductOrderListBean> productOrderList) {
        this.productOrderList = productOrderList;
    }

    public static class ProductOrderListBean {
        /**
         * proId : 25
         * amount : 10
         */

        private String proId;
        private String amount;

        public String getProId() {
            return proId;
        }

        public void setProId(String proId) {
            this.proId = proId;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }
    }
}
