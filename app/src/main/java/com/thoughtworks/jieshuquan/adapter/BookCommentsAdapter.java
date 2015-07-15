package com.thoughtworks.jieshuquan.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.thoughtworks.jieshuquan.R;
import com.thoughtworks.jieshuquan.model.CommentItem;
import com.thoughtworks.jieshuquan.viewholder.CommentViewHolder;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

public class BookCommentsAdapter extends BaseAdapter {

    private Context mContext;
    private List<CommentItem> mCommentList;

    public BookCommentsAdapter(Context context) {
        this.mContext = context;
        mCommentList = newArrayList();
    }

    @Override
    public int getCount() {
        return mCommentList.size();
    }

    @Override
    public Object getItem(int position) {
        return mCommentList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CommentViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.comments_list_item, parent, false);
            holder = new CommentViewHolder(mContext, convertView);
            convertView.setTag(holder);
        }
        holder = (CommentViewHolder) convertView.getTag();
        holder.populate((CommentItem) getItem(position));
        return convertView;
    }

    public void setCommentList(List<CommentItem> commentList) {
        this.mCommentList = commentList;
    }
}
