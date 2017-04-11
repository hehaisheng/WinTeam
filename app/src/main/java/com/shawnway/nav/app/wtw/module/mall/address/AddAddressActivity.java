package com.shawnway.nav.app.wtw.module.mall.address;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shawnway.nav.app.wtw.R;
import com.shawnway.nav.app.wtw.base.BaseActivity;
import com.shawnway.nav.app.wtw.module.mall.address.bean.AddAddrResultBean;
import com.shawnway.nav.app.wtw.module.mall.address.bean.AddressListResultBean;
import com.shawnway.nav.app.wtw.module.mall.address.bean.UpdateAddressResultBean;
import com.shawnway.nav.app.wtw.net.ErrorHanding;
import com.shawnway.nav.app.wtw.net.JsonRequestBody;
import com.shawnway.nav.app.wtw.net.RetrofitClient;
import com.shawnway.nav.app.wtw.tool.SchedulersCompat;
import com.shawnway.nav.app.wtw.tool.ToastUtil;
import com.shawnway.nav.app.wtw.view.LoadingView;
import com.shawnway.nav.app.wtw.view.city_pick.AddrXmlParser;
import com.shawnway.nav.app.wtw.view.city_pick.city_bean.CityInfoModel;
import com.shawnway.nav.app.wtw.view.city_pick.city_bean.DistrictInfoModel;
import com.shawnway.nav.app.wtw.view.city_pick.city_bean.ProvinceInfoModel;
import com.shawnway.nav.app.wtw.view.city_pick.widget.WheelView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import butterknife.BindView;
import butterknife.OnClick;
import rx.functions.Action1;

/**
 * Created by Cicinnus on 2016/11/16.
 */

public class AddAddressActivity extends BaseActivity {


    @BindView(R.id.root_view)
    LinearLayout root_view;
    @BindView(R.id.top_back)
    ImageButton topBack;
    @BindView(R.id.top_text_center)
    TextView topTextCenter;
    @BindView(R.id.toolbar)
    RelativeLayout toolbar;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.pick_city)
    TextView pickCity;
    @BindView(R.id.et_addr_detail)
    EditText etAddrDetail;
    @BindView(R.id.btn_save)
    Button btnSave;


    /**
     * 与选择地址相关
     */
    protected ArrayList<String> mProvinceDatas;
    protected Map<String, ArrayList<String>> mCitisDatasMap = new HashMap<String, ArrayList<String>>();
    protected Map<String, ArrayList<String>> mDistrictDatasMap = new HashMap<String, ArrayList<String>>();
    protected String mCurrentProviceName;
    protected String mCurrentCityName;
    protected String mCurrentDistrictName;

    //弹窗View
    private View contentView;
    //弹窗
    private PopupWindow addrPopWindow;
    //三个WheelView
    private WheelView mProvincePicker;
    private WheelView mCityPicker;
    private WheelView mCountyPicker;
    private TextView confirm;
    private TextView cancel;
    private boolean isAddrChoosed = false;
    private boolean isDataLoaded = false;
    private LoadingView loadingView;
    private AddressListResultBean.OrderAddressBean bean;


    public static void start(Context context) {
        Intent starter = new Intent(context, AddAddressActivity.class);
        context.startActivity(starter);
    }

    public static void start(Context context, AddressListResultBean.OrderAddressBean bean) {
        Intent starter = new Intent(context, AddAddressActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable("address", bean);
        starter.putExtra("bundle", bundle);
        context.startActivity(starter);
    }


    @Override
    protected int getLayout() {
        return R.layout.activity_add_address;
    }

    @Override
    protected void initEventAndData() {
        loadingView = new LoadingView(mContext);
        initTopBar();
        initPickCityView();
        Bundle bundle = getIntent().getBundleExtra("bundle");
        if (bundle != null) {
            bean = bundle.getParcelable("address");
        }
        if (bean != null) {
            etName.setText(bean.getConsignee());
            etPhone.setText(bean.getCellphone());
            String address = bean.getAddress();
            pickCity.setText(address.substring(0, address.indexOf("-")));
            etAddrDetail.setText(address.substring(address.indexOf("-") + 1, address.length()));
        }
    }


    private void initTopBar() {
        topTextCenter.setText("新增地址");
        topTextCenter.setVisibility(View.VISIBLE);
        topBack.setVisibility(View.VISIBLE);
        toolbar.setVisibility(View.VISIBLE);
    }

    /**
     * 初始化选择器
     */
    private void initPickCityView() {
        contentView = LayoutInflater.from(this).inflate(
                R.layout.layout_addr_picker, null);
        mProvincePicker = (WheelView) contentView.findViewById(R.id.province);
        mCityPicker = (WheelView) contentView.findViewById(R.id.city);
        mCountyPicker = (WheelView) contentView.findViewById(R.id.county);
        cancel = (TextView) contentView.findViewById(R.id.cancel);
        confirm = (TextView) contentView.findViewById(R.id.confirm);

        addrPopWindow = new PopupWindow(contentView, LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT, true);


        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addrPopWindow.dismiss();
            }
        });
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isAddrChoosed = true;
                String addr = mCurrentProviceName + mCurrentCityName;
                if (!mCurrentDistrictName.equals("其他")) {
                    addr += mCurrentDistrictName;
                }
                pickCity.setText(addr);
                addrPopWindow.dismiss();
            }
        });

        mProvincePicker.setOnSelectListener(new WheelView.OnSelectListener() {
            @Override
            public void endSelect(int id, String text) {
                String provinceText = mProvinceDatas.get(id);
                if (!mCurrentProviceName.equals(provinceText)) {
                    mCurrentProviceName = provinceText;
                    ArrayList<String> mCityData = mCitisDatasMap.get(mCurrentProviceName);
                    mCityPicker.resetData(mCityData);
                    mCityPicker.setDefault(0);
                    mCurrentCityName = mCityData.get(0);

                    ArrayList<String> mDistrictData = mDistrictDatasMap.get(mCurrentCityName);
                    mCountyPicker.resetData(mDistrictData);
                    mCountyPicker.setDefault(0);
                    mCurrentDistrictName = mDistrictData.get(0);
                }
            }

            @Override
            public void selecting(int id, String text) {
            }
        });

        mCityPicker.setOnSelectListener(new WheelView.OnSelectListener() {
            @Override
            public void endSelect(int id, String text) {
                ArrayList<String> mCityData = mCitisDatasMap.get(mCurrentProviceName);
                String cityText = mCityData.get(id);
                if (!mCurrentCityName.equals(cityText)) {
                    mCurrentCityName = cityText;
                    ArrayList<String> mCountyData = mDistrictDatasMap.get(mCurrentCityName);
                    mCountyPicker.resetData(mCountyData);
                    mCountyPicker.setDefault(0);
                    mCurrentDistrictName = mCountyData.get(0);
                }
            }

            @Override
            public void selecting(int id, String text) {

            }
        });

        mCountyPicker.setOnSelectListener(new WheelView.OnSelectListener() {
            @Override
            public void endSelect(int id, String text) {
                ArrayList<String> mDistrictData = mDistrictDatasMap.get(mCurrentCityName);
                String districtText = mDistrictData.get(id);
                if (!mCurrentDistrictName.equals(districtText)) {
                    mCurrentDistrictName = districtText;
                }
            }

            @Override
            public void selecting(int id, String text) {

            }
        });

        // 启动线程读取数据
        new Thread() {
            @Override
            public void run() {
                // 读取数据
                isDataLoaded = readAddrDatas();

                // 通知界面线程
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        mProvincePicker.setData(mProvinceDatas);
                        mProvincePicker.setDefault(0);
                        mCurrentProviceName = mProvinceDatas.get(0);

                        ArrayList<String> mCityData = mCitisDatasMap.get(mCurrentProviceName);
                        mCityPicker.setData(mCityData);
                        mCityPicker.setDefault(0);
                        mCurrentCityName = mCityData.get(0);

                        ArrayList<String> mDistrictData = mDistrictDatasMap.get(mCurrentCityName);
                        mCountyPicker.setData(mDistrictData);
                        mCountyPicker.setDefault(0);
                        mCurrentDistrictName = mDistrictData.get(0);
                    }
                });
            }
        }.start();
    }


    @OnClick({R.id.top_back, R.id.pick_city, R.id.btn_save})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.top_back:
                finish();
                break;
            case R.id.pick_city:
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                if (isDataLoaded)
                    addrPopWindow.showAtLocation(root_view, Gravity.BOTTOM, 0, 0);
                break;
            case R.id.btn_save:
                if (etName.getText().length() <= 0) {
                    ToastUtil.showShort(mContext, "请输入收货人姓名");
                    break;
                }
                if (etPhone.getText().length() != 11) {
                    ToastUtil.showShort(mContext, "请输入正确的收货人联系方式");
                    break;
                }
                if ("请选择".equals(pickCity.getText())) {
                    ToastUtil.showShort(mContext, "请选择收货地区");
                    break;
                }
                if (etAddrDetail.getText().length() <= 0) {
                    ToastUtil.showShort(mContext, "请输入详细的地址");
                    break;
                }
                if (bean == null) {
                    addAddr();
                } else {
                    updateAddr();
                }
                break;
        }
    }

    /**
     * 添加地址到服务器
     */
    private void addAddr() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("address",
                    pickCity.getText().toString().trim() + "-" + etAddrDetail.getText().toString().trim());
            jsonObject.put("cellphone", etPhone.getText().toString().trim());
            jsonObject.put("consignee", etName.getText().toString().trim());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        loadingView.show();
        RetrofitClient
                .getInstance()
                .api()
                .addAddr(JsonRequestBody.getInstance().convertJsonContent(jsonObject.toString()))
                .compose(SchedulersCompat.<AddAddrResultBean>applyIoSchedulers())
                .subscribe(new Action1<AddAddrResultBean>() {
                    @Override
                    public void call(AddAddrResultBean addAddrResultBean) {
                       loadingView.dismiss();
                        if ("403".equals(addAddrResultBean.getStatus())) {
                            ToastUtil.showShort(mContext, String.format("%s", addAddrResultBean.getDesc()));
                        } else {
                            ToastUtil.showShort(mContext, "添加成功");
                            finish();
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        loadingView.dismiss();
                        ToastUtil.showShort(mContext, ErrorHanding.handleError(throwable));
                    }
                });
    }

    /**
     * 更新地址到服务器
     */
    private void updateAddr() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("address",
                    pickCity.getText().toString().trim() + "-" + etAddrDetail.getText().toString().trim());
            jsonObject.put("cellphone", etPhone.getText().toString().trim());
            jsonObject.put("consignee", etName.getText().toString().trim());
            jsonObject.put("defaultAddress", bean.getDefaultAddress());
            jsonObject.put("id", bean.getId());
        } catch (JSONException e) {
            e.printStackTrace();
        }
       loadingView.show();
        RetrofitClient
                .getInstance()
                .api()
                .updateAddress(JsonRequestBody.getInstance().convertJsonContent(jsonObject.toString()))
                .compose(SchedulersCompat.<UpdateAddressResultBean>applyIoSchedulers())
                .subscribe(new Action1<UpdateAddressResultBean>() {
                    @Override
                    public void call(UpdateAddressResultBean updateAddressResultBean) {
                        loadingView.dismiss();
                        ToastUtil.showShort(mContext, "更新成功");
                        finish();
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        loadingView.dismiss();
                        ToastUtil.showShort(mContext, "更新失败" + ErrorHanding.handleError(throwable));
                    }
                });
    }


    /**
     * 读取地址数据，请使用线程进行调用
     *
     * @return
     */
    protected boolean readAddrDatas() {
        List<ProvinceInfoModel> provinceList = null;
        AssetManager asset = getAssets();
        try {
            InputStream input = asset.open("province_data.xml");
            SAXParserFactory spf = SAXParserFactory.newInstance();
            SAXParser parser = spf.newSAXParser();
            AddrXmlParser handler = new AddrXmlParser();
            parser.parse(input, handler);
            input.close();
            provinceList = handler.getDataList();
            if (provinceList != null && !provinceList.isEmpty()) {
                mCurrentProviceName = provinceList.get(0).getName();
                List<CityInfoModel> cityList = provinceList.get(0).getCityList();
                if (cityList != null && !cityList.isEmpty()) {
                    mCurrentCityName = cityList.get(0).getName();
                    List<DistrictInfoModel> districtList = cityList.get(0).getDistrictList();
                    mCurrentDistrictName = districtList.get(0).getName();
                }
            }
            mProvinceDatas = new ArrayList<String>();
            for (int i = 0; i < provinceList.size(); i++) {
                mProvinceDatas.add(provinceList.get(i).getName());
                List<CityInfoModel> cityList = provinceList.get(i).getCityList();
                ArrayList<String> cityNames = new ArrayList<String>();
                for (int j = 0; j < cityList.size(); j++) {
                    cityNames.add(cityList.get(j).getName());
                    List<DistrictInfoModel> districtList = cityList.get(j).getDistrictList();
                    ArrayList<String> distrinctNameArray = new ArrayList<String>();
                    DistrictInfoModel[] distrinctArray = new DistrictInfoModel[districtList.size()];
                    for (int k = 0; k < districtList.size(); k++) {
                        DistrictInfoModel districtModel = new DistrictInfoModel(districtList.get(k).getName(), districtList.get(k).getZipcode());
                        distrinctArray[k] = districtModel;
                        distrinctNameArray.add(districtModel.getName());
                    }
                    mDistrictDatasMap.put(cityNames.get(j), distrinctNameArray);
                }
                mCitisDatasMap.put(provinceList.get(i).getName(), cityNames);
            }
            return true;
        } catch (Throwable e) {
            e.printStackTrace();
            return false;
        }
    }


}
