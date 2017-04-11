package com.shawnway.nav.app.wtw.module.mall.point_detail;

import com.shawnway.nav.app.wtw.base.ILoadingView;
import com.shawnway.nav.app.wtw.base.IPresenter;

/**
 * Created by Administrator on 2016/10/28.
 * Administrator github = "https://github.com/Cicinnus0407"
 */

public class PointContract {

    public interface PointDetailView extends ILoadingView{
        void addPointDetail(PointDetailResult result);
    }

    public interface PointDetailPresenter extends IPresenter{
        void getPointDetail(int page);
    }
}
