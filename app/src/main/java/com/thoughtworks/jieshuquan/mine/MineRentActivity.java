package com.thoughtworks.jieshuquan.mine;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.thoughtworks.jieshuquan.R;
import com.thoughtworks.jieshuquan.activity.ToolbarBaseActivity;

public class MineRentActivity extends ToolbarBaseActivity {
    @Override
    protected int getToolbarTitle() {
        return R.string.mine_list_my_rent;
    }

    @Override
    protected void setContentView(LayoutInflater inflater, ViewGroup rootView) {
        inflater.inflate(R.layout.activity_mine_rent_books, rootView, true);
    }
}
