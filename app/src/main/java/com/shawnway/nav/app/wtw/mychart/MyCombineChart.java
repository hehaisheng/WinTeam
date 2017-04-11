package com.shawnway.nav.app.wtw.mychart;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;

import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.shawnway.nav.app.wtw.bean.DataParse;
import com.shawnway.nav.app.wtw.module.quotation.international.international_detail.InternationalDataParse;

/**
 * Created by Cicinnus on 2016/12/16.
 */

@SuppressWarnings("unchecked")
public class MyCombineChart extends CombinedChart {


    private MyLeftMarkerView myMarkerViewLeft;
//    private MyRightMarkerView myMarkerViewRight;
    private MyBottomMarkerView mMyBottomMarkerView;
    private DataParse minuteHelper1;
    private InternationalDataParse minuteHelper2;

    public MyCombineChart(Context context) {
        super(context);
    }

    public MyCombineChart(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyCombineChart(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
    public void setWpMarker(MyLeftMarkerView leftMarkerView, MyBottomMarkerView bottomMarkerView, DataParse mData) {
        this.myMarkerViewLeft = leftMarkerView;
        this.mMyBottomMarkerView = bottomMarkerView;
        this.minuteHelper1 = mData;
    }

    public void setInternationalMarker(MyLeftMarkerView leftMarkerView, MyBottomMarkerView bottomMarkerView, InternationalDataParse mData) {
        this.myMarkerViewLeft = leftMarkerView;
        this.mMyBottomMarkerView = bottomMarkerView;
        this.minuteHelper2 = mData;
    }


    @Override
    protected void init() {
        super.init();
        mXAxis = new MyXAxis();
        mAxisLeft = new MyYAxis(YAxis.AxisDependency.LEFT);
        mXAxisRenderer = new MyXAxisRenderer(mViewPortHandler, (MyXAxis) mXAxis, mLeftAxisTransformer, this);
        mAxisRendererLeft = new MyYAxisRenderer(mViewPortHandler, (MyYAxis) mAxisLeft, mLeftAxisTransformer);


    }

    @Override
    public MyXAxis getXAxis() {
        return ((MyXAxis) super.getXAxis());
    }

    @Override
    public MyYAxis getAxisLeft() {
        return (MyYAxis) super.getAxisLeft();
    }



    @Override
    protected void drawMarkers(Canvas canvas) {
        if (!mDrawMarkerViews || !valuesToHighlight())
            return;
        for (int i = 0; i < mIndicesToHighlight.length; i++) {
            Highlight highlight = mIndicesToHighlight[i];
            int xIndex = mIndicesToHighlight[i].getXIndex();
            int dataSetIndex = mIndicesToHighlight[i].getDataSetIndex();
            float deltaX = mXAxis != null
                    ? mXAxis.mAxisRange
                    : ((mData == null ? 0.f : mData.getXValCount()) - 1.f);
            if (xIndex <= deltaX && xIndex <= deltaX * mAnimator.getPhaseX()) {
                Entry e = mData.getEntryForHighlight(mIndicesToHighlight[i]);
                // make sure entry not null
                if (e == null || e.getXIndex() != mIndicesToHighlight[i].getXIndex())
                    continue;

                float[] pos = getMarkerPosition(e, highlight);
                // check bounds
                if (!mViewPortHandler.isInBounds(pos[0], pos[1]))
                    continue;
                float yValForXIndex1 = 0;
                String time = "";
                if(minuteHelper1!=null){
                    yValForXIndex1 = minuteHelper1.getKLineDatas().get(mIndicesToHighlight[i].getXIndex()).close;
                    time = minuteHelper1.getKLineDatas().get(mIndicesToHighlight[i].getXIndex()).date;
                    myMarkerViewLeft.setNumType(MyLeftMarkerView.TYPE_WP);
                }
                if(minuteHelper2!=null){
                    yValForXIndex1 = minuteHelper2.getKLineDatas().get(mIndicesToHighlight[i].getXIndex()).close;
                    time = minuteHelper2.getKLineDatas().get(mIndicesToHighlight[i].getXIndex()).date;
                    myMarkerViewLeft.setNumType(MyLeftMarkerView.TYPE_INTERNATIONAL_KLINE);

                }


                myMarkerViewLeft.setData(yValForXIndex1);
                mMyBottomMarkerView.setData(time);
                myMarkerViewLeft.refreshContent(e, mIndicesToHighlight[i]);
                mMyBottomMarkerView.refreshContent(e, mIndicesToHighlight[i]);
                /*修复bug*/
                // invalidate();
                /*重新计算大小*/
                myMarkerViewLeft.measure(MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED),
                        MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
                myMarkerViewLeft.layout(0, 0, myMarkerViewLeft.getMeasuredWidth(),
                        myMarkerViewLeft.getMeasuredHeight());

                mMyBottomMarkerView.measure(MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED),
                        MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
                mMyBottomMarkerView.layout(0, 0, mMyBottomMarkerView.getMeasuredWidth(),
                        mMyBottomMarkerView.getMeasuredHeight());

                mMyBottomMarkerView.draw(canvas, pos[0] - mMyBottomMarkerView.getWidth() / 2, mViewPortHandler.contentBottom());
                myMarkerViewLeft.draw(canvas, mViewPortHandler.contentLeft() - myMarkerViewLeft.getWidth(), pos[1] - myMarkerViewLeft.getHeight() / 2);
            }
        }
    }


}
