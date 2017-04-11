package com.shawnway.nav.app.wtw.module.login_register;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.shawnway.nav.app.wtw.R;
import com.shawnway.nav.app.wtw.base.BaseActivity;
import com.shawnway.nav.app.wtw.bean.RegisterWTXBean;
import com.shawnway.nav.app.wtw.module.information.NewsActivity;
import com.shawnway.nav.app.wtw.module.login_register.login.LoginActivity;
import com.shawnway.nav.app.wtw.net.JsonRequestBody;
import com.shawnway.nav.app.wtw.net.RetrofitClient;
import com.shawnway.nav.app.wtw.tool.PasswordUtil;
import com.shawnway.nav.app.wtw.tool.SchedulersCompat;
import com.shawnway.nav.app.wtw.tool.StringUtils;
import com.shawnway.nav.app.wtw.tool.ToastUtil;
import com.shawnway.nav.app.wtw.view.LoadingView;

import org.json.JSONException;
import org.json.JSONObject;

import rx.Observer;

/**
 * Created by Administrator on 2016/7/27 0027.
 */
public class RegisterActivity extends BaseActivity implements View.OnClickListener {

    private EditText mEtPhoneNum;
    private EditText mEtIdenCode;
    private EditText mInvitationCode;
    private Button mBtnGetCode;
    private EditText mEtLoginPwd;
    private EditText mEtNickname;
    private EditText activity_register_pwd_tran;
    private View mLoginWrapper;
    private View mNickNameWrapper;
    private Button mBtnRegister;
    private TextView mTvAgreement;
    private TextView mTvLogin;
    private TextView mTvShawnway;
    private boolean mShowNickName;//显示昵称的时候不显示登录密码
    private boolean isOpen = false;//倒计时开始

    public static void getInstance(Activity context) {
        Intent intent = new Intent(context, RegisterActivity.class);
        context.startActivity(intent);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_register;
    }

    @Override
    protected void initEventAndData() {
        mShowNickName = true;
        initToolbar();
        initView();
        initListener();
    }

    private void initListener() {
        mBtnGetCode.setOnClickListener(this);
        mBtnRegister.setOnClickListener(this);
        mTvAgreement.setOnClickListener(this);
        mTvLogin.setOnClickListener(this);
        mTvShawnway.setOnClickListener(this);
    }

    private void initView() {
        mEtPhoneNum = (EditText) findViewById(R.id.activity_register_phonenum);
        mEtIdenCode = (EditText) findViewById(R.id.activity_register_identifycode);
        mInvitationCode = (EditText) findViewById(R.id.invitationCode);
        mBtnGetCode = (Button) findViewById(R.id.activity_register_btn_getcode);
        mEtLoginPwd = (EditText) findViewById(R.id.activity_register_pwd_login);
        mEtNickname = (EditText) findViewById(R.id.activity_register_nickname);
        activity_register_pwd_tran = (EditText) findViewById(R.id.activity_register_pwd_tran);
        mLoginWrapper = findViewById(R.id.activity_register_loginpwd_wrapper);
        mNickNameWrapper = findViewById(R.id.activity_register_nickname_wrapper);
        mBtnRegister = (Button) findViewById(R.id.activity_register_btn);
        mTvAgreement = (TextView) findViewById(R.id.activity_register_agreement);
        mTvLogin = (TextView) findViewById(R.id.activity_register_login);
        mTvShawnway = (TextView) findViewById(R.id.shawnway);
        if (mShowNickName) {
            mNickNameWrapper.setVisibility(View.VISIBLE);

        } else {
            mNickNameWrapper.setVisibility(View.GONE);
        }
    }

    private void initToolbar() {
        findViewById(R.id.toolbar).setVisibility(View.VISIBLE);
        TextView centerText = (TextView) findViewById(R.id.top_text_center);
        centerText.setVisibility(View.VISIBLE);
        centerText.setText(getResources().getString(
                R.string.register));

        ImageButton backButton = (ImageButton) findViewById(R.id.top_back);
        backButton.setVisibility(View.VISIBLE);
        backButton.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        final String phoneNum = mEtPhoneNum.getText().toString().trim();
        switch (v.getId()) {
            case R.id.top_back:
                finish();
                break;
            case R.id.shawnway:
                NewsActivity.getInstance(this, "http://www.shawnway.cn/", "尚为软件");
                break;
            case R.id.activity_register_btn_getcode://获取验证码
                if (StringUtils.isEmpty(phoneNum)) {
                    ToastUtil.showShort(this, getString(R.string.phone_num_cannot_empty));
                    mEtPhoneNum.requestFocus();
                    return;
                }
                sendCode(phoneNum);
                break;
            case R.id.activity_register_btn://注册

                String idenCode = mEtIdenCode.getText().toString().trim();
                String loginPwd = activity_register_pwd_tran.getText().toString().trim();
                String nickname = mEtNickname.getText().toString().trim();
                try {
                    loginPwd = PasswordUtil.aesEncrypt(loginPwd, PasswordUtil._key);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (StringUtils.isEmpty(phoneNum)) {
                    ToastUtil.showShort(this, getString(R.string.phone_num_cannot_empty));
                    mEtPhoneNum.requestFocus();
                    return;
                }
                if (StringUtils.isEmpty(idenCode)) {
                    ToastUtil.showShort(this, getString(R.string.identify_code_cannot_empty));
                    mEtIdenCode.requestFocus();
                    return;
                }
                if (mShowNickName) {
                    if (StringUtils.isEmpty(nickname)) {
                        ToastUtil.showShort(this, getString(R.string.nickname_cannot_empty));
                        mEtNickname.requestFocus();
                        return;
                    }
                } else {
                    if (StringUtils.isEmpty(loginPwd)) {
                        ToastUtil.showShort(this, getString(R.string.loginpwd_cannot_empty));
                        mEtLoginPwd.requestFocus();
                        return;
                    }
                }
                if (StringUtils.isNotEmpty(phoneNum)) {
                    JSONObject params = new JSONObject();
                    try {
                        params.put("cellphoneNumber", mEtPhoneNum.getText().toString());
                        params.put("nickName", nickname);
                        params.put("userPass", loginPwd);
                        params.put("verificationCode", mEtIdenCode.getText().toString());
                        if (mInvitationCode.getText().length() > 0) {
                            params.put("invitationCode", mInvitationCode.getText().toString());
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    register(params.toString());

                }
                break;
            case R.id.activity_register_agreement://协议
                Intent intent = new Intent(this, XieyiActivity.class);
                startActivity(intent);
                break;
            case R.id.activity_register_login://登录
                LoginActivity.getInstance(this);
                break;
            default:
                break;
        }
    }


    /**
     * 发送验证码
     *
     * @param phoneNum 手机号码
     */
    private void sendCode(String phoneNum) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("cellphoneNumber", phoneNum);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RetrofitClient
                .getInstance()
                .api()
                .sendWTWCode(new JsonRequestBody().convertJsonContent(jsonObject.toString()))
                .compose(SchedulersCompat.<retrofit2.Response<SendResult>>applyIoSchedulers())
                .subscribe(new Observer<retrofit2.Response<SendResult>>() {
                               @Override
                               public void onCompleted() {

                               }

                               @Override
                               public void onError(Throwable e) {
                                   ToastUtil.showShort(RegisterActivity.this, "发送验证码失败,请重试");
                               }

                               @Override
                               public void onNext(retrofit2.Response<SendResult> response) {
                                   if (response.code() == 200) {
                                       if (response.body().getStatusCode().equals("SEND")) {
                                           isOpen = true;
                                           startTimer();
                                           ToastUtil.showShort(getBaseContext(), "验证码已发送，请留意手机");
                                       } else if (response.body().getStatusCode().equals("403")) {
                                           ToastUtil.showShort(getBaseContext(), "用户已存在");
                                       }
                                   } else if (response.code() == 404) {
                                       ToastUtil.showShort(RegisterActivity.this, "发送验证码失败,请重试");
                                   }
                               }
                           }
                );
    }


    /**
     * 注册赢天下
     *
     * @param s
     */
    private void register(String s) {
        final LoadingView loadingGif = new LoadingView(mContext);
        loadingGif.show();
        RetrofitClient
                .getInstance()
                .api()
                .registerWTW(new JsonRequestBody().convertJsonContent(s))
                .compose(SchedulersCompat.<RegisterWTXBean>applyIoSchedulers())
                .subscribe(new Observer<RegisterWTXBean>() {
                    @Override
                    public void onCompleted() {
                        loadingGif.dismiss();
                    }

                    @Override
                    public void onError(Throwable e) {
                        loadingGif.dismiss();
                        ToastUtil.showShort(getBaseContext(), "注册失败,请重试"+e.getMessage());
                    }

                    @Override
                    public void onNext(RegisterWTXBean response) {
                        final String code = "BE0004";
                        final String code2 = "BE0003";
                        final String code3 = "BE0000";
                        if (response.statusCode != null && response.statusCode.equals("SUCCESS")) {
                            //关闭计时器
                            isOpen = false;
                            mBtnGetCode.setText("获取验证码");
                            mBtnGetCode.setEnabled(true);
                            ToastUtil.showShort(RegisterActivity.this, "注册成功");
                            finish();
                            LoginActivity.getInstance(RegisterActivity.this);
                        } else if (response.statusCode!=null&&response.statusCode.equals("403")) {
                            ToastUtil.showShort(getBaseContext(), "验证码错误，请重新输入!");
                        } else if (response.statusCode != null && response.statusCode.equals("FAIL") && response.errorCode.equals(code2)) {
                            ToastUtil.showShort(getBaseContext(), "密码非法，请重新输入");
                        } else if (response.statusCode != null && response.statusCode.equals("FAIL") && response.errorCode.equals(code3)) {
                            ToastUtil.showShort(getBaseContext(), "该手机号码已被注册过");
                        } else if (response.statusCode != null && response.error != null && response.error.equals("Internal Server Error")) {
                            ToastUtil.showShort(getBaseContext(), "服务器错误，请稍后再试");
                        }
                    }
                });
    }


    public android.os.Handler handler = new android.os.Handler();
    //5分钟
    private int i = 300;

    /**
     * 启动验证码倒计时
     */
    public void startTimer() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (isOpen) {
                    try {
                        Thread.sleep(1000);
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                if (i == 0) {
                                    //将线程关闭
                                    isOpen = false;
                                }
                                if (i > -1) {
                                    mBtnGetCode.setText(i + "秒后重发");
                                    mBtnGetCode.setBackgroundColor(getBaseContext().getResources().getColor(R.color.line_divider));
                                    if (mBtnGetCode.isEnabled())
                                        mBtnGetCode.setEnabled(false);
                                } else {
                                    mBtnGetCode.setText("获取验证码");
                                    mBtnGetCode.setBackgroundResource(R.drawable.selector_main_bigbtn_bg);
                                    mBtnGetCode.setEnabled(true);
                                }
                            }
                        });
                        i--;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    public static class SendResult {

        /**
         * statusCode : SEND
         * errorCode : null
         * verificationCode : null
         */

        private String statusCode;
        private Object errorCode;
        private Object verificationCode;

        public String getStatusCode() {
            return statusCode;
        }

        public void setStatusCode(String statusCode) {
            this.statusCode = statusCode;
        }

        public Object getErrorCode() {
            return errorCode;
        }

        public void setErrorCode(Object errorCode) {
            this.errorCode = errorCode;
        }

        public Object getVerificationCode() {
            return verificationCode;
        }

        public void setVerificationCode(Object verificationCode) {
            this.verificationCode = verificationCode;
        }
    }

}
