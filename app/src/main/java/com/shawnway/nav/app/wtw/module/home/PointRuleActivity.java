package com.shawnway.nav.app.wtw.module.home;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shawnway.nav.app.wtw.R;
import com.shawnway.nav.app.wtw.base.BaseActivity;
import com.shawnway.nav.app.wtw.bean.PointRuleResult;
import com.shawnway.nav.app.wtw.module.mall.point_detail.PointDetailActivity;
import com.shawnway.nav.app.wtw.net.RetrofitClient;
import com.shawnway.nav.app.wtw.tool.SchedulersCompat;
import com.shawnway.nav.app.wtw.tool.ToastUtil;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Observer;

/**
 * Created by Administrator on 2016/10/27.
 * Administrator github = "https://github.com/Cicinnus0407"
 * 积分规则
 */

public class PointRuleActivity extends BaseActivity {

    @BindView(R.id.rv_point_rule)
    RecyclerView rvPoint;
    @BindView(R.id.top_back)
    ImageButton topBack;
    @BindView(R.id.top_text_center)
    TextView topTextCenter;
    @BindView(R.id.top_text_right)
    TextView topTextRight;
    @BindView(R.id.toolbar)
    RelativeLayout toolbar;
    @BindView(R.id.swipe)
    SwipeRefreshLayout swipe;
    private PointRuleAdapter adapter;


    @Override
    protected int getLayout() {
        return R.layout.activity_point_rule;
    }

    @Override
    protected void initEventAndData() {
        topBack.setVisibility(View.VISIBLE);
        topTextCenter.setText("积分赚取");
        topTextCenter.setVisibility(View.VISIBLE);
        topTextRight.setText("积分明细");
        topTextRight.setVisibility(View.VISIBLE);
        toolbar.setVisibility(View.VISIBLE);

        adapter = new PointRuleAdapter();
        rvPoint.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        rvPoint.setAdapter(adapter);
        swipe.setColorSchemeColors(getResources().getColor(R.color.lightred));
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                FetchPointRule();
            }
        });
        FetchPointRule();
    }

    private void FetchPointRule() {
        swipe.setRefreshing(true);
        RetrofitClient
                .getInstance()
                .api()
                .getPointRule()
                .compose(SchedulersCompat.<PointRuleResult>applyIoSchedulers())
                .subscribe(new Observer<PointRuleResult>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        swipe.setRefreshing(false);
                        ToastUtil.showShort(mContext, "加载积分赚取规则失败");
                    }

                    @Override
                    public void onNext(PointRuleResult pointRuleResult) {
                        adapter.setNewData(pointRuleResult.getList());
                        swipe.setRefreshing(false);
                    }
                });
    }

    @OnClick({R.id.top_back, R.id.top_text_right})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.top_back:
                finish();
                break;
            case R.id.top_text_right:
                startActivity(new Intent(mContext, PointDetailActivity.class));
//                if (SPUtils.getInstance(mContext, SPUtils.SP_ACCOUNT)
//                        .getBoolean(SPUtils.IS_LOGIN, false)) {
//
//                } else {
//                    ToastUtil.showShort(mContext, "登录后再进行查询");
//                }
                break;
        }
    }


    private class PointRuleAdapter extends BaseQuickAdapter<PointRuleResult.ListBean> {

        private final int[] bgs;

        public PointRuleAdapter() {
            super(R.layout.item_layout_point_rule, null);
            bgs = new int[]{R.drawable.blu, R.drawable.yel, R.drawable.red, R.drawable.gre};
        }

        @Override
        protected void convert(BaseViewHolder baseViewHolder, PointRuleResult.ListBean bean) {
            String desc = "";
            switch (bean.getType()) {
                case 1:
                    desc = "成功注册即送积分";
                    break;
                case 2:
                    desc = "抽奖赚取积分";
                    break;
                case 3:
                    desc = "邀请好友成功注册即送积分";
                    break;
                case 4:
                    desc = "成功绑定银行卡充值即送积分";
                    break;
                case 5:
                    desc = "成功操盘即送积分";
                    break;
                case 6:
                    desc = "每日签到即送积分";
                    break;
            }
            baseViewHolder.setText(R.id.point_name, bean.getName())
                    .setText(R.id.point, String.format("%s%s", bean.getType() != 2 ? "+" : "-", bean.getPoint()))
                    .setText(R.id.point_desc, desc);
            RelativeLayout rl_point_rule = baseViewHolder.getView(R.id.rl_point_rule);
            rl_point_rule.setBackgroundResource(bgs[bean.getType() % 4]);
        }
    }
}
