package com.thoughtworks.jieshuquan.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.thoughtworks.jieshuquan.R;
import com.thoughtworks.jieshuquan.login.LoginActivity;
import com.thoughtworks.jieshuquan.service.AuthService;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class MoreActivity extends AppCompatActivity {

    @InjectView(R.id.toolbar)
    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more);
        ButterKnife.inject(this);

        initToolbar();
    }

    private void initToolbar() {
        mToolbar.setTitle(R.string.title_activity_more);
        mToolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MoreActivity.this.finish();
            }
        });
    }

    @OnClick(R.id.modify_password)
    protected void onModifyPasswordClick() {

    }

    @OnClick(R.id.modify_nickname) 
    protected void onModifyNickName() {

    }

    @OnClick(R.id.feedback)
    protected void onFeedbackClick() {
        Intent intent = new Intent(this, FeedbackActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.logout)
    protected void onLogoutClick() {
        AuthService.getInstance().logout();

        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }
}
