package com.shawnway.nav.app.wtw.bean;

/**
 * Created by Cicinnus on 2016/12/8.
 */

public class WpInfoResult {

    /**
     * name : null
     * nickname : null
     * mobile : 15521214395
     * password : 123456
     * state : 200
     * desc : success
     */

    private Object name;
    private Object nickname;
    private String mobile;
    private String password;
    private String state;
    private String desc;
    private boolean updated;

    public Object getName() {
        return name;
    }

    public void setName(Object name) {
        this.name = name;
    }

    public Object getNickname() {
        return nickname;
    }

    public void setNickname(Object nickname) {
        this.nickname = nickname;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

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

    public boolean isUpdated() {
        return updated;
    }

    public void setUpdated(boolean updated) {
        this.updated = updated;
    }
}
