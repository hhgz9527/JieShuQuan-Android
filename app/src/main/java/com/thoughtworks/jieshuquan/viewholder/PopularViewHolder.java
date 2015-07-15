package com.thoughtworks.jieshuquan.viewholder;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.GetCallback;
import com.bumptech.glide.Glide;
import com.thoughtworks.jieshuquan.R;
import com.thoughtworks.jieshuquan.service.model.Book;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by leihuang on 7/10/15.
 */
public class PopularViewHolder {

    @InjectView(R.id.bookImage)
    ImageView iconImageView;

    @InjectView(R.id.bookName)
    TextView nameTextView;

    @InjectView(R.id.bookAuthor)
    TextView authorTextView;

    @InjectView(R.id.bookDetail)
    TextView contentTextView;

    public PopularViewHolder(View view) {
        ButterKnife.inject(this, view);
    }

    public void populate(Book book, final Context context) {

        book.fetchIfNeededInBackground(new GetCallback<AVObject>() {
            @Override
            public void done(AVObject avObject, AVException e) {
                if (e == null) {
                    Book abook = (Book) avObject;
                    Glide.with(context).load(abook.getBookImageHref()).placeholder(R.drawable.book_placeholder).crossFade().into(iconImageView);
                    nameTextView.setText(abook.getBookName());
                    authorTextView.setText(abook.getBookAuthor());
                    contentTextView.setText(abook.getBookDescription());
                }
            }
        });
    }
}

