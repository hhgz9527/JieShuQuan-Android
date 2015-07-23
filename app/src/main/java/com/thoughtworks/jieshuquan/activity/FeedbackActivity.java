package com.thoughtworks.jieshuquan.activity;

import android.app.ProgressDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.thoughtworks.jieshuquan.R;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


public class FeedbackActivity extends ToolbarBaseActivity {

    @InjectView(R.id.submit_feedback)
    protected Button submitFeedback;

    @Override
    protected void setContentView(LayoutInflater inflater, ViewGroup rootView) {
        inflater.inflate(R.layout.activity_feedback, rootView, true);
        ButterKnife.inject(this);
    }

    @Override
    protected int getToolbarTitle() {
        return R.string.feedback;
    }

    @OnClick(R.id.submit_feedback)
    protected void onSumbitFeedbackClick(View view) {
        ProgressDialog dialog = ProgressDialog.show(this, "请稍微", "正在提交反馈");
    }
}
