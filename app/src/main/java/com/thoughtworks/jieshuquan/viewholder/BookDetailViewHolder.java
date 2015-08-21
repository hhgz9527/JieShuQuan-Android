package com.thoughtworks.jieshuquan.viewholder;

import android.content.Context;
import android.graphics.Typeface;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.thoughtworks.jieshuquan.Constants;
import com.thoughtworks.jieshuquan.R;
import com.thoughtworks.jieshuquan.service.model.Book;

import java.util.Locale;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class BookDetailViewHolder {

    public interface CallBack {
        void onChangeState(String bookId);
        void onDelete(String bookId);
        void onBorrow(String bookId);
    }

    @InjectView(R.id.book_image)
    ImageView mBookImage;
    @InjectView(R.id.book_name)
    TextView mBookName;
    @InjectView(R.id.book_state)
    TextView mBookState;
    @InjectView(R.id.book_auther)
    TextView mBookAuther;
    @InjectView(R.id.book_press)
    TextView mBookPress;
    @InjectView(R.id.tab_change_state)
    ViewGroup mTabChangeState;
    @InjectView(R.id.tab_delte)
    ViewGroup mTabDelete;

    @InjectView(R.id.book_detail_change_textView)
    TextView mChangeText;

    @InjectView(R.id.book_detail_delete_textView)
    TextView mDeleteText;

    private Context mContext;
    private CallBack mCallBack;
    private int mType;

    public BookDetailViewHolder(int type, Context context, CallBack callBack, View view) {
        mContext = context;
        mCallBack = callBack;
        mType = type;
        ButterKnife.inject(this, view);

        if (mType == Constants.K_TYPE_BOOK_LIST){
            mChangeText.setText(R.string.book_detail_borrowlist);
            mDeleteText.setVisibility(View.INVISIBLE);
            mTabDelete.setEnabled(false);
        }
    }

    public void populate(Book book, boolean canBorrow) {
        Glide.with(mContext).load(book.getBookImageHref())
                .placeholder(R.drawable.book_placeholder).crossFade().into(mBookImage);
        mBookName.setText(book.getBookName());
        final String autherFmt = mContext.getString(R.string.book_detail_author_fmt);
        mBookAuther.setText(String.format(Locale.US, autherFmt, book.getBookAuthor()));
        final String pressFmt = mContext.getString(R.string.book_detail_press_fmt);
        mBookPress.setText(String.format(Locale.US, pressFmt, book.getBookPress()));
        populateState(book.getBookDoubanId(), canBorrow);
    }

    public void changeBookStatus(boolean available){
        SpannableString spannableString = createStateSpanable(available);
        mBookState.setText(spannableString);
    }

    private void populateState(final String bookId, final boolean available) {
        SpannableString spannableString = createStateSpanable(available);
        mBookState.setText(spannableString);

        if (mType == Constants.K_TYPE_BOOK_LIST) {
            mTabChangeState.setEnabled(available);
        }
        mTabChangeState.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mType == Constants.K_TYPE_BOOK_LIST){
                    mCallBack.onBorrow(bookId);
                } else if (mType == Constants.K_TYPE_MYBOOK) {
                    mCallBack.onChangeState(bookId);
                }
            }
        });


        mTabDelete.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mCallBack.onDelete(bookId);
            }
        });
    }

    private SpannableString createStateSpanable(boolean available) {
        String state = null;
        int stateColor = 0;
        if (available) {
            state = mContext.getString(R.string.state_available);
            stateColor = mContext.getResources().getColor(R.color.state_available);
        } else {
            state = mContext.getString(R.string.state_disable);
            stateColor = mContext.getResources().getColor(R.color.state_disable);
        }
        final String stateFmt = mContext.getString(R.string.book_detail_state_fmt);
        String displayState = String.format(Locale.US, stateFmt, state);
        int startIndex = displayState.toLowerCase(Locale.US).indexOf(state.toLowerCase(Locale.US));
        SpannableString spannableString = new SpannableString(displayState);
        spannableString.setSpan(new StyleSpan(Typeface.NORMAL), 0, startIndex + state.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new StyleSpan(Typeface.BOLD), startIndex, displayState.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new ForegroundColorSpan(stateColor), startIndex, displayState.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannableString;
    }
}
