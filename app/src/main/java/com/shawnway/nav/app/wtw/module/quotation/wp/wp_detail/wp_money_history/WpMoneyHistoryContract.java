package com.shawnway.nav.app.wtw.module.quotation.wp.wp_detail.wp_money_history;

import com.shawnway.nav.app.wtw.base.ILoadingView;

import java.util.List;

/**
 * Created by Cicinnus on 2016/12/11.
 */

public class WpMoneyHistoryContract {
    public interface IWpMoneyHistoryView extends ILoadingView{
        void addWpMoneyHistory(List<WpMoneyHistoryBean.DataBean.ListBean> list);
    }

    public interface IWpMoneyHistoryPresenter{
        void getWpMoneyHistory(int page,String token);
    }
}
