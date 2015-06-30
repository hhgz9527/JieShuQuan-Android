package com.thoughtworks.jieshuquan_android.viewholder;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.thoughtworks.jieshuquan_android.R;
import com.thoughtworks.jieshuquan_android.model.Book;

import butterknife.ButterKnife;
import butterknife.InjectView;

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
                .placeholder(R.drawable.s1086045).crossFade().into(mImage);
        mNameText.setText(book.getBookName());
    }
}
