package com.thoughtworks.jieshuquan.service.model;

import com.avos.avoscloud.AVClassName;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVUser;
import com.thoughtworks.jieshuquan.Constants;

/**
 * Created by leihuang on 6/12/15.
 */
@AVClassName("BookEntity")

public class BookEntity extends AVObject {

    public boolean isBookAvailability() {
        return getInt(Constants.KBOOK_AVAILABILITY) == 1 ? true : false;
    }

    public void setBookAvailability(boolean bookAvailability) {
        int value = 1;
        if (!bookAvailability)
            value = 0;
        put(Constants.KBOOK_AVAILABILITY, (value));

    }

    public String getBookName() {
        return getString(Constants.KBOOK_NAME);
    }

    public void setBookName(String bookName) {

        put(Constants.KBOOK_NAME, bookName);
    }

    public String getBookImageHref() {
        return getString(Constants.KBOOK_IMAGE_HREF);
    }

    public void setBookImageHref(String bookImageHref) {
        put(Constants.KBOOK_IMAGE_HREF, bookImageHref);
    }

    public Book getBook() {
        return getAVObject(Constants.KBOOKENTITY_BOOK);
    }

    public void setBook(Book book) {
        put(Constants.KBOOKENTITY_BOOK, book);
    }

    public AVUser getUser() {
        return getAVObject(Constants.KBOOKENTITY_USER);
    }

    public void setUser(AVUser user) {
        put(Constants.KBOOKENTITY_USER, user);
    }
}
