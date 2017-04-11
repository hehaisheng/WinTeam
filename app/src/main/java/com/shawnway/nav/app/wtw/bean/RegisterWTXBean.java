package com.shawnway.nav.app.wtw.bean;

/**
 * Created by Administrator on 2016/8/22.
 */
public class RegisterWTXBean {
    public String statusCode;
    public String errorCode;
    public String verificationCode;
    public String timestamp;
    public String status;
    public String error;
    public String exception;
    public String message;
    public String path;
    @Override
    public String toString() {
        return "RegisterWTXBean{" +
                "statusCode='" + statusCode + '\'' +
                ", errorCode='" + errorCode + '\'' +
                ", verificationCode='" + verificationCode + '\'' +
                '}';
    }
}
