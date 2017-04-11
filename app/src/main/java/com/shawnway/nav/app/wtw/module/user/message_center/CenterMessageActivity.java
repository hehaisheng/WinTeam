package com.shawnway.nav.app.wtw.module.user.message_center;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.shawnway.nav.app.wtw.R;

public class CenterMessageActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_center_message);
        initToolbar();
        initView();
    }

    private void initView() {
        findViewById(R.id.activity_center_tran).setOnClickListener(this);
        findViewById(R.id.activity_center_sys).setOnClickListener(this);
    }

    public void initToolbar(){
        findViewById(R.id.toolbar).setVisibility(View.VISIBLE);
        TextView centerText = (TextView) findViewById(R.id.top_text_center);
        centerText.setVisibility(View.VISIBLE);
        centerText.setText("消息中心");
        ImageButton backButton = (ImageButton) findViewById(R.id.top_back);
        backButton.setVisibility(View.VISIBLE);
        backButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.top_back:
                finish();
                break;
            case R.id.activity_center_tran:
                SystemMessageActivity.start(this,"交易中心");
                break;
            case R.id.activity_center_sys:
                SystemMessageActivity.start(this,"系统消息");
                break;
        }
    }
}
