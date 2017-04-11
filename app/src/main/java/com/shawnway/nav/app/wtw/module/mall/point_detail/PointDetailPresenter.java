package com.shawnway.nav.app.wtw.module.mall.point_detail;

import android.app.Activity;

import com.orhanobut.logger.Logger;
import com.shawnway.nav.app.wtw.base.BasePresenter;
import com.shawnway.nav.app.wtw.module.login_register.login.LoginActivity;

import retrofit2.Response;
import rx.functions.Action1;

/**
 * Created by Administrator on 2016/10/28.
 * Administrator github = "https://github.com/Cicinnus0407"
 */

@SuppressWarnings("unchecked")
public class PointDetailPresenter extends BasePresenter<PointContract.PointDetailView> implements PointContract.PointDetailPresenter {


    private final PointManager pointManager;

    public PointDetailPresenter(Activity activity, PointContract.PointDetailView view) {
        super(activity, view);
        pointManager = new PointManager();
    }

    @Override
    public void getPointDetail(int page) {
        mView.showLoading();
        addSubscribe(pointManager.getPointDetail(page)
                .subscribe(new Action1<Response<PointDetailResult>>() {
                    @Override
                    public void call(Response<PointDetailResult> pointDetailResultResponse) {
                        if (pointDetailResultResponse.code()==200) {
                            mView.showContent();
                            mView.addPointDetail(pointDetailResultResponse.body());
                        }else if(pointDetailResultResponse.code()==401){
                            LoginActivity.getInstance(mActivity);
                            mActivity.finish();
                        }else {
                            mView.showError(pointDetailResultResponse.message());
                        }
                    }


                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        mView.showError(throwable.getMessage());
                        Logger.e(throwable.getMessage());
                    }
                }));
    }
}
