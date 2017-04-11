package com.shawnway.nav.app.wtw.module.user.ticket.ticket;

import com.shawnway.nav.app.wtw.base.ILoadingView;

import java.util.List;

/**
 * Created by Cicinnus on 2016/12/19.
 */

public class TicketContract {
    public interface TicketView extends ILoadingView {
        void addTicket(List<TicketBean.ListBean> list);
    }

    public interface TicketPresenter {
        void getTicket(int status, int pageNo);
    }
}
