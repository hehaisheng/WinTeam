package com.shawnway.nav.app.wtw.module.information;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.orhanobut.logger.Logger;
import com.shawnway.nav.app.wtw.R;
import com.shawnway.nav.app.wtw.base.BaseWebViewFragment;
import com.shawnway.nav.app.wtw.bean.NewsAddressBean;
import com.shawnway.nav.app.wtw.bean.NewsBean;
import com.shawnway.nav.app.wtw.net.JsonRequestBody;
import com.shawnway.nav.app.wtw.net.RetrofitClient;
import com.shawnway.nav.app.wtw.tool.StringUtils;

import org.json.JSONException;
import org.json.JSONObject;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class NewsActivity extends FragmentActivity implements View.OnClickListener {


    private NewsBean data;
    private String url;
    private String givenUrl;
    private String title;

    public static void getInstance(Context context, NewsBean bean) {
        Intent intent = new Intent(context, NewsActivity.class);
        intent.putExtra("bean", bean);
        context.startActivity(intent);
    }

    public static void getInstance(Context context, String url, String title) {
        Intent intent = new Intent(context, NewsActivity.class);
        intent.putExtra("url", url);
        intent.putExtra("title", title);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        data = (NewsBean) getIntent().getSerializableExtra("bean");
        givenUrl = getIntent().getStringExtra("url");
        title = getIntent().getStringExtra("title");
        setContentView(R.layout.activity_news);

        if (data != null) {
            initUrl();
        } else {
            url = givenUrl;

        }
        initWebView();
        initToolBar();
    }

    private void initUrl() {
        JSONObject json = new JSONObject();
        try {
            json.put("id", data.id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RetrofitClient
                .getInstance()
                .api()
                .getNewsById(JsonRequestBody.getInstance().convertJsonContent(json.toString()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<NewsAddressBean>() {
                    @Override
                    public void call(NewsAddressBean newsAddressBean) {
                        url = newsAddressBean.newsAddress;
                        initWebView();
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Logger.e(throwable.getMessage());
                    }
                });

    }

    private void initWebView() {
        Fragment fragment = new BaseWebViewFragment();
        Bundle bundle = new Bundle();
        if (url == null) {
            return;
        }
        bundle.putString("url", url);
        bundle.putString("src", null);
        bundle.putBoolean("fitcenter", false);
        bundle.putBoolean("js", true);
        bundle.putBoolean("zoom", false);
        fragment.setArguments(bundle); // FragmentActivity将点击的菜单列表标题传递给Fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.activity_news_fragment, fragment).commit();
    }

    private void initToolBar() {
        findViewById(R.id.toolbar).setVisibility(View.VISIBLE);
        ImageButton iBtn = (ImageButton) findViewById(R.id.top_back);
        iBtn.setVisibility(View.VISIBLE);
        iBtn.setOnClickListener(this);
        TextView center = (TextView) findViewById(R.id.top_text_center);
        center.setVisibility(View.VISIBLE);
        if (StringUtils.isNotEmpty(givenUrl)) {
            center.setText(title);
        } else {
            center.setText(data.newsTitle);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.top_back:
                finish();
                break;
        }
    }

}
