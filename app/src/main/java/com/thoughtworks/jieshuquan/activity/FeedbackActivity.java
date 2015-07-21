package com.thoughtworks.jieshuquan.activity;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;

import com.thoughtworks.jieshuquan.R;
import com.thoughtworks.jieshuquan.utils.ShowUtils;

import butterknife.ButterKnife;


public class FeedbackActivity extends ToolbarBaseActivity {

    @Override
    protected void setContentView(LayoutInflater inflater, ViewGroup rootView) {
        inflater.inflate(R.layout.activity_feedback, rootView, true);
        ButterKnife.inject(this);
    }

    @Override
    protected int getToolbarTitle() {
        return R.string.feedback;
    }
}
