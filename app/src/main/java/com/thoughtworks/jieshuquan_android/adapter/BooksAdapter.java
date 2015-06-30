package com.thoughtworks.jieshuquan_android.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.thoughtworks.jieshuquan_android.R;
import com.thoughtworks.jieshuquan_android.model.Book;
import com.thoughtworks.jieshuquan_android.viewholder.BookItemHolder;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static com.thoughtworks.jieshuquan_android.utils.CommonUtils.dipToPx;

public class BooksAdapter extends BaseAdapter {

    private Context mContext;
    private List<Book> mBookList;

    public BooksAdapter(Context c) {
        mContext = c;
        mBookList = newArrayList();
    }

    public int getCount() {
        return mBookList.size();
    }

    public Object getItem(int position) {
        return mBookList.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        BookItemHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.book_item, parent, false);
            holder = new BookItemHolder(mContext, convertView);
            convertView.setTag(holder);
        }
        Book book = mBookList.get(position);
        ((BookItemHolder)convertView.getTag()).populate(book);
        return convertView;
    }

    public void setBookList(List<Book> bookList) {
        this.mBookList = bookList;
    }

}
