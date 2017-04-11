package com.shawnway.nav.app.wtw.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/9/14.
 */

public abstract class BaseFragment<T extends IPresenter> extends Fragment {

    public  Activity mContext;
    protected T mPresenter;

    //绑定activity
    @Override
    public void onAttach(Context context) {
        mContext = (Activity) context;
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(getLayoutId(), container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //用来取消view
        mPresenter = getPresenter();
        initEventAndData();
        initEventAndData(savedInstanceState);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) mPresenter.detachView();
    }


    protected T getPresenter() {
        return null;
    }

    //让子类自己设置布局
    protected abstract int getLayoutId();


    protected void initEventAndData(Bundle saveData) {
    }

    protected void initEventAndData() {
    }


}
