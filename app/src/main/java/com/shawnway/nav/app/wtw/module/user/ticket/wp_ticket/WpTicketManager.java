package com.shawnway.nav.app.wtw.module.user.ticket.wp_ticket;

import com.shawnway.nav.app.wtw.net.WPRetrofitClient;
import com.shawnway.nav.app.wtw.tool.SchedulersCompat;

import retrofit2.Response;
import rx.Observable;

/**
 * Created by Cicinnus on 2016/12/6.
 */

public class WpTicketManager {
    public Observable<Response<WpTicketBean>> getTicket(int page, int size, String token){
        return WPRetrofitClient
                .getInstance()
                .api()
                .getTicket(page,size,token)
                .compose(SchedulersCompat.<Response<WpTicketBean>>applyIoSchedulers());
    }
}
