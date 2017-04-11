package com.shawnway.nav.app.wtw.module.information;

import android.graphics.PixelFormat;
import android.os.Handler;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shawnway.nav.app.wtw.R;
import com.shawnway.nav.app.wtw.base.BaseActivity;
import com.shawnway.nav.app.wtw.view.X5WebView;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebView;

import butterknife.BindView;


/**
 * Created by Administrator on 2016/9/20.
 */

public class LiveActivity extends BaseActivity {
    private static final String TAG = "Live";
    @BindView(R.id.top_back)
    ImageButton topBack;
    @BindView(R.id.top_text_center)
    TextView topTextCenter;
    @BindView(R.id.toolbar)
    RelativeLayout toolbar;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.web_live)
    X5WebView webLive;
    Handler handler = new Handler();
    private String url;

    @Override
    protected int getLayout() {
        return R.layout.live_activity;
    }

    @Override
    protected void initEventAndData() {
        initToolbar();
        url = getIntent().getStringExtra("live");

        getWindow().setFormat(PixelFormat.TRANSLUCENT);
        WebChromeClient webChromeClient = new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView webView, int i) {
                progressBar.setProgress(i);
            }
        };

        webLive.setWebChromeClient(webChromeClient);
        webLive.canGoBack();


    }

    @Override
    protected void onResume() {
        super.onResume();
        if (url != null) {
            webLive.loadUrl(url);
        }
    }

    private void initToolbar() {
        toolbar.setVisibility(View.VISIBLE);
        topBack.setVisibility(View.VISIBLE);
        topBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        topTextCenter.setVisibility(View.VISIBLE);
        topTextCenter.setText("直播间");
    }
}
