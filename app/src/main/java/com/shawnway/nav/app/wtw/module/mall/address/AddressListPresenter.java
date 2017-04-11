package com.shawnway.nav.app.wtw.module.mall.address;

import android.app.Activity;

import com.shawnway.nav.app.wtw.base.BasePresenter;
import com.shawnway.nav.app.wtw.module.login_register.login.LoginActivity;
import com.shawnway.nav.app.wtw.module.mall.address.bean.AddressListResultBean;
import com.shawnway.nav.app.wtw.module.mall.address.bean.DeleteAddressResultBean;
import com.shawnway.nav.app.wtw.module.mall.address.bean.SetDefaultAddrResultBean;

import retrofit2.Response;
import rx.functions.Action1;

/**
 * Created by Cicinnus on 2016/11/16.
 */

@SuppressWarnings("unchecked")
public class AddressListPresenter extends BasePresenter<AddressListContract.IAddressListView> implements AddressListContract.IAddressListPresenter {

    private final AddressListManager addressListManager;

    public AddressListPresenter(Activity activity, AddressListContract.IAddressListView view) {
        super(activity, view);

        addressListManager = new AddressListManager();
    }

    @Override
    public void getAddressList() {
        mView.showLoading();
        addSubscribe(addressListManager.getAddressList()
                .subscribe(new Action1<Response<AddressListResultBean>>() {
                    @Override
                    public void call(Response<AddressListResultBean> response) {
                        if (response.code() == 200) {
                            mView.showContent();
                            mView.addAddressList(response.body().getOrderAddress());
                        } else if (response.code() == 401) {
                            LoginActivity.getInstance(mActivity);
                            mActivity.finish();
                        }else {
                            mView.showError("");
                        }
                    }

                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        mView.showError("");
                        handleError(throwable);
                    }
                }));
    }


    @Override
    public void setDefaultAddress(int id) {
        mView.showLoading();
        addSubscribe(addressListManager.setDefaultAddress(id)
                .subscribe(new Action1<SetDefaultAddrResultBean>() {
                    @Override
                    public void call(SetDefaultAddrResultBean resultBean) {
                        mView.showContent();
                        mView.setDefaultAddressSuccess();
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        handleError(throwable);
                    }
                }));
    }

    @Override
    public void deleteAddress(int id) {
        mView.showLoading();
        addSubscribe(addressListManager.deleteAddress(id)
                .subscribe(new Action1<DeleteAddressResultBean>() {
                    @Override
                    public void call(DeleteAddressResultBean deleteAddressResultBean) {
                        mView.showContent();
                        mView.deleteAddressSuccess();
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        handleError(throwable);
                    }
                }));
    }


}
