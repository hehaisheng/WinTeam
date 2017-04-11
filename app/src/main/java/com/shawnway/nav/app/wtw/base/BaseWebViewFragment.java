package com.shawnway.nav.app.wtw.base;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shawnway.nav.app.wtw.R;
import com.shawnway.nav.app.wtw.tool.NetWorkUtil;
import com.shawnway.nav.app.wtw.tool.WebViewStyleFactory;
import com.shawnway.nav.app.wtw.view.MyWebViewClient;
import com.shawnway.nav.app.wtw.view.X5WebView;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;

@SuppressLint("SetJavaScriptEnabled")
public class BaseWebViewFragment extends Fragment {

    private final String TAG = "yuSimpleFragment";
    private X5WebView webView;
    private WebViewStyleFactory.LaunchDisplayer uilaunchStyle;

    private String mDefaultURl;
    private String mHtmlSrc;
    private boolean isFixCenter = true;

    @SuppressLint("SetJavaScriptEnabled")

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            mDefaultURl = bundle.getString("url");
            mHtmlSrc = bundle.getString("src");
            isFixCenter = bundle.getBoolean("fitcenter", true);
        }
    }

    View view = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.base_fragment_webbase, container, false);
        webView = (X5WebView) view.findViewById(R.id.webview);
        setWebViewStyle(view);
        setWebView(view);
        return view;
    }

    private void setWebViewStyle(View view) {
        View webViewContainer = view.findViewById(R.id.web_view_container);
        View loadingView = view.findViewById(R.id.loading_bar);
        View failLoadView = view.findViewById(R.id.fail_load);
        failLoadView.setOnClickListener(new ErrorOnclickListener());
        setLuanchStyle(WebViewStyleFactory.createDefaultLuanchStyle(
                webViewContainer, loadingView, failLoadView));
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        setWebViewStyle(view);
        setWebView(view);
    }

    protected void setWebView(View view) {
        X5WebView webView = getWebView();

        if (webView != null) {
            webView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    return true;
                }
            });

            if (Build.VERSION.SDK_INT >= 19) {
                webView.getSettings().setCacheMode(
                        WebSettings.LOAD_CACHE_ELSE_NETWORK);
            }

        }
        if (webView != null) {
            webView.setWebChromeClient(new WebChromeClient());
            webView.setWebViewClient(new MyWebViewClient(uilaunchStyle, this));
        }

        if (mDefaultURl != null && !mDefaultURl.isEmpty())
            loadurl(mDefaultURl);


    }

    // 注入js函数监听
    public void addImageClickListner() {
        webView.loadUrl("javascript:(function(){" +
                "var objs = document.getElementsByClassName(\"form-control\");"
                + "var as=document.getElementsByTagName(\"a\").length;"
                + "var a=document.getElementsByTagName(\"a\")[9];"
                + "var avalue=a.getAttribute(\"ng-click\");"
                + "a.setAttribute(\"ng-click\",\"\");"
                + "    a.onclick=function()  " +
                "    {  "
                + "var objs = document.getElementsByClassName(\"form-control\");"
                + " var index = objs[0].selectedIndex;"
                + " var text = objs[0].options[index].text;"
                + "var obj2=objs[1].value;"
                + "window.imagelistner.openImage(text,obj2);"
                + "recharge();" +
                "    }  " +
                "})()");
    }




    public void loadurl(String url) {
        if (!NetWorkUtil.isNetworkConnected(getActivity())) {
            getLuanchStyle().delayConnectFalse();
        } else {
            if (url != null) {
                webView.loadUrl(url);
            } else {
                getLuanchStyle().pageNeedFix();
            }
        }

    }

    public WebViewStyleFactory.LaunchDisplayer getLuanchStyle() {
        return uilaunchStyle;
    }

    private void setLuanchStyle(WebViewStyleFactory.LaunchDisplayer displayer) {
        uilaunchStyle = displayer;
    }

    public X5WebView getWebView() {
        return webView;
    }


    @Override
    public Context getContext() {

        return getActivity();
    }


    public void loadUrlWithNetCheck() {
        getLuanchStyle().loading();
        if (getWebView().getUrl() != null) {
            Log.d(TAG, getWebView().getUrl());
            loadurl(getWebView().getUrl());
        } else {
            loadurl(mDefaultURl);
        }
    }

    class ErrorOnclickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            loadUrlWithNetCheck();
        }

    }
}
