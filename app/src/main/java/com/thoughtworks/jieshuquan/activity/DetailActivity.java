package com.thoughtworks.jieshuquan.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.thoughtworks.jieshuquan.Constants;
import com.thoughtworks.jieshuquan.R;
import com.thoughtworks.jieshuquan.adapter.BookCommentsAdapter;
import com.thoughtworks.jieshuquan.model.BookDetail;
import com.thoughtworks.jieshuquan.viewholder.BookDetailViewHolder;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class DetailActivity extends AppCompatActivity {

    @InjectView(R.id.toolbar)
    Toolbar mToolbar;
    @InjectView(R.id.book_comments_all)
    TextView mCommenstAll;
    @InjectView(R.id.book_comments_progress_bar)
    ProgressBar mCommentsProgressBar;
    @InjectView(R.id.book_comments_all_container)
    ViewGroup mCommentsContainer;
    @InjectView(R.id.book_comments_list)
    ListView mCommentsListView;

    View mDetailHeaderView;

    private String mBookId;
    private BookDetailViewHolder mBookDetailViewHolder;
    private BookCommentsAdapter mBookCommentsAdapter;

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
        initToolbar();
        mBookCommentsAdapter = new BookCommentsAdapter(this);
        mCommentsListView.setAdapter(mBookCommentsAdapter);
        mDetailHeaderView = LayoutInflater.from(this).inflate(R.layout.book_detail_header, mCommentsListView, false);
        mCommentsListView.addHeaderView(mDetailHeaderView);
        mBookDetailViewHolder = new BookDetailViewHolder(this, mDetailHeaderView, new BookDetailViewHolder.CallBack() {
            @Override
            public void onChangeState(String bookId) {

            }

            @Override
            public void onDelete(String bookId) {

            }
        });
        BookDetail book = new BookDetail();
        book.setId(mBookId);
        book.setImageHref("http:\\/\\/img3.douban.com\\/mpic\\/s6523000.jpg");
        book.setAuther("ABC");
        book.setName("算法导论");
        book.setPress("机械工业出版社");
        book.setAvailable(true);
        mBookDetailViewHolder.populate(book);
    }

    private void initToolbar() {
        mToolbar.setTitle(R.string.title_activity_detail);
        mToolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DetailActivity.this.finish();
            }
        });
    }

    private void loadData() {

    }

    @OnClick(R.id.book_comments_all)
    void setAllComments() {
        new AsyncTask<Void, Void, Void>() {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                mCommenstAll.setVisibility(View.GONE);
                mCommentsProgressBar.setVisibility(View.VISIBLE);
            }

            @Override
            protected Void doInBackground(Void... params) {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                mCommentsContainer.setVisibility(View.GONE);
            }
        }.execute();
    }
}
