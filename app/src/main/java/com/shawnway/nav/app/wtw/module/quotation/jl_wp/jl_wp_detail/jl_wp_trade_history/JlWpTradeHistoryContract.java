package com.shawnway.nav.app.wtw.module.quotation.jl_wp.jl_wp_detail.jl_wp_trade_history;

import com.shawnway.nav.app.wtw.base.ILoadingView;

import java.util.List;

/**
 * Created by Cicinnus on 2016/12/12.
 */

public class JlWpTradeHistoryContract {
    public interface IWpTradeHistoryView extends ILoadingView{
        void addTradeHistory(List<JlWpTradeHistoryBean.DataBean.ListBean> list);

        void addTradeTotal(JlWpTradeTotalBean.DataBean data);
    }

    public interface IWpTradeHistoryPresenter{
        void getTradeHistory(int pageNum, String token);

        void getTradeTotal(String token);

        void loadAllSuccess();
    }
}
