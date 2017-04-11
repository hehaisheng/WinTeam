package com.shawnway.nav.app.wtw.module.quotation.international.positions.today;

import com.shawnway.nav.app.wtw.net.RetrofitClient;
import com.shawnway.nav.app.wtw.tool.SchedulersCompat;

import rx.Observable;

/**
 * Created by Cicinnus on 2016/11/28.
 */

public class TodayTradingManager {

    public Observable getTodayTrading(){
        return RetrofitClient
                .getInstance()
                .api()
                .getTodayTrading()
                .compose(SchedulersCompat.<TodayTradingBean>applyIoSchedulers());
    }
}
