package com.shawnway.nav.app.wtw.module.quotation.wp.wp_detail.wp_chart;

import com.shawnway.nav.app.wtw.bean.QuotationsWPBean;
import com.shawnway.nav.app.wtw.module.quotation.wp.wp_detail.tran.bean.UserAccountBean;
import com.shawnway.nav.app.wtw.net.WPRetrofitClient;
import com.shawnway.nav.app.wtw.tool.SchedulersCompat;

import rx.Observable;

/**
 * Created by Cicinnus on 2016/12/7.
 */

public class WpChartManager {
    public Observable<QuotationsWPBean[]> getData() {
        return WPRetrofitClient
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
        return WPRetrofitClient
                .getInstance()
                .api()
                .getUserAccountBalance(token)
                .compose(SchedulersCompat.<UserAccountBean>applyIoSchedulers());
    }
}
