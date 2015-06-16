package com.thoughtworks.jieshuquan_android.activity.main.mine;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.thoughtworks.jieshuquan_android.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MineBookActivity extends AppCompatActivity {

    @InjectView(R.id.toolbar)
    Toolbar mToolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine_books);
        ButterKnife.inject(this);

        initToolbar();
    }

    private void initToolbar() {
        mToolbar.setTitle(R.string.title_activity_mine_books);
        mToolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MineBookActivity.this.finish();
            }
        });
    }
}
