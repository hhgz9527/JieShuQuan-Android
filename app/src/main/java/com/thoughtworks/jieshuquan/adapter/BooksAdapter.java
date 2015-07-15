package com.thoughtworks.jieshuquan.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.thoughtworks.jieshuquan.R;
import com.thoughtworks.jieshuquan.model.BookItem;
import com.thoughtworks.jieshuquan.viewholder.BookItemHolder;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

public class BooksAdapter extends BaseAdapter {

    private Context mContext;
    private List<BookItem> mBookList;

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
        BookItem book = mBookList.get(position);
        ((BookItemHolder)convertView.getTag()).populate(book);
        return convertView;
    }

    public void setBookList(List<BookItem> bookList) {
        this.mBookList = bookList;
    }

}
