package com.thoughtworks.jieshuquan.mine;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.FindCallback;
import com.thoughtworks.jieshuquan.Constants;
import com.thoughtworks.jieshuquan.R;
import com.thoughtworks.jieshuquan.activity.DetailActivity;
import com.thoughtworks.jieshuquan.adapter.BookEntitysAdapter;
import com.thoughtworks.jieshuquan.service.BookService;
import com.thoughtworks.jieshuquan.service.model.BookEntity;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MineBookActivity extends AppCompatActivity {

    private static final String TAG = MineBookActivity.class.getSimpleName();

    @InjectView(R.id.toolbar)
    Toolbar mToolbar;
    @InjectView(R.id.books_grid_view)
    GridView mGridView;
    @InjectView(R.id.loading_view)
    ViewGroup mLoadingView;
    @InjectView(R.id.info_view)
    ViewGroup mInfoView;
    @InjectView(R.id.info_text)
    TextView mInfoText;

    private BookEntitysAdapter mBooksAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine_books);
        ButterKnife.inject(this);

        initToolbar();
        initViews();
        initData();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Constants.BOOK_DETAIL_ACTIVITY_RESULT_TAG) {
            initData();
        }

    }


    private void initData() {
        BookService.getInstance().fetchBookEntitiesForCurrentUser(new FindCallback<BookEntity>() {
            @Override
            public void done(List<BookEntity> list, AVException e) {
                if (e == null && list.size() > 0) {
                    showContentView();
                    mBooksAdapter.setBookList(list);
                    mBooksAdapter.notifyDataSetChanged();
                } else if (list == null || list.size() == 0) {
                    showInfoView(getString(R.string.msg_default_empty));
                } else if (e != null) {
                    showInfoView(e.getMessage());
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

    private void initViews() {
        mBooksAdapter = new BookEntitysAdapter(this);
        mGridView.setAdapter(mBooksAdapter);
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                BookEntity bookEntity = (BookEntity) parent.getItemAtPosition(position);
                Intent intent = new Intent(MineBookActivity.this, DetailActivity.class);
                intent.putExtra(Constants.KBOOK_ENTITY_ID, bookEntity.getObjectId());
                intent.putExtra(Constants.KBOOK_TYPE,Constants.K_TYPE_MYBOOK);
                startActivityForResult(intent, Constants.BOOK_DETAIL_ACTIVITY_RESULT_TAG);

            }
        });
        mGridView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (firstVisibleItem + visibleItemCount >= totalItemCount) {
//                    Integer[] books = {R.drawable.s1495029,R.drawable.s1106934,R.drawable.s1086045};
//                    Random random = new Random();
//                    int nextInt = random.nextInt(3);
//                    mBooksAdapter.mThumbIds.add(books[nextInt]);
//                    mBooksAdapter.notifyDataSetChanged();
                }
            }
        });
        showLoadingView();
    }

    private void showLoadingView() {
        mLoadingView.setVisibility(View.VISIBLE);
        mGridView.setVisibility(View.GONE);
        mInfoView.setVisibility(View.GONE);
    }

    private void showContentView() {
        mGridView.setVisibility(View.VISIBLE);
        mLoadingView.setVisibility(View.GONE);
        mInfoView.setVisibility(View.GONE);
    }

    private void showInfoView(String textInfo) {
        if (!TextUtils.isEmpty(textInfo)) {
            mInfoText.setText(textInfo);
        }
        mInfoView.setVisibility(View.VISIBLE);
        mLoadingView.setVisibility(View.GONE);
        mGridView.setVisibility(View.GONE);
    }

}
