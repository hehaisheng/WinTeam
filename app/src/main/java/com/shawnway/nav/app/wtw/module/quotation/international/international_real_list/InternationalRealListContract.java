package com.shawnway.nav.app.wtw.module.quotation.international.international_real_list;

import com.shawnway.nav.app.wtw.base.ILoadingView;
import com.shawnway.nav.app.wtw.module.quotation.international.InternationalListBean;

import java.util.List;

/**
 * Created by Cicinnus on 2016/12/20.
 */

public class InternationalRealListContract {

    public interface IInternationRealListView extends ILoadingView{
        void addList(List<InternationalListBean.InstrumentRealmarketBean> instrumentRealmarket);
    }

    public interface IInternationalRealListPresenter{
        void getList();
    }
}
