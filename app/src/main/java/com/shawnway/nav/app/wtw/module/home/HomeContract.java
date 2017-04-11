package com.shawnway.nav.app.wtw.module.home;

import com.shawnway.nav.app.wtw.base.ILoadingView;
import com.shawnway.nav.app.wtw.bean.BannerPicBean;
import com.shawnway.nav.app.wtw.bean.LuckyDrawGoodsBean;
import com.shawnway.nav.app.wtw.bean.SignInResult;

/**
 * Created by Cicinnus on 2016/11/23.
 */

public class HomeContract {

    public interface IHomeView extends ILoadingView{

        void showUserSignIn(SignInResult signInResult);

        void addBanner(BannerPicBean bannerPicBean);

        void addPrizesList(LuckyDrawGoodsBean bean);


        void showSignLoading();
    }

    public interface IHomePresenter {
        void getBanner();

        void getPrizesList();

        void signIn();
    }
}
