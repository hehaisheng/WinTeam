package com.shawnway.nav.app.wtw.module.integral_mall;

import com.shawnway.nav.app.wtw.base.ILoadingView;
import com.shawnway.nav.app.wtw.base.IPresenter;
import com.shawnway.nav.app.wtw.bean.UserWinningResult;

/**
 * Created by Administrator on 2016/10/28.
 * Administrator github = "https://github.com/Cicinnus0407"
 */

public class UserWinningContract {
    public interface UserWinningView extends ILoadingView{
        void addUserWinningData(UserWinningResult result);
    }

    public interface UserWinningPresenter extends IPresenter{
        void getUserWinningResult(int page);
    }
}
