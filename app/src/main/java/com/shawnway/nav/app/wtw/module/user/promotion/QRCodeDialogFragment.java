package com.shawnway.nav.app.wtw.module.user.promotion;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;

import com.shawnway.nav.app.wtw.R;

/**
 * Created by Kevin on 2016/11/16
 */

public class QRCodeDialogFragment extends DialogFragment {
    private static QRCodeDialogFragment qrCodeDialogFragment;
    private Context mContext;

    public static QRCodeDialogFragment getInstant(Context mContext) {
        if (qrCodeDialogFragment == null) {
            qrCodeDialogFragment = new QRCodeDialogFragment();
            Bundle bundle = new Bundle();
            qrCodeDialogFragment.setArguments(bundle);
        }
        qrCodeDialogFragment.setmContext(mContext);
        return qrCodeDialogFragment;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Window window = getDialog().getWindow();
        window.setWindowAnimations(R.style.CustomDialog);
        window.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_FRAME, R.style.dialogFrament);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_qrcode, container);
        ImageView button_cancel = (ImageView) view.findViewById(R.id.button_del_re);
        button_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });
        return view;
    }

    @Override
    public Dialog getDialog() {
        return super.getDialog();
    }

    public void setmContext(Context mContext) {
        this.mContext = mContext;
    }
}
