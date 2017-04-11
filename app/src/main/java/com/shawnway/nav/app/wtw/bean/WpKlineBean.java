package com.shawnway.nav.app.wtw.bean;

import java.util.List;

public class WpKlineBean {


    private List<String> time;
    private List<List<String>> data;

    public List<String> getTime() {
        return time;
    }

    public void setTime(List<String> time) {
        this.time = time;
    }

    public List<List<String>> getData() {
        return data;
    }

    public void setData(List<List<String>> data) {
        this.data = data;
    }
}
