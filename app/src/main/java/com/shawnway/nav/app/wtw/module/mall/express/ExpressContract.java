package com.shawnway.nav.app.wtw.module.mall.express;

import com.shawnway.nav.app.wtw.base.ILoadingView;
import com.shawnway.nav.app.wtw.base.IPresenter;
import com.shawnway.nav.app.wtw.module.mall.bean.ExpressDeliver;

/**
 * Created by Kevin on 2016/11/24
 */

public class ExpressContract {
    public interface IExpressView extends ILoadingView {
        void addExpressResult(ExpressDeliver expressDeliver);
    }

    public interface IExpressPresenter extends IPresenter {

        void getExpressByOrderId(String orderId);
    }

}
