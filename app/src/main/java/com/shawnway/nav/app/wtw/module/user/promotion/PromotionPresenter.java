package com.shawnway.nav.app.wtw.module.user.promotion;

import android.app.Activity;

import com.shawnway.nav.app.wtw.base.BasePresenter;
import com.shawnway.nav.app.wtw.base.BaseSubscriber;
import com.shawnway.nav.app.wtw.bean.RetrieveInvitationCodeBean;
import com.shawnway.nav.app.wtw.module.login_register.login.LoginActivity;

import retrofit2.Response;

/**
 * Created by Kevin on 2016/11/15
 */

public class PromotionPresenter extends BasePresenter<PromotionContract.IPromotionView> implements PromotionContract.IPromotionPresenter {
    private PromotionManager promotionManager;

    public PromotionPresenter(Activity activity, PromotionContract.IPromotionView view) {
        super(activity, view);
        this.promotionManager = new PromotionManager();
    }

    @Override
    public void getInviteCode() {
        mView.showLoading();
        addSubscribe(promotionManager.getInviteCode().subscribe(new BaseSubscriber<Response<RetrieveInvitationCodeBean>>() {
            @Override
            public void onSuccess(Response<RetrieveInvitationCodeBean> response) {
                if (response.code() == 200) {
                    mView.addInviteCode(response.body());
                    mView.showContent();
                } else if (response.code() == 401) {
                    mActivity.finish();
                    LoginActivity.getInstance(mActivity);
                }
            }

        }));

    }
}
