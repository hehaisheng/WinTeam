package com.shawnway.nav.app.wtw.module.mall.productDetail;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shawnway.nav.app.wtw.R;
import com.shawnway.nav.app.wtw.base.BaseActivity;
import com.shawnway.nav.app.wtw.module.login_register.login.LoginActivity;
import com.shawnway.nav.app.wtw.module.mall.order.ShoppingCarOrderActivity;
import com.shawnway.nav.app.wtw.module.mall.shopping_car.ShoppingCarActivity;
import com.shawnway.nav.app.wtw.module.mall.shopping_car.bean.ShoppingCarListBean;
import com.shawnway.nav.app.wtw.module.mall.shopping_car.bean.ShoppingCarResultBean;
import com.shawnway.nav.app.wtw.tool.ToastUtil;
import com.shawnway.nav.app.wtw.view.SlideDetailsLayout;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * Created by Cicinnus on 2016/11/14.
 */

public class ProductDetailActivity extends BaseActivity<ProductDetailPresenter> implements ProductDetailContract.IProductDetailView {
    @BindView(R.id.layout_product_details)
    RelativeLayout layoutProductDetails;

    @BindView(R.id.slidedetails_front)
    FrameLayout slidedetailsFront;
    @BindView(R.id.slidedetails_behind)
    RelativeLayout slidedetails_behind;
    @BindView(R.id.slidedetails)
    SlideDetailsLayout slidedetails;
    @BindView(R.id.top_back)
    ImageButton topBack;
    @BindView(R.id.top_text_center)
    TextView topTextCenter;
    @BindView(R.id.top_text_right)
    TextView topTextRight;
    @BindView(R.id.toolbar)
    RelativeLayout toolbar;

    private int quantity = 1;
    private int point;
    private String imgUrl;
    private String productName;

    private int proId;

    private String detailUrl;
    private WebView webView;
    private boolean isSetWebView = false;
    private TextView openText;

    public static void start(Context context, int id) {
        Intent starter = new Intent(context, ProductDetailActivity.class);
        starter.putExtra("proId", id);
        context.startActivity(starter);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_product_detail;
    }

    @Override
    protected void initEventAndData(Bundle savedInstanceState) {
        initToolbar();
        proId = getIntent().getIntExtra("proId", -1);
        slidedetails_behind.findViewById(R.id.layout_loading_text).setVisibility(View.VISIBLE);
        initSlidesDetails();
    }

    private void initToolbar() {
        topTextCenter.setText("商品详情");
        topTextCenter.setVisibility(View.VISIBLE);
        toolbar.setVisibility(View.VISIBLE);
        topBack.setVisibility(View.VISIBLE);
        topTextRight.setVisibility(View.VISIBLE);
        topTextRight.setText("");
        Drawable drawable = getResources().getDrawable(R.drawable.tro);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        topTextRight.setCompoundDrawables(drawable, null, null, null);
    }

    @Override
    protected ProductDetailPresenter getPresenter() {
        return new ProductDetailPresenter(mContext, this);
    }


    @OnClick({
            R.id.top_back,
            R.id.top_text_right,
            R.id.btnAddShoppingCar,
            R.id.changeImmediately
    })
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.top_back:
                finish();
                break;
            case R.id.top_text_right:
                ShoppingCarActivity.start(mContext);
                break;
            case R.id.btnAddShoppingCar:
                mPresenter.addShoppingCarProduct(proId, quantity);
                break;
            case R.id.changeImmediately:
                ArrayList<ShoppingCarListBean.ShoppingEntityListBean> dataList = new ArrayList<>();
                ShoppingCarListBean.ShoppingEntityListBean bean = new ShoppingCarListBean.ShoppingEntityListBean();
                bean.setProName(productName);
                bean.setProDiscCsptPoint(point);
                bean.setImg(imgUrl);
                bean.setChecked(true);
                bean.setProId(proId);
                bean.setQuantity(quantity);
                dataList.add(bean);
                ShoppingCarOrderActivity.start(mContext, dataList);
                break;
        }
    }


    /**
     * 初始化SlidesDetails
     */
    private void initSlidesDetails() {
        setFrameLayout();
        initSlidesDetailsListener();
    }

    /**
     * 是否立即加载监听
     */
    private void initSlidesDetailsListener() {
        slidedetails.setOnSlideDetailsListener(new SlideDetailsLayout.OnSlideDetailsListener() {
            @Override
            public void onStatucChanged(final SlideDetailsLayout.Status status) {
                getWindow().getDecorView().post(new Runnable() {
                    @Override
                    public void run() {
                        if (status.compareTo(SlideDetailsLayout.Status.OPEN) == 0) {
                            openText.setText("向下滑动关闭商品详细详情");

                            if (!isSetWebView) {
                                setWebView();
                                getWindow().getDecorView().post(new Runnable() {
                                    @Override
                                    public void run() {
                                        webView.loadUrl(detailUrl);
                                    }
                                });
                                isSetWebView = true;
                            }
                        } else if (status.compareTo(SlideDetailsLayout.Status.CLOSE) == 0) {
                            openText.setText("向上滑动展开商品详细详情");
                        }
                    }
                });
            }
        });
    }

    /**
     * 设置webview
     */
    private void setWebView() {
        webView = new WebView(getApplication());
        webView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        slidedetails_behind.addView(webView);
        webView.setVisibility(View.GONE);
        initWebViewOptions();
        initWebViewClient();
    }

    private void initWebViewClient() {
        webView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

        });
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView webView, int i) {
                if (i == 100) {
                    webView.setVisibility(View.VISIBLE);
                    slidedetails_behind.findViewById(R.id.layout_loading_text).setVisibility(View.GONE);
                } else {
                    slidedetails_behind.findViewById(R.id.layout_loading_text).setVisibility(View.VISIBLE);
                    webView.setVisibility(View.GONE);
                }
            }
        });
    }

    private void initWebViewOptions() {
        if (webView != null) {
            final WebSettings webSettings = webView.getSettings();
            //支持js
            webSettings.setJavaScriptEnabled(true);
            //支持缩放
            webSettings.setSupportZoom(true);
//            webSettings.setBuiltInZoomControls(true);
            //大视野显示
            webSettings.setUseWideViewPort(true);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ECLAIR_MR1) {
                new Object() {
                    public void setLoadWithOverviewMode(boolean overview) {
                        webSettings.setLoadWithOverviewMode(overview);
                    }
                }.setLoadWithOverviewMode(true);
            }
            //缓存
            webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
            webSettings.setDomStorageEnabled(true);
        }
    }

    /**
     * 设置FrameLayout填充内容
     */
    private void setFrameLayout() {
        ProductDetailFragment productDetailFragment = ProductDetailFragment.newInstance(proId);
        productDetailFragment.setOnProductDeatilFragmentListener(new ProductDetailFragment.OnProductDeatilFragmentListener() {
            @Override
            public void onGetDeatilsUrl(String url, int quantity, int point, String imgUrl, String productName) {
                setPoint(point);
                setQuantity(quantity);
                setImgUrl(imgUrl);
                setProductName(productName);
                setDetailUrl(url);
            }

            @Override
            public void onQuantityClick(int quantity) {
                setQuantity(quantity);
            }

            @Override
            public void changFootView(TextView textView) {
                openText = textView;
            }

            @Override
            public void onError(String errormsg) {
                showError(errormsg);
            }

            @Override
            public void onContent() {
                showContent();
            }

        });
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.slidedetails_front, productDetailFragment)
                .commit();
    }

    @Override
    public void addProDetail(ProDetailBean proDetailBean) {
    }

    @Override
    public void adding() {

    }

    @Override
    public void addShoppingCarSuccess(ShoppingCarResultBean bean) {
        ToastUtil.showShort(mContext, bean.getDesc());
    }

    @Override
    public void jumpLogin() {
        LoginActivity.getInstance(mContext);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void showNoData() {

    }

    @Override
    public void showContent() {
        Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                layoutProductDetails.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void showError(String errorMsg) {
        Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                layoutProductDetails.setVisibility(View.GONE);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        releaseWebView();
        //setConfigCallback(null);
    }


    private synchronized void releaseWebView() {
        if (webView != null) {
            ViewGroup parent = (ViewGroup) webView.getParent();
            if (parent != null) {
                parent.removeView(webView);
            }
            webView.getSettings().setJavaScriptEnabled(false);
            webView.setTag(null);
            webView.clearHistory();
            webView.removeAllViews();
            webView.destroy();
            webView = null;
        }
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setDetailUrl(String detailUrl) {
        this.detailUrl = detailUrl;
    }

}
