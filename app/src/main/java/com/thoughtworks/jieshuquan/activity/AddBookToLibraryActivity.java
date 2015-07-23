package com.thoughtworks.jieshuquan.activity;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.SaveCallback;
import com.bumptech.glide.Glide;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.thoughtworks.jieshuquan.Constants;
import com.thoughtworks.jieshuquan.R;
import com.thoughtworks.jieshuquan.service.model.Book;
import com.thoughtworks.jieshuquan.service.model.Discover;
import com.thoughtworks.jieshuquan.service.BookService;
import com.thoughtworks.jieshuquan.service.DoubanService;

import org.apache.http.Header;
import org.json.JSONObject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class AddBookToLibraryActivity extends ActionBarActivity {

    @InjectView(R.id.bookImageView)
    ImageView bookImageView;

    @InjectView(R.id.nameTextView)
    TextView nameTextView;

    @InjectView(R.id.borrowSwith)
    Switch borrowSwith;

    @InjectView(R.id.toolbar)
    Toolbar mToolbar;

    private String mIsbn;
    private Book mBook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book_to_library);
        ButterKnife.inject(this);
        Intent intent = getIntent();
        if (intent != null) {
            this.mIsbn = intent.getStringExtra("ISBN");
            this.getBookInfoFromDouBan(this.mIsbn);
        }

        mToolbar.setTitle(R.string.title_activity_add_book_to_library);
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddBookToLibraryActivity.this.finish();
            }
        });
    }

    private void getBookInfoFromDouBan(String ibns) {
        DoubanService doubanService = DoubanService.getInstance();

        doubanService.getBookInfoFromDouBan(ibns, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                if (statusCode != 200) {
                    Toast.makeText(getApplicationContext(), R.string.common_http_error, Toast.LENGTH_SHORT).show();
                    return;
                }

                mBook = new Book();
                mBook.setBookDoubanId(response.optString(Constants.K_DOUBANBOOK_ID));
                mBook.setBookName(response.optString(Constants.K_DOUBANBOOK_TITLE));
                mBook.setBookAuthor(response.optJSONArray(Constants.K_DOUBANBOOK_AUTHOR).toString());
                mBook.setBookImageHref(response.optString(Constants.K_DOUBANBOOK_IMAGE));
                mBook.setBookDescription(response.optString(Constants.K_DOUBANBOOK_DESCRIPTION));
                mBook.setBookPress(response.optString(Constants.K_DOUBANBOOK_PUBLISHER));

                String largeImagePath = response.optJSONObject(Constants.K_DOUBANBOOK_IMAGES).optString(Constants.K_DOUBANBOOK_LARGE);

                AddBookToLibraryActivity.this.showBookImagewithPath(largeImagePath);
                AddBookToLibraryActivity.this.showBookName(mBook.getBookName());
            }
        });
    }

    private void showBookImagewithPath(String imagePath) {
        Glide.with(this).load(imagePath).into(bookImageView);

    }

    private void showBookName(String name) {
        nameTextView.setText(name);
    }


    @OnClick(R.id.addBookButton)
    void addBookToLibrary() {

        Boolean canBorrow = borrowSwith.isChecked();
        BookService.getInstance().addBookToLibrary(mBook, canBorrow, new SaveCallback() {
            @Override
            public void done(AVException e) {
                if (e == null) {
                    // add success
                    AddBookToLibraryActivity.this.sendMessageToDiscoverView(mBook.getBookName());
                } else if (e.getCode() == -1) {
                    Toast.makeText(getApplicationContext(), R.string.add_to_library_already_have, Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), R.string.common_http_error, Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void sendMessageToDiscoverView(String bookName) {
        Discover discover = new Discover();
        discover.setUser(AVUser.getCurrentUser());
        discover.setBookName(bookName);
        discover.setType(Constants.DISCOVERTYPE_ADDBOOK);
        discover.saveInBackground(new SaveCallback() {
            @Override
            public void done(AVException e) {
                if (e == null) {
                    Toast.makeText(getApplicationContext(), R.string.add_to_library_success, Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
    }
}
