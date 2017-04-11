package com.shawnway.nav.app.wtw.base;

/**
 * Created by Cicinnus on 2016/12/8.
 */

public class BaseWpResult{
    /**
     * state : 200
     * desc : ok
     * data : null
     */

    private String state;
    private String desc;
    private Object data;


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

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
