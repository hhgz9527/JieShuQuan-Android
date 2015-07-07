package com.thoughtworks.jieshuquan_android.converter;

import com.thoughtworks.jieshuquan_android.model.BookItem;
import com.thoughtworks.jieshuquan_android.service.model.Book;
import com.thoughtworks.jieshuquan_android.service.model.BookEntity;

public class BookItemConverter {

    public BookItem convert(Book book) {
        BookItem item = new BookItem();
        item.setBookId(book.getBookDoubanId());
        item.setBookName(book.getBookName());
        item.setBookImageHref(book.getBookImageHref());
        return item;
    }

    public BookItem convert(BookEntity entity) {
        BookItem item = new BookItem();
        item.setBookName(entity.getBookName());
        item.setBookImageHref(entity.getBookImageHref());
        return item;
    }
}
