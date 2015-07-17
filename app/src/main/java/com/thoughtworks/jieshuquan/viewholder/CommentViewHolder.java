package com.thoughtworks.jieshuquan.viewholder;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.thoughtworks.jieshuquan.R;
import com.thoughtworks.jieshuquan.service.model.BookComment;

import java.text.SimpleDateFormat;
import java.util.Locale;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class CommentViewHolder {

    @InjectView(R.id.user_icon)
    ImageView mUserIcon;
    @InjectView(R.id.user_name)
    TextView mUserName;
    @InjectView(R.id.comment_time)
    TextView mCommentTime;
    @InjectView(R.id.comment_content)
    TextView mCommentContent;

    private Context mContext;

    public CommentViewHolder(Context context, View view) {
        mContext = context;
        ButterKnife.inject(this, view);
    }

    public void populate(BookComment bookComment) {
        Glide.with(mContext).load(bookComment.getUserIconUrl())
                .placeholder(R.drawable.avatar_placeholder).crossFade().into(mUserIcon);
        mUserName.setText(bookComment.getUserName());
        mCommentTime.setText(new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.US).format(bookComment.getCommentTime()));
        mCommentContent.setText(bookComment.getCommentContent());
    }
}
