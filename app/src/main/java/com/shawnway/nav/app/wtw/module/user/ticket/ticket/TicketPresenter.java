package com.shawnway.nav.app.wtw.module.user.ticket.ticket;

import android.app.Activity;

import com.shawnway.nav.app.wtw.base.BasePresenter;
import com.shawnway.nav.app.wtw.base.BaseSubscriber;

/**
 * Created by Cicinnus on 2016/12/19.
 */

public class TicketPresenter extends BasePresenter<TicketContract.TicketView> implements TicketContract.TicketPresenter {

    private final TicketManager ticketManager;

    public TicketPresenter(Activity activity, TicketContract.TicketView view) {
        super(activity, view);
        ticketManager = new TicketManager();
    }

    @Override
    public void getTicket(int status, int pageNo) {
        mView.showLoading();
        addSubscribe(ticketManager.getCoupon(status, pageNo)
                .subscribe(new BaseSubscriber<TicketBean>() {
                    @Override
                    public void onSuccess(TicketBean ticketBean) {
                        mView.showContent();
                        mView.addTicket(ticketBean.getList());
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        mView.showError("网络连接失败");
                    }
                }));
    }
}
