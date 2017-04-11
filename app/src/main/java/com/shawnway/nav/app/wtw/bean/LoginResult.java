package com.shawnway.nav.app.wtw.bean;

/**
 * Created by Administrator on 2016/10/21.
 * 登录
 */

public class LoginResult{

    /**
     * errorCode : ACCESS_FAILED
     */

    private String errorCode;
    private String statusCode;

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }
}
