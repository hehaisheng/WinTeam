package com.shawnway.nav.app.wtw.module.quotation.jl_wp.jl_wp_detail.jl_wp_trade_history;

import com.shawnway.nav.app.wtw.net.JlWPRetrofitClient;
import com.shawnway.nav.app.wtw.tool.SchedulersCompat;

import rx.Observable;

/**
 * Created by Cicinnus on 2016/12/12.
 */

public class JlWpTradeHistoryManager {


    /**
     * 交易明细
     *
     * @param pageNum 页数
     * @param token   token
     * @return
     */
    public Observable<JlWpTradeHistoryBean> getWoTradeHistory(int pageNum, String token) {
        return JlWPRetrofitClient
                .getInstance()
                .api()
                .getWpTradeHistory(pageNum, 10, token)
                .compose(SchedulersCompat.<JlWpTradeHistoryBean>applyIoSchedulers());

    }

//    /**
//     * 交易统计
//     * @param token token
//     * @return
//     */
//    public Observable<JlWpTradeTotalBean> getWpTotal(String token) {
//        return JlWPRetrofitClient
//                .getInstance()
//                .api()
//                .getWpTradeTotal(token)
//                .compose(SchedulersCompat.<JlWpTradeTotalBean>applyIoSchedulers());
//    }
}
