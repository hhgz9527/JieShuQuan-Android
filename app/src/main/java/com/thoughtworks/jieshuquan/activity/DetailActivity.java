package com.thoughtworks.jieshuquan.activity;

import android.content.Intent;
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
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.DeleteCallback;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.GetCallback;
import com.avos.avoscloud.SaveCallback;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.thoughtworks.jieshuquan.Constants;
import com.thoughtworks.jieshuquan.R;
import com.thoughtworks.jieshuquan.adapter.BookCommentsAdapter;
import com.thoughtworks.jieshuquan.service.BookService;
import com.thoughtworks.jieshuquan.service.DoubanService;
import com.thoughtworks.jieshuquan.service.model.Book;
import com.thoughtworks.jieshuquan.service.model.BookComment;
import com.thoughtworks.jieshuquan.service.model.BookEntity;
import com.thoughtworks.jieshuquan.viewholder.BookDetailViewHolder;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

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

    private BookDetailViewHolder mBookDetailViewHolder;
    private BookCommentsAdapter mBookCommentsAdapter;

    private BookEntity mBookEntity;
    private Book mBook;
    private int mType; // type 1 from book list   2,my book

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.inject(this);
        mType = getIntent().getIntExtra(Constants.KBOOK_TYPE, 0);
        if (mType == Constants.K_TYPE_MYBOOK) {
            String bookEntityId = getIntent().getStringExtra(Constants.KBOOK_ENTITY_ID);
            loadBookEntityData(bookEntityId);
        } else if (mType == Constants.K_TYPE_BOOK_LIST) {
            String bookId = getIntent().getStringExtra(Constants.KBOOK_ID);
            loadBookDate(bookId);
        }

        initViews();
    }

    private void initViews() {
        initToolbar();
        mBookCommentsAdapter = new BookCommentsAdapter(this);
        mCommentsListView.setAdapter(mBookCommentsAdapter);
        mDetailHeaderView = LayoutInflater.from(this).inflate(R.layout.book_detail_header, mCommentsListView, false);
        mCommentsListView.addHeaderView(mDetailHeaderView);
        mBookDetailViewHolder = new BookDetailViewHolder(mType, this, new BookDetailViewHolder.CallBack() {
            @Override
            public void onChangeState(String bookId) {
                boolean bookStatus = mBookEntity.isBookAvailability();
                BookService.getInstance().instance.updateBookAvailability(mBookEntity, !bookStatus, new SaveCallback() {
                    @Override
                    public void done(AVException e) {
                        if (e == null) {
                            mBookDetailViewHolder.changeBookStatus(mBookEntity.isBookAvailability());
                            Toast.makeText(getApplicationContext(), R.string.update_info_success, Toast.LENGTH_SHORT).show();

                        } else {
                            Toast.makeText(getApplicationContext(), R.string.common_http_error, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }

            @Override
            public void onBorrow(String bookId) {

                String book = bookId;
                System.out.print(book);
                //Toast.makeText(getApplicationContext(), R.string.common_http_error, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onDelete(String bookId) {
                mBookEntity.deleteInBackground(new DeleteCallback() {
                    @Override
                    public void done(AVException e) {
                        if (e == null) {
                            Toast.makeText(getApplicationContext(), R.string.book_detail_delete_success, Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent();
                            setResult(Constants.BOOK_DETAIL_ACTIVITY_RESULT_TAG, intent);
                            finish();
                        } else {
                            Toast.makeText(getApplicationContext(), R.string.common_http_error, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }, mDetailHeaderView);

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

    private void loadBookEntityData(String bookEntityId) {
        if (bookEntityId != null) {
            final AVQuery<BookEntity> query = new AVQuery<>("BookEntity");
            query.whereEqualTo(Constants.KOBJECT_ID, bookEntityId);
            query.findInBackground(new FindCallback<BookEntity>() {
                @Override
                public void done(List<BookEntity> list, AVException e) {
                    if (e == null && list.size() > 0) {
                        mBookEntity = list.get(0);
                        // get BookInfo
                        if (mBookEntity != null) {
                            mBook = mBookEntity.getBook();
                            mBook.fetchIfNeededInBackground(new GetCallback<AVObject>() {
                                @Override
                                public void done(AVObject avObject, AVException e) {
                                    if (e == null) {
                                        mBook = (Book) avObject;
                                        mBookDetailViewHolder.populate(mBook, mBookEntity.isBookAvailability());
                                        loadBookComments();
                                    } else {
                                        Toast.makeText(getApplicationContext(), R.string.common_http_error, Toast.LENGTH_SHORT).show();
                                        finish();
                                    }
                                }
                            });
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), R.string.common_http_error, Toast.LENGTH_SHORT).show();
                        finish();

                    }
                }
            });
        }

    }

    private void loadBookDate(String bookId) {
        if (bookId != null) {
            final AVQuery<Book> query = new AVQuery<>("Book");
            query.whereEqualTo(Constants.KOBJECT_ID, bookId);
            query.findInBackground(new FindCallback<Book>() {
                @Override
                public void done(List<Book> list, AVException e) {
                    if (e == null && list.size() > 0) {
                        mBook = list.get(0);
                        BookService.getInstance().fetchAllAvaliableBookEntities(mBook, new FindCallback() {
                            @Override
                            public void done(List list, AVException e) {
                                if (e == null) {
                                    mBookDetailViewHolder.populate(mBook, list.size() > 0);
                                } else {
                                    Toast.makeText(getApplicationContext(), R.string.common_http_error, Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                            }
                        });
                    } else {
                        Toast.makeText(getApplicationContext(), R.string.common_http_error, Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }
            });
        }
    }

    private void loadBookComments() {
        if (mBook != null) {
            String bookId = mBook.getBookDoubanId();
            DoubanService.getInstance().getBookComments(bookId, 0, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    if (statusCode != 200) {
                        Toast.makeText(getApplicationContext(), R.string.common_http_error, Toast.LENGTH_SHORT).show();
                        return;
                    }
                    try {
                        JSONArray reviewsArray = response.getJSONArray("reviews");
                        List<BookComment> bookCommentList = new ArrayList<BookComment>();

                        for (int i = 0; i < reviewsArray.length(); i++) {
                            JSONObject jsonObject = reviewsArray.getJSONObject(i);
                            JSONObject userInfo = jsonObject.getJSONObject("author");

                            BookComment bookComment = new BookComment(mBook.getBookDoubanId(),
                                    userInfo.getString("name"),
                                    userInfo.getString("avatar"),
                                    jsonObject.getString("published"),
                                    jsonObject.getString("summary"));
                            bookCommentList.add(bookComment);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            });
        }
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
