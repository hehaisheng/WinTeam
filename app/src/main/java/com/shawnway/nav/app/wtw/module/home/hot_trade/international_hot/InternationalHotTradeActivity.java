package com.shawnway.nav.app.wtw.module.home.hot_trade.international_hot;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shawnway.nav.app.wtw.R;
import com.shawnway.nav.app.wtw.base.BaseActivity;
import com.shawnway.nav.app.wtw.base.BaseSubscriber;
import com.shawnway.nav.app.wtw.module.quotation.international.InternationalListBean;
import com.shawnway.nav.app.wtw.net.RetrofitClient;
import com.shawnway.nav.app.wtw.tool.SPUtils;
import com.shawnway.nav.app.wtw.tool.SchedulersCompat;
import com.shawnway.nav.app.wtw.tool.ToastUtil;
import com.shawnway.nav.app.wtw.view.LoadingView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Cicinnus on 2016/11/2.
 * 关注产品选中列表
 */

public class InternationalHotTradeActivity extends BaseActivity implements View.OnClickListener {


    @BindView(R.id.selected_list)
    RecyclerView rvSelected;

    private SelectedAdapter selectedAdapter;
    private List<InternationalListBean.InstrumentRealmarketBean> data = new ArrayList<>();

    @Override
    protected int getLayout() {
        return R.layout.activity_int_hot_trade;
    }

    @Override
    protected void initEventAndData() {
        initToolbar();
        selectedAdapter = new SelectedAdapter();
        rvSelected.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        rvSelected.setAdapter(selectedAdapter);

        FetchList();
    }

    private void initToolbar() {
        findViewById(R.id.toolbar).setVisibility(View.VISIBLE);
        TextView centerText = (TextView) findViewById(R.id.top_text_center);
        centerText.setVisibility(View.VISIBLE);
        centerText.setText("热门交易");
        TextView confirm = (TextView) findViewById(R.id.top_text_right);
        confirm.setVisibility(View.VISIBLE);
        confirm.setText("保存");
        confirm.setOnClickListener(this);
        ImageButton backButton = (ImageButton) findViewById(R.id.top_back);
        backButton.setVisibility(View.VISIBLE);
        backButton.setOnClickListener(this);
    }

    /**
     * 保存选中的产品到SP
     */
    private void saveListDataToSP() {
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).isChecked()) {
                SPUtils.getInstance(mContext,SPUtils.SP_INT_HOT_SELECTED)
                        .putString(data.get(i).getSecurityGrpCode(),data.get(i).getSecurityDesc())
                        .apply();
            }else {
                SPUtils.getInstance(mContext,SPUtils.SP_INT_HOT_SELECTED)
                        .putString(data.get(i).getSecurityGrpCode(),"")
                        .apply();
            }
        }
    }

    /**
     * 获取产品列表
     */
    private void FetchList() {
        final LoadingView loadingView = new LoadingView(mContext);
        loadingView.show();
        RetrofitClient
                .getInstance()
                .api()
                .getInternationalList()
                .compose(SchedulersCompat.<InternationalListBean>applyIoSchedulers())
                .subscribe(new BaseSubscriber<InternationalListBean>() {
                    @Override
                    public void onSuccess(InternationalListBean bean) {
                        loadingView.dismiss();
                        for (int i = 0; i < bean.getInstrumentRealmarket().size(); i++) {
                            if (!SPUtils.getInstance(mContext, SPUtils.SP_INT_HOT_SELECTED)
                                    .getString(bean.getInstrumentRealmarket().get(i).getSecurityGrpCode(), "").equals("")) {
                                InternationalListBean.InstrumentRealmarketBean newBean = bean.getInstrumentRealmarket().get(i);
                                newBean.setChecked(true);
                                data.add(newBean);
                            }else {
                                InternationalListBean.InstrumentRealmarketBean newBean = bean.getInstrumentRealmarket().get(i);
                                data.add(newBean);
                            }

                        }
                        selectedAdapter.setNewData(data);
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        ToastUtil.showShort(mContext,"获取列表失败");
                    }
                });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.top_text_right:
                saveListDataToSP();
                ToastUtil.showShort(mContext, "保存成功");
                finish();
                break;
            case R.id.top_back:
                finish();
                break;
        }
    }

    static class SelectedAdapter extends BaseQuickAdapter<InternationalListBean.InstrumentRealmarketBean> {



        public SelectedAdapter() {
            super(R.layout.activity_hot_item, null);
        }

        @Override
        protected void convert(final BaseViewHolder baseViewHolder, final InternationalListBean.InstrumentRealmarketBean bean) {


            baseViewHolder
                    .setText(R.id.activity_hot_tv, bean.getSecurityDesc());

            final CheckBox cb_choice = baseViewHolder.getView(R.id.cb_choice);
            cb_choice.setChecked(bean.isChecked());

            baseViewHolder.convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (cb_choice.isChecked()) {
                        bean.setChecked(false);
                    }else {
                        bean.setChecked(true);
                    }
                    notifyItemChanged(baseViewHolder.getAdapterPosition());
                }
            });
        }

    }


}
