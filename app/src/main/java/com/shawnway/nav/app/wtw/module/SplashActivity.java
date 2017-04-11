package com.shawnway.nav.app.wtw.module;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.shawnway.nav.app.wtw.R;
import com.shawnway.nav.app.wtw.application.WinApplication;
import com.shawnway.nav.app.wtw.service.AutoLoginService;
import com.shawnway.nav.app.wtw.tool.NetWorkUtil;

/**
 * Created by Cicinnus on 2016/12/2.
 */

public class SplashActivity extends AppCompatActivity {

    private NetworkErrorDialog networkErrorDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        networkErrorDialog = new NetworkErrorDialog();
        networkErrorDialog.setOnRetryClickListener(new NetworkErrorDialog.OnRetryClickListener() {
            @Override
            public void onClick() {
                judgeNetWork();
            }
        });
        networkErrorDialog.setOnFinishClickListener(new NetworkErrorDialog.OnFinishClickListener() {
            @Override
            public void onClick() {
                finish();
            }
        });
        networkErrorDialog.setOnSettingClickListener(new NetworkErrorDialog.OnSettingClickListener() {
            @Override
            public void onClick() {
                //跳转设置
                Intent intent =  new Intent(Settings.ACTION_SETTINGS);
                startActivity(intent);
            }
        });
        judgeNetWork();

    }

    /**
     * 判断网络状态是否进入应用
     */
    private void judgeNetWork(){
        if (NetWorkUtil.isNetworkConnected(WinApplication.getInstance())) {
            startActivity(new Intent(SplashActivity.this, MainActivity.class));
            //自动登录
            startService(new Intent(SplashActivity.this, AutoLoginService.class));
            finish();
        }else {
            networkErrorDialog.show(getSupportFragmentManager(),"");

        }

    }

    public static class NetworkErrorDialog extends DialogFragment {

        private OnFinishClickListener onFinishClickListener;
        private OnSettingClickListener onSettingClickListener;
        private OnRetryClickListener onRetryClickListener;

        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            Dialog dialog = new Dialog(getActivity());
            dialog.setContentView(R.layout.layout_network_error_dialog);
            dialog.setCanceledOnTouchOutside(false);
            dialog.findViewById(R.id.btn_setting).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onSettingClickListener != null) {
                        onSettingClickListener.onClick();
                    }
                }
            });
            dialog.findViewById(R.id.btn_finish).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onFinishClickListener != null) {
                        onFinishClickListener.onClick();
                    }
                }
            });

            return dialog;
        }

        public void setOnFinishClickListener(OnFinishClickListener onFinishClickListener) {
            this.onFinishClickListener = onFinishClickListener;
        }

        public void setOnSettingClickListener(OnSettingClickListener onSettingClickListener) {
            this.onSettingClickListener = onSettingClickListener;
        }
        public void setOnRetryClickListener(OnRetryClickListener onRetryClickListener) {
            this.onRetryClickListener = onRetryClickListener;
        }

        public interface OnRetryClickListener {
            void onClick();
        }
        public interface OnFinishClickListener {
            void onClick();
        }
        public interface OnSettingClickListener {
            void onClick();
        }
    }

}
