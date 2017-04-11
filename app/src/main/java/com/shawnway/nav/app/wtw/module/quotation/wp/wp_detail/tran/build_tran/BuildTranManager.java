package com.shawnway.nav.app.wtw.module.quotation.wp.wp_detail.tran.build_tran;

import com.shawnway.nav.app.wtw.module.quotation.wp.wp_detail.tran.bean.BuildTranBean;
import com.shawnway.nav.app.wtw.module.quotation.wp.wp_detail.tran.bean.TranGoodsWrapper;
import com.shawnway.nav.app.wtw.module.quotation.wp.wp_detail.tran.bean.NotLiquidateOrder;
import com.shawnway.nav.app.wtw.module.quotation.wp.wp_detail.tran.bean.UserAccountBean;
import com.shawnway.nav.app.wtw.module.user.ticket.wp_ticket.WpTicketBean;
import com.shawnway.nav.app.wtw.net.WPRetrofitClient;
import com.shawnway.nav.app.wtw.tool.SchedulersCompat;

import rx.Observable;

/**
 * Created by Kevin on 2016/12/8
 */

public class BuildTranManager {
    private final int TICKET_TYPE = 1;
    private final int TICKET_PAGE_NO = 1;
    private final int TICKET_PAGE_SIZE = 10;

    /**
     * 获取产品详情
     */
    public Observable<TranGoodsWrapper> getTranGoods() {
        return WPRetrofitClient.getInstance()
                .api()
                .getTranGoods()
                .compose(SchedulersCompat.<TranGoodsWrapper>applyIoSchedulers());
    }

    /**
     * 获取用户实时盈亏和当前仓位
     *
     * @return
     */
    public Observable<NotLiquidateOrder> getNotLiquidateOrder(String access_token) {
        return WPRetrofitClient.getInstance()
                .api()
                .getNotLiquidateOrder(access_token)
                .compose(SchedulersCompat.<NotLiquidateOrder>applyIoSchedulers());
    }

    /**
     * 获取用户未使用的赢家券
     *
     * @param access_token
     * @return
     */
    public Observable<WpTicketBean> getUserTicket(String access_token) {
        return WPRetrofitClient.getInstance()
                .api()
                .getUserTicket(TICKET_TYPE,
                        TICKET_PAGE_NO,
                        TICKET_PAGE_SIZE,
                        access_token)
                .compose(SchedulersCompat.<WpTicketBean>applyIoSchedulers());
    }

    /**
     * 获取用户资金信息
     *
     * @param access_token
     * @return
     */
    public Observable<UserAccountBean> getUserAccountBalance(String access_token) {
        return WPRetrofitClient.getInstance()
                .api()
                .getUserAccountBalance(access_token)
                .compose(SchedulersCompat.<UserAccountBean>applyIoSchedulers());

    }

    /**
     * 建仓
     *
     * @param buildTranBean
     * @return
     */
    public Observable<BuildTranBean> createOrder(String access_token, BuildTranBean.BuildBean buildTranBean) {
        BuildTranBean.BuildBean bean = buildTranBean;
        return WPRetrofitClient.getInstance()
                .api()
                .createOrder(bean.getProdectId(),
                        bean.getTradeType(),
                        bean.getAmount(),
                        bean.getTargetProfit(),
                        bean.getStopLoss(),
                        bean.getUseTicket(),
                        access_token).compose(SchedulersCompat.<BuildTranBean>applyIoSchedulers());
    }
}
