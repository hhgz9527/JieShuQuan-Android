package com.thoughtworks.jieshuquan.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.thoughtworks.jieshuquan.R;
import com.thoughtworks.jieshuquan.fragment.SettingsFragment;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class SettingsActivity extends AppCompatActivity {

    @InjectView(R.id.toolbar)
    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.inject(this);
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction().add(R.id.main_fragment, new SettingsFragment()).commit();
        }

        overridePendingTransition(0, 0);
        initToolbar();
    }

    private void initToolbar() {
        mToolbar.setTitle(R.string.title_activity_settings);
        mToolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SettingsActivity.this.finish();
            }
        });
    }
}
