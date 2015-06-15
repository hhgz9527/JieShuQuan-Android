package com.thoughtworks.jieshuquan_android.model;

import com.avos.avoscloud.AVClassName;
import com.avos.avoscloud.AVObject;

/**
 * Created by leihuang on 6/10/15.
 */

@AVClassName("Book")

public class Book extends AVObject {

    public String getBookDoubanId() {
        return getString("bookDoubanId");
    }

    public void setBookDoubanId(String bookDoubanId) {
        put("bookDoubanId", bookDoubanId);
    }


    public String getBookName() {
        return getString("bookName");
    }

    public void setBookName(String bookName) {
        put("bookName", bookName);
    }


    public String getBookAuthor() {
        return getString("bookAuthor");
    }

    public void setBookAuthor(String bookAuthor) {
        put("bookAuthor", bookAuthor);
    }


    public String getBookImageHref() {
        return getString("bookImageHref");
    }

    public void setBookImageHref(String bookImageHref) {
        put("bookImageHref", bookImageHref);
    }

}
