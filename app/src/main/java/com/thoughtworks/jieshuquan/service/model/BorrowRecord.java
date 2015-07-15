package com.thoughtworks.jieshuquan.service.model;

import com.avos.avoscloud.AVClassName;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVUser;
import com.thoughtworks.jieshuquan.Constants;

/**
 * Created by leihuang on 7/7/15.
 */
@AVClassName("BorrowRecord")

public class BorrowRecord  extends AVObject {
    public String status;
    public String fromUserName;
    public String toUserName;
    public String bookName;
    public String bookImageHref;

    // pending, agreed, rejected, returned
    public String getStatus() {
        return getString(Constants.KBORROWRECORD_STATUS);
    }

    public void setStatus(String status) {
        put(Constants.KBORROWRECORD_STATUS, status);

    }

    public String getFromUserName() {
        return getString(Constants.KBORROWRECORD_FROM_USER_NAME);

    }

    public void setFromUserName(String fromUserName) {
        put(Constants.KBORROWRECORD_FROM_USER_NAME, fromUserName);

    }

    public String getToUserName() {
        return getString(Constants.KBORROWRECORD_TO_USER_NAME);

    }

    public void setToUserName(String toUserName) {
        put(Constants.KBORROWRECORD_TO_USER_NAME, toUserName);

    }

    public String getBookName() {
        return getString(Constants.KBOOKNAME);
    }

    public void setBookName(String bookName) {
        put(Constants.KBOOKNAME, bookName);
    }

    public String getBookImageHref() {
        return getString(Constants.KBORROWRECORD_BOOK_IMAGE_HREF);
    }

    public void setBookImageHref(String bookImageHref) {
        put(Constants.KBORROWRECORD_BOOK_IMAGE_HREF,bookImageHref);
    }


    public AVUser getToUser() {
        return getAVObject(Constants.KBORROWRECORD_TO_USER);
    }

    public void setToUser(AVUser toUser) {
        put(Constants.KBORROWRECORD_TO_USER,toUser);
    }

    public BookEntity getBookEntity() {
        return getAVObject(Constants.KBORROWRECORD_BOOK_ENTITY);
    }

    public void setBookEntity(BookEntity bookEntity) {
        put(Constants.KBORROWRECORD_BOOK_ENTITY,bookEntity);
    }

    public AVUser getFromUser() {
        return getAVObject(Constants.KBORROWRECORD_FROM_USER);
    }

    public void setFromUser(AVUser fromUser) {
        put(Constants.KBORROWRECORD_FROM_USER,fromUser);
    }

}
