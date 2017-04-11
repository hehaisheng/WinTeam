package com.shawnway.nav.app.wtw.module.mall.filter.bean;

import java.util.List;

/**
 * Created by Cicinnus on 2016/11/9.
 */

public class FilterListBean {

    private List<AllProductTypeBean> allProductType;

    public List<AllProductTypeBean> getAllProductType() {
        return allProductType;
    }

    public void setAllProductType(List<AllProductTypeBean> allProductType) {
        this.allProductType = allProductType;
    }

    public static class AllProductTypeBean {
        /**
         * typeId : 2
         * agentId : 1
         * typeName : 食品饮料
         */
        public boolean isSelect;
        private int typeId;
        private int agentId;
        private String typeName;

        public int getTypeId() {
            return typeId;
        }

        public void setTypeId(int typeId) {
            this.typeId = typeId;
        }

        public int getAgentId() {
            return agentId;
        }

        public void setAgentId(int agentId) {
            this.agentId = agentId;
        }

        public String getTypeName() {
            return typeName;
        }

        public void setTypeName(String typeName) {
            this.typeName = typeName;
        }
    }
}
