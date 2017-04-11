package com.shawnway.nav.app.wtw.module.quotation.wp.wp_detail.tran.transfer_tran;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shawnway.nav.app.wtw.R;
import com.shawnway.nav.app.wtw.base.BaseFragment;
import com.shawnway.nav.app.wtw.net.WpApi;
import com.shawnway.nav.app.wtw.tool.SPUtils;
import com.shawnway.nav.app.wtw.view.X5WebView;
import com.tencent.smtt.export.external.interfaces.SslErrorHandler;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import butterknife.BindView;

/**
 * Created by Kevin on 2016/12/9
 * 充值页面
 */

public class RechargeFragment extends BaseFragment {

    @BindView(R.id.layout_fragment_recharge)
    RelativeLayout layoutFragmentRecharge;
    @BindView(R.id.layout_loading_text)
    TextView layoutLoading;

   /* @BindView(R.id.webview_go_back)
    TextView tvGoBack;*/

    private X5WebView wb;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_wp_recharge_tran;
    }

    public static RechargeFragment newInstance() {

        Bundle args = new Bundle();

        RechargeFragment fragment = new RechargeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initEventAndData() {
        initWebView();
    }

    private void initListener() {
        /*tvGoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (wb.canGoBack()) {
                    wb.goBack();
                }
            }
        });*/
    }

    /**
     * 初始化webview
     */
    private void initWebView() {
        wb = new X5WebView(mContext);
        initWebViewClient();
        initWebViewOptions();

        layoutFragmentRecharge.addView(wb);

        String access_token = SPUtils.getInstance(mContext, SPUtils.SP_WP)
                .getString(SPUtils.WP_TOKEN, "");
        wb.loadUrl(WpApi.URL_RECHARGE + access_token);
    }

    private void initWebViewClient() {
        wb.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onReceivedSslError(WebView webView, SslErrorHandler sslErrorHandler, com.tencent.smtt.export.external.interfaces.SslError sslError) {
                sslErrorHandler.proceed();
            }
        });
        wb.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView webView, int i) {
                if (i == 100) {
//                    tvGoBack.setVisibility(webView.canGoBack() ? View.VISIBLE : View.GONE);
                    initListener();
                    webView.setVisibility(View.VISIBLE);
                    layoutLoading.setVisibility(View.GONE);
                } else {
                    layoutLoading.setVisibility(View.VISIBLE);
                    webView.setVisibility(View.GONE);
                }
            }
        });
    }

    private void initWebViewOptions() {
        if (wb != null) {
            wb.setHorizontalScrollBarEnabled(false);
            wb.setVerticalScrollBarEnabled(false);

            final WebSettings webSettings = wb.getSettings();
            //支持js
            webSettings.setJavaScriptEnabled(true);
            webSettings.setBuiltInZoomControls(false);// 设置缩放
            webSettings.setDisplayZoomControls(false);
            webSettings.setSupportZoom(false);
            webSettings.setDefaultTextEncodingName("UTF-8");

            webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        }
    }


    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && isVisiable) {
            if (wb.canGoBack()) {
                wb.goBack();//返回上一浏览页面
                return true;
            } else return false;
        }
        return false;
    }

    private Boolean isVisiable = true;

    /**
     * 懒加载
     *
     * @param hidden
     */
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            isVisiable = true;
        } else isVisiable = false;
    }

}
