package com.shawnway.nav.app.wtw.module.quotation.jl_wp.jl_wp_detail.jl_wp_positions;

import com.shawnway.nav.app.wtw.base.BaseWpResult;
import com.shawnway.nav.app.wtw.module.quotation.wp.wp_detail.tran.bean.UserAccountBean;
import com.shawnway.nav.app.wtw.net.JlWPRetrofitClient;
import com.shawnway.nav.app.wtw.tool.SchedulersCompat;

import rx.Observable;

/**
 * Created by Cicinnus on 2016/12/10.
 */

public class JlWpPositionManager {

    /**
     * 获取用户持仓
     * @param token token
     * @return
     */
    public Observable<JlWpPositionsBean> getWpPositions(String token){
        return JlWPRetrofitClient
                .getInstance()
                .api()
                .getWpPositions(token)
                .compose(SchedulersCompat.<JlWpPositionsBean>applyIoSchedulers());
    }

    /**
     * 账户资金信息
     * @param token token
     * @return
     */
    public Observable<UserAccountBean> getWpUserAccountBalance(String token){
        return JlWPRetrofitClient
                .getInstance()
                .api()
                .getUserAccountBalance(token)
                .compose(SchedulersCompat.<UserAccountBean>applyIoSchedulers());
    }

    /**
     * 平仓
     * @param orderId orderId
     * @param token token
     * @return
     */
    public Observable<BaseWpResult> liquidate(String orderId,String token){
        return JlWPRetrofitClient
                .getInstance()
                .api()
                .liquidate(orderId,token)
                .compose(SchedulersCompat.<BaseWpResult>applyIoSchedulers());
    }

    /**
     * 一键平仓
     * @param token
     * @return
     */
    public Observable<BaseWpResult> liquidateAll(String token){
        return JlWPRetrofitClient
                .getInstance()
                .api()
                .liquidateAll(token)
                .compose(SchedulersCompat.<BaseWpResult>applyIoSchedulers());
    }
}
