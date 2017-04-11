package com.shawnway.nav.app.wtw.base;

import android.app.Activity;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/9/14.
 */

public abstract class BaseActivity<T extends IPresenter> extends AppCompatActivity {

    protected Activity mContext;

    protected T mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        mContext = this;

        ButterKnife.bind(this);

        mPresenter = getPresenter();

        initEventAndData();

        initEventAndData(savedInstanceState);
    }

    /**
     * activity销毁时取消订阅事件
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.detachView();
        }
    }



    protected void setColor(Activity activity, int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {

//            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

            View statusView = createStatusView(activity, color);

            ViewGroup decorView = (ViewGroup) activity.getWindow().getDecorView();
            decorView.addView(statusView);

            ViewGroup rootView = (ViewGroup) ((ViewGroup) activity.findViewById(android.R.id.content)).getChildAt(0);

            rootView.setFitsSystemWindows(true);

            rootView.setClipToPadding(true);
        }
    }


    private View createStatusView(Activity activity, int color) {

        int resourceId = activity.getResources().getIdentifier("status_bar_height", "dimen", "android");
        int statusBarHeight = activity.getResources().getDimensionPixelSize(resourceId);


        View statusView = new View(activity);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                statusBarHeight);

        statusView.setLayoutParams(params);

        statusView.setBackgroundColor(color);

        return statusView;
    }


    protected T getPresenter() {
        return mPresenter;
    }


    protected abstract int getLayout();


    protected void initEventAndData() {
    }

    protected void initEventAndData(Bundle savedInstanceState) {
    }

    /**
     * 防止修改系统字体大小导致字体变大
     *
     * @return resource
     */
    @Override
    public Resources getResources() {

        Resources res = super.getResources();

        Configuration config = new Configuration();

        config.setToDefaults();

        res.updateConfiguration(config, res.getDisplayMetrics());
        return res;
    }

    /**
     * 设置可视化
     *
     * @param views
     */

    protected void setVisiable(View... views) {
        for (View view : views) {
            view.setVisibility(View.VISIBLE);
        }
    }

}
