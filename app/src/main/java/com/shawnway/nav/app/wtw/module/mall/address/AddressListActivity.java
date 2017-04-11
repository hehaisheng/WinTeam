package com.shawnway.nav.app.wtw.module.mall.address;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shawnway.nav.app.wtw.R;
import com.shawnway.nav.app.wtw.base.BaseActivity;
import com.shawnway.nav.app.wtw.module.mall.address.bean.AddressListResultBean;
import com.shawnway.nav.app.wtw.tool.Utils;
import com.shawnway.nav.app.wtw.view.LoadingView;
import com.shawnway.nav.app.wtw.view.NormalDialog;
import com.shawnway.nav.app.wtw.view.ProgressLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Cicinnus on 2016/11/16.
 */

public class AddressListActivity extends BaseActivity<AddressListPresenter> implements AddressListContract.IAddressListView {

    @BindView(R.id.progress_layout)
    ProgressLayout progressLayout;
    @BindView(R.id.top_back)
    ImageButton topBack;
    @BindView(R.id.top_text_center)
    TextView topTextCenter;
    @BindView(R.id.toolbar)
    RelativeLayout toolbar;
    @BindView(R.id.rv_addressList)
    RecyclerView rvAddressList;
    private AddressListAdapter adapter;

    private int deletePosition;
    private View.OnClickListener click = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mPresenter.getAddressList();
        }
    };
    private LoadingView loadingView;

    public static void start(Context context) {
        Intent starter = new Intent(context, AddressListActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected AddressListPresenter getPresenter() {
        return new AddressListPresenter(mContext, this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_address_list;
    }

    @Override
    protected void initEventAndData() {
        loadingView = new LoadingView(mContext);
        topTextCenter.setText("收货地址管理");
        setVisiable(topBack, toolbar, topTextCenter);
        adapter = new AddressListAdapter();
        rvAddressList.setLayoutManager(new LinearLayoutManager(mContext));
        rvAddressList.setAdapter(adapter);
        View emptyView = LayoutInflater.from(mContext).inflate(R.layout.layout_no_data, (ViewGroup) rvAddressList.getParent(), false);
        ((TextView) emptyView.findViewById(R.id.no_data_text)).setText("还没有收货地址~");
        adapter.setEmptyView(emptyView);
        adapter.setOnSetDefaultAddressClickListener(new AddressListAdapter.OnSetDefaultAddressClickListener() {
            @Override
            public void onClick(int id) {
                mPresenter.setDefaultAddress(id);
            }
        });
        adapter.setOnDeleteAddressClickListener(new AddressListAdapter.OnDeleteAddressClickListener() {
            @Override
            public void onClick(final int position, final int id) {
                final NormalDialog normalDialog = NormalDialog
                        .newInstance("删除该地址？", "删除后无法恢复", "确定");
                normalDialog
                        .showDialog(getSupportFragmentManager())
                        .setOnDialogClickListener(new NormalDialog.OnConfirmClickListener() {
                            @Override
                            public void onClick() {
                                deletePosition = position;
                                mPresenter.deleteAddress(id);
                                normalDialog.dismiss();
                            }
                        });

            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.getAddressList();
    }

    @OnClick({R.id.top_back, R.id.add_address})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.top_back:
                finish();
                break;
            case R.id.add_address:
                AddAddressActivity.start(mContext);
                break;
        }
    }

    @Override
    public void addAddressList(List<AddressListResultBean.OrderAddressBean> beanList) {
        adapter.setNewData(beanList);
    }

    @Override
    public void deleteAddressSuccess() {
        adapter.remove(deletePosition);

    }

    @Override
    public void setDefaultAddressSuccess() {
        mPresenter.getAddressList();
    }

    @Override
    public void showLoading() {
        loadingView.show();
    }

    @Override
    public void showNoData() {

    }

    @Override
    public void showContent() {
        if (!progressLayout.isContent()) {
            progressLayout.showContent();
        }
        loadingView.dismiss();
    }

    @Override
    public void showError(String errorMsg) {
        loadingView.dismiss();
        progressLayout.showError(click);
    }

    private static class AddressListAdapter extends BaseQuickAdapter<AddressListResultBean.OrderAddressBean> {

        private OnSetDefaultAddressClickListener onSetDefaultAddressClickListener;
        private OnDeleteAddressClickListener onDeleteAddressClickListener;

        AddressListAdapter() {
            super(R.layout.item_address, null);
        }

        @Override
        protected void convert(final BaseViewHolder baseViewHolder, final AddressListResultBean.OrderAddressBean
                bean) {
            baseViewHolder
                    .setText(R.id.consignee, String.format("%s", bean.getConsignee()))
                    .setText(R.id.phone, Utils.setPhoneNum(String.format("%s", bean.getCellphone())))
                    .setText(R.id.address, String.format("%s", bean.getAddress()));

            final CheckBox cb = baseViewHolder.getView(R.id.cb_defaultAdd);
            cb.setText(bean.getDefaultAddress() != 0 ? "默认地址" : "设为默认地址");
            cb.setChecked(bean.getDefaultAddress() != 0);
            cb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onSetDefaultAddressClickListener != null) {
                        cb.setChecked(false);
                        onSetDefaultAddressClickListener.onClick(bean.getId());
                    }
                }
            });
            TextView tv_delete = baseViewHolder.getView(R.id.tv_delete);
            tv_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onDeleteAddressClickListener != null) {
                        onDeleteAddressClickListener.onClick(baseViewHolder.getAdapterPosition(), bean.getId());
                    }
                }
            });

            TextView tv_edit = baseViewHolder.getView(R.id.tv_edit);
            tv_edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AddAddressActivity.start(mContext, bean);
                }
            });
        }

        void setOnSetDefaultAddressClickListener(OnSetDefaultAddressClickListener onSetDefaultAddressClickListener) {
            this.onSetDefaultAddressClickListener = onSetDefaultAddressClickListener;
        }

        public void setOnDeleteAddressClickListener(OnDeleteAddressClickListener onDeleteAddressClickListener) {
            this.onDeleteAddressClickListener = onDeleteAddressClickListener;
        }

        public interface OnSetDefaultAddressClickListener {
            void onClick(int id);
        }

        public interface OnDeleteAddressClickListener {
            void onClick(int position, int id);
        }

        public interface OnEditAddressClickListener {
            void onClick(int position, AddressListResultBean.OrderAddressBean addressBean);
        }
    }


}
