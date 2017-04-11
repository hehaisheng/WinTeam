package com.shawnway.nav.app.wtw.module.quotation.wp.wp_detail.tran.transfer_tran;

import com.shawnway.nav.app.wtw.module.quotation.wp.wp_detail.tran.bean.UserAccountBean;
import com.shawnway.nav.app.wtw.module.quotation.wp.wp_detail.tran.bean.UserBalanceBean;
import com.shawnway.nav.app.wtw.module.quotation.wp.wp_detail.tran.bean.WPStateDescWrapper;
import com.shawnway.nav.app.wtw.module.quotation.wp.wp_detail.tran.bean.WithDrawTranBean;
import com.shawnway.nav.app.wtw.module.quotation.wp.wp_detail.tran.build_tran.BuildTranManager;
import com.shawnway.nav.app.wtw.net.JsonRequestBody;
import com.shawnway.nav.app.wtw.net.WPRetrofitClient;
import com.shawnway.nav.app.wtw.tool.SchedulersCompat;

import org.json.JSONException;
import org.json.JSONObject;

import rx.Observable;

/**
 * Created by Kevin on 2016/12/12
 */

public class TransferTranManager {

    /**
     * 获取用户绑定银行卡信息
     *
     * @param access_token
     * @return
     */
    public Observable<UserBalanceBean> getUserBalanceInfo(String access_token) {
        return WPRetrofitClient.getInstance()
                .api()
                .getUserBalanceInfo(access_token)
                .compose(SchedulersCompat.<UserBalanceBean>applyIoSchedulers());
    }

    /**
     * 获取用户资金信息(账号余额)
     *
     * @param access_token
     * @return
     */
    public Observable<UserAccountBean> getUserAccountBalance(String access_token) {
        BuildTranManager buildTranManager = new BuildTranManager();
        return buildTranManager.getUserAccountBalance(access_token);
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
            jsonObject.put("bankname", withDrawTranBean.getBankname());
            jsonObject.put("branchname", withDrawTranBean.getBranchname());
            jsonObject.put("cardno", withDrawTranBean.getCardno());
            jsonObject.put("cardusername", withDrawTranBean.getCardusername());
            jsonObject.put("amount", withDrawTranBean.getAmount());
            jsonObject.put("province", withDrawTranBean.getProvince());
            jsonObject.put("city", withDrawTranBean.getCity());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return WPRetrofitClient.getInstance()
                .api()
                .putWithdrawTran(access_token,
                        JsonRequestBody.getInstance()
                                .convertJsonContent(jsonObject.toString()))
                .compose(SchedulersCompat.<WPStateDescWrapper>applyIoSchedulers());
    }
}
