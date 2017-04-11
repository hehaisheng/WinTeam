package com.shawnway.nav.app.wtw.view;

import android.graphics.Bitmap;

import com.shawnway.nav.app.wtw.base.BaseWebViewFragment;
import com.shawnway.nav.app.wtw.tool.WebViewStyleFactory;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

/**
 *
 */
public class MyWebViewClient extends WebViewClient {
    private WebViewStyleFactory.LaunchDisplayer launchDisplayer;
    private BaseWebViewFragment baseWebViewFragment;
    public MyWebViewClient(WebViewStyleFactory.LaunchDisplayer launchDisplayer,BaseWebViewFragment baseWebViewFragment) {
        this.launchDisplayer = launchDisplayer;
        this.baseWebViewFragment =baseWebViewFragment;
    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {

        view.getSettings().setJavaScriptEnabled(true);
        super.onPageStarted(view, url, favicon);
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
        launchDisplayer.connectSuccess();
        baseWebViewFragment.addImageClickListner();
    }

    @Override
    public void onLoadResource(WebView view, String url) {
        super.onLoadResource(view, url);
    }


    @Override
    public void onReceivedError(WebView view, int errorCode,
                                String description, String failingUrl) {

        launchDisplayer.connectFalse();

    }
}
