package com.shawnway.nav.app.wtw.module.mall.bean;

import java.util.List;

/**
 * Created by Kevin on 2016/11/17
 */

public class MallOrders {
    private int pageNo;
    private int pageSize;
    private int tradeStatus;
    private int signStatus;
    private String desc;
    private String statusCode;
    private int position;
    private List<ProductOrderTransactionsBean> productOrderTransactions;

    public int getSignStatus() {
        return signStatus;
    }

    public void setSignStatus(int signStatus) {
        this.signStatus = signStatus;
    }

    public int getTradeStatus() {
        return tradeStatus;
    }

    public void setTradeStatus(int tradeStatus) {
        this.tradeStatus = tradeStatus;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public List<ProductOrderTransactionsBean> getProductOrderTransactions() {
        return productOrderTransactions;
    }

    public void setProductOrderTransactions(List<ProductOrderTransactionsBean> productOrderTransactions) {
        this.productOrderTransactions = productOrderTransactions;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }


    public static class ProductOrderTransactionsBean {
        /**
         * orderDate : 1479464856000
         * status : 0
         * desc : 订单等待发货中
         * quantity : 1
         * point : 500
         * id : 175
         * orderId : 9E5E8166C9727772
         * expressNumber : null
         * expressCompany : null
         * productOrderEntities : [{"proId":20,"proQuantity":1,"proDiscCsptPoint":500,"proImg":"http://120.76.153.154:9088/commom/file/efc22ab1bike.png","proName":"ddddd25"}]
         */

        private long orderDate;
        private int status;
        private String desc;
        private int quantity;
        private int point;
        private String id;
        private String orderId;
        private Object expressNumber;
        private Object expressCompany;
        private List<ProductOrderEntitiesBean> productOrderEntities;

        public long getOrderDate() {
            return orderDate;
        }

        public void setOrderDate(long orderDate) {
            this.orderDate = orderDate;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }

        public int getPoint() {
            return point;
        }

        public void setPoint(int point) {
            this.point = point;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }

        public Object getExpressNumber() {
            return expressNumber;
        }

        public void setExpressNumber(Object expressNumber) {
            this.expressNumber = expressNumber;
        }

        public Object getExpressCompany() {
            return expressCompany;
        }

        public void setExpressCompany(Object expressCompany) {
            this.expressCompany = expressCompany;
        }

        public List<ProductOrderEntitiesBean> getProductOrderEntities() {
            return productOrderEntities;
        }

        public void setProductOrderEntities(List<ProductOrderEntitiesBean> productOrderEntities) {
            this.productOrderEntities = productOrderEntities;
        }

        public static class ProductOrderEntitiesBean {
            /**
             * proId : 20
             * proQuantity : 1
             * proDiscCsptPoint : 500
             * proImg : http://120.76.153.154:9088/commom/file/efc22ab1bike.png
             * proName : ddddd25
             */

            private int proId;
            private int proQuantity;
            private int proDiscCsptPoint;
            private String proImg;
            private String proName;
            private long orderDate;

            public int getProId() {
                return proId;
            }

            public void setProId(int proId) {
                this.proId = proId;
            }

            public int getProQuantity() {
                return proQuantity;
            }

            public void setProQuantity(int proQuantity) {
                this.proQuantity = proQuantity;
            }

            public int getProDiscCsptPoint() {
                return proDiscCsptPoint;
            }

            public void setProDiscCsptPoint(int proDiscCsptPoint) {
                this.proDiscCsptPoint = proDiscCsptPoint;
            }

            public String getProImg() {
                return proImg;
            }

            public void setProImg(String proImg) {
                this.proImg = proImg;
            }

            public String getProName() {
                return proName;
            }

            public void setProName(String proName) {
                this.proName = proName;
            }

            public long getOrderDate() {
                return orderDate;
            }

            public void setOrderDate(long orderDate) {
                this.orderDate = orderDate;
            }
        }
    }
}
