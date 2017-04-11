package com.shawnway.nav.app.wtw.module.user.ticket.jl_wp_ticket;

import com.shawnway.nav.app.wtw.net.JlWPRetrofitClient;
import com.shawnway.nav.app.wtw.tool.SchedulersCompat;

import retrofit2.Response;
import rx.Observable;

/**
 * Created by Cicinnus on 2016/12/6.
 */

public class JlWpTicketManager {
    public Observable<Response<JlWpTicketBean>> getTicket(int page, int size, String token){
        return JlWPRetrofitClient
                .getInstance()
                .api()
                .getTicket(page,size,1,token)
                .compose(SchedulersCompat.<Response<JlWpTicketBean>>applyIoSchedulers());
    }
}
