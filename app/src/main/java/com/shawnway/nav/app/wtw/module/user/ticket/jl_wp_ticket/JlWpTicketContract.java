package com.shawnway.nav.app.wtw.module.user.ticket.jl_wp_ticket;

import com.shawnway.nav.app.wtw.base.ILoadingView;

import java.util.List;

/**
 * Created by Cicinnus on 2016/12/6.
 */

public class JlWpTicketContract {
    public interface ITicketView extends ILoadingView{
        void addTicket(List<JlWpTicketBean.DataBean.TicketsBean> data);
    }

    public interface ITicketPresenter{
        void getTicket(int page, int size, String token);
    }
}
