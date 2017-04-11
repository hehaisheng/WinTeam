package com.shawnway.nav.app.wtw.module.mall.productDetail;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.shawnway.nav.app.wtw.R;
import com.shawnway.nav.app.wtw.base.BaseFragment;
import com.shawnway.nav.app.wtw.bean.BannerPicBean;
import com.shawnway.nav.app.wtw.module.login_register.login.LoginActivity;
import com.shawnway.nav.app.wtw.module.mall.shopping_car.bean.ShoppingCarResultBean;
import com.shawnway.nav.app.wtw.tool.ToastUtil;
import com.shawnway.nav.app.wtw.view.Banner;
import com.shawnway.nav.app.wtw.view.LoadingView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Kevin on 2016/11/23
 */

public class ProductDetailFragment extends BaseFragment<ProductDetailPresenter> implements ProductDetailContract.IProductDetailView {


    @BindView(R.id.rv_product_detail)
    ListView listView;

    private LoadingView loadingGif;
    private View.OnClickListener tryClick;
    private int quantity = 1;
    private int point;
    private String imgUrl;
    private String productName;

    private int proId;
    private OnProductDeatilFragmentListener onProductDeatilFragmentListener;
    private View view;

    public static ProductDetailFragment newInstance(int id) {

        Bundle args = new Bundle();
        args.putInt("proId", id);
        ProductDetailFragment fragment = new ProductDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected ProductDetailPresenter getPresenter() {
        return new ProductDetailPresenter(mContext, this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_product_deatil;
    }

    @Override
    protected void initEventAndData() {
        loadingGif = new LoadingView(mContext);
        proId = getArguments().getInt("proId", -1);
        tryClick = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.getProDeteail(proId);
            }
        };
        mPresenter.getProDeteail(proId);
    }

    private void initListView(List<ProDetailBean> list) {
        view = mContext.getLayoutInflater()
                .inflate(R.layout.layout_loading_more, null);
        view.findViewById(R.id.progressbar).setVisibility(View.GONE);
        ((TextView) view.findViewById(R.id.loading_text)).setText("向上滑动展开商品详细详情");
        onProductDeatilFragmentListener.changFootView((TextView) view.findViewById(R.id.loading_text));
        listView.addFooterView(view);
        listView.setAdapter(new Adapter(mContext, list));
    }

    @Override
    public void addProDetail(ProDetailBean proDetailBean) {
        loadingGif.dismiss();
        List<ProDetailBean> list = new ArrayList<>();
        list.add(proDetailBean);
        initListView(list);
    }


    @Override
    public void adding() {

    }

    @Override
    public void addShoppingCarSuccess(ShoppingCarResultBean bean) {
        ToastUtil.showShort(mContext, bean.getDesc());
    }

    @Override
    public void jumpLogin() {
        LoginActivity.getInstance(mContext);
    }

    @Override
    public void showLoading() {
        loadingGif.show();
    }

    @Override
    public void showNoData() {

    }

    @Override
    public void showContent() {
        loadingGif.dismiss();
        onProductDeatilFragmentListener.onContent();
    }

    @Override
    public void showError(String errorMsg) {
        loadingGif.dismiss();
        onProductDeatilFragmentListener.onError(errorMsg);
    }

    public OnProductDeatilFragmentListener getOnProductDeatilFragmentListener() {
        return onProductDeatilFragmentListener;
    }

    public void setOnProductDeatilFragmentListener(OnProductDeatilFragmentListener onProductDeatilFragmentListener) {
        this.onProductDeatilFragmentListener = onProductDeatilFragmentListener;
    }


    public interface OnProductDeatilFragmentListener {
        void onGetDeatilsUrl(String url, int quantity, int point, String imgUrl, String productName);

        void onQuantityClick(int quantity);

        void changFootView(TextView textView);

        void onError(String errormsg);

        void onContent();
    }


    private class Adapter extends BaseAdapter {

        private List<ProDetailBean> datas;
        private Context mContext;
        private Banner banner;

        Adapter(Context mContext, List<ProDetailBean> datas) {
            this.mContext = mContext;
            this.datas = datas;
            banner = new Banner(mContext);
        }

        @Override
        public int getCount() {
            return null == datas ? 0 : datas.size();
        }

        @Override
        public ProDetailBean getItem(int position) {
            return null == datas ? null : datas.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ProDetailBean.ProductEntityBean bean = datas.get(position).getProductEntity();
            if (null == convertView) {
                convertView = getActivity().getLayoutInflater().inflate(R.layout.item_product_detail, null);
            }
            ((TextView) convertView.findViewById(R.id.proCode)).setText(String.format("商品编码：%s", bean.getProCode()));
            ((TextView) convertView.findViewById(R.id.proName)).setText(bean.getProName());
            ((TextView) convertView.findViewById(R.id.proPrice)).setText(String.format("市场价：%s", bean.getProPrice()));
            ((TextView) convertView.findViewById(R.id.proAmount)).setText(String.format("库存：%s", bean.getProAmount()));
            ((TextView) convertView.findViewById(R.id.proDisPrice)).setText(String.format("%s 积分", bean.getProDiscCsptPoint()));
            ((TextView) convertView.findViewById(R.id.proNaturePrice)).setText(String.format("%s 积分", bean.getProRealCsptPoint()));
            ((TextView) convertView.findViewById(R.id.exchangedAmount)).setText(String.format("已有%s人兑换", bean.getProExchangedAmount()));
            ((TextView) convertView.findViewById(R.id.proDesc)).setText(String.format("%s", bean.getProDesc()));
            final List<BannerPicBean.PicBean> urls = new ArrayList<>();
            String url1 = bean.getProImg1();
            String url2 = bean.getProImg2();
            String url3 = bean.getProImg3();
            String url4 = bean.getProImg4();
            String url5 = bean.getProImg5();
            if (url1!=null) {
                BannerPicBean.PicBean picBean1 = new BannerPicBean.PicBean();
                picBean1.picUrl = url1;
                urls.add(picBean1);
            }
            if (url2!=null) {
                BannerPicBean.PicBean picBean2 = new BannerPicBean.PicBean();
                picBean2.picUrl = url2;
                urls.add(picBean2);
            }
            if (url3!=null) {
                BannerPicBean.PicBean picBean3 = new BannerPicBean.PicBean();
                picBean3.picUrl = url3;
                urls.add(picBean3);
            }
            if (url4!=null) {
                BannerPicBean.PicBean picBean4 = new BannerPicBean.PicBean();
                picBean4.picUrl = url4;
                urls.add(picBean4);
            }
            if (url5!=null) {
                BannerPicBean.PicBean picBean5 = new BannerPicBean.PicBean();
                picBean5.picUrl = url5;
                urls.add(picBean5);
            }
            point = bean.getProDiscCsptPoint();
            productName = bean.getProName();
            imgUrl = bean.getProImg1();
            final TextView proQuantity = (TextView) convertView.findViewById(R.id.proQuantity);
            convertView.findViewById(R.id.addQuantity).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    quantity = Integer.parseInt(proQuantity.getText().toString());
                    quantity += 1;
                    proQuantity.setText(String.format("%s", quantity));
                    onProductDeatilFragmentListener.onQuantityClick(quantity);
                }
            });
            convertView.findViewById(R.id.reduceQuantity).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    quantity = Integer.parseInt(proQuantity.getText().toString());
                    if (quantity >= 2) {
                        quantity -= 1;
                        proQuantity.setText(String.format("%s", quantity));
                    }
                    onProductDeatilFragmentListener.onQuantityClick(quantity);
                }
            });
            banner = (Banner) convertView.findViewById(R.id.banner);
            banner.setmScyleType(ImageView.ScaleType.CENTER_INSIDE);
            banner.setPic(urls);
            onProductDeatilFragmentListener.onGetDeatilsUrl(bean.getProDetail(), quantity, point, imgUrl, productName);
            return convertView;
        }
    }
}
