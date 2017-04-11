package com.shawnway.nav.app.wtw.module.mall;

import com.shawnway.nav.app.wtw.base.ILoadingView;
import com.shawnway.nav.app.wtw.base.IPresenter;
import com.shawnway.nav.app.wtw.bean.LuckyDrawGoodsBean;
import com.shawnway.nav.app.wtw.bean.UserPointResult;
import com.shawnway.nav.app.wtw.module.mall.bean.NewestProductBean;
import com.shawnway.nav.app.wtw.module.mall.bean.RecommendProductBean;

import java.util.List;

/**
 * Created by Administrator on 2016/10/24.
 * Administrator github = "https://github.com/Cicinnus0407"
 */

public class MallContract {

    public interface MallView extends ILoadingView {
        void addUserPoint(UserPointResult userPointResult);

        void addPrizesList(LuckyDrawGoodsBean bean);

        void addNewestProduct(List<NewestProductBean.AllProductEntityBean> productEntityBean);

        void addRecommendProduct(List<RecommendProductBean.AllProductEntityBean> bean);
    }

    public interface MallPresenter extends IPresenter {

        void getUserPoint();

        void getPrizesList();

        void signIn();

        void getProducts();


    }
}
