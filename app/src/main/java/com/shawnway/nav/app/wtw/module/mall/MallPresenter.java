package com.shawnway.nav.app.wtw.module.mall;

import android.app.Activity;

import com.shawnway.nav.app.wtw.base.BasePresenter;
import com.shawnway.nav.app.wtw.base.BaseSubscriber;
import com.shawnway.nav.app.wtw.bean.LuckyDrawGoodsBean;
import com.shawnway.nav.app.wtw.bean.SignInResult;
import com.shawnway.nav.app.wtw.bean.UserPointResult;
import com.shawnway.nav.app.wtw.module.login_register.login.LoginActivity;
import com.shawnway.nav.app.wtw.module.mall.bean.NewestProductBean;
import com.shawnway.nav.app.wtw.module.mall.bean.RecommendProductBean;
import com.shawnway.nav.app.wtw.net.ErrorHanding;
import com.shawnway.nav.app.wtw.tool.SPUtils;

import retrofit2.Response;
import rx.Observer;
import rx.functions.Action1;

/**
 * Created by Administrator on 2016/10/26.
 * Administrator github = "https://github.com/Cicinnus0407"
 */

@SuppressWarnings("unchecked")
public class MallPresenter extends BasePresenter<MallContract.MallView> implements MallContract.MallPresenter {


    private final MallManager mallManager;

    public MallPresenter(Activity activity, MallContract.MallView view) {
        super(activity, view);
        mallManager = new MallManager();
    }


    @Override
    public void getUserPoint() {
        addSubscribe(mallManager.getUserPoint()
                .subscribe(new BaseSubscriber<Response<UserPointResult>>() {

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        mView.showContent();
                    }

                    @Override
                    public void onSuccess(Response<UserPointResult> response) {
                        if (response.code() == 200) {
                            mView.showContent();
                            mView.addUserPoint(response.body());
                            SPUtils.getInstance(mActivity, SPUtils.SP_ACCOUNT)
                                    .putInt(SPUtils.USER_POINT, response.body().getPoint())
                                    .apply();
                        } else {
                            mView.showContent();
                        }
                    }
                }));
    }

    @Override
    public void getPrizesList() {
        mView.showLoading();
        addSubscribe(mallManager.getPrizesList()
                .subscribe(new Observer<LuckyDrawGoodsBean>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        showToast("获取失败抽奖数据失败");
                    }

                    @Override
                    public void onNext(LuckyDrawGoodsBean luckyDrawGoodsBean) {
                        mView.showContent();
                        mView.addPrizesList(luckyDrawGoodsBean);
                    }
                }));
    }

    @Override
    public void signIn() {
        mView.showLoading();
        addSubscribe(mallManager.signIn()
                .subscribe(new BaseSubscriber<Response<SignInResult>>() {
                    @Override
                    public void onSuccess(Response<SignInResult> signInResultResponse) {
                        if (signInResultResponse.code() == 200) {
                            getUserPoint();
                            mView.showContent();
                            showToast(signInResultResponse.body().getStatusCode());
                        } else if (signInResultResponse.code() == 401) {
                            LoginActivity.getInstance(mActivity);
                            mView.showContent();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        showToast("签到失败请重试");
                    }
                }));
    }

    @Override
    public void getProducts() {
        mView.showLoading();
        addSubscribe(mallManager.getHomeProduct()
                .subscribe(new Action1() {
                    @Override
                    public void call(Object o) {
                        mView.showContent();
                        if (o instanceof NewestProductBean) {
                            mView.addNewestProduct(((NewestProductBean) o).getAllProductEntity());
                        } else if (o instanceof RecommendProductBean) {
                            mView.addRecommendProduct(((RecommendProductBean) o).getAllProductEntity());
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        mView.showError(ErrorHanding.handleError(throwable));
                    }
                }));
    }


}
