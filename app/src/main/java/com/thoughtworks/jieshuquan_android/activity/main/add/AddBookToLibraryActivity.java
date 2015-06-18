package com.thoughtworks.jieshuquan_android.activity.main.add;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.SaveCallback;
import com.bumptech.glide.Glide;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.thoughtworks.jieshuquan_android.BuildConfig;
import com.thoughtworks.jieshuquan_android.Constants;
import com.thoughtworks.jieshuquan_android.R;
import com.thoughtworks.jieshuquan_android.model.Book;
import com.thoughtworks.jieshuquan_android.service.BookService;

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
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_book_to_library, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void getBookInfoFromDouBan(String ibns) {

        String urlString = BuildConfig.DOUBAN_SERVER_URL + ibns;
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("apikey", BuildConfig.DOUBAN_SERVER_API_KEY);

        client.get(urlString, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                if (statusCode != 200) {
                    // TODO: show network error
                    return;
                }

                mBook = new Book();
                mBook.setBookDoubanId(response.optString(Constants.K_DOUBANBOOK_ID));
                mBook.setBookName(response.optString(Constants.K_DOUBANBOOK_TITLE));
                mBook.setBookAuthor(response.optJSONArray(Constants.K_DOUBANBOOK_AUTHOR).toString());
                mBook.setBookImageHref(response.optString(Constants.K_DOUBANBOOK_IMAGE));

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

                    Toast.makeText(getApplicationContext(), R.string.add_to_library_success, Toast.LENGTH_SHORT).show();
                    finish();
                } else if (e.getCode() == -1) {
                    Toast.makeText(getApplicationContext(), R.string.add_to_library_already_have, Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), R.string.common_http_error, Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
