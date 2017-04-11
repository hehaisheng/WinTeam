package com.shawnway.nav.app.wtw.module.login_register;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.shawnway.nav.app.wtw.R;

public class XieyiActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xieyi);
        initToolBar();

    }
    private void initToolBar() {
        this.findViewById(R.id.toolbar).setVisibility(View.VISIBLE);
        ImageButton iBtn = (ImageButton) this.findViewById(R.id.top_back);
        iBtn.setVisibility(View.VISIBLE);
        TextView center = (TextView) this.findViewById(R.id.top_text_center);
        center.setVisibility(View.VISIBLE);
        center.setText("协议");
        iBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
