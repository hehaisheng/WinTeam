package com.shawnway.nav.app.wtw.module.quotation.wp.wp_detail.tran.build_tran;

import com.shawnway.nav.app.wtw.base.ILoadingView;
import com.shawnway.nav.app.wtw.base.IPresenter;
import com.shawnway.nav.app.wtw.bean.QuotationsWPBean;
import com.shawnway.nav.app.wtw.module.quotation.wp.wp_detail.tran.bean.BuildTranBean;
import com.shawnway.nav.app.wtw.module.quotation.wp.wp_detail.tran.bean.TranGoodsWrapper;
import com.shawnway.nav.app.wtw.module.quotation.wp.wp_detail.tran.bean.NotLiquidateOrder;
import com.shawnway.nav.app.wtw.module.quotation.wp.wp_detail.tran.bean.UserAccountBean;
import com.shawnway.nav.app.wtw.module.user.ticket.wp_ticket.WpTicketBean;

/**
 * Created by Kevin on 2016/12/8
 */

public class BuildTranContract {
    public interface IBuildTranView extends ILoadingView {
        //添加产品列表（已筛选）
        void addTranGoods(TranGoodsWrapper tranGoodsWrapper);

        //添加用户实时盈亏和当前仓位
        void addNotLiquidateOrder(NotLiquidateOrder.NotLiquidateBean notLiquidateBean);

        //添加用户赢家券
        void addUserTicket(WpTicketBean wpTicketBean);

        void addUserAccountBalance(UserAccountBean userAccountBean);

        //建仓返回结果
        void onCreateResult(BuildTranBean buildTranBean);
    }

    public interface IBuildPresenter extends IPresenter {
        //获取产品列表(通过参数来筛选）
        void getTranGoods(QuotationsWPBean quotationsWPBean);

        //获取用户实时盈亏和当前仓位
        void getNotLiquidateOrder(String access_token);

        //获取用户赢家券
        void getUserTicket(String access_token);

        void getUserAccountBalance(String access_token);

        //建仓
        void createOrder(String access_token, BuildTranBean.BuildBean buildBean);
    }

}
