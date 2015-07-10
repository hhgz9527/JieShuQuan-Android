package com.thoughtworks.jieshuquan_android.activity.main.borrow;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.thoughtworks.jieshuquan_android.R;
import com.thoughtworks.jieshuquan_android.adapter.PopularAdapter;
import com.thoughtworks.jieshuquan_android.service.BookService;
import com.thoughtworks.jieshuquan_android.service.model.Book;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class PopularActivity extends ActionBarActivity {

    public PopularAdapter adapter;
    public List<Book> books;

    @InjectView(R.id.popularListView)
    ListView popularListView;

    @InjectView(R.id.toolbar)
    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popular);
        ButterKnife.inject(this);

        mToolbar.setTitle(R.string.title_activity_popular);
        setSupportActionBar(mToolbar);

        adapter = new PopularAdapter(this);
        popularListView.setAdapter(adapter);

        this.getBooksFromService();

        popularListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //TODO: show the item detail
            }
        });
    }


    public void getBooksFromService() {

        BookService.getInstance().fetchRecoBooks(new FindCallback() {
            @Override
            public void done(List list, AVException e) {
                books = list;
                adapter.setList(books);
                adapter.notifyDataSetChanged();
            }
        });
    }
}
