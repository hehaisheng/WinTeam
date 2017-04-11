package com.shawnway.nav.app.wtw.module.mall.shopping_car.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by Cicinnus on 2016/11/16.
 */

public class ShoppingCarListBean implements Parcelable{

    private List<ShoppingEntityListBean> shoppingEntityList;

    public List<ShoppingEntityListBean> getShoppingEntityList() {
        return shoppingEntityList;
    }

    public void setShoppingEntityList(List<ShoppingEntityListBean> shoppingEntityList) {
        this.shoppingEntityList = shoppingEntityList;
    }

    public static class ShoppingEntityListBean implements Parcelable{
        /**
         * proId : 10
         * proName : 企鹅
         * proDiscCsptPoint : 500
         * img : http://120.76.153.154:9088/commom/file/3db212d3bike.png
         * proStatus : 0
         * quantity : 5
         * desc : null
         */

        private int proId;
        private String proName;
        private int proDiscCsptPoint;
        private String img;
        private int proStatus;
        private int quantity;
        private String desc;
        private boolean isChecked;

        public int getProId() {
            return proId;
        }

        public void setProId(int proId) {
            this.proId = proId;
        }

        public String getProName() {
            return proName;
        }

        public void setProName(String proName) {
            this.proName = proName;
        }

        public int getProDiscCsptPoint() {
            return proDiscCsptPoint;
        }

        public void setProDiscCsptPoint(int proDiscCsptPoint) {
            this.proDiscCsptPoint = proDiscCsptPoint;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public int getProStatus() {
            return proStatus;
        }

        public void setProStatus(int proStatus) {
            this.proStatus = proStatus;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public boolean isChecked() {
            return isChecked;
        }

        public void setChecked(boolean checked) {
            isChecked = checked;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.proId);
            dest.writeString(this.proName);
            dest.writeInt(this.proDiscCsptPoint);
            dest.writeString(this.img);
            dest.writeInt(this.proStatus);
            dest.writeInt(this.quantity);
            dest.writeString(this.desc);
            dest.writeByte(this.isChecked ? (byte) 1 : (byte) 0);
        }

        public ShoppingEntityListBean() {
        }

        protected ShoppingEntityListBean(Parcel in) {
            this.proId = in.readInt();
            this.proName = in.readString();
            this.proDiscCsptPoint = in.readInt();
            this.img = in.readString();
            this.proStatus = in.readInt();
            this.quantity = in.readInt();
            this.desc = in.readString();
            this.isChecked = in.readByte() != 0;
        }

        public static final Creator<ShoppingEntityListBean> CREATOR = new Creator<ShoppingEntityListBean>() {
            @Override
            public ShoppingEntityListBean createFromParcel(Parcel source) {
                return new ShoppingEntityListBean(source);
            }

            @Override
            public ShoppingEntityListBean[] newArray(int size) {
                return new ShoppingEntityListBean[size];
            }
        };
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.shoppingEntityList);
    }

    public ShoppingCarListBean() {
    }

    protected ShoppingCarListBean(Parcel in) {
        this.shoppingEntityList = in.createTypedArrayList(ShoppingEntityListBean.CREATOR);
    }

    public static final Creator<ShoppingCarListBean> CREATOR = new Creator<ShoppingCarListBean>() {
        @Override
        public ShoppingCarListBean createFromParcel(Parcel source) {
            return new ShoppingCarListBean(source);
        }

        @Override
        public ShoppingCarListBean[] newArray(int size) {
            return new ShoppingCarListBean[size];
        }
    };
}
