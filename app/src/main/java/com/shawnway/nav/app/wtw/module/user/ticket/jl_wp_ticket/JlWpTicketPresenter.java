package com.shawnway.nav.app.wtw.module.user.ticket.jl_wp_ticket;

import android.app.Activity;

import com.shawnway.nav.app.wtw.base.BasePresenter;
import com.shawnway.nav.app.wtw.base.BaseSubscriber;

import retrofit2.Response;

/**
 * Created by Cicinnus on 2016/12/6.
 */

public class JlWpTicketPresenter extends BasePresenter<JlWpTicketContract.ITicketView> implements JlWpTicketContract.ITicketPresenter {

    private final JlWpTicketManager jlWpTicketManager;

    public JlWpTicketPresenter(Activity activity, JlWpTicketContract.ITicketView view) {
        super(activity, view);
        jlWpTicketManager = new JlWpTicketManager();
    }

    @Override
    public void getTicket(int page, int size, String token) {
        mView.showLoading();
        addSubscribe(jlWpTicketManager.getTicket(page, size, token)
                .subscribe(new BaseSubscriber<Response<JlWpTicketBean>>() {
                    @Override
                    public void onSuccess(Response<JlWpTicketBean> ticketBeanResponse) {
                        if (ticketBeanResponse.code()==200) {
                            mView.showContent();
                            mView.addTicket(ticketBeanResponse.body().getData().getTickets());
                        }
                    }
                }));
    }
}
