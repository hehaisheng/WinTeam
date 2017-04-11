package com.shawnway.nav.app.wtw.module.quotation.wp.wp_detail.tran.bean;

/**
 * Created by Kevin on 2016/12/12
 * 微盘提现bean
 */

public class WithDrawTranBean {

    /**
     * bankname : 中国银行
     * province : 广东
     * city : 广州
     * branchname :  前 进 支 行
     * cardno : 12545685545213232
     * cardusername : Allen Wong
     * amount : 3
     */

    private String bankname;
    private String province;
    private String city;
    private String branchname;
    private String cardno;
    private String cardusername;
    private String amount;
    private String pwd;
    private String verifyCode;

    public String getBankname() {
        return bankname;
    }

    public void setBankname(String bankname) {
        this.bankname = bankname;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getBranchname() {
        return branchname;
    }

    public void setBranchname(String branchname) {
        this.branchname = branchname;
    }

    public String getCardno() {
        return cardno;
    }

    public void setCardno(String cardno) {
        this.cardno = cardno;
    }

    public String getCardusername() {
        return cardusername;
    }

    public void setCardusername(String cardusername) {
        this.cardusername = cardusername;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }
}
