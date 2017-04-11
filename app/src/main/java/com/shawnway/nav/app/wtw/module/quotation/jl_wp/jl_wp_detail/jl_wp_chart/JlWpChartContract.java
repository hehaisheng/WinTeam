package com.shawnway.nav.app.wtw.module.quotation.jl_wp.jl_wp_detail.jl_wp_chart;

import com.shawnway.nav.app.wtw.base.ILoadingView;
import com.shawnway.nav.app.wtw.bean.QuotationsWPBean;
import com.shawnway.nav.app.wtw.module.quotation.wp.wp_detail.tran.bean.UserAccountBean;

/**
 * Created by Cicinnus on 2016/12/7.
 */

public class JlWpChartContract {

    public interface IWpChartView extends ILoadingView{
        void addData(QuotationsWPBean[] quotationsWPBeen);

        void addMoney(UserAccountBean.AccBanBean data);
    }

    public interface IWpChartPresenter{
        void getData();

        void getUseageMoney(String token);

        void loadData();
    }
}
