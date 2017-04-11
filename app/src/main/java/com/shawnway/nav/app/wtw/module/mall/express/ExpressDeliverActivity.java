package com.shawnway.nav.app.wtw.module.mall.express;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shawnway.nav.app.wtw.R;
import com.shawnway.nav.app.wtw.base.BaseActivity;
import com.shawnway.nav.app.wtw.module.mall.bean.ExpressDeliver;
import com.shawnway.nav.app.wtw.view.LoadingView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Kevin on 2016/11/24
 */

public class ExpressDeliverActivity extends BaseActivity<ExpressDeliverPresenter> implements ExpressContract.IExpressView {
    @BindView(R.id.top_back)
    ImageButton topBack;
    @BindView(R.id.top_text_center)
    TextView topTextCenter;
    @BindView(R.id.top_text_right)
    TextView topTextRight;
    @BindView(R.id.toolbar)
    RelativeLayout toolbar;
    @BindView(R.id.express_diliver_status)
    TextView expressDiliverStatus;
    @BindView(R.id.expressCompany)
    TextView expressCompany;
//    @BindView(R.id.expressNumber)
//    TextView expressNumber;
//    @BindView(R.id.express_diliver_name)
//    TextView expressDiliverName;
//    @BindView(R.id.express_diliver_moblie)
//    TextView expressDiliverMoblie;
    @BindView(R.id.rv_express)
    RecyclerView rvExpress;
    private ExpressDeliverAdapter deliverAdapter;
    private LoadingView loadingView;

    @Override
    protected int getLayout() {
        return R.layout.activity_express_deliver;
    }

    @Override
    protected void initEventAndData() {
        initToolbar();
        initRecyclerView();
        getExpressByOrderId();
    }

    private void getExpressByOrderId() {
        String orderId = getIntent().getStringExtra("orderId");
//        String orderId = "809323982149";
        mPresenter.getExpressByOrderId(orderId);
    }

    private void initRecyclerView() {
        View view = getLayoutInflater().inflate(R.layout.layout_footer_no_data, (ViewGroup) rvExpress.getParent(),false);
        ((TextView) view.findViewById(R.id.no_data_text)).setText("暂无物流信息~~");
        rvExpress.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        deliverAdapter = new ExpressDeliverAdapter(R.layout.item_express_deliver, null);
        deliverAdapter.setEmptyView(view);
        rvExpress.setAdapter(deliverAdapter);
    }

    @Override
    protected ExpressDeliverPresenter getPresenter() {
        return new ExpressDeliverPresenter(mContext, this);
    }

    private void initToolbar() {
        topTextCenter.setText("我的物流");
        setVisiable(toolbar, topBack, topTextCenter);
    }


    public static void start(Context context, String orderId) {
        Intent starter = new Intent(context, ExpressDeliverActivity.class);
        starter.putExtra("orderId", orderId);
        context.startActivity(starter);
    }


    @OnClick(R.id.top_back)
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.top_back:
                finish();
                break;
        }
    }

    @Override
    public void addExpressResult(ExpressDeliver expressDeliver) {
        deliverAdapter.setNewData(expressDeliver.getExpressTraces());
    }

    @Override
    public void showLoading() {
        loadingView = new LoadingView(mContext);
        loadingView.show();
    }

    @Override
    public void showNoData() {

    }

    @Override
    public void showContent() {
       loadingView.dismiss();
    }

    @Override
    public void showError(String errorMsg) {
        loadingView.dismiss();

    }
}
