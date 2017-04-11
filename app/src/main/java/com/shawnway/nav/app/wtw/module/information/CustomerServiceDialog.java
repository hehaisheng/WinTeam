package com.shawnway.nav.app.wtw.module.information;

import android.app.Dialog;
import android.app.DialogFragment;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.shawnway.nav.app.wtw.R;

/**
 * Created by Administrator on 2016/9/13.
 */
public class CustomerServiceDialog extends DialogFragment {

    private OnNumberClickListener OnNumberClickListener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = new Dialog(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_customer_service, null);
        //没有标题
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //加载动画
        dialog.getWindow().setWindowAnimations(R.style.CustomDialog);
        //布局
        dialog.setContentView(dialogView);
        final TextView call_phone_number = (TextView) dialogView.findViewById(R.id.call_phone_number);
        call_phone_number.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(OnNumberClickListener!=null){
                    OnNumberClickListener.onClick(call_phone_number.getText().toString());
                }

            }
        });

        return dialog;
    }

    public void setOnNumberClickListener(CustomerServiceDialog.OnNumberClickListener onNumberClickListener) {
        OnNumberClickListener = onNumberClickListener;
    }

    public interface  OnNumberClickListener{
        void onClick(String num);
    }
}

