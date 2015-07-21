package com.thoughtworks.jieshuquan.activity;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.thoughtworks.jieshuquan.R;

public class ModifyNicknameActivity extends ToolbarBaseActivity {


    @Override
    protected int getToolbarTitle() {
        return R.string.modify_nickname;
    }

    @Override
    protected void setContentView(LayoutInflater inflater, ViewGroup rootView) {
        inflater.inflate(R.layout.activity_modify_nickname, rootView, true);
    }

}
