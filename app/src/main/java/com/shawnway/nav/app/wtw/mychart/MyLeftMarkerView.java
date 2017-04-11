package com.shawnway.nav.app.wtw.mychart;

import android.content.Context;
import android.widget.TextView;

import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.shawnway.nav.app.wtw.R;

import java.text.DecimalFormat;


public class MyLeftMarkerView extends MarkerView {
    /**
     * Constructor. Sets up the MarkerView with a custom layout resource.
     *
     * @param context
     * @param layoutResource the layout resource to use for the MarkerView
     */
    private TextView markerTv;
    private float num;
    private DecimalFormat mFormat;
    private int mType;

    public static final int TYPE_WP = 0;
    public static final int TYPE_INTERNATIONAL_TIME = 1;
    public static final int TYPE_INTERNATIONAL_KLINE = 2;
    public static final int TYPE_INTERNATIONAL_BAR = 3;


    public MyLeftMarkerView(Context context, int layoutResource) {
        super(context, layoutResource);
        mFormat = new DecimalFormat("#0.00");
        markerTv = (TextView) findViewById(R.id.marker_tv);
        markerTv.setTextSize(10);
    }

    public void setData(float num) {
        this.num = num;
    }

    @Override
    public void refreshContent(Entry e, Highlight highlight) {
        int u = 0;
        switch (mType) {
            case TYPE_WP:
                u = 0;
                break;
            case TYPE_INTERNATIONAL_TIME:
                u = 0;
                break;
            case TYPE_INTERNATIONAL_KLINE:
                u = 0;
                break;
            case TYPE_INTERNATIONAL_BAR:
                u = (int) Math.floor(Math.log10(num));
                if (u >= 8) {
                    u = 8;
                } else if (u >= 4) {
                    u = 4;
                }
                break;
        }
        markerTv.setText(mFormat.format(num / Math.pow(10, u)));


    }

    public void setNumType(int type) {
        this.mType = type;
    }

    @Override
    public int getXOffset(float xpos) {
        return 0;
    }

    @Override
    public int getYOffset(float ypos) {
        return 0;
    }
}
