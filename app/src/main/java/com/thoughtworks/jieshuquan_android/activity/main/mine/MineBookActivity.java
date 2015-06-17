package com.thoughtworks.jieshuquan_android.activity.main.mine;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;
import com.thoughtworks.jieshuquan_android.Constants;
import com.thoughtworks.jieshuquan_android.R;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MineBookActivity extends AppCompatActivity {

    private static final String TAG = MineBookActivity.class.getSimpleName();
    @InjectView(R.id.toolbar)
    Toolbar mToolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine_books);
        ButterKnife.inject(this);

        initToolbar();
        initData();
    }

    private void initData() {
        AVUser user = AVUser.getCurrentUser();
        AVQuery<AVObject> query = new AVQuery<>(Constants.BOOK_ENTITY);
        query.whereEqualTo(Constants.KBOOKENTITY_USER, user);
        query.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                if (e == null) {
                    Log.d(TAG, list.toString());
                }
            }
        });
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
