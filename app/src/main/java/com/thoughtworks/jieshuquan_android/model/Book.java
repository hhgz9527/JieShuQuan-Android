package com.thoughtworks.jieshuquan_android.model;

import com.avos.avoscloud.AVClassName;
import com.avos.avoscloud.AVObject;
import com.thoughtworks.jieshuquan_android.Constants;

/**
 * Created by leihuang on 6/10/15.
 */

@AVClassName("Book")

public class Book extends AVObject {

    public String getBookDoubanId() {
        return getString(Constants.KBOOK_DOUBANID);
    }

    public void setBookDoubanId(String bookDoubanId) {
        put(Constants.KBOOK_DOUBANID, bookDoubanId);
    }


    public String getBookName() {
        return getString(Constants.KBOOK_NAME);
    }

    public void setBookName(String bookName) {
        put(Constants.KBOOK_NAME, bookName);
    }


    public String getBookAuthor() {
        return getString(Constants.KBOOK_AUTHOR);
    }

    public void setBookAuthor(String bookAuthor) {
        put(Constants.KBOOK_AUTHOR, bookAuthor);
    }


    public String getBookImageHref() {
        return getString(Constants.KBOOK_IMAGE_HREF);
    }

    public void setBookImageHref(String bookImageHref) {
        put(Constants.KBOOK_IMAGE_HREF, bookImageHref);
    }

}
