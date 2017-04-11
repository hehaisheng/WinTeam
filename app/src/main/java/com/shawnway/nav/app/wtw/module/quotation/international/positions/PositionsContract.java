package com.shawnway.nav.app.wtw.module.quotation.international.positions;

import com.shawnway.nav.app.wtw.base.ILoadingView;
import com.shawnway.nav.app.wtw.module.quotation.international.InternationalListBean;
import com.shawnway.nav.app.wtw.module.quotation.international.order.TradeBean;

import java.util.List;

/**
 * Created by Cicinnus on 2016/11/25.
 */

public class PositionsContract {
    public interface IPositionsView extends ILoadingView {
        void addPositions(PositionsBean body);

        void addMarketPrice(InternationalListBean internationalListBean);

        void orderSuccess(TradeBean tradeBean);

        void addPointValue(List<PointValueResultBean.ListBean> list);

    }

    public interface IPositionsPresenter {
        void getPositions();

        void getMarketPrice();

        void order(PositionsBean.WareHouseInfosBean bean);

        void getPointValue();

        void loadAllData();
    }
}
