package com.thoughtworks.jieshuquan.mine;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.thoughtworks.jieshuquan.R;
import com.thoughtworks.jieshuquan.activity.ToolbarBaseActivity;

public class MineBorrowActivity extends ToolbarBaseActivity {
    @Override
    protected int getToolbarTitle() {
        return R.string.mine_list_my_borrow;
    }

    @Override
    protected void setContentView(LayoutInflater inflater, ViewGroup rootView) {
        inflater.inflate(R.layout.activity_mine_borrow_books, rootView, true);
    }
}
