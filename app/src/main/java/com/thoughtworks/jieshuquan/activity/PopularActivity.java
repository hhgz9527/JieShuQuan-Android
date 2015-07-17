package com.thoughtworks.jieshuquan.activity;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.FindCallback;
import com.thoughtworks.jieshuquan.R;
import com.thoughtworks.jieshuquan.adapter.PopularAdapter;
import com.thoughtworks.jieshuquan.service.BookService;
import com.thoughtworks.jieshuquan.service.model.Book;

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
        mToolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopularActivity.this.finish();
            }
        });
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
