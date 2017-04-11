package com.shawnway.nav.app.wtw.module.home;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shawnway.nav.app.wtw.R;
import com.shawnway.nav.app.wtw.base.BaseFragment;
import com.shawnway.nav.app.wtw.bean.NewsBean;
import com.shawnway.nav.app.wtw.module.information.NewsActivity;
import com.shawnway.nav.app.wtw.net.JsonRequestBody;
import com.shawnway.nav.app.wtw.net.RetrofitClient;
import com.shawnway.nav.app.wtw.tool.SchedulersCompat;
import com.shawnway.nav.app.wtw.tool.TimeUtils;
import com.shawnway.nav.app.wtw.tool.ToastUtil;
import com.shawnway.nav.app.wtw.view.MyListView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import rx.functions.Action1;

/**
 * Created by Administrator on 2016/7/22 0022.
 * 重要消息
 */
public class ImportanceNewsFragment extends BaseFragment {

    private List<NewsBean> mNewsDatas = new ArrayList<>();

    @BindView(R.id.rv_importantNews)
    MyListView rv_importantNews;
    private ImportantNewsAdapter newsAdapter;

    private notifyRefreshFinished notifyRefreshFinished;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_importancenews;
    }

    @Override
    protected void initEventAndData(Bundle saveData) {

        initView();
        initDatas();
        EventBus.getDefault().register(this);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    public void initDatas() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("page", 0);
            jsonObject.put("size", 15);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RetrofitClient
                .getInstance()
                .api()
                .getTop30Important(JsonRequestBody.getInstance().convertJsonContent(jsonObject.toString()))
                .compose(SchedulersCompat.<NewsBean[]>applyIoSchedulers())
                .subscribe(new Action1<NewsBean[]>() {
                    @Override
                    public void call(NewsBean[] newsBeen) {
                        mNewsDatas.clear();
                        Collections.addAll(mNewsDatas, newsBeen);
                        newsAdapter.setNewData(mNewsDatas);
                        if (notifyRefreshFinished != null) {
                            notifyRefreshFinished.finished();
                        }

                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        if (notifyRefreshFinished != null) {
                            notifyRefreshFinished.onFailure();
                        }
                    }
                });
    }

    private void initView() {

        newsAdapter = new ImportantNewsAdapter(mContext);
        rv_importantNews.setAdapter(newsAdapter);
        rv_importantNews.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                NewsActivity.getInstance(mContext, mNewsDatas.get(position));
                ToastUtil.showShort(mContext, "click");
            }
        });

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshData(ImportanceNews importanceNews) {
        initDatas();
    }

    public void setNotifyRefreshFinished(ImportanceNewsFragment.notifyRefreshFinished notifyRefreshFinished) {
        this.notifyRefreshFinished = notifyRefreshFinished;
    }

    static class ImportantNewsAdapter extends BaseAdapter {

        private List<NewsBean> mNewsDatas;
        private LayoutInflater layoutInflater;
        private Context mContext;

        public ImportantNewsAdapter(Context context) {
            mNewsDatas = new ArrayList<>();
            mContext = context;
            layoutInflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return mNewsDatas.size();
        }

        @Override
        public Object getItem(int position) {
            return mNewsDatas.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null) {
                viewHolder = new ViewHolder();
                convertView = layoutInflater.inflate(R.layout.item_important_news, null);
                viewHolder.converView = (LinearLayout) convertView.findViewById(R.id.ll_importance);
                viewHolder.title = (TextView) convertView.findViewById(R.id.item_impornews_tv_title);
                viewHolder.type = (TextView) convertView.findViewById(R.id.item_impornews_btn);
                viewHolder.str = (TextView) convertView.findViewById(R.id.item_impornews_tv_str);
                viewHolder.time = (TextView) convertView.findViewById(R.id.item_impornews_tv_time);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();

            }
            String type = "";
            switch (mNewsDatas.get(position).newsCategoryId) {
                case 1:
                    type = "系统消息";
                    break;
                case 2:
                    type = "重要信息";
                    break;
                case 41:
                    type = "最新公告";
                    break;
                case 42:
                    type = "最新活动";
                    break;
                case 43:
                    type = "交易攻略";
                    break;
            }

            viewHolder.type.setText(type);
            viewHolder.time.setText(String.format("%s", TimeUtils.YMDToDate(mNewsDatas.get(position).updateTime)));
            viewHolder.title.setText(String.format("%s", mNewsDatas.get(position).newsTitle));
            viewHolder.str.setText(String.format("%s", mNewsDatas.get(position).shortDesc));
            viewHolder.converView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    NewsActivity.getInstance(mContext, mNewsDatas.get(position));
                }
            });

            return convertView;
        }

        public void setNewData(List<NewsBean> data) {
            this.mNewsDatas.clear();
            this.mNewsDatas.addAll(data);
            notifyDataSetChanged();
        }

        private class ViewHolder {
            TextView type;
            TextView time;
            TextView title;
            TextView str;
            LinearLayout converView;

        }

        public interface onItemClick {

        }


    }

    public interface notifyRefreshFinished {
        void finished();

        void onFailure();
    }


    public static class ImportanceNews {

    }


}
