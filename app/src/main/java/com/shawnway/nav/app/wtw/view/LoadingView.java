package com.shawnway.nav.app.wtw.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.shawnway.nav.app.wtw.R;

import java.util.Calendar;

/**
 * 全局加载动画
 */

public class LoadingView extends Dialog {


    private TextView tvMessage;
    private ImageView ivSuccess;
    private ImageView ivFailure;
    private ImageView ivProgressSpinner;
    private AnimationDrawable adProgressSpinner;
    private Context context;

    private OnDialogDismiss onDialogDismiss;


    public OnDialogDismiss getOnDialogDismiss() {
        return onDialogDismiss;
    }

    public void setOnDialogDismiss(OnDialogDismiss onDialogDismiss) {
        this.onDialogDismiss = onDialogDismiss;
    }


    private long defMinShowTime;

    public LoadingView(Context context) {
        this(context, 500);
    }

    public LoadingView(Context context, long i) {
        super(context, R.style.DialogTheme);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        this.setCanceledOnTouchOutside(false);
        this.context = context;
        defMinShowTime = i;

        View view = getLayoutInflater().inflate(R.layout.dialog_progress, null);
        tvMessage = (TextView) view.findViewById(R.id.textview_message);
        ivSuccess = (ImageView) view.findViewById(R.id.imageview_success);
        ivFailure = (ImageView) view.findViewById(R.id.imageview_failure);
        ivProgressSpinner = (ImageView) view
                .findViewById(R.id.imageview_progress_spinner);

        ivProgressSpinner.setImageResource(R.drawable.round_spinner_fade);
        adProgressSpinner = (AnimationDrawable) ivProgressSpinner.getDrawable();
        this.setContentView(view);
    }


    public void setMessage(String message) {
        tvMessage.setText(message);

    }

    private long showTimestamp;

    @Override
    public void show() {
        if (!((Activity) context).isFinishing()) {
            super.show();
        }
        showTimestamp = Calendar.getInstance().getTimeInMillis();
    }

    @Override
    public void dismiss() {
        final long gap = Calendar.getInstance().getTimeInMillis() - showTimestamp;
        if (gap < defMinShowTime) {
            AsyncTask<String, Integer, Long> task = new AsyncTask<String, Integer, Long>() {

                @Override
                protected Long doInBackground(String... params) {
                    SystemClock.sleep(defMinShowTime - gap);
                    return null;
                }

                @Override
                protected void onPostExecute(Long result) {
                    super.onPostExecute(result);
                    dismiss();
                    reset();
                }
            };
            task.execute();
        } else {
            try {
                super.dismiss();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public void dismissWithSuccess() {
        tvMessage.setText("Success");
        showSuccessImage();

        if (onDialogDismiss != null) {
            this.setOnDismissListener(new OnDismissListener() {

                @Override
                public void onDismiss(DialogInterface dialog) {
                    onDialogDismiss.onDismiss();
                }
            });
        }
        dismissLoading();
    }

    public void dismissWithSuccess(String message) {
        showSuccessImage();
        if (message != null) {
            tvMessage.setText(message);
        } else {
            tvMessage.setText("");
        }

        if (onDialogDismiss != null) {
            this.setOnDismissListener(new OnDismissListener() {

                @Override
                public void onDismiss(DialogInterface dialog) {
                    onDialogDismiss.onDismiss();
                }
            });
        }
        dismissLoading();
    }

    public void dismissWithFailure() {
        showFailureImage();
        tvMessage.setText("Failure");
        if (onDialogDismiss != null) {
            this.setOnDismissListener(new OnDismissListener() {

                @Override
                public void onDismiss(DialogInterface dialog) {
                    onDialogDismiss.onDismiss();
                }
            });
        }
        dismissLoading();
    }

    public void dismissWithFailure(String message) {
        showFailureImage();
        if (message != null) {
            tvMessage.setText(message);
        } else {
            tvMessage.setText("");
        }
        if (onDialogDismiss != null) {
            this.setOnDismissListener(new OnDismissListener() {

                @Override
                public void onDismiss(DialogInterface dialog) {
                    onDialogDismiss.onDismiss();
                }
            });
        }
        dismissLoading();
    }

    protected void showSuccessImage() {
        ivProgressSpinner.setVisibility(View.GONE);
        ivSuccess.setVisibility(View.VISIBLE);
    }

    protected void showFailureImage() {
        ivProgressSpinner.setVisibility(View.GONE);
        ivFailure.setVisibility(View.VISIBLE);
    }

    protected void reset() {
        ivProgressSpinner.setVisibility(View.VISIBLE);
        ivFailure.setVisibility(View.GONE);
        ivSuccess.setVisibility(View.GONE);
        tvMessage.setText("正在加载 ...");
    }

    protected void dismissLoading() {
        AsyncTask<String, Integer, Long> task = new AsyncTask<String, Integer, Long>() {

            @Override
            protected Long doInBackground(String... params) {
                SystemClock.sleep(500);
                return null;
            }

            @Override
            protected void onPostExecute(Long result) {
                super.onPostExecute(result);
                dismiss();
                reset();
            }
        };
        task.execute();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        ivProgressSpinner.post(new Runnable() {

            @Override
            public void run() {
                adProgressSpinner.start();

            }
        });
    }


    public interface OnDialogDismiss {
        public void onDismiss();
    }

}
