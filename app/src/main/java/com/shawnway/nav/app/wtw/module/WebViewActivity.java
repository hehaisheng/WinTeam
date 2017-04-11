package com.shawnway.nav.app.wtw.module;

import android.graphics.PixelFormat;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shawnway.nav.app.wtw.R;
import com.shawnway.nav.app.wtw.base.BaseActivity;
import com.shawnway.nav.app.wtw.module.login_register.login.LoginActivity;
import com.shawnway.nav.app.wtw.net.RetrofitClient;
import com.shawnway.nav.app.wtw.tool.SPUtils;
import com.shawnway.nav.app.wtw.tool.SchedulersCompat;
import com.shawnway.nav.app.wtw.tool.StringUtils;
import com.shawnway.nav.app.wtw.tool.TimeUtils;
import com.shawnway.nav.app.wtw.tool.ToastUtil;
import com.shawnway.nav.app.wtw.view.X5WebView;

import butterknife.BindView;
import rx.Observer;

/**
 * Created by Administrator on 2016/9/18.
 */

public class WebViewActivity extends BaseActivity {

    private static final String TAG = "WebViewTestActivity";
    @BindView(R.id.web_view)
    X5WebView webView;
    @BindView(R.id.top_back)
    ImageButton topBack;
    @BindView(R.id.top_text_center)
    TextView topTextCenter;
    @BindView(R.id.toolbar)
    RelativeLayout toolbar;
    private TextView topRightText;
    private Button mJoinActBtn;
    private String title;
    private String url;
    private int id;
    private String startTime;
    private String endTime;

    @Override
    protected int getLayout() {
        return R.layout.video_web_view;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        getWindow().setFormat(PixelFormat.TRANSLUCENT);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initEventAndData() {
        webView = (X5WebView) findViewById(R.id.web_view);
        url = getIntent().getStringExtra("url");
        title = getIntent().getStringExtra("title");
        id = getIntent().getIntExtra("id", -1);
        startTime = getIntent().getStringExtra("startTime");
        endTime = getIntent().getStringExtra("endTime");
        if (url != null) {
            webView.loadUrl(url);
        }
        getWindow().setFormat(PixelFormat.TRANSLUCENT);

        toolbar.setVisibility(View.VISIBLE);
        topBack.setVisibility(View.VISIBLE);
        topBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        topTextCenter.setVisibility(View.VISIBLE);
        topTextCenter.setText(title);
        mJoinActBtn = (Button) findViewById(R.id.web_joinact);
        mJoinActBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long currentTime = System.currentTimeMillis();
                if (TimeUtils.startTimeToLong(startTime) > currentTime) {
                    ToastUtil.showShort(WebViewActivity.this, "活动还没开始");
                    return;
                }
                if (TimeUtils.endTimeToLong(endTime) < currentTime) {
                    ToastUtil.showShort(WebViewActivity.this, "活动已经结束");
                    return;
                }
                joinActivity();
            }
        });
    }

    private void joinActivity() {
        String phone = SPUtils.getInstance(mContext, SPUtils.SP_ACCOUNT).getString(SPUtils.PHONE, "");
        if (StringUtils.isEmpty(phone)) {
            ToastUtil.showShort(this, "登录之后才能报名参与活动");
            LoginActivity.getInstance(this);
            return;
        }
        RetrofitClient
                .getInstance()
                .api()
                .joinActivity(Integer.toString(id))
                .compose(SchedulersCompat.<JoinActivityBean>applyIoSchedulers())
                .subscribe(new Observer<JoinActivityBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "" + e.getMessage());
                    }

                    @Override
                    public void onNext(JoinActivityBean joinActivityBean) {
                        if (joinActivityBean.status.equals("200")) {
                            ToastUtil.showShort(mContext, "报名成功");
                        }
                    }
                });
    }

    public class JoinActivityBean {
        public String status;
        public String desc;
    }


}
