package com.shawnway.nav.app.wtw.module.quotation.wp.wp_list;

import com.shawnway.nav.app.wtw.base.ILoadingView;
import com.shawnway.nav.app.wtw.bean.QuotationsWPBean;

/**
 * Created by Cicinnus on 2016/12/23.
 */

public class WpListContract {
    public interface IWpListView extends ILoadingView{
        void addList(QuotationsWPBean[] quotationsWPBeen);
    }
    public interface IWpPresenter{
        void getList();
    }
}
