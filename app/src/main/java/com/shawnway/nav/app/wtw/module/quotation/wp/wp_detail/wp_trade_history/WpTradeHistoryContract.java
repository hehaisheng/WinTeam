package com.shawnway.nav.app.wtw.module.quotation.wp.wp_detail.wp_trade_history;

import com.shawnway.nav.app.wtw.base.ILoadingView;

import java.util.List;

/**
 * Created by Cicinnus on 2016/12/12.
 */

public class WpTradeHistoryContract {
    public interface IWpTradeHistoryView extends ILoadingView{
        void addTradeHistory(List<WpTradeHistoryBean.DataBean.ListBean> list);

        void addTradeTotal(WpTradeTotalBean.DataBean data);
    }

    public interface IWpTradeHistoryPresenter{
        void getTradeHistory(int pageNum,String token);

        void getTradeTotal(String token);

        void loadAllSuccess();
    }
}
