package com.thoughtworks.jieshuquan.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.thoughtworks.jieshuquan.R;
import com.thoughtworks.jieshuquan.service.model.Book;
import com.thoughtworks.jieshuquan.viewholder.PopularViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by leihuang on 7/10/15.
 */
public class PopularAdapter extends BaseAdapter {

    private Context mContext;
    private List<Book> mList;

    public PopularAdapter(Context context) {
        this.mContext = context;
        this.mList = new ArrayList<>();
    }

    public void setList(List<Book> list) {
        this.mList = list;
    }

    @Override
    public int getCount() {
        return mList == null ? 0 : mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.popular_list_item, parent, false);
            convertView.setTag(new PopularViewHolder(convertView));
        }
        Book book = mList.get(position);
        ((PopularViewHolder) convertView.getTag()).populate(book, mContext);
        return convertView;
    }

}