package com.shawnway.nav.app.wtw.module.user;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shawnway.nav.app.wtw.R;
import com.shawnway.nav.app.wtw.base.BaseFragment;
import com.shawnway.nav.app.wtw.module.login_register.login.LoginActivity;
import com.shawnway.nav.app.wtw.module.mall.address.AddressListActivity;
import com.shawnway.nav.app.wtw.module.mall.user_orders.UserOrdersActivity;
import com.shawnway.nav.app.wtw.module.user.latest_act.LatestActActivity;
import com.shawnway.nav.app.wtw.module.user.message_center.CenterMessageActivity;
import com.shawnway.nav.app.wtw.module.user.promotion.PromotionActivity;
import com.shawnway.nav.app.wtw.module.user.setting.SettingActivity;
import com.shawnway.nav.app.wtw.module.user.ticket.MyTicketActivity;
import com.shawnway.nav.app.wtw.module.user.user_center.UserCenterActivity;
import com.shawnway.nav.app.wtw.tool.SPUtils;
import com.shawnway.nav.app.wtw.tool.StringUtils;
import com.shawnway.nav.app.wtw.tool.Utils;
import com.shawnway.nav.app.wtw.view.NormalDialog;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/7/19 0019.
 */
public class UserFragment extends BaseFragment implements View.OnClickListener {

    private String acc;

    public static UserFragment newInstance() {

        Bundle args = new Bundle();

        UserFragment fragment = new UserFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private static final String TAG = "UserFragment";

    @BindView(R.id.toolbar)
    RelativeLayout toolbar;
    @BindView(R.id.top_text_center)
    TextView center;
    @BindView(R.id.fragment_user_btn_login)
    Button mBtn;
    @BindView(R.id.fragment_user_tv_user)
    TextView mTv;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_user;
    }

    @Override
    protected void initEventAndData() {
        initToolBar();
    }

    @Override
    public void onResume() {
        super.onResume();
        acc = SPUtils.getInstance(mContext, SPUtils.SP_ACCOUNT).getString(SPUtils.PHONE, "");

        if (StringUtils.isEmpty(acc)) {
            mBtn.setVisibility(View.VISIBLE);
            mTv.setVisibility(View.GONE);

        } else {
            mBtn.setVisibility(View.GONE);
            mTv.setVisibility(View.VISIBLE);
            mTv.setText(Utils.setPhoneNum(acc));

        }
    }


    private void initToolBar() {
        toolbar.setVisibility(View.VISIBLE);
        center.setVisibility(View.VISIBLE);
        center.setText("个人");
    }

    @OnClick({
            R.id.fragment_user_ticket_wrapper,
            R.id.fragment_user_invite_wrapper,
            R.id.fragment_user_infocenter_wrapper,
            R.id.fragment_user_newact_wrapper,
            R.id.fragment_user_trancir_wrapper,
            R.id.fragment_user_guide_wrapper,
            R.id.fragment_user_setting_wrapper,
            R.id.fragment_user_per_bg,
            R.id.user_order_list,
            R.id.user_address,
            R.id.fragment_user_btn_login})
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.fragment_user_btn_login:
                LoginActivity.getInstance(getActivity());
                break;
            case R.id.fragment_user_ticket_wrapper://我的红包
                if (!acc.equals("")) {
                    MyTicketActivity.getInstance(getContext());
                }else {
                    LoginActivity.getInstance(mContext);
                }
                break;
            case R.id.fragment_user_invite_wrapper://邀请好友
                PromotionActivity.start(getActivity());
                break;
            case R.id.fragment_user_per_bg://头像
                if(!acc.equals("")) {
                    Intent UserCenterintent = new Intent(getActivity(), UserCenterActivity.class);
                    startActivity(UserCenterintent);
                }else {
                    LoginActivity.getInstance(mContext);
                }
                break;
            case R.id.fragment_user_setting_wrapper://设置
                Intent intent = new Intent(getActivity(), SettingActivity.class);
                startActivity(intent);
                break;
            case R.id.fragment_user_infocenter_wrapper://消息中心TODO:
                Intent Centerintent = new Intent(getActivity(), CenterMessageActivity.class);
                startActivity(Centerintent);
                break;
            case R.id.fragment_user_newact_wrapper://最新活动TODO:
                LatestActActivity.getInstance(getContext());
                break;
            case R.id.fragment_user_trancir_wrapper://交易圈子
                final NormalDialog normalDialog = NormalDialog.newInstance("功能暂未开放", "敬请期待", "确定");
                normalDialog.setOnDialogClickListener(new NormalDialog.OnConfirmClickListener() {
                    @Override
                    public void onClick() {
                        normalDialog.dismiss();
                    }
                });
                normalDialog.showDialog(getFragmentManager());
                break;
            case R.id.fragment_user_guide_wrapper://新手指引
                NewGuidelinesActivity.getInstance(getContext());
                break;
            case R.id.user_order_list://我的订单
                UserOrdersActivity.start(getActivity());
                break;
            case R.id.user_address://地址管理
                AddressListActivity.start(getActivity());
                break;
        }
    }

    public static class LoginEvent{}

    public static class LogoutEvent{}

}
