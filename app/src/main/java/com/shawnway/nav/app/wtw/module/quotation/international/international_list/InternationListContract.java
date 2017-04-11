package com.shawnway.nav.app.wtw.module.quotation.international.international_list;

import com.shawnway.nav.app.wtw.base.ILoadingView;
import com.shawnway.nav.app.wtw.module.quotation.international.InternationalListBean;

import java.util.List;

/**
 * Created by Cicinnus on 2016/12/5.
 */

public class InternationListContract {
    public interface IInternationalListView extends ILoadingView{
        void addInternationalList(List<InternationalListBean.InstrumentRealmarketBean> instrumentRealmarket);
    }

    public interface IInternationalPresenter{
        void getInternationalList();
    }
}
