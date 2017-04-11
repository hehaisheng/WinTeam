package com.shawnway.nav.app.wtw.module.user.ticket.wp_ticket;

import android.app.Activity;

import com.shawnway.nav.app.wtw.base.BasePresenter;
import com.shawnway.nav.app.wtw.base.BaseSubscriber;

import retrofit2.Response;

/**
 * Created by Cicinnus on 2016/12/6.
 */

public class WpTicketPresenter extends BasePresenter<WpTicketContract.ITicketView> implements WpTicketContract.ITicketPresenter {

    private final WpTicketManager wpTicketManager;

    public WpTicketPresenter(Activity activity, WpTicketContract.ITicketView view) {
        super(activity, view);
        wpTicketManager = new WpTicketManager();
    }

    @Override
    public void getTicket(int page, int size, String token) {
        mView.showLoading();
        addSubscribe(wpTicketManager.getTicket(page, size, token)
                .subscribe(new BaseSubscriber<Response<WpTicketBean>>() {
                    @Override
                    public void onSuccess(Response<WpTicketBean> ticketBeanResponse) {
                        if (ticketBeanResponse.code()==200) {
                            mView.showContent();
                            mView.addTicket(ticketBeanResponse.body().getData());
                        }
                    }
                }));
    }
}
