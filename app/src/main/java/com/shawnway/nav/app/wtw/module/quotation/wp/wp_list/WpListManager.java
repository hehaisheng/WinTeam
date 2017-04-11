package com.shawnway.nav.app.wtw.module.quotation.wp.wp_list;

import com.shawnway.nav.app.wtw.bean.QuotationsWPBean;
import com.shawnway.nav.app.wtw.net.WPRetrofitClient;
import com.shawnway.nav.app.wtw.tool.SchedulersCompat;

import rx.Observable;

/**
 * Created by Cicinnus on 2016/12/23.
 */

public class WpListManager {

    public Observable<QuotationsWPBean[]> getWpList(){
        return WPRetrofitClient
                .getInstance()
                .api()
                .getWPPrice()
                .compose(SchedulersCompat.<QuotationsWPBean[]>applyIoSchedulers());
    }
}
