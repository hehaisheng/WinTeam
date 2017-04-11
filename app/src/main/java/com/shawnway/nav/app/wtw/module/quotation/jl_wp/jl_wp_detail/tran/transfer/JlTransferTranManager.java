package com.shawnway.nav.app.wtw.module.quotation.jl_wp.jl_wp_detail.tran.transfer;

import com.shawnway.nav.app.wtw.base.BaseWpResult;
import com.shawnway.nav.app.wtw.module.quotation.jl_wp.jl_wp_detail.tran.build_tran.JlBuildTranManager;
import com.shawnway.nav.app.wtw.module.quotation.wp.wp_detail.tran.bean.UserAccountBean;
import com.shawnway.nav.app.wtw.module.quotation.wp.wp_detail.tran.bean.WPStateDescWrapper;
import com.shawnway.nav.app.wtw.module.quotation.wp.wp_detail.tran.bean.WithDrawTranBean;
import com.shawnway.nav.app.wtw.net.JlWPRetrofitClient;
import com.shawnway.nav.app.wtw.net.JsonRequestBody;
import com.shawnway.nav.app.wtw.tool.SchedulersCompat;

import org.json.JSONException;
import org.json.JSONObject;

import rx.Observable;

/**
 * Created by Kevin on 2016/12/12
 */

public class JlTransferTranManager {

    /**
     * 获取用户绑定银行卡信息
     *
     * @param access_token
     * @return
     */
    public Observable<JlWithDrawBean> getUserBalanceInfo(String access_token) {
        return JlWPRetrofitClient.getInstance()
                .api()
                .getUserBankInfo(access_token)
                .compose(SchedulersCompat.<JlWithDrawBean>applyIoSchedulers());
    }

    /**
     * 获取用户资金信息(账号余额)
     *
     * @param access_token
     * @return
     */
    public Observable<UserAccountBean> getUserAccountBalance(String access_token) {
        JlBuildTranManager jlBuildTranManager = new JlBuildTranManager();
        return jlBuildTranManager.getUserAccountBalance(access_token);
    }

    /**
     * 微盘提现
     *
     * @param access_token
     * @param withDrawTranBean
     * @return
     */
    public Observable<WPStateDescWrapper> putWithDrawTran(String access_token, WithDrawTranBean withDrawTranBean) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("pwd",withDrawTranBean.getPwd());
            jsonObject.put("cardno", withDrawTranBean.getCardno());
            jsonObject.put("cardusername", withDrawTranBean.getCardusername());
            jsonObject.put("amount", withDrawTranBean.getAmount());
            jsonObject.put("verifyCode",withDrawTranBean.getVerifyCode());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return JlWPRetrofitClient.getInstance()
                .api()
                .putWithdrawTran(access_token,
                        JsonRequestBody.getInstance()
                                .convertJsonContent(jsonObject.toString()))
                .compose(SchedulersCompat.<WPStateDescWrapper>applyIoSchedulers());
    }

    public Observable<BaseWpResult> sendSmsCode(String token){
        return JlWPRetrofitClient
                .getInstance()
                .api()
                .sendWithDrawVerifyCode(token)
                .compose(SchedulersCompat.<BaseWpResult>applyIoSchedulers());
    }
}
