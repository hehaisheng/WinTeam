package com.shawnway.nav.app.wtw.module.login_register.forget_pwd;

/**
 * Created by Administrator on 2016/8/31.
 * 赢天下忘记密码的bean
 */
public class ForgetCodeResult {
    private String statusCode;
    private String errorCode;
    private String verificationCode;

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }
}
