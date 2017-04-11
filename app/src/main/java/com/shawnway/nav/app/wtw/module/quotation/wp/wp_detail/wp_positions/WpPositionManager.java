package com.shawnway.nav.app.wtw.module.quotation.wp.wp_detail.wp_positions;

import com.shawnway.nav.app.wtw.base.BaseWpResult;
import com.shawnway.nav.app.wtw.module.quotation.wp.wp_detail.tran.bean.UserAccountBean;
import com.shawnway.nav.app.wtw.net.WPRetrofitClient;
import com.shawnway.nav.app.wtw.tool.SchedulersCompat;

import rx.Observable;

/**
 * Created by Cicinnus on 2016/12/10.
 */

public class WpPositionManager {

    /**
     * 获取用户持仓
     * @param token token
     * @return
     */
    public Observable<WpPositionsBean> getWpPositions(String token){
        return WPRetrofitClient
                .getInstance()
                .api()
                .getWpPositions(token)
                .compose(SchedulersCompat.<WpPositionsBean>applyIoSchedulers());
    }

    /**
     * 账户资金信息
     * @param token token
     * @return
     */
    public Observable<UserAccountBean> getWpUserAccountBalance(String token){
        return WPRetrofitClient
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
        return WPRetrofitClient
                .getInstance()
                .api()
                .liquidate(orderId,token)
                .compose(SchedulersCompat.<BaseWpResult>applyIoSchedulers());
    }

    public Observable<BaseWpResult> liquidateAll(String token){
        return WPRetrofitClient
                .getInstance()
                .api()
                .liquidateAll(token)
                .compose(SchedulersCompat.<BaseWpResult>applyIoSchedulers());
    }
}
