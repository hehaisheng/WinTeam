package com.shawnway.nav.app.wtw.module.user.user_center;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.shawnway.nav.app.wtw.R;
import com.shawnway.nav.app.wtw.base.BaseActivity;
import com.shawnway.nav.app.wtw.base.BaseSubscriber;
import com.shawnway.nav.app.wtw.module.quotation.wp.wp_detail.tran.bean.WPStateDescWrapper;
import com.shawnway.nav.app.wtw.net.WPRetrofitClient;
import com.shawnway.nav.app.wtw.tool.SPUtils;
import com.shawnway.nav.app.wtw.tool.SchedulersCompat;
import com.shawnway.nav.app.wtw.view.NormalDialog;

/**
 * Created by Kevin on 2016/12/13.
 * 解绑银行卡
 */
public class UnbindlingBankCardActivity extends BaseActivity implements View.OnClickListener {

    private EditText mEtTranPwd;
    private Button mBtnConfirmUnbund;
    private NormalDialog unBundBanckDialog;


    public static void getInstance(Context context) {
        Intent intent = new Intent(context, UnbindlingBankCardActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_unbind_wp_bankcard;
    }

    @Override
    protected void initEventAndData() {
        initTitleBar();
        initView();
        initListener();
    }

    private void initListener() {
        mBtnConfirmUnbund.setOnClickListener(this);
    }

    private void initView() {
        mEtTranPwd = (EditText) findViewById(R.id.activity_unbundbankcard_et_tranpwd);
        mBtnConfirmUnbund = (Button) findViewById(R.id.activity_unbundbankcard_btn);
    }

    private void initTitleBar() {
        findViewById(R.id.toolbar).setVisibility(View.VISIBLE);
        TextView centerText = (TextView) findViewById(R.id.top_text_center);
        centerText.setVisibility(View.VISIBLE);
        centerText.setText("解绑银行卡");
        ImageButton backButton = (ImageButton) findViewById(R.id.top_back);
        backButton.setVisibility(View.VISIBLE);
        backButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.top_back:
                finish();
                break;
            case R.id.activity_unbundbankcard_btn:
                if (!TextUtils.isEmpty(mEtTranPwd.getText().toString())
                        && !TextUtils.equals(mEtTranPwd.getText().toString(), "")) {
                    unBundBanckDialog = NormalDialog.newInstance("解绑", "是否解绑银行卡", "确定");
                    unBundBanckDialog.setOnDialogClickListener(new NormalDialog.OnConfirmClickListener() {
                        @Override
                        public void onClick() {
                            unBundBankCard();
                        }
                    });
                    unBundBanckDialog.showDialog(getSupportFragmentManager());
                } else Toast.makeText(mContext, "交易密码不能为空", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }

    /**
     * 解绑银行卡
     */
    private void unBundBankCard() {
        unBundBanckDialog.dismiss();
        String access_token = SPUtils.getInstance(mContext,
                SPUtils.SP_WP)
                .getString(SPUtils.WP_TOKEN, "");

        WPRetrofitClient.getInstance()
                .api()
                .unBundBankCard(mEtTranPwd.getText().toString(), access_token)
                .compose(SchedulersCompat.<WPStateDescWrapper>applyIoSchedulers())
                .subscribe(new BaseSubscriber<WPStateDescWrapper>() {
                    @Override
                    public void onSuccess(WPStateDescWrapper wpStateDescWrapper) {
                        Toast.makeText(mContext, checkDesc(wpStateDescWrapper.desc), Toast.LENGTH_SHORT).show();
                        if ("解绑成功".equals(checkDesc(wpStateDescWrapper.desc))) {
                            finish();
                        }
                    }
                });
    }

    private String DESC_MORE_THAN_TWO = "balance_more_than_2"; //用户余额大于2元
    private String DESC_EXIST_PPOSITIONS = "exist_positions";//用户持仓单不为空
    private String DESC_WRONG_PWD = "wrong_password"; //交易密码错误
    private String DESC_EXIST_FROZEN = "exist_frozen_balance";//用户有冻结资金


    /**
     * 检查返回结果
     *
     * @param desc
     */
    private String checkDesc(String desc) {
        if (TextUtils.equals(desc, DESC_MORE_THAN_TWO)) {
            return "用户余额大于2元";
        }
        if (TextUtils.equals(desc, DESC_EXIST_PPOSITIONS)) {
            return "用户当前有持仓单";
        }
        if (TextUtils.equals(desc, DESC_WRONG_PWD)) {
            return "交易密码错误";
        }
        if (TextUtils.equals(desc, DESC_EXIST_FROZEN)) {
            return "用户有冻结资金";
        }
        return "解绑成功";
    }
}
