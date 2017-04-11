package com.shawnway.nav.app.wtw.module.quotation.wp.wp_detail.wp_trade_history;

import com.shawnway.nav.app.wtw.net.WPRetrofitClient;
import com.shawnway.nav.app.wtw.tool.SchedulersCompat;

import rx.Observable;

/**
 * Created by Cicinnus on 2016/12/12.
 */

public class WpTradeHistoryManager {


    /**
     * 交易明细
     *
     * @param pageNum 页数
     * @param token   token
     * @return
     */
    public Observable<WpTradeHistoryBean> getWoTradeHistory(int pageNum, String token) {
        return WPRetrofitClient
                .getInstance()
                .api()
                .getWpTradeHistory(pageNum, 10, token)
                .compose(SchedulersCompat.<WpTradeHistoryBean>applyIoSchedulers());

    }

    /**
     * 交易统计
     * @param token token
     * @return
     */
    public Observable<WpTradeTotalBean> getWpTotal(String token) {
        return WPRetrofitClient
                .getInstance()
                .api()
                .getWpTradeTotal(token)
                .compose(SchedulersCompat.<WpTradeTotalBean>applyIoSchedulers());
    }
}
