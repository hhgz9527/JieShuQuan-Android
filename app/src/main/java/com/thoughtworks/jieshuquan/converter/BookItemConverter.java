package com.thoughtworks.jieshuquan.converter;

import com.thoughtworks.jieshuquan.model.BookItem;
import com.thoughtworks.jieshuquan.service.model.Book;
import com.thoughtworks.jieshuquan.service.model.BookEntity;

public class BookItemConverter {

    public BookItem convert(Book book) {
        BookItem item = new BookItem();
        item.setId(book.getBookDoubanId());
        item.setName(book.getBookName());
        item.setImageHref(book.getBookImageHref());
        return item;
    }

    public BookItem convert(BookEntity entity) {
        BookItem item = new BookItem();
        item.setName(entity.getBookName());
        item.setImageHref(entity.getBookImageHref());
        return item;
    }
}
