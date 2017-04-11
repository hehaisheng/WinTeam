package com.shawnway.nav.app.wtw.module.user.setting;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.shawnway.nav.app.wtw.R;
import com.shawnway.nav.app.wtw.net.JsonRequestBody;
import com.shawnway.nav.app.wtw.net.RetrofitClient;
import com.shawnway.nav.app.wtw.tool.SchedulersCompat;
import com.shawnway.nav.app.wtw.tool.ToastUtil;

import org.json.JSONException;
import org.json.JSONObject;

import rx.Observer;

public class FeedBackActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText et_feedback_content;
    private EditText et_phoneNumber;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_my_seggestction);
        mContext = this;
        initToolbar();
        et_feedback_content = (EditText) findViewById(R.id.et_feedback_content);
        et_phoneNumber = (EditText) findViewById(R.id.et_phoneNumber);
    }

    public void initToolbar() {
        findViewById(R.id.toolbar).setVisibility(View.VISIBLE);
        TextView centerText = (TextView) findViewById(R.id.top_text_center);
        centerText.setVisibility(View.VISIBLE);
        centerText.setText("意见反馈");
        ImageButton backButton = (ImageButton) findViewById(R.id.top_back);
        backButton.setVisibility(View.VISIBLE);
        backButton.setOnClickListener(this);

    }

    /**
     * 提交内容
     */
    private void commitFeedBack() {
        final String content = et_feedback_content.getText().toString();
        String phoneNum = et_phoneNumber.getText().toString();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("type", "2");
            jsonObject.put("content", content);
            jsonObject.put("contact_way", phoneNum);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RetrofitClient
                .getInstance()
                .api()
                .sendFeedBack(new JsonRequestBody().convertJsonContent(jsonObject.toString()))
                .compose(SchedulersCompat.<BasicResponse>applyIoSchedulers())
                .subscribe(new Observer<BasicResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("MySuggestion", e.getMessage());
                    }

                    @Override
                    public void onNext(BasicResponse basicResponse) {
                        if (("SUCCESS").equals(basicResponse.statusCode)) {
                            ToastUtil.showShort(mContext, "反馈成功");
                            finish();
                        } else {
                            ToastUtil.showShort(mContext, "反馈失败");
                        }
                    }
                });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.top_back:
                finish();
                break;
        }
    }

    public void commitFeedBack(View view) {
        commitFeedBack();
    }

    public class BasicResponse {
        String statusCode;
    }

}
