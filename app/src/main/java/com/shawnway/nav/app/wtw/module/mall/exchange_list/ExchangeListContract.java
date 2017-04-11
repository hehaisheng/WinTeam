package com.shawnway.nav.app.wtw.module.mall.exchange_list;

import com.shawnway.nav.app.wtw.base.ILoadingView;
import com.shawnway.nav.app.wtw.module.mall.bean.MallOrders;

import java.util.List;

/**
 * Created by Cicinnus on 2016/11/29.
 */

public class ExchangeListContract {

    public interface IExchangeListView extends ILoadingView{

        void addExchangeList(MallOrders body);
    }

    public interface IExchangePresenter{

        void getExchangeList(int page);
    }
}
