package com.shawnway.nav.app.wtw.module.quotation.jl_wp.jl_wp_list;

import com.shawnway.nav.app.wtw.bean.QuotationsWPBean;
import com.shawnway.nav.app.wtw.net.JlWPRetrofitClient;
import com.shawnway.nav.app.wtw.tool.SchedulersCompat;

import rx.Observable;

/**
 * Created by Cicinnus on 2016/12/23.
 */

public class JlWpListManager {

    public Observable<QuotationsWPBean[]> getWpList(){
        return JlWPRetrofitClient
                .getInstance()
                .api()
                .getWPPrice()
                .compose(SchedulersCompat.<QuotationsWPBean[]>applyIoSchedulers());
    }
}
