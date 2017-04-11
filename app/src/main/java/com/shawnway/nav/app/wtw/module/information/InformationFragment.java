package com.shawnway.nav.app.wtw.module.information;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shawnway.nav.app.wtw.R;
import com.shawnway.nav.app.wtw.base.BaseFragment;
import com.shawnway.nav.app.wtw.bean.BannerPicBean;
import com.shawnway.nav.app.wtw.module.WebViewActivity;
import com.shawnway.nav.app.wtw.net.RetrofitClient;
import com.shawnway.nav.app.wtw.tool.SchedulersCompat;
import com.shawnway.nav.app.wtw.view.Banner;
import com.shawnway.nav.app.wtw.view.ProgressLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Observer;

/**
 * 资讯页面
 * Created by Administrator on 2016/7/19 0019.
 */
public class InformationFragment extends BaseFragment implements View.OnClickListener {


    public static InformationFragment newInstance() {

        Bundle args = new Bundle();

        InformationFragment fragment = new InformationFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @BindView(R.id.swipe)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.progress_layout)
    ProgressLayout progressLayout;
    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.toolbar)
    RelativeLayout toolbar;
    @BindView(R.id.top_text_center)
    TextView topTextCenter;
    @BindView(R.id.top_text_right)
    TextView topTextRight;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_information;
    }

    @Override
    protected void initEventAndData() {
        initToolBar();
        initView();
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.appcolor));
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                FetchPic();
            }
        });
    }

    private void initView() {
        FetchPic();
        banner.setOnItemClickListener(new Banner.OnItemClickListener() {
            @Override
            public void click(View v, BannerPicBean.PicBean bean) {
                Intent intent = new Intent(getActivity(), WebViewActivity.class);
                intent.putExtra("url", bean.contentUrl);
                intent.putExtra("title", bean.getPicDesc());
                intent.putExtra("id", bean.getId());
                intent.putExtra("startTime", bean.getStartTime());
                intent.putExtra("endTime", bean.getEndTime());
                startActivity(intent);
            }
        });
    }


    private void initToolBar() {
        toolbar.setVisibility(View.VISIBLE);
        topTextRight.setVisibility(View.VISIBLE);
        topTextCenter.setVisibility(View.VISIBLE);
        topTextCenter.setText("资讯");
        topTextRight.setText("客服");
    }

    private void FetchPic() {
        swipeRefreshLayout.setRefreshing(true);
        RetrofitClient
                .getInstance()
                .api()
                .getBannerUrl()
                .compose(SchedulersCompat.<BannerPicBean>applyIoSchedulers())
                .subscribe(new Observer<BannerPicBean>() {
                    @Override
                    public void onCompleted() {
                        swipeRefreshLayout.setRefreshing(false);

                    }

                    @Override
                    public void onError(Throwable e) {
                        swipeRefreshLayout.setRefreshing(false);
                        progressLayout.showError(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                FetchPic();
                            }
                        });
                    }

                    @Override
                    public void onNext(BannerPicBean bannerPicBean) {
                        if (bannerPicBean.getBannerPicList().size() > 0) {
                            banner.setPic(bannerPicBean.getBannerPicList());
                            if (!progressLayout.isContent()) {
                                progressLayout.showContent();
                            }
                        } else {
                            List<BannerPicBean.PicBean> errorList = new ArrayList<>();
                            BannerPicBean.PicBean bean = new BannerPicBean.PicBean();
                            bean.setPicUrl(getResources().getDrawable(R.mipmap.wtw).toString());
                            errorList.add(bean);
                            banner.setErrorPic(errorList);
                        }
                    }
                });
    }

    @OnClick({R.id.fragment_info_transtrawrapper,
            R.id.fragment_info_roomwrapper,
            R.id.fragment_info_wrapper,
            R.id.top_text_right})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.top_text_right:
                //显示Dialog
                CustomerServiceDialog customerServiceDialogFragment = new CustomerServiceDialog();
                customerServiceDialogFragment.setOnNumberClickListener(new CustomerServiceDialog.OnNumberClickListener() {

                    @Override
                    public void onClick(String num) {
                        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + num));
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        getActivity().startActivity(intent);

                    }
                });
                customerServiceDialogFragment.show(getActivity().getFragmentManager(), "customerServiceDialogFragment");
                break;
            case R.id.fragment_info_transtrawrapper:
                TransTrategyActivity.getInstance(getContext());
                break;
            case R.id.fragment_info_roomwrapper:
                LiveListActivity.getInstance(getContext());
                break;
            case R.id.fragment_info_wrapper:
                NewsActivity.getInstance(getContext(),
                        "http://mp.weixin.qq.com/s?__biz=MzIwMDYzODU5Ng==&mid=402214517&idx=1&sn=e5c3e9aafa27f8b8d46258ded0f0d07b&scene=18#wechat_redirect", "嘉投学院");
                break;
            default:
                break;
        }
    }
}
