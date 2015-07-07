package com.thoughtworks.jieshuquan_android.activity.main.mine;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;
import com.google.common.base.Function;
import com.google.common.collect.ImmutableList;
import com.thoughtworks.jieshuquan_android.Constants;
import com.thoughtworks.jieshuquan_android.R;
import com.thoughtworks.jieshuquan_android.activity.DetailActivity;
import com.thoughtworks.jieshuquan_android.adapter.BooksAdapter;
import com.thoughtworks.jieshuquan_android.converter.BookItemConverter;
import com.thoughtworks.jieshuquan_android.model.BookItem;
import com.thoughtworks.jieshuquan_android.service.BookService;
import com.thoughtworks.jieshuquan_android.service.model.BookEntity;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

import static com.google.common.collect.FluentIterable.from;

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

    private BooksAdapter mBooksAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine_books);
        ButterKnife.inject(this);

        initToolbar();
        initViews();
        initData();
    }

    private void initData() {
        BookService.getInstance().fetchBookEntitiesForCurrentUser(new FindCallback<BookEntity>() {
            @Override
            public void done(List<BookEntity> list, AVException e) {
                if (e == null && list.size() > 0) {
                    showContentView();
                    final BookItemConverter converter = new BookItemConverter();
                    ImmutableList<BookItem> bookItemList = from(list).transform(new Function<BookEntity, BookItem>() {
                        @Override
                        public BookItem apply(BookEntity input) {
                            return converter.convert(input);
                        }
                    }).toList();
                    mBooksAdapter.setBookList(bookItemList);
                    mBooksAdapter.notifyDataSetChanged();
                } else if (list.size() == 0) {
                    showInfoView(getString(R.string.msg_default_empty));
                } else if (e != null) {
                    showInfoView(e.getMessage());
                }
            }
        });

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

    private void initViews() {
        mBooksAdapter = new BooksAdapter(this);
        mGridView.setAdapter(mBooksAdapter);
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                BookItem book = (BookItem) parent.getItemAtPosition(position);
                Intent intent = new Intent(MineBookActivity.this, DetailActivity.class);
                intent.putExtra(Constants.EXTRA_BOOK_ID, book.getBookId());
                startActivity(intent);
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
