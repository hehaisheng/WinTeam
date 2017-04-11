package com.shawnway.nav.app.wtw.module.user.promotion;

import com.shawnway.nav.app.wtw.bean.RetrieveInvitationCodeBean;
import com.shawnway.nav.app.wtw.net.RetrofitClient;
import com.shawnway.nav.app.wtw.tool.SchedulersCompat;

import retrofit2.Response;
import rx.Observable;

/**
 * Created by Kevin on 2016/11/15
 */

public class PromotionManager {
    /**
     * 获取用户邀请码
     *
     * @return observable
     */
    public Observable<Response<RetrieveInvitationCodeBean>> getInviteCode() {
        return RetrofitClient.getInstance()
                .api()
                .getInviteCode()
                .compose(SchedulersCompat.<Response<RetrieveInvitationCodeBean>>applyIoSchedulers());
    }
}
