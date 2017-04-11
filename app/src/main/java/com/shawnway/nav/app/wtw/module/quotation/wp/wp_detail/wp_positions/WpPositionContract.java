package com.shawnway.nav.app.wtw.module.quotation.wp.wp_detail.wp_positions;

import com.shawnway.nav.app.wtw.base.ILoadingView;
import com.shawnway.nav.app.wtw.module.quotation.wp.wp_detail.tran.bean.UserAccountBean;

/**
 * Created by Cicinnus on 2016/12/10.
 */

public class WpPositionContract {
    public interface IWpPositionView extends ILoadingView{
        void addPositions(WpPositionsBean list);

        void addWpUserBalance(UserAccountBean.AccBanBean useableBalance);

        void liquidateSuccess();

        void liquidateAllSuccess();
    }

    public interface IWpPositionPresenter{
        void getPositions(String token);

        void getWpUserAccountBalance(String token);

        void liquidate(String orderId,String token);

        void liquidateAll(String token);

        void loadSuccess();
    }
}
