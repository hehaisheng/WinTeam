package com.shawnway.nav.app.wtw.view;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.shawnway.nav.app.wtw.R;

/**
 * Created by Cicinnus on 2017/1/9.
 * 设置加载更多失败布局
 */

public class LoadMoreFailView {

    public View getView(Activity context, ViewGroup viewGroup, final onLoadMoreFailedClickListener onLoadMoreFailedClickListener){
        View loadMoreFailedView  = context.getLayoutInflater().inflate(R.layout.layout_load_more_failed_view,viewGroup,false);
        LinearLayout ll_load_more_failed = (LinearLayout) loadMoreFailedView.findViewById(R.id.ll_load_more_failed);
        ll_load_more_failed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onLoadMoreFailedClickListener.onClick();
            }
        });
        return loadMoreFailedView;
    }

    public interface onLoadMoreFailedClickListener{
        void onClick();
    }
}
