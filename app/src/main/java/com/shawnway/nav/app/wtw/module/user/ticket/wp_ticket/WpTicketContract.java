package com.shawnway.nav.app.wtw.module.user.ticket.wp_ticket;

import com.shawnway.nav.app.wtw.base.ILoadingView;

import java.util.List;

/**
 * Created by Cicinnus on 2016/12/6.
 */

public class WpTicketContract {
    public interface ITicketView extends ILoadingView{
        void addTicket(List<WpTicketBean.DataBean> data);
    }

    public interface ITicketPresenter{
        void getTicket(int page,int size,String token);
    }
}
