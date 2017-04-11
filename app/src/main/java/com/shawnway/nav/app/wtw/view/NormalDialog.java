package com.shawnway.nav.app.wtw.view;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.shawnway.nav.app.wtw.R;


/**
 * Created by Cicinnus on 2016/9/23.
 * 全局Dialog 调用newInstance（arg0,arg1,arg2）方法传入标题，内容，确认按钮文字
 */

public class NormalDialog extends DialogFragment {

    private Button cancel;
    private Button confirm;

    public static NormalDialog newInstance(String title,
                                           String content,
                                           String confirm) {
        Bundle args = new Bundle();
        args.putString("title", title);
        args.putString("content", content);
        args.putString("confirm",confirm);
        NormalDialog fragment = new NormalDialog();
        fragment.setArguments(args);
        return fragment;
    }

    private OnConfirmClickListener onConfirmClickListener;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_dialog);
        dialog.setCanceledOnTouchOutside(false);
        initDialogContent(dialog);
        initListener();
        return dialog;
    }

    private void initDialogContent(Dialog dialog) {
        TextView title = (TextView) dialog.findViewById(R.id.dialog_title);
        TextView content = (TextView) dialog.findViewById(R.id.dialog_content);
        cancel = (Button) dialog.findViewById(R.id.btn_cancel);
        confirm = (Button) dialog.findViewById(R.id.btn_confirm);
        title.setText(getArguments().getString("title"));
        content.setText(getArguments().getString("content"));
        confirm.setText(getArguments().getString("confirm","确定"));
    }

    private void initListener() {

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismissDialog();
            }
        });
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onConfirmClickListener != null) {
                    onConfirmClickListener.onClick();
                }
            }
        });
    }

    private void dismissDialog(){
        getDialog().dismiss();
    }
    public NormalDialog showDialog(FragmentManager fragmentManager){
        show(fragmentManager,"Dialog");
        return this;
    }

    public void setOnDialogClickListener(OnConfirmClickListener onConfirmClickListener ) {
        this.onConfirmClickListener = onConfirmClickListener;
    }

    public interface OnConfirmClickListener {
        void onClick();
    }

}
