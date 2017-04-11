package com.shawnway.nav.app.wtw.module.mall.address;

import com.shawnway.nav.app.wtw.base.ILoadingView;
import com.shawnway.nav.app.wtw.module.mall.address.bean.AddressListResultBean;

import java.util.List;

/**
 * Created by Cicinnus on 2016/11/16.
 */

public class AddressListContract {

    public interface IAddressListView extends ILoadingView {
        void addAddressList(List<AddressListResultBean.OrderAddressBean> beanList);

        void deleteAddressSuccess();

        void setDefaultAddressSuccess();
    }

    public interface IAddressListPresenter {
        void getAddressList();

        void setDefaultAddress(int id);

        void deleteAddress(int id);


    }
}
