package com.shawnway.nav.app.wtw.module.user.promotion;

import android.Manifest;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.shawnway.nav.app.wtw.R;
import com.shawnway.nav.app.wtw.base.BaseActivity;
import com.shawnway.nav.app.wtw.bean.RetrieveInvitationCodeBean;
import com.shawnway.nav.app.wtw.view.LoadingView;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Kevin on 2016/11/15
 */

public class PromotionActivity extends BaseActivity<PromotionPresenter> implements PromotionContract.IPromotionView {

    @BindView(R.id.top_back)
    ImageButton topBack;
    @BindView(R.id.top_text_center)
    TextView topTextCenter;
    @BindView(R.id.top_text_right)
    TextView topTextRight;
    @BindView(R.id.toolbar)
    RelativeLayout toolbar;
    /*@BindView(R.id.text_promotion_commission)
    TextView textPromotionCommission;
    @BindView(R.id.button_withdraw)
    TextView buttonWithdraw;
    @BindView(R.id.text_promotion_commission_rate)
    TextView textPromotionCommissionRate;
    @BindView(R.id.text_promotion_invited_number)
    TextView textPromotionInvitedNumber;
    */
    @BindView(R.id.text_promotion_invited_code)
    TextView textPromotionInvitedCode;
    @BindView(R.id.button_invited_code_copy)
    TextView buttonInvitedCodeCopy;
    @BindView(R.id.iv_promotion_num1)
    ImageView ivPromotionNum1;
    @BindView(R.id.layout_earn1)
    RelativeLayout layoutEarn1;
    @BindView(R.id.iv_promotion_num2)
    ImageView ivPromotionNum2;
    @BindView(R.id.layout_earn2)
    RelativeLayout layoutEarn2;
    @BindView(R.id.iv_promotion_num3)
    ImageView ivPromotionNum3;
    @BindView(R.id.layout_earn3)
    RelativeLayout layoutEarn3;
    @BindView(R.id.iv_promotion_num4)
    ImageView ivPromotionNum4;
    @BindView(R.id.layout_earn4)
    RelativeLayout layoutEarn4;
    @BindView(R.id.button_share_wechat)
    ImageView buttonShareWechat;
    @BindView(R.id.button_share_cicrle)
    ImageView buttonShareCicrle;
    @BindView(R.id.button_share_qq)
    ImageView buttonShareQq;
    @BindView(R.id.button_share_qrcode)
    ImageView buttonShareQrcode;
    @BindView(R.id.button_share_weibo)
    ImageView buttonShareWeibo;
    @BindView(R.id.button_share_qqzone)
    ImageView buttonShareQqzone;
    private String title = "邀请好友";

    private String share_url = "http://winteams.cn/";

    private String share_image_url = "http://120.76.153.154:9088/doc/qq_share.jpg";
    private UMImage umImage;
    private String share_title;
    private String share_content;
    private LoadingView loadingGif;
    private static boolean isCheckPermission = false;

    @Override
    protected int getLayout() {
        return R.layout.activity_promotion;
    }

    @Override
    protected void initEventAndData() {
        ButterKnife.bind(this);
        initToolbar();
        mPresenter.getInviteCode();
    }

    @Override
    protected PromotionPresenter getPresenter() {
        return new PromotionPresenter(mContext, this);
    }

    private static void checkLoginInfo(Context context) {

        Intent starter = new Intent(context, PromotionActivity.class);
        context.startActivity(starter);

    }

    /**
     * 初始化标题
     */
    private void initToolbar() {
        setVisiable(toolbar, topTextCenter, topBack);
        topTextCenter.setText(title);
    }


    public static void start(Context context) {
        checkLoginInfo(context);
    }

    @OnClick({R.id.top_back, R.id.button_invited_code_copy,  R.id.button_share_wechat, R.id.button_share_cicrle, R.id.button_share_qq, R.id.button_share_qrcode, R.id.button_share_weibo, R.id.button_share_qqzone})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.top_back:
                finish();
                break;
            case R.id.button_invited_code_copy:
                copyCode();
                break;
            /*case R.id.button_withdraw:
                if (isLogin()) {
                    Toast.makeText(this, "提现", Toast.LENGTH_SHORT).show();
                } else {
                    LoginActivity.getInstance(mContext);
                }
                break;*/
            case R.id.button_share_wechat:
                shareWithPlatFormName(SHARE_MEDIA.WEIXIN);
                break;
            case R.id.button_share_cicrle:
                shareWithPlatFormName(SHARE_MEDIA.WEIXIN_CIRCLE);
                break;
            case R.id.button_share_qq:
                if (Build.VERSION.SDK_INT >= 23) {
                    sharePermission();
                    if (isCheckPermission) {
                        shareWithPlatFormName(SHARE_MEDIA.QQ);
                    }
                } else {
                    shareWithPlatFormName(SHARE_MEDIA.QQ);

                }
                break;
            case R.id.button_share_qrcode:
                QRCodeDialogFragment.getInstant(this).show(getFragmentManager(), "QRCodeDialog");
                break;
            case R.id.button_share_weibo:
                shareWithPlatFormName(SHARE_MEDIA.SINA);
                break;
            case R.id.button_share_qqzone:
                if (Build.VERSION.SDK_INT >= 23) {
                    sharePermission();
                    if (isCheckPermission) {
                        shareWithPlatFormName(SHARE_MEDIA.QZONE);
                    }
                } else {
                    shareWithPlatFormName(SHARE_MEDIA.QZONE);

                }
                break;
        }
    }

    /**
     * 复制代码
     */
    private void copyCode() {
        ClipboardManager clipboardManager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        ClipData clipData = ClipData.newPlainText("invitedcode", textPromotionInvitedCode.getText().toString());
        clipboardManager.setPrimaryClip(clipData);
        Toast.makeText(this, "已复制到粘贴板", Toast.LENGTH_SHORT).show();
    }


    /**
     * 通过平台名称分享
     *
     * @param platFormName
     */
    private void shareWithPlatFormName(SHARE_MEDIA platFormName) {
        initShareInfo();
        new ShareAction(this).setPlatform(platFormName)
                .withMedia(umImage)
                .withTargetUrl(share_url)
                .withTitle(share_title)
                .withText(share_content)
                .setCallback(umShareListener)
                .share();
    }

    /**
     * 初始化分享信息
     */
    private void initShareInfo() {
        umImage = new UMImage(this, share_image_url);
        share_title = getString(R.string.share_to_qq_title);
        share_content = getString(R.string.share_to_qq_content);
    }

    /**
     * 分享回调
     */
    private UMShareListener umShareListener = new UMShareListener() {
        @Override
        public void onResult(SHARE_MEDIA platform) {
            Log.d("plat", "platform" + platform);

            Toast.makeText(PromotionActivity.this, platform + " 分享成功啦", Toast.LENGTH_SHORT).show();

        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            if (t != null) {
                Log.d("throw", "throw:" + t.getMessage());
                Toast.makeText(PromotionActivity.this, platform + "分享失败:" + t.getMessage(), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(PromotionActivity.this, platform + " 分享失败啦", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {
//            Toast.makeText(PromotionActivity.this, platform + " 分享取消了", Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);

    }

    @Override
    public void addInviteCode(RetrieveInvitationCodeBean retrieveInvitationCodeBean) {
        String phoneNumbers = retrieveInvitationCodeBean.getLoginName();
        String inviteCode = retrieveInvitationCodeBean.getInviteCode();
        textPromotionInvitedCode.setText(inviteCode);
        share_url = "http://winteams.cn/wx/views/introduction/share.html?cellphonenumber=" + phoneNumbers + "&inviteCode=" + inviteCode;
    }

    @Override
    public void showLoading() {
        loadingGif = new LoadingView(mContext);
        loadingGif.show();
    }

    @Override
    public void showNoData() {

    }

    @Override
    public void showContent() {
        loadingGif.dismiss();
    }

    @Override
    public void showError(String errorMsg) {
        loadingGif.dismiss();
    }

    private void sharePermission() {
        String[] mPermissionList = new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.CALL_PHONE,
                Manifest.permission.READ_LOGS, Manifest.permission.READ_PHONE_STATE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.SET_DEBUG_APP, Manifest.permission.SYSTEM_ALERT_WINDOW, Manifest.permission.GET_ACCOUNTS, Manifest.permission.WRITE_APN_SETTINGS};
        if (!isCheckPermission) {
            ActivityCompat.requestPermissions(this, getPermissionArray(mPermissionList), 123);
        }
    }

    /**
     * 获取未授权权限
     *
     * @param permissions
     */
    private String[] getPermissionArray(String... permissions) {
        List<String> permissionList = new ArrayList<>();
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission)
                    == PackageManager.PERMISSION_DENIED) {
                permissionList.add(permission);
            } else if (ContextCompat.checkSelfPermission(this, permission)
                    == PackageManager.PERMISSION_GRANTED) {
                isCheckPermission = true;
            }
        }
        String[] permissionArray = permissionList.toArray(new String[permissionList.size()]);
        return permissionArray;
    }
}
