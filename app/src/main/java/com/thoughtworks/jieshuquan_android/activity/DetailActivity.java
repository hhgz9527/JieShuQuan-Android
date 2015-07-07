package com.thoughtworks.jieshuquan_android.activity;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.thoughtworks.jieshuquan_android.Constants;
import com.thoughtworks.jieshuquan_android.R;

import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity {

    private String mBookId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.inject(this);
        mBookId = getIntent().getStringExtra(Constants.EXTRA_BOOK_ID);
        initViews();
        loadData();
    }

    private void initViews() {

    }

    private void loadData() {

    }
}
