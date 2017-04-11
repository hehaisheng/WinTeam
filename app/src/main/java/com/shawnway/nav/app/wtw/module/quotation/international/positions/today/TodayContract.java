package com.shawnway.nav.app.wtw.module.quotation.international.positions.today;

import com.shawnway.nav.app.wtw.base.ILoadingView;

import java.util.List;

/**
 * Created by Cicinnus on 2016/11/28.
 */

public class TodayContract {

    public interface ITodayTradingView extends ILoadingView {
        void addTodayTrading(List<TodayTradingBean.WareHouseInfosBean> wareHouseInfos);
    }

    public interface ITodayTradingPresenter {
        void getTodayTrading();
    }
}
