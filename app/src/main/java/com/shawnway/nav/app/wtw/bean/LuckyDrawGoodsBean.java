package com.shawnway.nav.app.wtw.bean;

import java.io.Serializable;
import java.util.List;



public class LuckyDrawGoodsBean {

    /**
     * id : 1
     * desc : iphone7
     * name : iphone7
     * price : 0
     */

    private List<LotteryRaffleBean> lotteryRaffle;

    public List<LotteryRaffleBean> getLotteryRaffle() {
        return lotteryRaffle;
    }

    public void setLotteryRaffle(List<LotteryRaffleBean> lotteryRaffle) {
        this.lotteryRaffle = lotteryRaffle;
    }

    public static class LotteryRaffleBean implements Serializable {
        private int id;
        private String desc;
        private String name;
        private int price;
        private String imgurl;

        public String getImgurl() {
            return imgurl;
        }

        public void setImgurl(String imgurl) {
            this.imgurl = imgurl;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }
    }
}
