package com.shawnway.nav.app.wtw.module.quotation.wp.wp_detail.wp_money_history;

import com.shawnway.nav.app.wtw.net.WPRetrofitClient;
import com.shawnway.nav.app.wtw.tool.SchedulersCompat;

import rx.Observable;

/**
 * Created by Cicinnus on 2016/12/11.
 */

public class WpMoneyHistoryManager {

    public Observable<WpMoneyHistoryBean> getWpMoneyHistory(int page,String token){
        return WPRetrofitClient
                .getInstance()
                .api()
                .getMoneyHistory(page,token)
                .compose(SchedulersCompat.<WpMoneyHistoryBean>applyIoSchedulers());
    }
}
