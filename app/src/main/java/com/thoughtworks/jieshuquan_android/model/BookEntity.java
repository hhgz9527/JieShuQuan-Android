package com.thoughtworks.jieshuquan_android.model;

import com.avos.avoscloud.AVClassName;
import com.avos.avoscloud.AVObject;

/**
 * Created by leihuang on 6/12/15.
 */
@AVClassName("BookEntity")

public class BookEntity extends AVObject {

    public boolean isBookAvailability() {
        return getBoolean("bookAvailability");
    }

    public void setBookAvailability(boolean bookAvailability) {
        int value = 1;
        if (!bookAvailability)
            value = 0;
        put("bookAvailability", (value));

    }

    public String getBookName() {
        return getString("bookName");
    }

    public void setBookName(String bookName) {

        put("bookName", bookName);
    }

    public String getBookImageHref() {
        return getString("getBookImageHref");
    }

    public void setBookImageHref(String bookImageHref) {
        put("bookImageHref", bookImageHref);
    }

}
