package com.shawnway.nav.app.wtw.bean;

import java.util.Objects;

/**
 * Created by Administrator on 2016/7/27 0027.
 */
public class SendSmsCodeResult {
    private String state;
    private String desc;
    private Objects data;

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

    public Objects getData() {
        return data;
    }

    public void setData(Objects data) {
        this.data = data;
    }
}
