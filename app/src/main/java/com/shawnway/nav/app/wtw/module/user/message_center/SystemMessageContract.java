package com.shawnway.nav.app.wtw.module.user.message_center;

import com.shawnway.nav.app.wtw.base.ILoadingView;
import com.shawnway.nav.app.wtw.bean.NewsBean;

/**
 * Created by Cicinnus on 2016/11/29.
 */

public class SystemMessageContract {

    public interface ISystemMessageView extends ILoadingView{
        void addSystemMessage(NewsBean[] newsBeen);
    }

    public interface ISystemMessagePresenter{
        void getSystemMessage(int page);
    }
}
