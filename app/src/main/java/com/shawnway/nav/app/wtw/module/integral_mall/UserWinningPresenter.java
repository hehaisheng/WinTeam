package com.shawnway.nav.app.wtw.module.integral_mall;

import android.app.Activity;

import com.shawnway.nav.app.wtw.base.BasePresenter;
import com.shawnway.nav.app.wtw.bean.UserWinningResult;

import rx.Observer;

/**
 * Created by Administrator on 2016/10/28.
 * Administrator github = "https://github.com/Cicinnus0407"
 */

@SuppressWarnings("unchecked")
public class UserWinningPresenter extends BasePresenter<UserWinningContract.UserWinningView> implements UserWinningContract.UserWinningPresenter {

    private final UserWinningManager userWinningManager;

    public UserWinningPresenter(Activity activity, UserWinningContract.UserWinningView view) {
        super(activity, view);
        userWinningManager = new UserWinningManager();
    }

    @Override
    public void getUserWinningResult(int page) {
        mView.showLoading();
        addSubscribe(userWinningManager.getUserWinningResult(page)
        .subscribe(new Observer<UserWinningResult>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                mView.showError(e.getMessage());
            }

            @Override
            public void onNext(UserWinningResult result) {
                mView.showContent();
                mView.addUserWinningData(result);
            }
        }));
    }
}
