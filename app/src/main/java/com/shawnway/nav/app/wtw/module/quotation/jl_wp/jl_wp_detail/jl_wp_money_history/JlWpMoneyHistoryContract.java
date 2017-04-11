package com.shawnway.nav.app.wtw.module.quotation.jl_wp.jl_wp_detail.jl_wp_money_history;

import com.shawnway.nav.app.wtw.base.ILoadingView;

import java.util.List;

/**
 * Created by Cicinnus on 2016/12/11.
 */

public class JlWpMoneyHistoryContract {
    public interface IWpMoneyHistoryView extends ILoadingView{
        void addWpMoneyHistory(List<JlWpMoneyHistoryBean.DataBean.ListBean> list);
    }

    public interface IWpMoneyHistoryPresenter{
        void getWpMoneyHistory(int page, String token);
    }
}
