package com.shawnway.nav.app.wtw.module.quotation.jl_wp.jl_wp_detail.jl_wp_money_history;

import com.shawnway.nav.app.wtw.net.JlWPRetrofitClient;
import com.shawnway.nav.app.wtw.tool.SchedulersCompat;

import rx.Observable;

/**
 * Created by Cicinnus on 2016/12/11.
 */

public class JlWpMoneyHistoryManager {

    public Observable<JlWpMoneyHistoryBean> getWpMoneyHistory(int page, String token){
        return JlWPRetrofitClient
                .getInstance()
                .api()
                .getMoneyHistory(page,token)
                .compose(SchedulersCompat.<JlWpMoneyHistoryBean>applyIoSchedulers());
    }
}
