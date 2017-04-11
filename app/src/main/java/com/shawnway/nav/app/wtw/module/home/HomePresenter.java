package com.shawnway.nav.app.wtw.module.home;

import android.app.Activity;

import com.shawnway.nav.app.wtw.base.BasePresenter;
import com.shawnway.nav.app.wtw.base.BaseSubscriber;
import com.shawnway.nav.app.wtw.bean.BannerPicBean;
import com.shawnway.nav.app.wtw.bean.LuckyDrawGoodsBean;
import com.shawnway.nav.app.wtw.bean.SignInResult;
import com.shawnway.nav.app.wtw.module.login_register.login.LoginActivity;
import com.shawnway.nav.app.wtw.net.ErrorHanding;
import com.shawnway.nav.app.wtw.net.RetryWhenProcess;

import retrofit2.Response;

/**
 * Created by Cicinnus on 2016/11/23.
 */

@SuppressWarnings("unchecked")
public class HomePresenter extends BasePresenter<HomeContract.IHomeView> implements HomeContract.IHomePresenter {


    private final HomeManager homeManager;



    public HomePresenter(Activity activity, HomeContract.IHomeView view) {

        super(activity, view);

        homeManager = new HomeManager();
    }


    @Override
    public void getBanner() {

        mView.showLoading();

        addSubscribe(homeManager.getBanner()
                .retryWhen(new RetryWhenProcess(5))

                .subscribe(new BaseSubscriber<BannerPicBean>() {
                    @Override
                    public void onSuccess(BannerPicBean bannerPicBean) {

                        mView.showContent();

                        mView.addBanner(bannerPicBean);
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        mView.showError("连接服务器失败");
                    }
                }));
    }

    @Override
    public void getPrizesList() {
        addSubscribe(homeManager.getPrizesList()
                .subscribe(new BaseSubscriber<LuckyDrawGoodsBean>() {
                    @Override
                    public void onSuccess(LuckyDrawGoodsBean luckyDrawGoodsBean) {
                        mView.addPrizesList(luckyDrawGoodsBean);
                    }
                }));
    }

    @Override
    public void signIn() {

        mView.showSignLoading();

        addSubscribe(homeManager.signIn()
                .subscribe(new BaseSubscriber<Response<SignInResult>>() {
                    @Override
                    public void onSuccess(Response<SignInResult> signInResultResponse) {

                        if (signInResultResponse.code() == 200) {

                            mView.showContent();

                            mView.showUserSignIn(signInResultResponse.body());
                        } else if (signInResultResponse.code() == 401) {
                            mView.showContent();

                            LoginActivity.getInstance(mActivity);
                        } else {
                            mView.showContent();
                            showToast("请求服务器失败，请稍后重试");
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        mView.showContent();
                        showToast(ErrorHanding.handleError(e));
                    }
                }));
    }

}
