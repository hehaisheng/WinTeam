package com.shawnway.nav.app.wtw.module.quotation.jl_wp.jl_wp_detail.jl_wp_chart;

import com.shawnway.nav.app.wtw.bean.QuotationsWPBean;
import com.shawnway.nav.app.wtw.module.quotation.wp.wp_detail.tran.bean.UserAccountBean;
import com.shawnway.nav.app.wtw.net.JlWPRetrofitClient;
import com.shawnway.nav.app.wtw.tool.SchedulersCompat;

import rx.Observable;

/**
 * Created by Cicinnus on 2016/12/7.
 */

public class JlWpChartManager {
    public Observable<QuotationsWPBean[]> getData() {
        return JlWPRetrofitClient
                .getInstance()
                .api()
                .getWPPrice()
                .compose(SchedulersCompat.<QuotationsWPBean[]>applyIoSchedulers());
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
}
