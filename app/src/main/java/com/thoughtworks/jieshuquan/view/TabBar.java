package com.thoughtworks.jieshuquan.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import com.thoughtworks.jieshuquan.R;

public class TabBar extends LinearLayout {

    private OnTabClickListener mOnTabClickListener;

    public TabBar(Context context) {
        super(context);
        init(null, 0);
    }

    public TabBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public TabBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {
        inflate(getContext(), R.layout.view_tab_bar, this);

        int tabCount = getChildCount();
        for (int i = 0; i < tabCount; i++) {
            final int position = i;
            View tab = getChildAt(i);
            tab.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    unselectAll();
                    v.setSelected(true);
                    if (mOnTabClickListener != null) {
                        mOnTabClickListener.onTabClick(v, position);
                    }
                }
            });
        }
    }

    public void setCurrentItem(final int position) {
        if (position >= 0 && position < getChildCount()) {
            unselectAll();
            getChildAt(position).setSelected(true);
        }
    }

    private void unselectAll() {
        int tabCount = getChildCount();
        for (int i = 0; i < tabCount; i++) {
            View tab = getChildAt(i);
            tab.setSelected(false);
        }
    }

    public void setOnTabClickListener(OnTabClickListener listener) {
        this.mOnTabClickListener = listener;
    }

    public interface OnTabClickListener {
        void onTabClick(View tabView, int position);
    }
}
