package com.thoughtworks.jieshuquan.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.thoughtworks.jieshuquan.R;
import com.thoughtworks.jieshuquan.service.model.BookEntity;
import com.thoughtworks.jieshuquan.viewholder.BookEntityItemHolder;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

/**
 * Created by leihuang on 7/17/15.
 */
public class BookEntitysAdapter extends BaseAdapter {
    private Context mContext;
    private List<BookEntity> mBookList;

    public BookEntitysAdapter(Context c) {
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
        BookEntityItemHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.book_item, parent, false);
            holder = new BookEntityItemHolder(mContext, convertView);
            convertView.setTag(holder);
        }
        BookEntity bookEntity = mBookList.get(position);
        ((BookEntityItemHolder)convertView.getTag()).populate(bookEntity);
        return convertView;
    }

    public void setBookList(List<BookEntity>  bookList) {
        this.mBookList = bookList;
    }

}