package com.shawnway.nav.app.wtw.module.user.promotion;

import com.shawnway.nav.app.wtw.base.ILoadingView;
import com.shawnway.nav.app.wtw.base.IPresenter;
import com.shawnway.nav.app.wtw.bean.RetrieveInvitationCodeBean;

/**
 * Created by Kevin on 2016/11/15
 */

public class PromotionContract {
    public interface IPromotionView extends ILoadingView {
        void addInviteCode(RetrieveInvitationCodeBean retrieveInvitationCodeBean);
    }

    public interface IPromotionPresenter{
        void getInviteCode();
    }
}
