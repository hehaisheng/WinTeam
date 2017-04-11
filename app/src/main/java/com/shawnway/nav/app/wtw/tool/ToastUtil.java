package com.shawnway.nav.app.wtw.tool;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

public class ToastUtil {
    // Toast
    private static Toast toast;

    public static void showShort(Context context, CharSequence message) {
        showShort(context, message, false);
    }

    public static void showShort(Context context, CharSequence message, boolean center) {
        show(context, message, Toast.LENGTH_SHORT, center);
    }

    private static void show(Context context, CharSequence message, int duration, boolean middel) {
        if (message==null||StringUtils.isBlank(message.toString()))
            return;
        try {
            if (null == toast) {
                toast = Toast.makeText(context.getApplicationContext(), message, duration);
                if (middel)
                    toast.setGravity(Gravity.CENTER, 0, 0);
            } else {
                toast.setText(message);
            }
            toast.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
