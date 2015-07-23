package com.thoughtworks.jieshuquan.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.thoughtworks.jieshuquan.R;

import butterknife.ButterKnife;


public abstract class ToolbarBaseActivity extends AppCompatActivity {

    protected Toolbar toolbar;
    protected ViewGroup rootView;
    protected ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toolbar_base);

        init();
    }

    protected void init() {
        toolbar = ButterKnife.findById(this, R.id.toolbar);
        rootView = ButterKnife.findById(this, R.id.root_view);
        setContentView(LayoutInflater.from(this), rootView);

        initToolbar();
        initNavigationClickListener();
    }

    protected void initToolbar() {
        toolbar.setTitle(getToolbarTitle());
        toolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        setSupportActionBar(toolbar);
    }

    protected void initNavigationClickListener() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onNavigationOnClick(v);
            }
        });
    }

    protected void showLoadingScreen(String title, String msg) {
        if (progressDialog != null && !progressDialog.isShowing()) {
            progressDialog.setTitle(title);
            progressDialog.setMessage(msg);
            progressDialog.show();
            return ;
        }

        if(progressDialog == null) {
            progressDialog = ProgressDialog.show(this, title, msg);
            return ;
        }
    }

    protected void hideLoadingScreen() {
        if(progressDialog != null && progressDialog.isShowing()) {
            progressDialog.hide();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        hideLoadingScreen();
    }

    protected void onNavigationOnClick(View v) {
        finish();
    }

    protected abstract int getToolbarTitle();
    protected abstract void setContentView(LayoutInflater inflater, ViewGroup rootView);
}
