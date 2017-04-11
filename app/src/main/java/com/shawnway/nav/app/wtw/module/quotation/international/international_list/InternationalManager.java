package com.shawnway.nav.app.wtw.module.quotation.international.international_list;

import com.shawnway.nav.app.wtw.module.quotation.international.InternationalListBean;
import com.shawnway.nav.app.wtw.net.RetrofitClient;
import com.shawnway.nav.app.wtw.tool.SchedulersCompat;

import rx.Observable;

/**
 * Created by Cicinnus on 2016/12/5.
 */

public class InternationalManager {

    public Observable<InternationalListBean> getInternationalList() {
        return RetrofitClient
                .getInstance()
                .api()
                .getInternationalList()
                .compose(SchedulersCompat.<InternationalListBean>applyIoSchedulers());
    }
}
