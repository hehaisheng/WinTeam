package com.shawnway.nav.app.wtw.base;

/**
 * Created by Cicinnus on 2016/9/15.
 */

public interface ILoadingView {
    void showLoading();
    void showNoData();
    void showContent();
    void showError(String errorMsg);



}
