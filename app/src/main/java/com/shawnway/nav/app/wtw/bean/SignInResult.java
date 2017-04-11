package com.shawnway.nav.app.wtw.bean;

/**
 * Created by Administrator on 2016/10/26.
 */

public class SignInResult {

    /**
     * statusCode : FAIL
     * consecutive : 1
     */

    private String statusCode;
    private int consecutive;

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public int getConsecutive() {
        return consecutive;
    }

    public void setConsecutive(int consecutive) {
        this.consecutive = consecutive;
    }
}
