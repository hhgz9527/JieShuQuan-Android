package com.thoughtworks.jieshuquan.viewholder;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.thoughtworks.jieshuquan.R;
import com.thoughtworks.jieshuquan.service.model.Book;
import com.thoughtworks.jieshuquan.service.model.BookEntity;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by leihuang on 7/17/15.
 */
public class BookItemHolder {

    @InjectView(R.id.book_image)
    ImageView mImage;
    @InjectView(R.id.book_name)
    TextView mNameText;
    private Context mContext;

    public BookItemHolder(Context context, View view) {
        mContext = context;
        ButterKnife.inject(this, view);
    }

    public void populate(Book book) {
        Glide.with(mContext).load(book.getBookImageHref())
                .placeholder(R.drawable.book_placeholder).crossFade().into(mImage);
        mNameText.setText(book.getBookName());
    }
}
